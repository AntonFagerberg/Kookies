package models

import play.api.db._
import play.api.Play.current
import anorm._
import anorm.SqlParser._

case class Customer(
  name: String,
  address: String
)

object Customer {
  val customerParser =
    get[String]("customer.name") ~
    get[String]("customer.address") map {
      case name ~ address => Customer(name, address)
    }

  def all(): Seq[Customer] = {
    DB.withConnection { implicit connection =>
      SQL(
        """
          |SELECT
          | *
          |FROM
          | customer
        """.stripMargin
      ).as(
        customerParser *
      )
    }
  }
}
