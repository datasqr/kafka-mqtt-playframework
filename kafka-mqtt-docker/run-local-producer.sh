#!/bin/bash

source local-kafka/bin/activate && \
python3 -u local-producer.py $1 && \
python3 -u local-mqtt-producer $2
