import java.util.HashSet;

public class ExemploHashSimples {
    public static void main(String[] args) {
        
        // Trazer a diferença entre HashMap e HashSet com exemplos 
        HashSet<String> tabelaHashString = new HashSet<>();

        tabelaHashString.add("alexandre");
        tabelaHashString.add("hermes");
        tabelaHashString.add("davi");
        tabelaHashString.add("alan");
        tabelaHashString.add("alexandre");
        tabelaHashString.add("davi");

        System.out.println(tabelaHashString);

        for(String item : tabelaHashString){
            System.out.println(item + " -> " + item.hashCode()); // mostra o endereço de cada um
        }

         System.out.println(tabelaHashString.contains("alan") + " -> " + "alan".hashCode());

    }
}
