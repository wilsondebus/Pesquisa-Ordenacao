def esta_contido(valor_pesquisa, lista):
    for item in lista:
        if item == valor_pesquisa:
            return True 
    return False


lista = [6,1,3,7,4,2,9,7]

numero_pesquisa = 7

# a ideia do contains 
print(numero_pesquisa in lista)
# utilizando o método
print(esta_contido(numero_pesquisa, lista))