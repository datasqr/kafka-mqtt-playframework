from kafka import KafkaProducer
from kafka.errors import NoBrokersAvailable
import random
import json
import time
import sys

KAFKA_HOST = sys.argv[1]+':9094'
print('kafka host: ' + KAFKA_HOST)
try:
    producer = KafkaProducer(value_serializer=lambda v: json.dumps(v).encode('utf-8'),
        bootstrap_servers=KAFKA_HOST)
except NoBrokersAvailable:
    sys.exit('broker not available (yet?)')

if not producer.bootstrap_connected():
    sys.exit('not connected, restarting...')

try:
    print('producing')
    while True:
        
        valueTmp = random.randrange(1,11)
        currentTime = int(round(time.time()*1000))
        message = {"name":"kafka","value":valueTmp,"timestamp":currentTime}
        producer.send('test-local', value=message)
        producer.flush()
        print('produced  message_'+str(message))

        time.sleep(1)

except Exception as e:
    print(repr(e))
    producer.flush()
    producer.close()
    


