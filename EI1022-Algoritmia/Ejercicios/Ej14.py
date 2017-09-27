'''
Created on 27 sept. 2017

@author: mariu
'''


def take_while(cond, iterador):
    for elem in iterador:
        if not cond(elem): break
        yield elem
            
print(list(take_while(lambda n: n<100, range(50, 200))))
print(list(lambda n: n % 2 == 0, [2, 4, 5, 7, 2])) 