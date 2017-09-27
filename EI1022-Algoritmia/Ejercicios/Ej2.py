'''
Created on 13 sept. 2017

@author: mariu
'''

lista = []
a = int(input("Introduce un entero: "))

while a >= 0:
    lista.append(a)
    a = int(input("Introduce un entero: "))

lista.sort()
print("Tu lista de enteros ordenada es: {0}".format(lista))
for elem in lista:
    print(elem, end=" ")