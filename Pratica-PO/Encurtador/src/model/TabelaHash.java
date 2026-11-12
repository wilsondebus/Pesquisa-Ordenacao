package model;

import java.util.HashMap;

public class TabelaHash {
    // a chave é o hash enquanto o conteudo é o link original
    public HashMap<String,String> links;

    public TabelaHash() {
        links = new HashMap<>();
    }
}