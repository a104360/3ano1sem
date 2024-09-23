import random

def hashing(str):
    key = int(random.random() * 10)
    if key == 0: key = 11
    print("The Key is : ",key)
    i = 0
    n = len(str)
    while i < n:
        c = ord(str[i])
        ce = (c + key) % 126
        if ce < 48: ce += 48
        str = str[:i] + chr(ce) + str[i+1:]
        #str = str[:i] + chr((c + key) % 52 + 71) + str[i + 1:]
        i = i + 1
    print("The Hashed key is : " + str)
    str = chr(key) + str
    try:
        file = open("hash.exe","xb")
    except:
        file = open("hash.exe","wb")
    file.write(str.encode("utf-8"))
    file.close()
    return str



password = input()
password = hashing(password)
print(password)
