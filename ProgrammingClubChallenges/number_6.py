import random
import math


global_hashes = {}

def message_digest(input:str) -> str:
    hash_ref = []
    i = 0
    while i < 3:
        hash_ref.extend(get_rand_char(input))
        i += 1
    if input in global_hashes.keys():
        return global_hashes.get(input)
    else: 
        global_hashes[input] =  "".join([hasher(n) for n in hash_ref])
    return global_hashes.get(input)

def get_rand_char(input:str):
    return input[math.floor(random.random()*len(input))]

def hasher(input:str):
    sc = hex(ord(input)**math.floor(random.random()*10))
    return sc[len(sc)-3:len(sc)-1]



print(message_digest('OakleyWhitehouse'))
lowercase = 'abcdefghijklmnopqrstuvwxyz'

'''for i in range(26):
    for j in range(26):
        for k in range(26):
            print(message_digest(lowercase[i:i+1]+lowercase[j:j+1]+lowercase[k:k+1]))'''




