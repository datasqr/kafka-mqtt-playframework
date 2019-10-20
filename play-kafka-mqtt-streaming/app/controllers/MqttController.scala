package controllers

import javax.inject.{Inject, Singleton}
import akka.actor.ActorSystem
import akka.stream.Materializer
import controllers.socket.LightSocketActor
import model.Message
import play.api.libs.streams.ActorFlow
import play.api.mvc._
import services.SubscribeService


@Singleton
class MqttController @Inject()(implicit system: ActorSystem, materializer: Materializer) extends Controller {

  SubscribeService.subscribe()

  def socket: WebSocket = WebSocket.accept[Message, Message] { _ =>
    ActorFlow.actorRef(out => LightSocketActor.props(out))
  }

  def mqttstream = Action { implicit request =>
    Ok(views.html.mqttstream(routes.MqttController.socket().webSocketURL()))
  }

}
