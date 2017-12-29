package controllers

import javax.inject._

import play.api.libs.ws.WSClient
import play.api.mvc._
import services.{RERService, SlackService}

import scala.concurrent.{ExecutionContext, Future}
import scala.concurrent.ExecutionContext.Implicits.global

/**
  * This is a bot for Slack, for Lunatech, that monitors RER and is able to send traffic details to Slack.
  */
@Singleton
class HomeController @Inject()(cc: ControllerComponents,
                               ws: WSClient,
                               rerService: RERService,
                               slackService: SlackService
                              ) extends AbstractController(cc) {

  def index = Action {
    Ok(views.html.index())
  }

  // This is called by Slack when a user enters "/next_rer" on Slack
  def checkNextRER = Action {
    val result = rerService.nextRERHours
    Ok(result)
  }

  def sendMessage = Action {
    implicit request =>

      // Post a message to Slack to the Lunatech Channel
      slackService.postMessage("Test bot RER...")


      Ok(".... message posté ... ")

  }

}
