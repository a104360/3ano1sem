import socket

class AlertFlow:
    def __init__(self, serverAddress, serverPort):
        self.serverAddress = serverAddress
        self.serverPort = serverPort
        self.socket = socket.socket(socket.AF_INET, socket.SOCK_STREAM)

    def listen(self):
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

    def sendAlert(self, id, metrics):
        # Parse metrics into a string
        parsedMetrics = ";".join([f"{metric}={value}" for metric, value in metrics])
        message = f"{id}|{parsedMetrics}"

        try:
            # Attempt to send the message
            self.socket.send(message.encode())
            print(f"Sent alert: {message}")
        except (BrokenPipeError, ConnectionResetError):
            # Connection issues, handle reconnection
            print("Connection lost, attempting to reconnect...")
            self.reconnect()
            try:
                self.socket.send(message.encode())
                print(f"Sent alert after reconnect: {message}")
            except Exception as error:
                print(f"Failed to send alert after reconnect: {error}")
        except Exception as error:
            # General exception handling
            print(f"Failed to send alert: {error}")

    def reconnect(self):
        # Close existing socket and create a new one
        if self.socket is not None:
            self.socket.close()
        self.socket = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
        try:
            self.socket.connect((self.serverAddress, self.serverPort))
            print("Reconnected successfully.")
        except Exception as error:
            print(f"Failed to reconnect: {error}")
            self.socket = None  # Ensure socket is None if reconnection fails

    def endConnection(self):
        if self.socket is not None:
            self.socket.close()
            print("Connection closed.")