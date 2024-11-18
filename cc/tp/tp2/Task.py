import os

def removeNulls(final):
    while True:
        try:
            final.remove("")
        except:
            break
    return final

class Task:
    def __init__(self,id,bandwidth,jitter,packetLoss,latency):
        self.id = id
        self.bandwidth = bandwidth
        self.jitter = jitter
        self.packetLoss = packetLoss
        self.latency = latency



    def getBandwidth(serverAddress,serverPort,time):

        output = os.popen(f"iperf -c {serverAddress} -u -p {serverPort} -t {time}").read()

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

        output = os.popen(f"ping serverAddress").read()



        return final
    
    def getLatency(self,address):
        output = os.popen(f"ping -c 3 {address}").read()

        output = output.split("\n")
        
        output = removeNulls(output)

        

        print(output)

Task(1,True,True,True,True).getLatency("localhost")