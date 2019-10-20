package controllers

import akka.actor.ActorSystem
import akka.stream.Materializer
import javax.inject.{Inject, Singleton}
import play.api.mvc._

@Singleton
class HomeController @Inject()(implicit system: ActorSystem, materializer: Materializer) extends Controller {

  def index = Action { implicit request =>
    Ok(views.html.index())
  }

}
