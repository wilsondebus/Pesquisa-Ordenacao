package model; 

import java.util.List;

/**
 * MODEL
 * Representa o resultado de uma rotina: o nome dela, a lista gerada
 * e o tempo que ela levou para rodar. É só uma estrutura de dados,
 * sem lógica de exibição.
 */

public class ResultadoMedicao {
    private final String nomeRotina; 
    private final List<Integer> lista; 
    private final long tempoMs; 

    public ResultadoMedicao(String nomeRotina, List<Integer> lista, long tempoMs){
        this.nomeRotina = nomeRotina; 
        this.lista = lista; 
        this.tempoMs = tempoMs; 
    }

    public String getNomeRotina() {
        return nomeRotina;
    }

    public List<Integer> getLista() {
        return lista;
    }

    public long getTempoMs() {
        return tempoMs;
    }

}
