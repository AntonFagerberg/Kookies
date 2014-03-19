package models

import org.joda.time.LocalDateTime
import play.api.db._
import play.api.Play.current
import anorm._
import anorm.SqlParser._
import java.util.Date

case class Block(
  productName: String,
  start: LocalDateTime,
  end: LocalDateTime
)

object Block {
  val blockParser =
    get[String]("product_name") ~
    get[Date]("start") ~
    get[Date]("end") map {
      case productName ~ start ~ end =>
      Block(productName, new LocalDateTime(start), new LocalDateTime(end))
    }

  def all(): Seq[Block] = {
    DB.withConnection { implicit connection =>
      SQL(
        """
          |SELECT
          | *
          |FROM
          | block
        """.stripMargin
      ).as(
        blockParser *
      )
    }
  }

  def create(block: Block): Boolean = {
    DB.withConnection { implicit connection =>
      try {
        SQL(
          """
            |INSERT INTO
            | block
            |SET
            | product_name = {productName},
            | start = {start},
            | end = {end}
          """.stripMargin
        ).on(
          'productName -> block.productName,
          'start -> block.start.toString,
          'end -> block.end.toString
        ).executeUpdate() == 1
      } catch {
        case e: Exception => false
      }
    }
  }
}
