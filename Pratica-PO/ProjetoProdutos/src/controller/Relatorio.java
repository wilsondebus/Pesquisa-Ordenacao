package controller;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import model.Produto;

public class Relatorio {

    public static final String[] ARQUIVOS = {"produtos_1000.txt", "produtos_10000.txt", "produtos_50000.txt"};
    public static final String[] TAMANHOS = {"1.000", "10.000", "50.000"};
    public static final long LIMITE_2_MIN = 120L * 1000000000L;

    private static final String[] OPERACOES = {
        "Ordenação simples (por preço)",
        "Ordenação eficiente (por preço)",
        "Busca linear por id (média)",
        "Busca binária por id (média)",
        "Busca por HashMap (média)",
        "Construção do HashMap"
    };

    // Lê os 3 arquivos, mede tudo, grava o relatorio.txt e devolve o texto.
    public static String gerar() {
        ArrayList<ArrayList<Produto>> listas = new ArrayList<>();
        for (int i = 0; i < ARQUIVOS.length; i++) {
            ArrayList<Produto> lista = new ArrayList<>();
            if (!Util.carregarArquivo(ARQUIVOS[i], lista)) {
                return "ERRO: não foi possível ler " + ARQUIVOS[i]
                        + ".\nColoque os 3 arquivos na pasta do projeto.";
            }
            listas.add(lista);
        }

        aquecer(listas.get(0));

        String[][] tabela = new String[6][3];
        double[] linear = new double[3];
        double[] binaria = new double[3];
        double[] hash = new double[3];
        double[] construcao = new double[3];

        for (int t = 0; t < 3; t++) {
            ArrayList<Produto> original = listas.get(t);

            // --- Ordenação ---
            double tempo = medirOrdenacao(original, true);
            if (tempo < 0) {
                tabela[0][t] = "não concluído (> 2 min)";
            } else {
                tabela[0][t] = Util.formatarTempo(tempo);
            }
            tabela[1][t] = Util.formatarTempo(medirOrdenacao(original, false));

            // --- Construção do HashMap (média de 10 construções) ---
            for (int r = 0; r < 10; r++) {
                Util.montarMapaPorId(original); // aquecimento
            }
            long inicio = System.nanoTime();
            HashMap<Integer, Produto> mapa = null;
            for (int r = 0; r < 10; r++) {
                mapa = Util.montarMapaPorId(original);
            }
            construcao[t] = (double) (System.nanoTime() - inicio) / 10;

            // --- Buscas: 1.000 ids sorteados que existem na lista ---
            Random sorteio = new Random(42);
            int[] ids = new int[1000];
            for (int i = 0; i < ids.length; i++) {
                ids[i] = original.get(sorteio.nextInt(original.size())).getId();
            }
            // A busca binária precisa da lista ordenada por id
            ArrayList<Produto> porId = new ArrayList<>(original);
            Ordenacao.mergeSort(porId, Comparadores.POR_ID);

            linear[t] = Pesquisa.tempoMedio(Pesquisa.LINEAR, original, mapa, ids, 20);
            binaria[t] = Pesquisa.tempoMedio(Pesquisa.BINARIA, porId, mapa, ids, 20);
            hash[t] = Pesquisa.tempoMedio(Pesquisa.HASH, original, mapa, ids, 20);

            tabela[2][t] = Util.formatarTempo(linear[t]);
            tabela[3][t] = Util.formatarTempo(binaria[t]);
            tabela[4][t] = Util.formatarTempo(hash[t]);
            tabela[5][t] = Util.formatarTempo(construcao[t]);
        }

        String texto = montarTexto(tabela, linear[2], binaria[2], hash[2], construcao[2]);
        gravar(texto);
        return texto;
    }

    // Ordena cópias da lista original. Repete até 3 vezes e fica com a menor medição
    // (para de repetir se já gastou mais de 3 s). Devolve -1 se passou de 2 minutos.
    private static double medirOrdenacao(ArrayList<Produto> original, boolean simples) {
        double melhor = -1;
        long gasto = 0;
        for (int i = 0; i < 3 && gasto < 3000000000L; i++) {
            ArrayList<Produto> copia = new ArrayList<>(original);
            long inicio = System.nanoTime();
            boolean concluiu = true;
            if (simples) {
                concluiu = Ordenacao.insercao(copia, Comparadores.POR_PRECO, LIMITE_2_MIN);
            } else {
                Ordenacao.mergeSort(copia, Comparadores.POR_PRECO);
            }
            long tempo = System.nanoTime() - inicio;
            if (!concluiu) {
                return -1;
            }
            gasto += tempo;
            if (melhor < 0 || tempo < melhor) {
                melhor = tempo;
            }
        }
        return melhor;
    }

