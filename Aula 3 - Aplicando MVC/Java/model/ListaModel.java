package model; 

import java.util.List;
import java.util.Random; 

/**
 * MODEL
 * Responsável apenas pelos dados e pelas regras de negócio.
 * Não sabe nada sobre como os dados serão exibidos (isso é papel da View)
 * nem sobre quem chama o método (isso é papel do Controller).
 */

public class ListaModel{

    /**
     * Popula lista ou com números aleatórios ou de forma sequencial dentro de uma faixa.
     *
     * @param lista               lista a ser populada
     * @param quantidadeNumeros   quantidade de números a gerar
     * @param inicio              início da faixa
     * @param fim                 fim da faixa
     * @param aleatorio           se true os números devem ser aleatórios
     */

    public static void popularLista(List<Integer> lista, long quantidadeNumeros, int inicio, int fim, boolean aleatorio){

        Random gerador = new Random(); 

        if(aleatorio){
            for(long i = 0; i < quantidadeNumeros; i++){
                lista.add(gerador.nextInt(inicio, fim)); 
            }
        } else  {
            for(long i = inicio; i < quantidadeNumeros; i++){
                lista.add((int)i); 
            }
        }
    }

}