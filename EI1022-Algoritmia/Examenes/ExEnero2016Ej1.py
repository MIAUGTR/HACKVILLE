'''
Created on 17 de jun. de 2017

@author: MariusMiau
'''

'''
Reduce y venceras
'''

def fun_rec(vector):
    def miau(i, j):
        aux = vector[i:j]
        if len(aux) == 3:
            if (aux[0] > aux[1] < aux[2]): return i+1
            return
        elif len(aux) > 3:
            k = (i + j)//2
            sol =  miau(i, k)
            if sol == None:
                sol = miau(k, j)
                if sol == None:
                    return
        else:
            return   
        return sol
    return miau(0, len(vector))


vector = [14, 2, 5, 3, 7, 8, 10]
solucion = fun_rec(vector)

print('Posicion Minimo Local -> ', solucion)