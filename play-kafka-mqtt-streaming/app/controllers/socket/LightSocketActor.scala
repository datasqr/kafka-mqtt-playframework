package controllers.socket

import akka.actor.{Actor, ActorRef, Props}
import model.Message

import scala.collection.mutable.ListBuffer

/**
  * LightSocketActor
  */


class LightSocketActor(out: ActorRef) extends Actor {

  override def receive: Receive = {
    case message: Message =>
      play.Logger.debug(s"Message: ${message.information}")
  }
}

object LightSocketActor {
  var list: ListBuffer[ActorRef] = ListBuffer.empty[ActorRef]
  def props(out: ActorRef): Props = {
    list += out
    Props(new LightSocketActor(out))
  }

  def sendMessage(message: Message): Unit = {
    list.foreach(_ ! message)
  }
}
