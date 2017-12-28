'''
Created on 19 jun. 2017

@author: MariusMiau
'''
def busca(v, i, j):
    if len(v[i:j]) == 3:
        k = (i + j) // 2
        if v[k - 1] == v[k] or v[k] == v[k + 1]:
            return
        else: return k
    elif len(v[i:j]) > 3:
        k = (i + j) // 2
        indice = busca(v, i, k)
        if indice == None:
            indice = busca(v, k, j)
            if indice == None:
                return
    else:
        return
    return indice

v = [1, 1, 2, 2, 3, 4, 4, 5, 5, 6, 6, 7, 7, 8, 8]
pos = busca(v, 0, len(v))
print(pos, v[pos])