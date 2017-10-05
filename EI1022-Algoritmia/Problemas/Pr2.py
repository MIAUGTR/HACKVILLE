'''
Created on 4 oct. 2017

@author: mariu
'''


from algoritmia.datastructures.mergefindsets import MergeFindSet
from algoritmia.datastructures.digraphs import UndirectedGraph
from labyrinthviewer import LabyrinthViewer
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

lv = LabyrinthViewer(laberinto, canvas_width=600, canvas_height=400, margin=10)



def path(g: 'grafo', source: 'celdaInicial', target: 'celdaFinal'):
    
    def explorarDesde(vertice):
        camino.append(vertice)
        visto.add(vertice)
        if vertice == target:
            return True
        for suc in g.succs(vertice):
            if suc not in visto:
                if explorarDesde(suc):
                    return True
        camino.pop()
        return False   
    
    camino = []
    visto = set()
    explorarDesde(source)
    return camino


lv.add_path(path(laberinto, (0,0), (19, 49)))

lv.run()



