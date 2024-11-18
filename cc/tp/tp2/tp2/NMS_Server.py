import socket
import NetTask
import threading

class NMS_Server: 

    DISCONNECT_MESSAGE = "!DISCONNECT"

    def __init__(self,IPADDRESS,PORTTCP,PORTUDP,timeout = None):
        self.IPADDRESS = socket.gethostbyname(socket.gethostname())  #IPADDRESS
        self.PORTTCP = PORTTCP
        self.NetTask = NetTask.NetTask(IPADDRESS,PORTUDP,timeout)
        self.tcp = socket.socket(socket.AF_INET,socket.SOCK_STREAM)

    


    def listenAlertFlow(self):
        self.tcp.bind((self.IPADDRESS, self.PORTTCP))
        self.tcp.listen(1)
        print(f"Listening on {self.IPADDRESS}:{self.PORTTCP}")
        while True:
            (client_socket, address) = self.tcp.accept()
            print(f"Connected by {address}")
            try:
                while True:  # Keep connection alive to handle multiple messages
                    client_socket.send(b'Hello, client!')
                    data = client_socket.recv(1024)
                    if not data:  # Client closed the connection
                        break
                    message = data.decode()
                    if(message == self.DISCONNECT_MESSAGE):
                        print("BYE")
                        client_socket.close()
                        return
                    print(f"Received: {message}")
            except ConnectionResetError:
                print("Client disconnected abruptly.")
            finally:
                client_socket.close()
        
    def listenNetTask(self):
        self.udp.bind((self.IPADDRESS,self.PORTUDP))
        self.udp.listen()
        while(True):
            data,address =  self.udp.recvfrom(1024)
            print(f"Message received {data.decode}")