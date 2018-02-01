package services


import com.google.inject.Inject
import play.api.Configuration
import play.api.http.ContentTypes
import play.api.libs.ws.WSClient
import play.mvc.Http.HeaderNames

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

/**
  * Service to send messages to Slack
  */
class SlackService @Inject()(val ws: WSClient, val configuration: Configuration) {
  val token = configuration.get[String]("slack.api.token")
  val lunatechChannel = configuration.get[String]("slack.api.lunatechChannel")

  /**
    * Will post a message to a direct message channel (channel ID starts with a D) with attachments.
    */
  def postMessage(message: String): Future[String] = {

    val requestBody = Map(
      "channel" -> Seq(lunatechChannel),
      "token" -> Seq(token),
      "text" -> Seq(message)
    )

    val response = doPost(configuration.get[String]("slack.api.postMessage.url"), requestBody)
    response.map(_.body)
  }

  private def doPost(url: String, requestBody: Map[String, Seq[String]]) = {
    ws.url(url)
      .addHttpHeaders(HeaderNames.CONTENT_TYPE -> ContentTypes.FORM)
      .post(requestBody)
  }

}
