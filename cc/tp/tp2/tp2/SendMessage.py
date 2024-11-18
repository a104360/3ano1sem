"""from NMS_Agent import NMS_Agent

c = NMS_Agent(4096)

message = input()

while(True):
    c.sendMessage(message)
    if(message == 'shutdown') :
        break
    message = input()
"""

import AlertFlow


client = AlertFlow.AlertFlow("localhost",8080)

client.sendAlert("r1",[("jitter",0.8),("latency",3201)])

client.endConnection()

print("--- MESSAGE SENT ---")