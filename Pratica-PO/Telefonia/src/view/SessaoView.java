package view;

import java.util.Scanner;
import controller.SessaoController;
import model.Sessao;

public class SessaoView {

    private Scanner scanner = new Scanner(System.in);
    private SessaoController controller = new SessaoController();

    public void iniciar() {
        controller.carregarArquivo("telephony_sessions.txt");

        System.out.print("Digite o número do celular: ");
        String numero = scanner.nextLine().trim();

        Sessao sessao = controller.buscarSessao(numero);

        if (sessao != null) {
            System.out.println("\nSessão encontrada:");
            System.out.println(sessao);
        } else {
            System.out.println("Número não encontrado.");
        }

        scanner.close();
    }
}
