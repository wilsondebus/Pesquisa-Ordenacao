package controller;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

import model.Sessao;

public class SessaoController {

    private HashMap<String, Sessao> sessoes = new HashMap<>();

    public void carregarArquivo(String caminho) {
        try (BufferedReader br = new BufferedReader(
                new FileReader(caminho))) {

            String linha;

            while ((linha = br.readLine()) != null) {
                String[] partes = linha.split(";", 2);

                if (partes.length == 2) {
                    String numero = partes[0].trim();
                    String dados = partes[1].trim();

                    Sessao sessao = new Sessao(numero, dados);

                    sessoes.put(numero, sessao);
                }
            }

            System.out.println("Arquivo carregado com sucesso!");

        } catch (IOException e) {
            System.out.println("Erro ao ler o arquivo: "
                    + e.getMessage());
        }
    }

    public Sessao buscarSessao(String numero) {
        return sessoes.get(numero);
    }
}
