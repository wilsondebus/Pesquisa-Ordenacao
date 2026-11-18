/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package view;

import controller.Comparadores;
import controller.Ordenacao;
import controller.Pesquisa;
import controller.Relatorio;
import controller.Util;
import java.awt.Font;
import java.io.File;
import java.util.ArrayList;
import java.util.Comparator;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;
import model.Model;
import model.Produto;

/**
 *
 * @author laboratorio
 */
public class JFramePrincipal extends javax.swing.JFrame {

    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(JFramePrincipal.class.getName());

    private Object[] colunas = {"ID", "Nome", "Categoria", "Preço", "Estoque"};
    private DefaultTableModel modeloTabela = new DefaultTableModel(colunas, 0) {
        @Override
        public boolean isCellEditable(int linha, int coluna) {
            return false;
        }
    };

    /**
     * Creates new form JFramePrincipal
     */
    public JFramePrincipal() {
        initComponents();
        tabela.setModel(modeloTabela);
        txtArquivo.setEditable(false);
        ligarEventos();
    }

    private void ligarEventos() {
        btnAbrir.addActionListener(e -> escolherArquivo());
        btnExportar.addActionListener(e -> exportar());
        btnRelatorio.addActionListener(e -> gerarRelatorio(true));
        btnOrdenar.addActionListener(e -> ordenar());
        btnBuscar.addActionListener(e -> buscar());
        txtBusca.addActionListener(e -> buscar());
        btnTodos.addActionListener(e -> mostrarLista(Model.lista));
    }

    // Chamado pelo main logo depois de abrir a janela
    public void iniciar() {
        File padrao = new File("produtos_1000.txt");
        if (padrao.exists()) {
            carregar(padrao.getPath());
        }
        gerarRelatorio(false); // gera o relatorio.txt automaticamente
    }

    // ---------------------------- arquivos ----------------------------
    private void escolherArquivo() {
        JFileChooser escolhedor = new JFileChooser(new File(System.getProperty("user.dir")));
        if (escolhedor.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            carregar(escolhedor.getSelectedFile().getPath());
        }
    }

    private void carregar(String nomeArquivo) {
        ArrayList<Produto> nova = new ArrayList<>();
        if (!Util.carregarArquivo(nomeArquivo, nova)) {
            JOptionPane.showMessageDialog(this, "Não foi possível ler o arquivo:\n" + nomeArquivo);
            return;
        }
        Model.lista = nova;

        // Os HashMaps são montados na carga dos dados
        long inicio = System.nanoTime();
        Model.mapaPorId = Util.montarMapaPorId(nova);
        long fim = System.nanoTime();
        Model.mapaPorCategoria = Util.montarMapaPorCategoria(nova);

        txtArquivo.setText(nomeArquivo);
        mostrarLista(Model.lista);
        lblTempo.setText("Tempo da última operação: construção do HashMap por id = "
                + Util.formatarTempo(fim - inicio));
    }

    private void exportar() {
        if (Model.lista.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Carregue um arquivo primeiro.");
            return;
        }
        JFileChooser escolhedor = new JFileChooser(new File(System.getProperty("user.dir")));
        escolhedor.setSelectedFile(new File("produtos_exportados.txt"));
        if (escolhedor.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
            if (Util.exportarArquivo(escolhedor.getSelectedFile().getPath(), Model.lista)) {
                lblInfo.setText(Model.lista.size() + " produtos exportados.");
            } else {
                JOptionPane.showMessageDialog(this, "Erro ao gravar o arquivo.");
            }
        }
    }

    // ---------------------------- ordenação ----------------------------
    private void ordenar() {
        if (Model.lista.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Carregue um arquivo primeiro.");
            return;
        }
        final ArrayList<Produto> copia = new ArrayList<>(Model.lista);
        final String campo = (String) cmbCampo.getSelectedItem();
        final boolean simples = cmbAlgoritmo.getSelectedIndex() == 0;
        final Comparator<Produto> comp;
        if (cmbCampo.getSelectedIndex() == 0) {
            comp = Comparadores.POR_ID;
        } else if (cmbCampo.getSelectedIndex() == 1) {
            comp = Comparadores.POR_NOME;
        } else {
            comp = Comparadores.POR_PRECO;
        }

        bloquear(true);
        lblInfo.setText("Ordenando... aguarde.");

        // Thread separada para a janela não travar enquanto ordena
        new Thread(() -> {
            long inicio = System.nanoTime();
            boolean concluiu = true;
            if (simples) {
                concluiu = Ordenacao.insercao(copia, comp, Relatorio.LIMITE_2_MIN);
            } else {
                Ordenacao.mergeSort(copia, comp);
            }
            final long tempo = System.nanoTime() - inicio;
            final boolean ok = concluiu;

            SwingUtilities.invokeLater(() -> {
                if (ok) {
                    Model.lista = copia;
                    mostrarLista(Model.lista);
                    lblTempo.setText("Tempo da última operação: ordenação por " + campo + " = "
                            + Util.formatarTempo(tempo));
                } else {
                    lblTempo.setText("Tempo da última operação: não concluído (> 2 min)");
                    lblInfo.setText("Ordenação interrompida; a lista continua como estava.");
                }
                bloquear(false);
            });
        }).start();
    }

