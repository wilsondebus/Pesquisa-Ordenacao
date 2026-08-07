package view;

import model.ResultadoMedicao;

import java.util.List; 

/**
 * VIEW
 * Responsável apenas por exibir informação ao usuário.
 * Não decide nada, não calcula nada, só imprime o que recebe.
 */

public class MedicaoView {
    public void exibirLista(List<Integer> lista, String frase){
        System.out.println(frase);
        for(Integer item : lista){
            System.out.println(item);
        }
        System.out.println("--------------------");
        System.out.println("Total de registros: "+lista.size());
    }

    public void exibirTempo(ResultadoMedicao resultado){
        System.out.println("Tempo (ms) "+ resultado.getNomeRotina() + ": " + resultado.getTempoMs());
    }
}
