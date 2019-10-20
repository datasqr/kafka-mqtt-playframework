#!/bin/bash

python3 -m venv local-kafka && \
source local-kafka/bin/activate && \
pip3 install kafka-python && \
pip3 install paho-mqtt && \
deactivate
