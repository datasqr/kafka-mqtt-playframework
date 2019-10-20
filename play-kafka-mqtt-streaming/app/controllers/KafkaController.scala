package controllers


import javax.inject._
import akka.stream.scaladsl.{Flow, Sink}
import play.api.mvc.{InjectedController, WebSocket}
import services.Kafka

import scala.concurrent.Future
import scala.util.{Failure, Success}
import com.typesafe.config.{Config, ConfigFactory}


@Singleton
class KafkaController @Inject() (kafka: Kafka) extends InjectedController {

  private val config: Config = ConfigFactory.load()
  private final val kafkatopic = config.getString("kafka.topic")

  def kafkastream = Action { implicit request =>
    Ok(views.html.kafkastream(routes.KafkaController.ws().webSocketURL()))
  }

  def ws = WebSocket.acceptOrResult[Any, String] { _ =>
    kafka.source(kafkatopic) match {
      case Failure(e) =>
        Future.successful(Left(InternalServerError("Could not connect to Kafka")))
      case Success(source) =>
        val flow = Flow.fromSinkAndSource(Sink.ignore, source.map(_.value))
        Future.successful(Right(flow))
    }
  }

}
