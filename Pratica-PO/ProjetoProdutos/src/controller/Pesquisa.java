package controller;

import java.util.ArrayList;
import java.util.HashMap;
import model.Produto;

public class Pesquisa {

    public static final int LINEAR = 0;
    public static final int BINARIA = 1;
    public static final int HASH = 2;

    // Guarda quantos produtos foram achados; evita que o Java "pule" o laço de medição.
    public static int achados = 0;

    public static Produto buscaLinear(ArrayList<Produto> lista, int id) {
        for (int i = 0; i < lista.size(); i++) {
            if (lista.get(i).getId() == id) {
                return lista.get(i);
            }
        }
        return null;
    }

    // ATENÇÃO: a lista precisa estar ordenada por id!
    public static Produto buscaBinaria(ArrayList<Produto> lista, int id) {
        int ini = 0;
        int fim = lista.size() - 1;
        while (ini <= fim) {
            int meio = (ini + fim) / 2;
            if (id == lista.get(meio).getId()) {
                return lista.get(meio);
            }
            if (id < lista.get(meio).getId()) {
                fim = meio - 1;
            } else {
                ini = meio + 1;
            }
        }
        return null;
    }

    public static boolean estaOrdenadaPorId(ArrayList<Produto> lista) {
        for (int i = 1; i < lista.size(); i++) {
            if (lista.get(i - 1).getId() > lista.get(i).getId()) {
                return false;
            }
        }
        return true;
    }

    // Faz uma busca para cada id do vetor
    private static int executar(int tecnica, ArrayList<Produto> lista, HashMap<Integer, Produto> mapa, int[] ids) {
        int encontrados = 0;
        for (int i = 0; i < ids.length; i++) {
            Produto p;
            if (tecnica == LINEAR) {
                p = buscaLinear(lista, ids[i]);
            } else if (tecnica == BINARIA) {
                p = buscaBinaria(lista, ids[i]);
            } else {
                p = mapa.get(ids[i]);
            }
            if (p != null) {
                encontrados++;
            }
        }
        return encontrados;
    }

    // Tempo médio (em ns) de UMA busca. Repete as buscas "voltas" vezes e divide.
    // Antes de medir, "aquece" por 100 ms: o Java otimiza o código conforme ele roda,
    // e sem isso as primeiras medições saem muito maiores.
    public static double tempoMedio(int tecnica, ArrayList<Produto> lista,
            HashMap<Integer, Produto> mapa, int[] ids, int voltas) {
        long inicioAquecimento = System.nanoTime();
        while (System.nanoTime() - inicioAquecimento < 100000000L) {
            achados += executar(tecnica, lista, mapa, ids);
        }
        long inicio = System.nanoTime();
        for (int v = 0; v < voltas; v++) {
            achados += executar(tecnica, lista, mapa, ids);
        }
        long fim = System.nanoTime();
        return (double) (fim - inicio) / (voltas * ids.length);
    }

    // Tempo médio (em ns) de uma consulta ao HashMap de categorias
    public static double tempoMedioCategoria(HashMap<String, ArrayList<Produto>> mapa, String categoria, int repeticoes) {
        String chave = categoria.toLowerCase();
        long inicioAquecimento = System.nanoTime();
        while (System.nanoTime() - inicioAquecimento < 100000000L) {
            if (mapa.get(chave) != null) {
                achados++;
            }
        }
        long inicio = System.nanoTime();
        for (int i = 0; i < repeticoes; i++) {
            if (mapa.get(chave) != null) {
                achados++;
            }
        }
        long fim = System.nanoTime();
        return (double) (fim - inicio) / repeticoes;
    }
}