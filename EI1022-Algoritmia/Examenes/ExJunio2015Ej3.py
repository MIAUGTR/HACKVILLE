'''
Created on 18 jun. 2017

@author: MariusMiau
'''
from json.encoder import INFINITY


'''
Ramificacion y Acotacion
'''
def cota_optimista(grafo, camino):
    
    mejor = INFINITY
    for vertice in grafo:
        valor = 0
        sucesores = succs(grafo, vertice)
        for suc in sucesores:
            indice = h(sucesores, camino, suc)
            camino.extend( indice[0] )
            if mejor > valor and valor != 0:
                mejor=valor
                caminoM = camino.copy()
                
            
            valor += weight(grafo.index(suc), grafo.index(indice[0][0]))
            camino.clear()
        

    return caminoM + [vertice]



def h(grafo, camino, suc):
    back = {}
    sucesores = succs(grafo, suc)
    if len(sucesores) == 0:
        back[0] = grafo
        return back
    else:
        for v in sucesores:
            vertice = h(sucesores, camino, v)
            print(vertice)
            back = vertice
    print(back)
    return back
    
   
   
def succs(grafo, vertice):
    lista = []
    g = grafo.copy()
    for i in range(0, len(grafo)):
        if grafo[i] == vertice:
            del g[i]
        else:
            lista.append(grafo[i])
    return lista



def weight(v1, v2):
    return pesos[v1][v2]





grafo = [1, 2, 3, 4]
pesos = [[0, 1, 4, 5], [1, 0, 5, 3], [4, 5, 0, 2], [5, 3, 2, 0]]

print('Solucion -----> ', cota_optimista(grafo, []))
