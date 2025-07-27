package gui;

import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;
import model.*;

public class AlunoGUI {
    private JFrame frame;
    private Aluno aluno;
    private JTable treinosTable;
    private JTextArea exerciciosArea;

    public AlunoGUI(Aluno aluno) {
        this.aluno = aluno;
        initialize();
        carregarTreinos();
    }

    private void initialize() {
        frame = new JFrame("Área do Aluno - " + aluno.getNome());
        frame.setSize(900, 650);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLocationRelativeTo(null);

        // Layout principal
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Cabeçalho
        JLabel headerLabel = new JLabel("Bem-vindo, " + aluno.getNome(), SwingConstants.CENTER);
        headerLabel.setFont(new Font("Arial", Font.BOLD, 18));
        mainPanel.add(headerLabel, BorderLayout.NORTH);

        // Painel central dividido (treinos e exercícios)
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        splitPane.setDividerLocation(400);

        // Painel da tabela de treinos
        JPanel treinosPanel = new JPanel(new BorderLayout());
        treinosPanel.setBorder(BorderFactory.createTitledBorder("Meus Treinos"));

        treinosTable = new JTable();
        treinosTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        treinosTable.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                mostrarExerciciosDoTreinoSelecionado();
            }
        });

        JScrollPane tableScroll = new JScrollPane(treinosTable);
        treinosPanel.add(tableScroll, BorderLayout.CENTER);
        splitPane.setLeftComponent(treinosPanel);

        // Painel de detalhes dos exercícios
        JPanel exerciciosPanel = new JPanel(new BorderLayout());
        exerciciosPanel.setBorder(BorderFactory.createTitledBorder("Exercícios do Treino"));

        exerciciosArea = new JTextArea();
        exerciciosArea.setEditable(false);
        exerciciosArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
        exerciciosArea.setMargin(new Insets(10, 10, 10, 10));

        JScrollPane exerciciosScroll = new JScrollPane(exerciciosArea);
        exerciciosPanel.add(exerciciosScroll, BorderLayout.CENTER);
        splitPane.setRightComponent(exerciciosPanel);

        mainPanel.add(splitPane, BorderLayout.CENTER);

        // Rodapé com botão Sair - CORREÇÃO PRINCIPAL
        JButton sairButton = new JButton("Sair");
        sairButton.addActionListener(e -> {
            new LoginGUI().setVisible(true); // Abre a tela de login
            frame.dispose(); // Fecha a janela atual
        });

        JPanel footerPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        footerPanel.add(sairButton);
        mainPanel.add(footerPanel, BorderLayout.SOUTH);

        frame.add(mainPanel);
    }

    private void carregarTreinos() {
        DefaultTableModel model = new DefaultTableModel(
                new Object[]{"Treino", "Instrutor", "Qtd Exercícios"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        for (Treino treino : aluno.getTreinos()) {
            model.addRow(new Object[]{
                    treino.getNome(),
                    treino.getInstrutor().getNome(),
                    treino.getExercicios().size()
            });
        }

        treinosTable.setModel(model);
    }

    private void mostrarExerciciosDoTreinoSelecionado() {
        int selectedRow = treinosTable.getSelectedRow();
        if (selectedRow >= 0 && selectedRow < aluno.getTreinos().size()) {
            Treino treinoSelecionado = aluno.getTreinos().get(selectedRow);
            StringBuilder sb = new StringBuilder();

            for (Exercicio exercicio : treinoSelecionado.getExercicios()) {
                sb.append("● ").append(exercicio.getNome()).append("\n");
                sb.append("   Grupo Muscular: ").append(exercicio.getGrupoMuscular()).append("\n\n");
            }

            exerciciosArea.setText(sb.toString());
            exerciciosArea.setCaretPosition(0);
        }
    }

    public void setVisible(boolean visible) {
        frame.setVisible(visible);
    }
}