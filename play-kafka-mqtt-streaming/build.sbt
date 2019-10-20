lazy val root = (project in file(".")).enablePlugins(PlayScala)

name := "kafka-mqtt-streaming"

scalaVersion := "2.12.4"

libraryDependencies ++= Seq(
  guice,
  "com.typesafe.akka" %% "akka-stream-kafka" % "1.1.0",
  "org.eclipse.paho" % "org.eclipse.paho.client.mqttv3" % "1.2.2",
  "com.lightbend.akka" %% "akka-stream-alpakka-mqtt" % "1.1.2",
  "org.scala-sbt" %% "compiler-bridge" % "1.3.1" % Test
)
