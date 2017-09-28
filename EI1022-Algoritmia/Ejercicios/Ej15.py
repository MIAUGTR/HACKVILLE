'''
Created on 27 sept. 2017

@author: mariu
'''


def cuadrados(lista):
    res = []
    for elem in lista:
        res.append(elem**2)
    return res

def first(n, iterador):
    for i, elem in enumerate(iterador):
        if i < n:
            yield elem
        else:
            break
            
def filter(cond, iterador):
    for elem in iterador:
        if cond(elem):
            yield elem
            
def take_while(cond, iterador):
    for elem in iterador:
        if not cond(elem): break
        yield elem
            
def squares():
    n = 1
    while True:
        yield n*n
        n += 1   
            
def esCapicua(n):
    s1 = str(n)
    s2 = s1[::-1]
    return s1 == s2
            
            
            

print(list(first(20, squares())))
print(list(take_while(lambda n: n<100, squares())))
print(list(first(20, filter(esCapicua, squares()))))
