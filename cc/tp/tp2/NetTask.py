import socket
import json


class NetTask:
    def __init__(self,ipaddress,port,maxsize):
        self.ipaddress = ipaddress
        self.port = port
        self.maxsize = maxsize
        self.socket = None

    def setSocket(self):
        self.socket = socket.socket(socket.AF_INET,socket.SOCK_DGRAM)
        print("Client received a socker")
    
    def sendMessage(self,message):
        if self.socket == None:
            self.setSocket()
            print("SOCKET SET")
        self.socket.sendto(message.encode(),(self.ipaddress,int(self.port)))
        print("MESSAGE SENT")
        #for i in range(0,len(message),1024):
        #   self.socket.send(message[i:i+1024].encode(),(self.ipaddress,self.port))

    def getReply(self):
        data, _ = self.socket.recvfrom(1024)
        if not data:  # Client closed the connection
            return None
        print("MESSAGE RECEIVED")
        message = data.decode()
        return message
    
    def close(self):
        self.socket.close()

            

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

import UDP

ip = input("INSERT IP ADDRESS : ")

client = UDP.UDP(ip,8080,None,1024)

#file = open("template.json","rb")
#message = "ABCD\n" * 1000
#print(message)

file = input("Insira o nome do ficheiro a enviar : ")

client.sendFile(file)