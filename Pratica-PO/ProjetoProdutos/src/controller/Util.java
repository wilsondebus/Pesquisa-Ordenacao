package controller;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.HashMap;
import model.Produto;

public class Util {

    // Lê o arquivo (id;nome;categoria;preco;estoque) ignorando o cabeçalho.
    // Usa UTF-8 para os acentos funcionarem em qualquer computador.
    public static boolean carregarArquivo(String nomeArquivo, ArrayList<Produto> lista) {
        try {
            BufferedReader leitor = new BufferedReader(
                    new InputStreamReader(new FileInputStream(nomeArquivo), "UTF-8"));
            String linha = leitor.readLine(); // cabeçalho: não usamos
            while ((linha = leitor.readLine()) != null) {
                String[] partes = linha.split(";");
                if (partes.length == 5) {
                    int id = Integer.parseInt(partes[0].trim());
                    String nome = partes[1].trim();
                    String categoria = partes[2].trim();
                    double preco = Double.parseDouble(partes[3].trim());
                    int estoque = Integer.parseInt(partes[4].trim());
                    lista.add(new Produto(id, nome, categoria, preco, estoque));
                }
            }
            leitor.close();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    // Grava a lista, na ordem em que está, no mesmo formato do arquivo de entrada.
    public static boolean exportarArquivo(String nomeArquivo, ArrayList<Produto> lista) {
        try {
            BufferedWriter escritor = new BufferedWriter(
                    new OutputStreamWriter(new FileOutputStream(nomeArquivo), "UTF-8"));
            escritor.write("id;nome;categoria;preco;estoque");
            escritor.newLine();
            for (Produto p : lista) {
                escritor.write(p.paraLinha());
                escritor.newLine();
            }
            escritor.close();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public static HashMap<Integer, Produto> montarMapaPorId(ArrayList<Produto> lista) {
        HashMap<Integer, Produto> mapa = new HashMap<>();
        for (Produto p : lista) {
            mapa.put(p.getId(), p);
        }
        return mapa;
    }

    // A chave é a categoria em minúsculas, para a busca não diferenciar maiúscula de minúscula.
    public static HashMap<String, ArrayList<Produto>> montarMapaPorCategoria(ArrayList<Produto> lista) {
        HashMap<String, ArrayList<Produto>> mapa = new HashMap<>();
        for (Produto p : lista) {
            String chave = p.getCategoria().toLowerCase();
            if (!mapa.containsKey(chave)) {
                mapa.put(chave, new ArrayList<Produto>());
            }
            mapa.get(chave).add(p);
        }
        return mapa;
    }

    // Mostra o tempo na unidade mais adequada
    public static String formatarTempo(double nanos) {
        if (nanos < 1000) {
            return String.format("%.1f ns", nanos);
        } else if (nanos < 1000000) {
            return String.format("%.2f \u00b5s", nanos / 1000);
        } else if (nanos < 1000000000) {
            return String.format("%.2f ms", nanos / 1000000);
        } else {
            return String.format("%.2f s", nanos / 1000000000);
        }
    }
}