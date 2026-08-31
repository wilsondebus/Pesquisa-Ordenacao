import java.util.List;
import java.time.LocalDate;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        
        List<Produto> listaProduto = new ArrayList<>();
        LocalDate dataAtual1 = LocalDate.now();

        listaProduto.add(new Produto(1, "Pão de Forma", dataAtual1)); 
        listaProduto.add(new Produto(4, "Amendoim", dataAtual1)); 
        listaProduto.add(new Produto(2, "Pão Cacetinho", LocalDate.parse("2026-08-28"))); 
        listaProduto.add(new Produto(3, "Brocolis", dataAtual1)); 

        listaProduto.sort((p1, p2) -> p2.getData().compareTo(p1.getData())); 

        for (Produto item : listaProduto){
            System.out.println(item);
        }
    }
}
