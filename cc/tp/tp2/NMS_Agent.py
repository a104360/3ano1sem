import socket
import UDP
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

class NMS_Agent:
    def __init__(self,serverAddress,serverPort,BUFFERSIZE,connections = {}):
        self.id = None
        self.serverAddress = serverAddress
        self.serverPort = serverPort
        self.BUFFERSIZE = BUFFERSIZE
        self.udp = UDP.UDP(serverAddress,serverPort,2,1024)#socket.socket(socket.AF_INET,socket.SOCK_DGRAM)
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
    #    message = "M|"
    #    for m in metrics:
            

    
    def collectMetric(self):
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

        