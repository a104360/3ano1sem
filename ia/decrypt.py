def dehash(str):
    password = ""
    file = open(str,"rb")
    content = file.read()
    content = content.decode()
    key = ord(content[0])
    #print(key)
    #print(content)
    for i in range(len(content)):
        if i == 0 : continue
        decryptedCharValue = (ord(content[i]) - key) % 126
        if decryptedCharValue < 48: decryptedCharValue = 126 - decryptedCharValue
        password += chr(decryptedCharValue)
    file.close()
    print(password)


dehash("./hash.exe")