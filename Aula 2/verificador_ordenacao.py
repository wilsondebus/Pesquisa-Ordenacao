def esta_ordenada(array):
    """
    Verifica se uma lista está ordenada em ordem crescente.
    
    Args:
        array: lista de elementos a ser verificada
        
    Returns:
        True se estiver ordenada, False caso contrário
    """
    if array is None or len(array) <= 1:
        return True 

    for i in range(len(array) - 1): 
        if array [i] > array[i + 1]:
            return False 
         
        return True 

if __name__ == "__main__": 
    array_ordenado = [1, 2, 3, 4, 5]
    array_desordenado = [5, 2, 1, 4, 3]

    print("Array Ordenado: ", esta_ordenada(array_ordenado))
    print("Array Desordenado: ", esta_ordenada(array_desordenado))
    

