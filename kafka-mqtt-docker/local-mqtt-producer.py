import os
import time
import sys
import paho.mqtt.client as mqtt
import json
import random

MQTT_HOST = sys.argv[1]

client = mqtt.Client()

client.connect(MQTT_HOST, 1883, 60)

client.loop_start()

try:
    while True:
        
        valueTmp = random.randrange(1,11)
        currentTime = int(round(time.time()*1000))
        message = {"name":"mqtt","value":valueTmp,"timestamp":currentTime}
        client.publish('test-local', json.dumps(message), 1)
        print('produced  message_'+str(message))

        time.sleep(1)

except KeyboardInterrupt:
    pass

client.loop_stop()
client.disconnect()
