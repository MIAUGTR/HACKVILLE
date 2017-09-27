'''
Created on 27 sept. 2017

@author: mariu
'''

#Función anónima: lambda n: n % 2 == 0

def filter(cond, iterador):
    for elem in iterador:
        if cond(elem):
            yield elem

print(list(filter(lambda n: n<100, range(0, 200))))
print(list(filter(lambda n: n % 2 == 0, [2, 4, 5, 7, 2])))