def esta_ordenada(array):

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
    

