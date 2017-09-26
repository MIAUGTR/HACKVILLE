'''
Created on 18 jun. 2017

@author: MariusMiau
'''

def f (valor, peso, W):
    dic=[]
    for (i, p) in enumerate(peso):
        peso = W-p
        if peso >= 0 and W >= peso:
            W -= p
            dic.append(i)
    print(dic)
    return h(dic, valor)

def h (dic, valor):
    res = 0
    for i in dic:
        res += valor[i]
    return res
    pass

S = [2, 3, 4, 5, 6]
valor = [0.333, 0.25, 1, 0.5, 0.16]
peso = [3, 4, 1, 2, 6]
W = 5

valor = sorted(valor,reverse=True)
peso = sorted(peso)

print('Conjunto: ', f(valor, peso, W))