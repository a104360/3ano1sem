import os

def removeNulls(text):
    while True:
        try:
            text.remove("")
        except:
            break
    return text

class Task:
    def __init__(self,id,bandwidth,jitter,packetLoss,latency):
        self.id = id
        self.bandwidth = bandwidth
        self.jitter = jitter
        self.packetLoss = packetLoss
        self.latency = latency


    def getBandwidth(address,port,time):
        output = os.popen(f"iperf -c {address} -u -p {port} -t {time}").read()
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
    
    # No caso de não existir conexão, a primeira string da lista não começa por "rtt..."
    def getLatency(self,address,timeout = 5,pings = 3):
        output = os.popen(f"ping {address} -w {timeout} -c {pings}").read()

        items = output.split("\n")

        items = removeNulls(items)

        items.reverse()

        items = items[0:2]

        return items


print(Task(1,True,True,True,True).getLatency("localhost"))

