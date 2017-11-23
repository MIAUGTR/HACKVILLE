def mientras_quepa(W, C):
    pass

def primero_que_quepa(W, C):
    pass

def primero_que_quepa_ordenado(W, C):
    pass

W, C = [1, 2, 8, 7, 8, 3], 10

for solve in [mientras_quepa, primero_que_quepa, primero_que_quepa_ordenado]:
    sol = solve(W, C)
    print("Método:", solve.__name__)
    if sol is None:
        print("No implementado")
    else:
        print("Solución: {}, usados {} contenedores\n".format(sol, 1 + max(sol)))