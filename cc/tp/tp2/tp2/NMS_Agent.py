import socket
import NetTask
import os
import subprocess

"""
    Esta classe deve poder medir :
        bandwith
        jitter (iperf)
        packet loss (iperf)
        latency (ping)

    iperf Tutorial:
        iperf -u -f B -o filename.* ... - protocolo udp, formato em Bytes,output para ficheiro 
"""

def removeNulls(text):
    while True:
        try:
            text = text.remove("")
        except Exception as e:
            break
    return text


class NMS_Agent:
    def __init__(self,serverAddress,serverPort,BUFFERSIZE,connections = {}):
        self.id = None
        self.serverAddress = serverAddress
        self.serverPort = serverPort
        self.BUFFERSIZE = BUFFERSIZE
        self.udp = NetTask.NetTask(serverAddress,serverPort,2,1024)#socket.socket(socket.AF_INET,socket.SOCK_DGRAM)
        self.tcp = socket.socket(socket.AF_INET,socket.SOCK_STREAM)
        self.connections = connections
        self.tasks = {}
        self.frequency = 0

    def sendMessage(self,message):
        self.udp = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
        self.udp.connect(('localhost', 8080))  # Connect to the server
        self.udp.send(message.encode())  # Send the message
        data = self.udp.recv(self.BUFFERSIZE)  # Receive response
        print("Received " + data.decode())
        self.udp.close()

    def registerOnServer(self):
        message = "R|"
        for connection in self.connections:
            message += connection
        print(message)
        op = input("Packet/File : ")
        if op == "File":
            print(self.udp.sendFile(input("Name the file to send : ")))
        else:
            print(self.udp.sendPacket(message))
    

    #def sendMetrics(self,metrics):
    #    message = f"{self.id}|M|"
    #    for m in metrics:
            
            

    # Retorna uma lista com 
    # 0 - bandwidth
    # 1 - jitter
    # 2 - packet loss (%)
    def getBandwidth(self):
        output = os.popen(f"iperf -c {self.serverAddress} -u -p {self.serverPort} -t {self.frequency}").read()

        # Separar output em linhas
        items = output.split("\n")

        # Remover os casos em que existem dois espaços consecutivos
        items.remove("")

        # Apenas obter a ultima linha
        final = items[-1]


        # Da ultima linha reter os elementos posteriores à posição 9
        final = final.split(" ")[11:]

        # Remover possiveis caracteres nulos
        while True:
            try:
                final.remove("")
            except:
                break


        # Remover o numero de pacotes enviados
        final.pop(len(final) - 2)
        final.pop(len(final) - 2)

        # Reconstruir a lista para ter apenas as entradas necessárias
        i = 0
        while i != 2:
            final[i] += final[i+1]
            final.pop(i+1)
            i += 1

        return final

    def getLatency(self,address):
        output = os.popen(f"ping {address} -c 3").read()

        # Separar output em linhas
        items = output.split("\n")

        # Remover os casos em que existem dois espaços consecutivos
        while True:
            try:
                items.remove("")
            except:
                break

        # Apenas obter as 2 primeiras linhas
        items.reverse()
        items = items[0:2]

        for a in items:
            print(f"{len(a)} : {a}")
        """

        # Da ultima linha reter os elementos posteriores à posição 9
        final = final.split(" ")[11:]

        # Remover possiveis caracteres nulos
        while True:
            try:
                final.remove("")
            except:
                break


        # Remover o numero de pacotes enviados
        final.pop(len(final) - 2)
        final.pop(len(final) - 2)

        # Reconstruir a lista para ter apenas as entradas necessárias
        i = 0
        while i != 2:
            final[i] += final[i+1]
            final.pop(i+1)
            i += 1


        """