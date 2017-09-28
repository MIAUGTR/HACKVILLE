'''
Created on 27 sept. 2017

@author: mariu
'''

from algoritmia.datastructures.mergefindsets import MergeFindSet
from algoritmia.datastructures.digraphs import UndirectedGraph
from random import shuffle

def crea_laberinto(nfil, ncol):
    vertices = [ (f, c) for f in range(nfil) for c in range(ncol) ]
# Lo de arriba es igual a lo que sigue
#     for f in range(nfil):
#         for c in range(ncol):
#             vertices.append( (f, c) )
    mfs = MergeFindSet()
    for v in vertices:   
        mfs.add(v)
    aristasH = [((f, c), (f, c+1)) for f in range(nfil) for c in range(ncol-1)]
    aristasV = [((f, c), (f+1, c)) for f in range(nfil-1) for c in range(ncol)]
    aristas = aristasH + aristasV
    shuffle(aristas) #Baraja aristas
    
    pasillos = []
    for (u, v) in aristas:
        if mfs.find(u) != mfs.find(v):
            mfs.merge(u, v)
            pasillos.append( (u, v) )
    
    return UndirectedGraph(E=pasillos)
    
laberinto = crea_laberinto(20, 50)
print(laberinto)