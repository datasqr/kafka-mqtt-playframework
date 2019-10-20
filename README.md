# Run Kafka in a container for internal and external traffic

Below is a simple example on how to run dockerized kafka and Mqtt (here on docker compose but will be analogous on docker swarm or kubernetes). Kafka run in a way, that allows both, free traffic inside the clustes (container to container) and from external sources to the container (remote machine to container).

Kafka and Mqtt producer generate rundom data
Application in Playframework receive data and stream directly to the dashboard.

Make sure newest docker and at least python 3.3 is installed and available under **python** command.

Also, check what your distro is using for python virtual environments. In Arch it is **venv**, if you are using **virtualenv**, then modify "create-local-python.sh" script accordingly.



## set up all from folder /play-kafka-mqtt-streaming

```bash
$ docker-compose build
```

```bash
$ ./create-local-python.sh
```

## run docker and internal (dockerized) producer and consumer
```bash
$ docker-compose up
```

Give if up to 15 seconds (after images are downloaded). Produced and consumed messages 

## check ip of the kafka container

```
$ docker container ls
CONTAINER ID        IMAGE                      COMMAND                  CREATED             STATUS              PORTS                                            NAMES
fcab1ddcdf8a        wurstmeister/kafka         "start-kafka.sh"         5 minutes ago       Up 41 seconds       0.0.0.0:9094->9094/tcp                           kafka-mqtt-docker_kafka_1
7d3318630872        wurstmeister/zookeeper     "/bin/sh -c '/usr/sb…"   5 minutes ago       Up 42 seconds       22/tcp, 2181/tcp, 2888/tcp, 3888/tcp             kafka-mqtt-docker_zookeeper_1
7f0c279b8447        eclipse-mosquitto:latest   "/docker-entrypoint.…"   5 minutes ago       Up 43 seconds       0.0.0.0:1883->1883/tcp, 0.0.0.0:9001->9001/tcp   mosquitto
```



```bash
$ # assuming that kafka container is kafka-docker-internal-external_kafka_1
$ docker inspect -f '{{range .NetworkSettings.Networks}}{{.IPAddress}}{{end}}' fcab1ddcdf8a
172.21.0.4
$ docker inspect -f '{{range .NetworkSettings.Networks}}{{.IPAddress}}{{end}}' 7f0c279b8447
172.21.0.2
```

## run local producer 

#### assume that kafka ip is 1.2.3.4 and mqtt ip is 5.7.8.9

```bash
$ #assuming that kafka ip is 1.2.3.4
$ ./run-local-producer.sh 1.2.3.4 5.7.8.9
```

## run dashboard (folder /kafka-mqtt-docker)

#### import project to Intelij or Eclipse

## after test, remember to cleanup

```bash
$ docker-compose down
```

## Inspired by

* https://github.com/konradmalik/kafka-docker-internal-external
* https://github.com/camilosampedro/lights-on

