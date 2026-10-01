package controller;

import java.util.ArrayList;

public class Ordenacao {
    public static boolean contido(int numero, ArrayList<Integer> lista){
        for(Integer item : lista){
            if(item == numero){
                return true;
            }
        }
        return false; 
    }
    
    public static ArrayList bolha(ArrayList<Integer> lista) {
        ArrayList<Float> metricas = new ArrayList<>();
        long qtdComparacoes = 0;
        long qtdTrocas = 0;
        int aux;
        boolean houveTroca;
        int i;
        do {
            houveTroca = false;
            for (i = 0; i < lista.size() - 1; i++) {
                qtdComparacoes++;
                if (lista.get(i) > lista.get(i + 1)) {
                    houveTroca = true;
                    aux = lista.get(i);
                    lista.set(i, lista.get(i + 1));
                    lista.set(i + 1, aux);
                    qtdTrocas++;
                }
            }
        } while (houveTroca);
        metricas.add((float)qtdComparacoes);
        metricas.add((float)qtdTrocas);
        return metricas;
    }

    
    public static ArrayList selecao(ArrayList<Integer> lista) {
        ArrayList<Float> metricas = new ArrayList<>();
        long qtdComparacoes = 0;
        long qtdTrocas = 0;
        int i, j, posMenor, aux;
        posMenor = 0;
        
        for (i = 0; i < lista.size(); i++) {
            posMenor = i;
            for (j = i+1; j < lista.size(); j++) {
                qtdComparacoes++;
                if (lista.get(j) < lista.get(posMenor)) {
                    posMenor = j;
                }
            }
            if (posMenor != i) {
            aux = lista.get(i);
            lista.set(i, lista.get(posMenor));
            lista.set(posMenor, aux);
            qtdTrocas++;
            }
        }

        metricas.add((float)qtdComparacoes);
        metricas.add((float)qtdTrocas);
        return metricas;
    }
    
    public static ArrayList insercao(ArrayList<Integer> lista) {
        ArrayList<Float> metricas = new ArrayList<>();
        long qtdComparacoes = 0;
        long qtdTrocas = 0;
        int i, j, aux;

        for (i = 1; i < lista.size(); i++) {
            aux = lista.get(i);
            for (j = i-1; j > 0 && aux < lista.get(j); j-- , qtdComparacoes++) {
                qtdTrocas++;
                lista.set(j+1, lista.get(j));
            }
            lista.set(j+1, aux);
        }
        metricas.add((float)qtdComparacoes);
        metricas.add((float)qtdTrocas);
        return metricas;
    }
    
    public static ArrayList pente(ArrayList<Integer> lista) {
        ArrayList<Float> metricas = new ArrayList<>();
        long qtdComparacoes = 0;
        long qtdTrocas = 0;
        int aux;
        boolean houveTroca;
        int i;
        int distancia = lista.size();
        do {
            distancia = (int)(distancia/1.3);
            if (distancia <= 0) {
                distancia = 1;
            }
            houveTroca = false;
            for (i = 0; i + distancia < lista.size(); i++) {
                qtdComparacoes++;
                if (lista.get(i) > lista.get(i + distancia)) {
                    houveTroca = true;
                    aux = lista.get(i);
                    lista.set(i, lista.get(i + distancia));
                    lista.set(i + distancia, aux);
                    qtdTrocas++;
                }
            }
        } while (distancia > 1 || houveTroca);
        metricas.add((float)qtdComparacoes);
        metricas.add((float)qtdTrocas);
        return metricas;
    }
    
    public static ArrayList shell(ArrayList<Integer> lista){
        ArrayList<Float> metricas = new ArrayList<>(); 
        int i, j;
        int temp; 
        int n = lista.size(); 
        int qtdComparacoes = 0, qtdTrocas = 0;
        int distancia = 1; 
        int referenciaTamanho = 3; 
        
        do{
            distancia = referenciaTamanho * distancia + 1;
        } while ( distancia < n); 
        
        do{
            distancia = (int)(distancia / referenciaTamanho);
            
            for(i = distancia; i < n; i++){
                temp = lista.get(i);
                for(j = i - distancia; j >= 0; j = j - distancia){
                    qtdComparacoes++;
                    if(temp < lista.get(j)){
                        lista.set(j + distancia, lista.get(j));
                        qtdTrocas++;
                    } else{
                        break;
                    }                      
            }
            lista.set(j + distancia, temp);
            qtdTrocas++;
        }
    } while (distancia > 1);
        
    metricas.add((float)qtdComparacoes);
    metricas.add((float)qtdTrocas); 
    
    return metricas; 
    }
    
    public static void heap(ArrayList<Integer> lista) {
        if (lista == null || lista.size() <= 1) {
            return;
        }

        // 1. Duplica o primeiro elemento para "jogar" o array para a direita 
        // e poder ignorar a posição 0 (índices começam em 1)
        lista.add(0, lista.get(0));
        int tamanho = lista.size() - 1; // tamanho real dos dados válidos (índices 1 até tamanho)

        // 2. CONSTRUIR O MAX-HEAP
        // Vai do último nó que possui filhos (tamanho / 2) até a raiz (1)
        for (int i = tamanho / 2; i >= 1; i--) {
            heapify(lista, tamanho, i);
        }

        // 3. EXTRAIR OS ELEMENTOS DO HEAP (ORDENAÇÃO)
        for (int i = tamanho; i > 1; i--) {
            // Move a raiz atual (maior elemento) para o final do array útil
            int tmp = lista.get(1);
            lista.set(1, lista.get(i));
            lista.set(i, tmp);

            // Reduz o tamanho do heap e reorganiza a nova raiz que mudou de lugar
            heapify(lista, i - 1, 1);
        }

        // 4. Remove o elemento auxiliar que colocamos no índice 0
        lista.remove(0);
    }

    // Função auxiliar para descer o elemento (Down-Heap / Heapify)
    private static void heapify(ArrayList<Integer> lista, int tamanhoHeap, int i) {
        int maior = i;          // Inicializa o maior como o pai
        int esquerda = 2 * i;    // Filho da esquerda
        int direita = 2 * i + 1; // Filho da direita

        // Se o filho da esquerda for maior que o pai
        if (esquerda <= tamanhoHeap && lista.get(esquerda) > lista.get(maior)) {
            maior = esquerda;
        }

        // Se o filho da direita for maior que o maior atual
        if (direita <= tamanhoHeap && lista.get(direita) > lista.get(maior)) {
            maior = direita;
        }

        // Se o maior não for o pai, faz a troca e continua descendo recursivamente
        if (maior != i) {
            int swap = lista.get(i);
            lista.set(i, lista.get(maior));
            lista.set(maior, swap);

            // Aplica o heapify na subárvore afetada
            heapify(lista, tamanhoHeap, maior);
        }
    }     
}