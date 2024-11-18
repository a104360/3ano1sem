import os

server = input("SERVER IP : ")

command = "iperf -c "
command += server 
command += " -u -p 8080 -t 1 " #| awk '/%)/ {print $7, $8, $9, $10, $11, $14}'"

#python3 iperfCliente.py | awk '/%)/ {print $7, $8, $9, $10, $11, $14}'
os.system("clear")
os.system(command)
