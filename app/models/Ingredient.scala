package models

import play.api.db._
import play.api.Play.current
import anorm._
import anorm.SqlParser._

case class Ingredient(
  name: String,
  quantity: Long
)

object Ingredient {
  val ingredientParser =
    get[String]("ingredient.name") ~
    get[Long]("ingredient.quantity") map {
      case name ~ quantity => Ingredient(name, quantity)
    }

  def all(): Seq[Ingredient] = {
    DB.withConnection { implicit connection =>
      SQL(
        """
          |SELECT
          | *
          |FROM
          | ingredient
        """.stripMargin
      ).as(
        ingredientParser *
      )
    }
  }
}
