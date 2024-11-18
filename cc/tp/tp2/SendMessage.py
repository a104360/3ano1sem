from NMS_Agent import NMS_Agent

c = NMS_Agent("localhost",8080,1024)

c.registerOnServer()
"""
message = input()

while(True):
    c.sendMessage(message)
    if(message == 'shutdown') :
        break
    message = input()
    
"""