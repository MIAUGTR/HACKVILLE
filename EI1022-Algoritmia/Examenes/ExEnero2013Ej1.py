'''
Created on 18 jun. 2017

@author: MariusMiau
'''

def ruso_rec (m, n):
    if n == 0:
        return 0
    if n == 1:
        return m
    if n % 2 == 0:
        return ruso_rec(2*m, n//2)
    else:
        return m + ruso_rec(2*m, n//2)
    
def ruso_it (m, n):
    if n == 0:
        return 0
    elif n == 1:
        return m
    else:
        resultado = m
        if n % 2 == 0:
            for i in range(1, n**0.5):
                resultado += m*i
        else:
            for i in range(1, (n**0.5) + 1):
                resultado += m*i

        return resultado

m = 10
n = 6

print('Solucion Recursiva: {0}\nSolucion Iterativa: {1}'.format(ruso_rec(m, n), ruso_rec(m, n)))