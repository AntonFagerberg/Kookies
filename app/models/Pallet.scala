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

object Pallet {
  val palletParser =
    get[Int]("pallet.id") ~
    Product.productParser ~
    get[Date]("pallet.production_timestamp") ~
    Order.orderParser.? ~
    get[Long]("blocked") map {
      case id ~ product ~ timestamp ~ order ~ block =>
      Pallet(id, product, new LocalDateTime(timestamp), order, block == 1l)
    }

  def create(productName: String): Option[Long] = {
    DB.withConnection { implicit connection =>
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

  def all(): Seq[Pallet] = {
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
        """.stripMargin
      ).as(
        palletParser *
      )
    }
  }
}
