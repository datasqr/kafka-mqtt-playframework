package services

import javax.inject.{Inject, Singleton}
import akka.kafka.scaladsl.Consumer
import akka.kafka.{ConsumerSettings, Subscriptions}
import akka.stream.scaladsl.Source
import com.typesafe.config.{Config, ConfigFactory}
import org.apache.kafka.clients.consumer.ConsumerRecord
import org.apache.kafka.common.serialization.StringDeserializer
import play.api.Configuration

import scala.util.{Try}

trait Kafka {
  def source(topic: String): Try[Source[ConsumerRecord[String, String], _]]
}

@Singleton
class KafkaImpl @Inject() (configuration: Configuration) extends Kafka {

  private val config: Config = ConfigFactory.load()
  private final val kafkaurl = config.getString("kafka.url")
  private final val groupid = config.getString("kafka.groupid")

  def consumerSettings: Try[ConsumerSettings[String, String]] = {

      val deserializer = new StringDeserializer()
      val config = configuration.getOptional[Configuration]("akka.kafka.consumer").getOrElse(Configuration.empty)
      val groupId = groupid
      val consumerSettings = ConsumerSettings(config.underlying, deserializer, deserializer)
        .withBootstrapServers(kafkaurl)
        .withGroupId(groupId)

      Try(consumerSettings)
  }

  def source(topic: String): Try[Source[ConsumerRecord[String, String], _]] = {
    val topicWithPrefix = topic
    val subscriptions = Subscriptions.topics(topicWithPrefix)
    consumerSettings.map(Consumer.plainSource(_, subscriptions))
  }

}
