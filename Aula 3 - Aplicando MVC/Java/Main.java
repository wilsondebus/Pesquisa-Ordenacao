import controller.MedicaoController;
import view.MedicaoView;

/**
 * Ponto de entrada. Só monta as peças (View + Controller) e inicia.
 * Não contém lógica de negócio nem de exibição.
 */

public class Main {
    public static void main(String[] args) {
        MedicaoView view = new MedicaoView(); 
        MedicaoController controller = new MedicaoController(view);
        controller.executar();
    }
}
