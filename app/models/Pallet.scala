package models

import org.joda.time.LocalDateTime
import play.api.db._
import play.api.Play.current
import anorm._
import anorm.SqlParser._
import java.util.Date

case class Pallet(
  id: Int,
  product: Product,
  timestamp: LocalDateTime,
  order: Option[Order],
  blocked: Boolean
)

case class PalletSearch(
  palletId: Option[Int] = None,
  productName: Option[String] = None,
  customerName: Option[String] = None,
  startTime: Option[LocalDateTime] = None,
  endTime: Option[LocalDateTime] = None
)

object Pallet {
  val palletParser =
    get[Int]("pallet.id") ~
    Product.productParser ~
    get[Date]("pallet.production_timestamp") ~
    Order.orderParser.? ~
    get[Long]("blocked") map {
      case id ~ product ~ timestamp ~ order ~ block =>
      Pallet(id, product, new LocalDateTime(timestamp), order, block > 0l)
    }

  def create(productName: String): Option[Long] = {
    DB.withTransaction { implicit connection =>
      val enoughIngredients =
        SQL(
          """
            |SELECT
            | COUNT(*)
            |FROM
            | product_ingredient
            |LEFT JOIN
            | ingredient
            |ON
            | ingredient.name = product_ingredient.ingredient_name
            |WHERE
            | 54 * product_ingredient.quantity > ingredient.quantity
            |AND
            | product_ingredient.product_name = {productName}
            |FOR UPDATE
          """.stripMargin
        ).on(
          'productName -> productName
        ).as(
          scalar[Long] single
        ) == 0l

      if (!enoughIngredients) {
        None
      } else {
        val updatedIngredients =
          SQL(
            """
              |UPDATE
              | ingredient
              |LEFT JOIN
              | product_ingredient
              |ON
              | ingredient.name = product_ingredient.ingredient_name
              |SET
              | ingredient.quantity = ingredient.quantity - (54 * product_ingredient.quantity)
              |WHERE
              | product_ingredient.product_name = {productName}
            """.stripMargin
          ).on(
            'productName -> productName
          ).executeUpdate() > 0

        if (!updatedIngredients) {
          None
        } else {
          SQL(
            """
              |INSERT INTO
              | pallet
              |SET
              | product_name = {productName}
            """.stripMargin
          ).on(
            'productName -> productName
          ).executeInsert()
        }
      }
    }
  }

  def search(search: PalletSearch): Seq[Pallet] = {
    DB.withConnection { implicit connection =>
      SQL(
        """
          |SELECT
          | *,
          | (
          |   SELECT
          |     COUNT(*)
          |   FROM
          |     block
          |   WHERE
          |     product.name = block.product_name
          |   AND
          |     production_timestamp BETWEEN block.start AND block.end
          |   LIMIT 1
          | ) blocked
          |FROM
          | pallet
          |INNER JOIN
          | product
          |ON
          | product.name = pallet.product_name
          |LEFT JOIN
          | `order`
          |ON
          | pallet.order_id = `order`.id
          |LEFT JOIN
          | customer
          |ON
          | customer.name = `order`.customer_name
          |LEFT JOIN
          | load_bill
          |ON
          | load_bill.id = `order`.load_bill_id
          |WHERE
          | (pallet.id = {palletId} OR {palletId} IS NULL)
          |AND
          | (customer.name = {customerName} OR {customerName} IS NULL)
          |AND
          | (product.name = {productName} OR {productName} IS NULL)
          |AND
          | (pallet.production_timestamp >= {startTime} OR {startTime} IS NULL)
          |AND
          | (pallet.production_timestamp <= {endTime} OR {endTime} IS NULL)
        """.stripMargin
      ).on(
        'palletId -> search.palletId,
        'customerName -> search.customerName,
        'productName -> search.productName,
        'startTime -> search.startTime.map(_.toString("YYYY-MM-dd HH:mm:ss")),
        'endTime -> search.endTime.map(_.toString("YYYY-MM-dd HH:mm:ss"))
      ).as(
        palletParser *
      )
    }
  }
}
