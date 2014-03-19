package models

import play.api.db._
import play.api.Play.current
import anorm._
import anorm.SqlParser._

case class LoadBill(
  id: Int
)

object LoadBill {
  val loadBillParser =
    get[Int]("load_bill.id") map {
      id => LoadBill(id)
    }
}
