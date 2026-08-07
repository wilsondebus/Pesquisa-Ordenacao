package controller;
 
import model.ListaModel;
import model.ResultadoMedicao;
import view.MedicaoView;
 
import java.util.ArrayList;
import java.util.List;
 
/**
 * CONTROLLER
 * É o intermediário: chama o Model para gerar os dados, mede o tempo
 * de cada rotina e manda a View exibir o resultado.
 * Não guarda dados (isso é do Model) e não imprime nada diretamente (isso é da View).
 */
public class MedicaoController {
    private final MedicaoView view;
 
    public MedicaoController(MedicaoView view) {
        this.view = view;
    }
 
    public void executar() {
        List<Integer> listaAleatoria = new ArrayList<>();
        List<Integer> listaSequencial = new ArrayList<>();
 
        // rotina 1 - popular uma lista com 100000 aleatórios na faixa 100 a 100000
        long tempo1 = medir(() -> ListaModel.popularLista(listaAleatoria, 100000, 100, 100000, true));
        view.exibirTempo(new ResultadoMedicao("rotina 1", listaAleatoria, tempo1));
 
        // rotina 2 - popular uma lista com 100000 de forma crescente
        long tempo2 = medir(() -> ListaModel.popularLista(listaSequencial, 100000, 1, 100000, false));
        view.exibirTempo(new ResultadoMedicao("rotina 2", listaSequencial, tempo2));
 
        // Se quiser ver o conteúdo das listas, descomente:
        // view.exibirLista(listaAleatoria, "Lista aleatoria");
        // view.exibirLista(listaSequencial, "Lista sequencial");
    }
 
    private long medir(Runnable rotina) {
        long inicio = System.nanoTime();
        rotina.run();
        long fim = System.nanoTime();
        return (fim - inicio) / 1_000_000;
    }
}
