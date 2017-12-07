package controllers

import javax.inject._
import play.api.mvc._

/**
  * This is a bot for Slack, for Lunatech, that monitors RER and is able to send traffic details to Slack.
  */
@Singleton
class HomeController @Inject()(cc: ControllerComponents) extends AbstractController(cc) {
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

}
