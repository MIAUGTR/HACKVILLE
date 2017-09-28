'''
Created on 27 sept. 2017

@author: mariu
'''

#Versión normal

def cuadrados(lista):
    res = []
    for elem in lista:
        res.append(elem**2)
    return res


#Versión con yield

def cuadrados1(lista):
    for elem in lista:
        yield elem**2


#Resultados
print(cuadrados([2, 3, 4]))
print(list(cuadrados1([2, 3, 4])))
