#import NMS_Server
#
#import socket
#
#ipAddress = input("IP : ")
#
#s = NMS_Server.NMS_Server(ipAddress,8080,8081)
#
#option = input("What to do : ")
#
#if option == "udp":
#    s.listenUDP()
#else:
#    s.listen()


import UDP

server = UDP.UDP("localhost",8080,None,1024,"server_DB/")

server.startServer()