    // ------------------------------ busca ------------------------------
    private void buscar() {
        if (Model.lista.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Carregue um arquivo primeiro.");
            return;
        }
        String texto = txtBusca.getText().trim();
        if (texto.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Digite o ID ou a categoria.");
            return;
        }
        int opcao = cmbBusca.getSelectedIndex();

        if (opcao == 3) {
            buscarCategoria(texto);
            return;
        }

        int id;
        try {
            id = Integer.parseInt(texto);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "O ID deve ser um número inteiro.");
            return;
        }
        // A busca binária só funciona com a lista ordenada pelo campo buscado (id)
        if (opcao == 1 && !Pesquisa.estaOrdenadaPorId(Model.lista)) {
            JOptionPane.showMessageDialog(this,
                    "A busca binária precisa da lista ordenada por ID.\nUse \"Ordenar por: ID\" antes.");
            return;
        }

        // Repete a mesma busca 1.000 vezes e calcula o tempo médio
        int[] ids = new int[1000];
        java.util.Arrays.fill(ids, id);
        double media = Pesquisa.tempoMedio(opcao, Model.lista, Model.mapaPorId, ids, 1);

        Produto achado;
        if (opcao == Pesquisa.LINEAR) {
            achado = Pesquisa.buscaLinear(Model.lista, id);
        } else if (opcao == Pesquisa.BINARIA) {
            achado = Pesquisa.buscaBinaria(Model.lista, id);
        } else {
            achado = Model.mapaPorId.get(id);
        }

