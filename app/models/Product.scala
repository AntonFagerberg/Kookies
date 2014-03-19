package models

import org.joda.time.LocalDateTime
import play.api.db._
import play.api.Play.current
import anorm._
import anorm.SqlParser._

case class Product(
  name: String
)

object Product {
  val productParser =
    get[String]("product.name") map {
      name => Product(name)
    }

  def all(): Seq[Product] = {
    DB.withConnection { implicit connection =>
      SQL(
        """
          |SELECT
          | name
          |FROM
          | product
        """.stripMargin
      ).as(
        productParser *
      )
    }
  }
}
