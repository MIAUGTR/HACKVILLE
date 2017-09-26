'''
Created on 13 sept. 2017

@author: mariu
'''
from math import sqrt

print("\n********************CALCULA ECUACIONES 2do GRADO********************")
print("    ******************** aX^2 + bX + C = 0 ********************\n")

#Pedimos los datos
operando1 = int(input("Dame el operando 'a' o '0' si no tiene: "))
operando2 = int(input("Dame el operando 'b': "))
constante = int(input("Dame el operando 'c': "))

#Realizo las operaciones
if operando1 == 0:
    print("\nLa ecuación es de 1er Grado")
    if operando2 == 0:
        print("La ecuación no tiene solución")
    else:
        resultado = - constante / operando2
        print("El resultado es -> X = {0}".format(resultado))
else:
    if (operando2**2) - (4*operando1*constante) < 0:
        print("\nNo tiene solución real porque la raíz es negativa.")
    elif (operando2**2) - (4*operando1*constante) == 0:
        resultado = - operando2 / 2*operando1
        print("\nLa solución es doble -> X = {0}".format(resultado))
    else:
        resultado = (- operando2 + sqrt((operando2**2) - (4*operando1*constante))) / 2*operando1
        resultado1 = (- operando2 - sqrt((operando2**2) - (4*operando1*constante))) / 2*operando1
        print("\nEl resultado es -> X1 = {0} y X2 = {1}".format(resultado, resultado1))
        
print("\n********************EL PROGRAMA HA FINALIZADO********************")