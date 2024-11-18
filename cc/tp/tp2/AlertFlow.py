import socket

class AlertFlow:
    def __init__(self,address,port):
        self.serverAddress = address
        self.serverPort = port
        self.socket = socket.socket(socket.AF_INET,socket.SOCK_STREAM)

    async def listen(self):
        self.socket.bind((self.serverAddress,self.serverPort))
        self.socket.listen()
        print(f"Server listening on {self.serverAddress}:{self.serverPort}...")
        
        while True:
            clientSocket, address = self.socket.accept()
            print(f"Connection established with {address}")
            
            try:
                while True:
                    data = clientSocket.recv(1024)
                    
                    if not data:
                        print(f"Connection closed by {address}")
                        break  # Exit loop if the client disconnects

                    try:
                        decoded_data = data.decode()
                        print(f"Received data: {decoded_data}")
                        # Process the decoded_data here, e.g., store or handle the message
                    except UnicodeDecodeError:
                        print("Received invalid data, skipping...")
                        continue  # Skip to the next iteration if decoding fails

            except Exception as e:
                print(f"Error with client {address}: {e}")
            finally:
                clientSocket.close()
                print(f"Connection with {address} closed.")

    # Arguments :   id : int with the number of identification of the device
    #               metrics : list of tuples (metric, value)   
    def sendAlert(self,id,metrics):
        # It is better to just parse the mtrics into something better for
        # parsing at the server
        parsedMetrics = ";".join([f"{metric}={value}"] for metric,value in metrics)
        
        # Now we apply the format to also have the ID of the device sending 
        # the alert 
        message = f"{id}|{parsedMetrics}"

        try:
            self.socket.send(message.encode())
        except Exception as error:
            print(f"{error}")
            if self.socket == None:
                self.socket = socket.socket(socket.AF_INET,socket.SOCK_STREAM)
            self.socket.connect((self.serverAddress,self.serverPort))
            self.socket.send(message.encode())


