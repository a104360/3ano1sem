import os
import NMS_Agent

server = input("SERVER IP : ")

command = "iperf -c "
command += server 
command += " -u -p 8080 -t 1 "#| tail -n 1"#awk '/%)/ {print $7, $8, $9, $10, $11, $14}'"

# Comando clear apenas para limpar o ecra
os.system("clear")

# Executar comando e obter output
output = os.popen(command).read()

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


print(final)