    // Roda os algoritmos antes de medir, para o Java otimizá-los (senão o arquivo de 1.000 sai "inflado")
    private static void aquecer(ArrayList<Produto> lista) {
        for (int i = 0; i < 30; i++) {
            Ordenacao.insercao(new ArrayList<>(lista), Comparadores.POR_PRECO);
        }
        for (int i = 0; i < 300; i++) {
            Ordenacao.mergeSort(new ArrayList<>(lista), Comparadores.POR_PRECO);
        }
        // Aquece também as três buscas (resultado descartado)
        ArrayList<Produto> porId = new ArrayList<>(lista);
        Ordenacao.mergeSort(porId, Comparadores.POR_ID);
        HashMap<Integer, Produto> mapa = null;
        for (int i = 0; i < 200; i++) {
            mapa = Util.montarMapaPorId(lista);
        }
        int[] ids = new int[1000];
        for (int i = 0; i < ids.length; i++) {
            ids[i] = lista.get(i % lista.size()).getId();
        }
        for (int rodada = 0; rodada < 2; rodada++) {
            Pesquisa.tempoMedio(Pesquisa.LINEAR, lista, mapa, ids, 1);
            Pesquisa.tempoMedio(Pesquisa.BINARIA, porId, mapa, ids, 1);
            Pesquisa.tempoMedio(Pesquisa.HASH, lista, mapa, ids, 1);
        }
    }

    private static String montarTexto(String[][] tabela, double linear, double binaria,
            double hash, double construcao) {
        String formato = "%-34s|%25s|%25s|%25s\n";
        String traco = "----------------------------------+-------------------------+-------------------------+-------------------------\n";

        StringBuilder sb = new StringBuilder();
        sb.append("RELATÓRIO DE TEMPOS - PESQUISA E ORDENAÇÃO\n");
        sb.append("Ordenações: tempo total. Buscas: tempo médio de uma busca.\n\n");
        sb.append(String.format(formato, "Operação", TAMANHOS[0], TAMANHOS[1], TAMANHOS[2]));
        sb.append(traco);
        for (int i = 0; i < OPERACOES.length; i++) {
            sb.append(String.format(formato, OPERACOES[i], tabela[i][0], tabela[i][1], tabela[i][2]));
        }

        // Qual busca foi a mais rápida com 50.000 registros?
        String maisRapida = "HashMap";
        double menor = hash;
        if (binaria < menor) {
            maisRapida = "busca binária";
            menor = binaria;
        }
        if (linear < menor) {
            maisRapida = "busca linear";
        }

        sb.append("\nCONCLUSÃO (50.000 registros)\n");
        sb.append("1) Mais rápida: " + maisRapida + " (HashMap " + Util.formatarTempo(hash)
                + ", binária " + Util.formatarTempo(binaria)
                + ", linear " + Util.formatarTempo(linear) + " por busca).\n");
        sb.append("2) Por quê: o HashMap vai direto ao item pela chave (O(1)); a binária divide a lista ao meio a cada passo (O(log n)); a linear olha item por item (O(n)).\n");
        sb.append("3) Vale construir o HashMap quando há muitas buscas sobre os mesmos dados: a construção custa O(n) e é paga uma vez (" + Util.formatarTempo(construcao) + ").\n");
        double economia = linear - hash;
        if (economia > 0) {
            long buscas = (long) Math.ceil(construcao / economia);
            sb.append("4) Neste teste ele já se paga depois de ~" + buscas + " buscas, comparado à busca linear.\n");
        } else {
            sb.append("4) Neste teste o HashMap não foi mais rápido que a busca linear, então não compensou.\n");
        }
        sb.append("5) Para poucas buscas a linear basta; o HashMap também gasta mais memória.\n");
        return sb.toString();
    }

    private static void gravar(String texto) {
        try {
            BufferedWriter escritor = new BufferedWriter(
                    new OutputStreamWriter(new FileOutputStream("relatorio.txt"), "UTF-8"));
            escritor.write(texto);
            escritor.close();
        } catch (Exception e) {
            System.out.println("Erro ao gravar o relatório: " + e.getMessage());
        }
    }
}