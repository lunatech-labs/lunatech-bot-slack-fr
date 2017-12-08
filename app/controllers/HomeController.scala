package controllers

import javax.inject._

import play.api.libs.ws.WSClient
import play.api.mvc._

import scala.concurrent.{ExecutionContext, Future}
import scala.concurrent.ExecutionContext.Implicits.global

/**
  * This is a bot for Slack, for Lunatech, that monitors RER and is able to send traffic details to Slack.
  */
@Singleton
class HomeController @Inject()(cc: ControllerComponents, ws: WSClient) extends AbstractController(cc) {
  //
  //  @Inject
  //  val  wsClient:WSClient

  def index = Action {
    Ok(views.html.index())
  }

  def checkNextRER = Action {


    // Ici va chercher le prochain RER et ensuite

    Ok("Bonjour, le prochain RER est à 12h00")

  }

  def sendMessage = Action {
    // curl -X POST -H 'Content-type: application/json' --data '{"text":"Hello, World!"}' https://hooks.slack.com/services/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
    implicit request =>

      // WSClient

      Ok(".... message posté ... ")

  }

  def showHeaders = Action {
    implicit request =>
      val result = "IP Address origin:" + request.headers.get("X-Forwarded-For").getOrElse("Unknown")
      Ok(result)
  }

  def callMyself(url: String) = Action.async {
    implicit request =>
      ws.url(url).get().map {
        response =>
          Ok(s"Call Myself returned [${response.body}]")
      }
  }

}
