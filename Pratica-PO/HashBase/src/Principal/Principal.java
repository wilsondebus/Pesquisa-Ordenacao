/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Principal;

import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;


class TabelaHash{
    
    
    public static int minhaHash(String nome, int n){
        return n % nome.length(); 
    }
    
    public static void inicializar(List[] tabela, int n){
        for (int i = 0; i < n; i++){
            tabela[i] = new ArrayList<String>(); 
            
        }
    }
    
    public static void inserir(String nome, List[] tabela){
        int endereço = minhaHash(nome, tabela.length); 
        tabela[endereço].add(nome); 
        System.out.println(nome + " inserido na tabela no endereco " + endereço);
    }
    
    public static void exibir(List[] tabela){
        for(int i = 0; i < tabela.length; i++){
            for(Object object : tabela[i]){
                System.out.println(object);
            }
        }
    }
    
    public static int pesquisar(String nome, List[] tabela){
        int endereco = minhaHash(nome, tabela.length);
        
        if(tabela[endereco].contains(nome)){
            return endereco;
        } else {
            return -1; 
        }
    }
}

public class Principal {
    static void main() {
        final int TAMANHO = 10; 
        List[] tabelaHash = new List[TAMANHO]; 
        TabelaHash.inicializar(tabelaHash, TAMANHO);
        
        String nome;
        
        
        do {
            nome = JOptionPane.showInputDialog(null, "Informe um nome:");
            if(nome.equalsIgnoreCase("SAIR")){
                break;
            }
        TabelaHash.exibir(tabelaHash); 
        } while (true); 
        
            
            TabelaHash.exibir(tabelaHash); 
            
        do{
            nome = JOptionPane.showInputDialog(null, "Informe um nome para pesquisar:");
            if(nome.equalsIgnoreCase("SAIR")){
                break;
            }
            
            int endereco = 
            
        } while (true);  
    
}
