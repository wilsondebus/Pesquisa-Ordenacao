package controller; 

import java.util.ArrayList;

public class Ordenacao {
    ArrayList<Float> metricas = new ArrayList<>(); 

    
    public static ArrayList bolha(ArrayList<Integer> lista) {
        int aux;
        boolean houveTroca;
        int i;
        long quantidadeComparacoes = 0;
        long quantidadeTrocas = 0; 
        
        do {
            houveTroca = false;
            for (i = 0; i < lista.size() - 1; i++) {
                if (lista.get(i) > lista.get(i + 1)) {
                    houveTroca = true;
                    aux = lista.get(i);
                    lista.set(i, lista.get(i + 1));
                    lista.set(i + 1, aux);
                }
            }
        } while (houveTroca);
        metricas.add((float)quantidadeComparacoes);
        metricas.add((float)quantidadeTrocas); 
        return metricas; 
    }

    public static void selecaoPalavra(ArrayList<String> lista) {
        int i, j, posMenor;
        String aux;
        posMenor = 0;
        
        for (i = 0; i < lista.size()-1; i++) {
            posMenor = i;
            for (j = i+1; j < lista.size(); j++) {
                if ((lista.get(i).compareTo(lista.get(posMenor)) > 0)) {
                    posMenor = j;
                }
            }
        }
        if (posMenor != i) {
            aux = lista.get(i);
            lista.set(i, lista.get(posMenor));
            lista.set(posMenor, aux);
        }
    }

    public static void insercao(ArrayList<Integer> lista) {
        int i, j, aux;

        for (i = 1; i < lista.size(); i++) {
            aux = lista.get(i);
            for (j = i-1; j > 0 && aux < lista.get(j); j--) {
                lista.set(j+1, lista.get(j));
            }
            lista.set(j+1, aux);
        }
    }
}