        ArrayList<Produto> resultado = new ArrayList<>();
        if (achado != null) {
            resultado.add(achado);
        }
        mostrarLista(resultado);
        lblTempo.setText("Tempo da última operação: " + cmbBusca.getSelectedItem() + " = "
                + Util.formatarTempo(media) + " por busca (média de 1.000)");
        lblInfo.setText(achado == null ? "ID " + id + " não encontrado." : "Produto encontrado.");
    }

    private void buscarCategoria(String categoria) {
        double media = Pesquisa.tempoMedioCategoria(Model.mapaPorCategoria, categoria, 1000);
        ArrayList<Produto> resultado = Model.mapaPorCategoria.get(categoria.toLowerCase());
        if (resultado == null) {
            resultado = new ArrayList<>();
        }
        mostrarLista(resultado);
        lblTempo.setText("Tempo da última operação: Categoria - HashMap = "
                + Util.formatarTempo(media) + " por consulta (média de 1.000)");
        lblInfo.setText(resultado.size() + " produtos na categoria \"" + categoria + "\".");
    }

    // ----------------------------- relatório -----------------------------
    private void gerarRelatorio(final boolean mostrar) {
        bloquear(true);
        lblInfo.setText("Gerando relatorio.txt... aguarde.");

        new Thread(() -> {
            final String texto = Relatorio.gerar();
            SwingUtilities.invokeLater(() -> {
                bloquear(false);
                lblInfo.setText("relatorio.txt gerado.");
                if (mostrar || texto.startsWith("ERRO")) {
                    JTextArea area = new JTextArea(texto, 22, 110);
                    area.setEditable(false);
                    area.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 12));
                    JOptionPane.showMessageDialog(this, new JScrollPane(area), "relatorio.txt",
                            JOptionPane.PLAIN_MESSAGE);
                }
            });
        }).start();
    }

    // ------------------------------ auxiliares ------------------------------
    private void mostrarLista(ArrayList<Produto> lista) {
        Object[][] dados = new Object[lista.size()][5];
        for (int i = 0; i < lista.size(); i++) {
            Produto p = lista.get(i);
            dados[i][0] = p.getId();
            dados[i][1] = p.getNome();
            dados[i][2] = p.getCategoria();
            dados[i][3] = String.format("%.2f", p.getPreco());
            dados[i][4] = p.getEstoque();
        }
        modeloTabela.setDataVector(dados, colunas);
        lblInfo.setText(lista.size() + " produtos exibidos.");
    }

    // Desliga os botões enquanto algo demorado está rodando
    private void bloquear(boolean bloqueado) {
        btnAbrir.setEnabled(!bloqueado);
        btnExportar.setEnabled(!bloqueado);
        btnRelatorio.setEnabled(!bloqueado);
        btnOrdenar.setEnabled(!bloqueado);
        btnBuscar.setEnabled(!bloqueado);
        btnTodos.setEnabled(!bloqueado);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        txtArquivo = new javax.swing.JTextField();
        btnAbrir = new javax.swing.JButton();
        btnExportar = new javax.swing.JButton();
        btnRelatorio = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        cmbCampo = new javax.swing.JComboBox<>();
        jLabel3 = new javax.swing.JLabel();
        cmbAlgoritmo = new javax.swing.JComboBox<>();
        btnOrdenar = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        cmbBusca = new javax.swing.JComboBox<>();
        jLabel5 = new javax.swing.JLabel();
        txtBusca = new javax.swing.JTextField();
        btnBuscar = new javax.swing.JButton();
        btnTodos = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tabela = new javax.swing.JTable();
        lblTempo = new javax.swing.JLabel();
        lblInfo = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setText("Arquivo:");

        btnAbrir.setText("Escolher Arquivo");

        btnExportar.setText("Exportar Lista");

        btnRelatorio.setText("Gerar Relatório");

        jLabel2.setText("Ordenar por:");

        cmbCampo.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "ID", "Nome", "Preco" }));

        jLabel3.setText("Algoritmo:");

        cmbAlgoritmo.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Insertion Sort (simples)", "Merge Sort (eficiente)" }));

        btnOrdenar.setText("Ordenar");

        jLabel4.setText("Busca:");

        cmbBusca.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "ID - Linear", "ID - Binária", "ID - HashMap", "Categoria - HashMap" }));

        jLabel5.setText("ID Categoria:");

        btnBuscar.setText("Buscar");

        btnTodos.setText("Mostrar Todos");

        tabela.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "ID", "Nome", "Categoria", "Preço", "Estoque"
            }
        ));
        jScrollPane1.setViewportView(tabela);

        lblTempo.setText("Tempo da última operação:");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addGroup(layout.createSequentialGroup()
                            .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(txtArquivo, javax.swing.GroupLayout.PREFERRED_SIZE, 235, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(18, 18, 18)
                            .addComponent(btnAbrir)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(btnExportar, javax.swing.GroupLayout.PREFERRED_SIZE, 119, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(btnRelatorio, javax.swing.GroupLayout.PREFERRED_SIZE, 119, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(layout.createSequentialGroup()
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(cmbBusca, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                    .addComponent(jLabel5)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(txtBusca))
                                .addGroup(layout.createSequentialGroup()
                                    .addComponent(jLabel2)
                                    .addGap(18, 18, 18)
                                    .addComponent(cmbCampo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGap(18, 18, 18)
                                    .addComponent(jLabel3)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                    .addComponent(cmbAlgoritmo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGap(18, 18, 18)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(btnOrdenar)
                                .addGroup(layout.createSequentialGroup()
                                    .addComponent(btnBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(btnTodos))))
                        .addComponent(jScrollPane1))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(lblTempo)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblInfo, javax.swing.GroupLayout.PREFERRED_SIZE, 243, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(45, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(txtArquivo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnAbrir)
                    .addComponent(btnExportar)
                    .addComponent(btnRelatorio))
                .addGap(25, 25, 25)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(cmbCampo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3)
                    .addComponent(cmbAlgoritmo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnOrdenar))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(cmbBusca, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5)
                    .addComponent(txtBusca, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnBuscar)
                    .addComponent(btnTodos))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblTempo)
                    .addComponent(lblInfo))
                .addContainerGap(28, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ReflectiveOperationException | javax.swing.UnsupportedLookAndFeelException ex) {
            logger.log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> {
            JFramePrincipal tela = new JFramePrincipal();
            tela.setVisible(true);
            tela.iniciar();
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAbrir;
    private javax.swing.JButton btnBuscar;
    private javax.swing.JButton btnExportar;
    private javax.swing.JButton btnOrdenar;
    private javax.swing.JButton btnRelatorio;
    private javax.swing.JButton btnTodos;
    private javax.swing.JComboBox<String> cmbAlgoritmo;
    private javax.swing.JComboBox<String> cmbBusca;
    private javax.swing.JComboBox<String> cmbCampo;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblInfo;
    private javax.swing.JLabel lblTempo;
    private javax.swing.JTable tabela;
    private javax.swing.JTextField txtArquivo;
    private javax.swing.JTextField txtBusca;
    // End of variables declaration//GEN-END:variables
}
