package gui;

import model.*;
import fachada.Fachada;
import exceptions.*;
import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;
import java.util.List;

public class InstrutorGUI {
    private JFrame frame;
    private Instrutor instrutor;
    private JTable alunosTable;
    private JTable treinosTable;
    private DefaultTableModel alunosModel;
    private DefaultTableModel treinosModel;

    public InstrutorGUI(Instrutor instrutor) {
        this.instrutor = instrutor;
        initialize();
        carregarDados();
    }

    private void initialize() {
        frame = new JFrame("Área do Instrutor - " + instrutor.getNome());
        frame.setSize(1200, 700);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // cabeçalho
        JLabel headerLabel = new JLabel("Bem-vindo, Instrutor " + instrutor.getNome(), SwingConstants.CENTER);
        headerLabel.setFont(new Font("Arial", Font.BOLD, 18));
        mainPanel.add(headerLabel, BorderLayout.NORTH);


        JTabbedPane tabbedPane = new JTabbedPane();

        // linha do aluno
        JPanel alunosPanel = new JPanel(new BorderLayout());
        alunosModel = new DefaultTableModel(
                new Object[]{"Nome", "CPF", "Idade", "Plano", "Qtd Treinos"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        alunosTable = new JTable(alunosModel);
        alunosTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        alunosTable.setRowHeight(25);
        JScrollPane alunosScroll = new JScrollPane(alunosTable);
        alunosPanel.add(alunosScroll, BorderLayout.CENTER);

        // botao do estudante
        JPanel alunosButtonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));

        JButton associarAlunoButton = new JButton("Associar Aluno");
        associarAlunoButton.addActionListener(e -> associarNovoAluno());
        alunosButtonPanel.add(associarAlunoButton);

        JButton verTreinosButton = new JButton("Ver Treinos");
        verTreinosButton.addActionListener(e -> verTreinosDoAluno());
        alunosButtonPanel.add(verTreinosButton);

        JButton editarTreinoButton = new JButton("Editar Treino");
        editarTreinoButton.addActionListener(e -> editarTreino());
        alunosButtonPanel.add(editarTreinoButton);

        JButton criarTreinoButton = new JButton("Criar Treino");
        criarTreinoButton.addActionListener(e -> criarNovoTreino());
        alunosButtonPanel.add(criarTreinoButton);

        alunosPanel.add(alunosButtonPanel, BorderLayout.SOUTH);
        tabbedPane.addTab("Meus Alunos", alunosPanel);

        // aba do instrutor
        JPanel treinosPanel = new JPanel(new BorderLayout());
        treinosModel = new DefaultTableModel(
                new Object[]{"Nome do Treino", "Aluno", "Qtd Exercícios"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        treinosTable = new JTable(treinosModel);
        treinosTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        treinosTable.setRowHeight(25);
        JScrollPane treinosScroll = new JScrollPane(treinosTable);
        treinosPanel.add(treinosScroll, BorderLayout.CENTER);

        // instrutor botões
        JPanel treinosButtonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton removerTreinoButton = new JButton("Remover Treino");
        removerTreinoButton.addActionListener(e -> removerTreino());
        treinosButtonPanel.add(removerTreinoButton);

        treinosPanel.add(treinosButtonPanel, BorderLayout.SOUTH);
        tabbedPane.addTab("Meus Treinos", treinosPanel);

        mainPanel.add(tabbedPane, BorderLayout.CENTER);

        // rodape
        JButton sairButton = new JButton("Sair");
        sairButton.addActionListener(e -> {
            new LoginGUI().setVisible(true);
            frame.dispose();
        });

        JPanel footerPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        footerPanel.add(sairButton);
        mainPanel.add(footerPanel, BorderLayout.SOUTH);

        frame.add(mainPanel);
    }

    private void carregarDados() {
        try {
            // carrega aluno
            alunosModel.setRowCount(0);
            List<Aluno> alunos = Fachada.getInstancia().listarAlunosPorInstrutor(instrutor.getCpf());

            for (Aluno aluno : alunos) {
                long qtdTreinos = aluno.getTreinos().stream()
                        .filter(t -> t.getInstrutor().equals(instrutor))
                        .count();

                alunosModel.addRow(new Object[]{
                        aluno.getNome(),
                        aluno.getCpf(),
                        aluno.getIdade(),
                        aluno.getPlano(),
                        qtdTreinos
                });
            }

            // carrega instrutor
            treinosModel.setRowCount(0);
            for (Aluno aluno : alunos) {
                aluno.getTreinos().stream()
                        .filter(t -> t.getInstrutor().equals(instrutor))
                        .forEach(treino -> {
                            treinosModel.addRow(new Object[]{
                                    treino.getNome(),
                                    aluno.getNome(),
                                    treino.getExercicios().size()
                            });
                        });
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(frame,
                    "Erro ao carregar dados: " + e.getMessage(),
                    "Erro",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void associarNovoAluno() {
        String cpfAluno = JOptionPane.showInputDialog(frame,
                "Digite o CPF do aluno para associar:");

        if (cpfAluno != null && !cpfAluno.trim().isEmpty()) {
            try {
                Fachada.getInstancia().associarAlunoInstrutor(
                        cpfAluno,
                        instrutor.getCpf()
                );
                JOptionPane.showMessageDialog(frame,
                        "Aluno associado com sucesso!",
                        "Sucesso",
                        JOptionPane.INFORMATION_MESSAGE);
                carregarDados();
            } catch (AlunoNaoEncontradoException e) {
                JOptionPane.showMessageDialog(frame,
                        "Aluno não encontrado: " + e.getMessage(),
                        "Erro",
                        JOptionPane.ERROR_MESSAGE);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(frame,
                        "Erro na associação: " + e.getMessage(),
                        "Erro",
                        JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void verTreinosDoAluno() {
        int selectedRow = alunosTable.getSelectedRow();
        if (selectedRow >= 0) {
            String cpfAluno = (String) alunosModel.getValueAt(selectedRow, 1);
            try {
                Aluno aluno = Fachada.getInstancia().buscarAluno(cpfAluno);


                JDialog treinosDialog = new JDialog(frame, "Treinos de " + aluno.getNome(), true);
                treinosDialog.setSize(800, 500);
                treinosDialog.setLocationRelativeTo(frame);


                DefaultTableModel model = new DefaultTableModel(
                        new Object[]{"Treino", "Exercícios"}, 0) {
                    @Override
                    public boolean isCellEditable(int row, int column) {
                        return false;
                    }
                };


                for (Treino treino : aluno.getTreinos()) {
                    if (treino.getInstrutor().equals(instrutor)) {
                        StringBuilder exercicios = new StringBuilder();
                        for (Exercicio ex : treino.getExercicios()) {
                            exercicios.append("• ")
                                    .append(ex.getNome())
                                    .append(" (")
                                    .append(ex.getGrupoMuscular())
                                    .append(")\n");
                        }
                        model.addRow(new Object[]{treino.getNome(), exercicios.toString()});
                    }
                }


                JTable table = new JTable(model);
                table.setRowHeight(60);
                table.getColumnModel().getColumn(0).setPreferredWidth(150);
                table.getColumnModel().getColumn(1).setPreferredWidth(600);

                treinosDialog.add(new JScrollPane(table));
                treinosDialog.setVisible(true);

            } catch (AlunoNaoEncontradoException e) {
                JOptionPane.showMessageDialog(frame, e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(frame,
                    "Selecione um aluno primeiro",
                    "Aviso",
                    JOptionPane.WARNING_MESSAGE);
        }
    }

    private void editarTreino() {
        int selectedRow = alunosTable.getSelectedRow();
        if (selectedRow >= 0) {
            String cpfAluno = (String) alunosModel.getValueAt(selectedRow, 1);
            try {
                Aluno aluno = Fachada.getInstancia().buscarAluno(cpfAluno);
                new EditarTreinoGUI(instrutor, aluno).setVisible(true);
                carregarDados();
            } catch (AlunoNaoEncontradoException e) {
                JOptionPane.showMessageDialog(frame, e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(frame,
                    "Selecione um aluno primeiro",
                    "Aviso",
                    JOptionPane.WARNING_MESSAGE);
        }
    }

    private void criarNovoTreino() {
        int selectedRow = alunosTable.getSelectedRow();
        if (selectedRow >= 0) {
            String cpfAluno = (String) alunosModel.getValueAt(selectedRow, 1);
            try {
                Aluno aluno = Fachada.getInstancia().buscarAluno(cpfAluno);
                new CriarTreinoGUI(instrutor, aluno).setVisible(true);
                carregarDados();
            } catch (AlunoNaoEncontradoException e) {
                JOptionPane.showMessageDialog(frame, e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(frame,
                    "Selecione um aluno primeiro",
                    "Aviso",
                    JOptionPane.WARNING_MESSAGE);
        }
    }

    private void removerTreino() {
        int selectedRow = treinosTable.getSelectedRow();
        if (selectedRow >= 0) {
            String nomeTreino = (String) treinosModel.getValueAt(selectedRow, 0);
            String nomeAluno = (String) treinosModel.getValueAt(selectedRow, 1);

            int confirm = JOptionPane.showConfirmDialog(frame,
                    "Remover o treino '" + nomeTreino + "' do aluno " + nomeAluno + "?",
                    "Confirmar",
                    JOptionPane.YES_NO_OPTION);

            if (confirm == JOptionPane.YES_OPTION) {
                try {
                    String cpfAluno = null;
                    for (int i = 0; i < alunosModel.getRowCount(); i++) {
                        if (alunosModel.getValueAt(i, 0).equals(nomeAluno)) {
                            cpfAluno = (String) alunosModel.getValueAt(i, 1);
                            break;
                        }
                    }

                    if (cpfAluno != null) {
                        Fachada.getInstancia().removerTreino(
                                instrutor.getCpf(),
                                cpfAluno,
                                nomeTreino
                        );
                        carregarDados();
                    }
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(frame,
                            "Erro ao remover treino: " + e.getMessage(),
                            "Erro",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        } else {
            JOptionPane.showMessageDialog(frame,
                    "Selecione um treino primeiro",
                    "Aviso",
                    JOptionPane.WARNING_MESSAGE);
        }
    }

    public void setVisible(boolean visible) {
        frame.setVisible(visible);
    }
}