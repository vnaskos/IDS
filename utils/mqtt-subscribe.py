import paho.mqtt.client as mqttClient
import time
import json

#global variable for the state of the connection
Connected = False

def on_connect(client, userdata, flags, rc):
    if rc == 0:
        print("Connected to broker")
 
        global Connected
        Connected = True
    else:
        print("Connection failed")
 
def on_message(client, userdata, message):
    print("Message received: ", message.payload)

def on_publish(client,userdata,result):
    print("data published \n")
    pass

def start(): 
    broker_address= "localhost"  #Broker address
    port = 1883                  #Broker port
     
    client = mqttClient.Client("PythonDebugClient")
    client.on_connect= on_connect
    client.on_message= on_message
    client.on_publish = on_publish
    
    client.connect(broker_address, port=port)
    client.loop_start()
     
    while Connected != True:
        #Wait for connection
        time.sleep(0.1)
     
    client.subscribe("topic/default")
    
    
    try:
        while True:
            #ret=client.publish("topic/default","TestDATA")
            time.sleep(1)
    except KeyboardInterrupt:
        print("exiting")
        client.disconnect()
        client.loop_stop()

start()
