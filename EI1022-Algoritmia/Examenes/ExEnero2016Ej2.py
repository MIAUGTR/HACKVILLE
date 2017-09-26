'''
Created on 17 jun. 2017

@author: MariusMiau
'''
from json.encoder import INFINITY

'''
Ramificacion y acotacion
'''

def cota_pes_subp(dias, beneficios, duraciones):
    bM = INFINITY
    d = dias
    for i in range(0, len(beneficios)):
        beneficio = 0
        d -= duraciones[i]
        beneficio += beneficios[i] 
        for j in range(0, len(beneficios)):
            if j == i:
                pass
            elif d >= duraciones[j]:
                d -= duraciones[j]
                beneficio += beneficios[j]  
        if beneficio < bM: bM = beneficio
        d = dias
    return bM+0.0


def cota_opt_subp(diasRest, beneficios, duraciones):  
    
    bM = 0
    d = diasRest
    for i in range(0, len(beneficios)):
        beneficio = 0
        d -= duraciones[i]
        beneficio += beneficios[i] 
        for j in range(0, len(beneficios)):
            if j == i:
                pass
            elif d >= duraciones[j]:
                d -= duraciones[j]
                beneficio += beneficios[j]  
        if beneficio > bM: bM = beneficio
        d = diasRest
    return bM
    
    
    
#     bM=0
#     cont=0
#     d=diasRest
#     auxBen = beneficios.copy()
#     auxDur = duraciones.copy() 
#        
#     for i in range(len(auxBen)-1, -1, -1):
#         benef = 0
#         i -=cont
#         
#         if d >= duraciones[i]:
#             benef += auxBen[i]
#             d -= auxDur[i]
#             
#             del auxBen[i]
#             del auxDur[i]
#         
#             benef += cota_opt_subp(d, auxBen, auxDur)
#             cont += 1
#             d = diasRest
#             auxBen = beneficios.copy()
#             auxDur = duraciones.copy()
#             
#             if benef > bM: bM = benef;
#     return bM 

        
D = 13 #Dias
Beneficios = [80.0, 60.0, 90.0, 99.0]
Duraciones = [5, 4, 6, 9]
Beneficios1 = [99, 90, 60, 80]
Duraciones1 = [9, 6, 4, 5]

solucion_opt = cota_opt_subp(D, Beneficios, Duraciones)
solucion_pes = cota_pes_subp(D, Beneficios1, Duraciones1)

print('Optimista: {0}\nPesimista: {1}'.format(solucion_opt, solucion_pes))
