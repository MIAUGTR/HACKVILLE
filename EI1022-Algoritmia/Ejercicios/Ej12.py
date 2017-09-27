'''
Created on 27 sept. 2017

@author: mariu
'''


def first(n, iterador):
    for i, elem in enumerate(iterador):
        if i < n:
            yield elem
        else:
            break
            

print(list(first(20, range(50, 200))))
print(list(first(100, [2, 4, 5, 7, 2])))