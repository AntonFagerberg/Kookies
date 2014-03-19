package controllers

import play.api._
import play.api.mvc._
import play.api.data.Form
import play.api.data.Forms._
import org.joda.time.LocalDateTime
import org.joda.time.format.{DateTimeFormat, DateTimeFormatter}

object Application extends Controller {

  val palletForm = Form("productName" -> nonEmptyText)
  val searchForm = Form(
    mapping(
      "palletId" -> optional(number),
      "productName" -> optional(nonEmptyText),
      "customerName" -> optional(nonEmptyText),
      "start" -> optional(nonEmptyText).verifying(startString =>
        try {
          if (startString.isDefined)
            LocalDateTime.parse(startString.get, DateTimeFormat.forPattern("YYYY-MM-dd HH:mm"))
          true
        } catch {
          case e: Exception => false
        }
      ).transform(
        startString => startString.map(s => LocalDateTime.parse(s, DateTimeFormat.forPattern("YYYY-MM-dd HH:mm"))),
        (startJoda: Option[LocalDateTime]) => startJoda.map(_.toString("YYYY-MM-dd HH:mm"))
      ),
      "end" -> optional(nonEmptyText).verifying(startString =>
      try {
        if (startString.isDefined)
          LocalDateTime.parse(startString.get, DateTimeFormat.forPattern("YYYY-MM-dd HH:mm"))
        true
      } catch {
        case e: Exception => false
      }
    ).transform(
      startString => startString.map(s => LocalDateTime.parse(s, DateTimeFormat.forPattern("YYYY-MM-dd HH:mm"))),
      (startJoda: Option[LocalDateTime]) => startJoda.map(_.toString("YYYY-MM-dd HH:mm"))
    )
    )(models.PalletSearch.apply)(models.PalletSearch.unapply).verifying( search =>
      search.startTime -> search.endTime match {
        case (Some(start), Some(end)) => start.compareTo(end) < 0
        case _ => false
      }
    )
  )
  val blockForm = Form(
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
  )

  def index = Action {
    Ok(views.html.index(palletForm, blockForm, searchForm))
  }

  def createPallet = Action { implicit request =>
    val form = palletForm.bindFromRequest()

    if (!form.hasErrors && models.Pallet.create(form.get).isDefined) {
      Ok(views.html.index(form, blockForm, searchForm, Some("pallet was created!")))
    } else {
      Ok(views.html.index(form, blockForm, searchForm, Some("pallet could not be created - check ingredient supply!")))
    }

  }

  def createBlock = Action { implicit request =>
    val form = blockForm.bindFromRequest()

    if (!form.hasErrors && models.Block.create(form.value.get)) {
      Ok(views.html.index(palletForm, form, searchForm, Some("block was created!")))
    } else {
      Ok(views.html.index(palletForm, form, searchForm, Some("block could not be created!")))
    }

  }

  def search = Action { implicit request =>
    Ok(views.html.index(palletForm, blockForm, searchForm.bindFromRequest()))
  }
}