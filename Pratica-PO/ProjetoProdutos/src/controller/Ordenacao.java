package controller;

import java.util.ArrayList;
import java.util.Comparator;
import model.Produto;

public class Ordenacao {

    // ---------- Algoritmo simples: Insertion Sort ----------

    public static boolean insercao(ArrayList<Produto> lista, Comparator<Produto> comp) {
        return insercao(lista, comp, 0);
    }

    // Se limiteNanos > 0 e o tempo passar do limite, para e devolve false (não concluído).
    public static boolean insercao(ArrayList<Produto> lista, Comparator<Produto> comp, long limiteNanos) {
        long inicio = System.nanoTime();
        int i, j;
        Produto aux;
        for (i = 1; i < lista.size(); i++) {
            // confere o relógio só de 100 em 100 voltas para não atrapalhar a medição
            if (limiteNanos > 0 && i % 100 == 0 && System.nanoTime() - inicio > limiteNanos) {
                return false;
            }
            aux = lista.get(i);
            j = i - 1;
            while (j >= 0 && comp.compare(lista.get(j), aux) > 0) {
                lista.set(j + 1, lista.get(j));
                j--;
            }
            lista.set(j + 1, aux);
        }
        return true;
    }

    // ---------- Algoritmo eficiente: Merge Sort ----------

    public static void mergeSort(ArrayList<Produto> lista, Comparator<Produto> comp) {
        Produto[] vetor = lista.toArray(new Produto[0]);
        dividir(vetor, 0, vetor.length - 1, comp);
        for (int i = 0; i < vetor.length; i++) {
            lista.set(i, vetor[i]);
        }
    }

    private static void dividir(Produto[] v, int ini, int fim, Comparator<Produto> comp) {
        if (ini < fim) {
            int meio = (ini + fim) / 2;
            dividir(v, ini, meio, comp);
            dividir(v, meio + 1, fim, comp);
            juntar(v, ini, meio, fim, comp);
        }
    }

    private static void juntar(Produto[] v, int ini, int meio, int fim, Comparator<Produto> comp) {
        Produto[] aux = new Produto[fim - ini + 1];
        int i = ini;       // percorre a metade esquerda
        int j = meio + 1;  // percorre a metade direita
        int k = 0;
        while (i <= meio && j <= fim) {
            if (comp.compare(v[j], v[i]) < 0) {
                aux[k++] = v[j++];
            } else {
                aux[k++] = v[i++];
            }
        }
        while (i <= meio) {
            aux[k++] = v[i++];
        }
        while (j <= fim) {
            aux[k++] = v[j++];
        }
        for (k = 0; k < aux.length; k++) {
            v[ini + k] = aux[k];
        }
    }
}