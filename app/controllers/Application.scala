package controllers

import play.api._
import play.api.mvc._
import play.api.data.Form
import play.api.data.Forms._
import org.joda.time.LocalDateTime
import org.joda.time.format.{DateTimeFormat, DateTimeFormatter}

object Application extends Controller {

  def index = Action {
    Ok(views.html.index())
  }

  def createPallet = Action { implicit request =>
    val form = Form("productName" -> nonEmptyText).bindFromRequest()

    if (!form.hasErrors) {
      println(models.Pallet.create(form.get))
    }

    Ok(views.html.index())
  }

  def createBlock = Action { implicit request =>
    val form = Form(
      mapping(
        "productName" -> nonEmptyText,
        "start" -> nonEmptyText.verifying(startString =>
          try {
            LocalDateTime.parse(startString, DateTimeFormat.forPattern("YYYY-MM-dd HH:mm"))
            true
          } catch {
            case e: Exception => false
          }
        ).transform(
          startString => LocalDateTime.parse(startString, DateTimeFormat.forPattern("YYYY-MM-dd HH:mm")),
          (startJoda: LocalDateTime) => startJoda.toString("YYYY-MM-dd HH:mm")
        ),
        "end" -> nonEmptyText.verifying(endString =>
          try {
            LocalDateTime.parse(endString, DateTimeFormat.forPattern("YYYY-MM-dd HH:mm"))
            true
          } catch {
            case e: Exception => false
          }
        ).transform(
          endString => LocalDateTime.parse(endString, DateTimeFormat.forPattern("YYYY-MM-dd HH:mm")),
          (startJoda: LocalDateTime) => startJoda.toString("YYYY-MM-dd HH:mm")
        )
      )(models.Block.apply)(models.Block.unapply).verifying(
        block => block.start.compareTo(block.end) < 0
      )
    ).bindFromRequest()

    if (!form.hasErrors) {
      models.Block.create(form.value.get)
    }

    Ok(views.html.index())
  }
}