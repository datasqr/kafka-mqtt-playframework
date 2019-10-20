#!/bin/bash

source local-kafka/bin/activate && \
python3 -u local-mqtt-producer.py $1
