package services

import com.typesafe.config.{Config, ConfigFactory}
import controllers.socket.LightSocketActor
import model.Message
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence
import org.eclipse.paho.client.mqttv3.{IMqttDeliveryToken, MqttCallback, MqttClient, MqttMessage}

/**
  * Created by camilosampedro on 29/03/17.
  */
object SubscribeService {

  private val config: Config = ConfigFactory.load()
  private final val mqtturl = config.getString("mqtt.url")
  private final val mqtttopic = config.getString("mqtt.topic")

  //Set up persistence for messages
  val persistence = new MemoryPersistence

  val client = new MqttClient(mqtturl, MqttClient.generateClientId(), persistence)

  def subscribe(): Unit = {
    client.connect()

    client.subscribe(mqtttopic)

    val callback = new MqttCallback {
      override def deliveryComplete(token: IMqttDeliveryToken): Unit = {
        play.Logger.debug("Delivery complete!")
      }

      override def connectionLost(cause: Throwable): Unit = {
        play.Logger.debug("Connection to socket lost")
      }

      override def messageArrived(topic: String, message: MqttMessage): Unit = {
        LightSocketActor.sendMessage(Message(new String(message.getPayload)))
      }
    }

    client.setCallback(callback)

  }
}
