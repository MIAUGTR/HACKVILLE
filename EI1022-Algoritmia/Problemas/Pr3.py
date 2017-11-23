'''
Created on 4 oct. 2017

@author: mariu
'''


from algoritmia.datastructures.mergefindsets import MergeFindSet
from algoritmia.datastructures.digraphs import UndirectedGraph
from labyrinthviewer import LabyrinthViewer
from random import shuffle
from algoritmia.datastructures.queues.fifo import Fifo


def crea_laberinto2(n, nfil, ncol):
    vertices = [ (f, c) for f in range(nfil) for c in range(ncol) ]
    contador = 0
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
        elif contador < n:
            contador += 1
            pasillos.append( (u, v) )
    
    
    return UndirectedGraph(E=pasillos)

def pathCorto(g: 'grafo', s: 'celdaInicio', t: 'celdaFinal'):

    camino = []
    visto = set()
    cola = Fifo()
    visto.add(s)
    cola.push( (s, [s]) )
    while len(cola) > 0:
        origen, camino = cola.pop()
        if origen == t:
            return camino
        for suc in g.succs(origen):
            if suc not in visto:
                visto.add(suc)
                cola.push( (suc, camino + [suc]) )
    
nfils = 20
ncols = 50
laberinto = crea_laberinto2(5, nfils, ncols)

lv = LabyrinthViewer(laberinto, canvas_width=600, canvas_height=400, margin=10)
lv.add_path(pathCorto(laberinto, (0,0), (nfils-1, ncols-1) ))
lv.run()



