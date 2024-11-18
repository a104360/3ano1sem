"""

import NMS_Server

ip = input("IP do servidor : ")

t1 = NetTask(ip,"8081",1024)

text = input("Nova mensagem : ")

while True:
    t1.sendMessage(text)
    if text == "shutdown":
        print("CONNECTION CLOSED")
        break
    message = t1.getReply()
    print("SERVER RESPONSE : " + message)
    text = input("Nova mensagem : ")

"""


#a = json.load(open("template.json","r"))

#print(a)

#print(type(a))

import NetTask
import AlertFlow
import NMS_Agent


#agent = NMS_Agent.NMS_Agent("localhost",8080,1024,)
#output = agent.collectMetric("")
#print(output)


#al = AlertFlow.AlertFlow("localhost",8080)

#al.listen()

#print("--- SERVER OFFLINE ---")

NMS_Agent.NMS_Agent("localhost",8080,1024).getLatency("localhost")



