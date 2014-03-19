package models

import org.joda.time.{LocalDateTime, LocalDate}
import play.api.db._
import play.api.Play.current
import anorm._
import anorm.SqlParser._
import java.util.Date

case class Order(
  id: Int,
  customer: Customer,
  expectingDate: LocalDate,
  deliveryTimestamp: Option[LocalDateTime],
  loadBill: Option[LoadBill]
)
object Order {
  val orderParser =
    get[Int]("order.id") ~
    Customer.customerParser ~
    get[Date]("order.expecting_date") ~
    get[Option[Date]]("order.delivery_timestamp") ~
    LoadBill.loadBillParser.? map {
      case id ~ customer ~ expectingDate ~ deliveryTimestamp ~ loadBill =>
      Order(id, customer, new LocalDate(expectingDate), deliveryTimestamp.map(timestamp => new LocalDateTime(timestamp)), loadBill)
    }

}
