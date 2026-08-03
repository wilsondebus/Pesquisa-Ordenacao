public class VerificadorOrdenacao {
    
    /**
     * Verifica se um array de inteiros está ordenado em ordem crescente.
     * @param array o array a ser verificado
     * @return true se estiver ordenado, false caso contrário
     */
    public static boolean estaOrdenado(int[] array) {
        // Um array vazio ou com um único elemento é considerado ordenado
        if (array == null || array.length <= 1) {  // lenght serve para saber o tamanho de um array 
            return true;
        }
        
        for (int i = 0; i < array.length - 1; i++) {
            if (array[i] > array[i + 1]) {
                return false;
            }
        }
        
        return true;
    }
    
    public static void main(String[] args) {
        int[] arrayOrdenado = {1, 2, 3, 4, 5};
        int[] arrayDesordenado = {5, 3, 1, 4, 2};
        
        System.out.println("Array ordenado: " + estaOrdenado(arrayOrdenado));       // true
        System.out.println("Array desordenado: " + estaOrdenado(arrayDesordenado)); // false
    }
}