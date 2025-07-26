package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import model.*;
import fachada.Fachada;
import exceptions.*;
import java.util.ArrayList;
import java.util.List;

public class EditarTreinoGUI {
    private JFrame frame;
    private Instrutor instrutor;
    private Aluno aluno;
    private Treino treino;
    private DefaultListModel<String> exerciciosModel;
    private JList<String> exerciciosList;

    public EditarTreinoGUI(Instrutor instrutor, Aluno aluno) {
        this.instrutor = instrutor;
        this.aluno = aluno;
        this.treino = selecionarTreino();
        if (treino != null) {
            initialize();
        }
    }

    private Treino selecionarTreino() {
        List<String> options = new ArrayList<>();
        for (Treino t : aluno.getTreinos()) {
            if (t.getInstrutor().getCpf().equals(instrutor.getCpf())) {
                options.add(t.getNome());
            }
        }

        if (options.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Nenhum treino encontrado para editar", "Aviso", JOptionPane.WARNING_MESSAGE);
            return null;
        }

        String selected = (String) JOptionPane.showInputDialog(
                null,
                "Selecione o treino para editar:",
                "Selecionar Treino",
                JOptionPane.PLAIN_MESSAGE,
                null,
                options.toArray(),
                options.get(0));

        if (selected != null) {
            for (Treino t : aluno.getTreinos()) {
                if (t.getNome().equals(selected) && t.getInstrutor().getCpf().equals(instrutor.getCpf())) {
                    return t;
                }
            }
        }
        return null;
    }

    private void initialize() {
        frame = new JFrame("Editar Treino: " + treino.getNome());
        frame.setSize(600, 500);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Painel de exercícios
        exerciciosModel = new DefaultListModel<>();
        treino.getExercicios().forEach(e -> exerciciosModel.addElement(e.getNome() + " (" + e.getGrupoMuscular() + ")"));

        exerciciosList = new JList<>(exerciciosModel);
        JScrollPane scrollPane = new JScrollPane(exerciciosList);
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        // Painel de botões
        JPanel buttonPanel = new JPanel(new GridLayout(1, 3, 5, 5));

        JButton addButton = new JButton("Adicionar Exercício");
        addButton.addActionListener(e -> adicionarExercicio());
        buttonPanel.add(addButton);

        JButton removeButton = new JButton("Remover Selecionado");
        removeButton.addActionListener(e -> removerExercicio());
        buttonPanel.add(removeButton);

        JButton saveButton = new JButton("Salvar Alterações");
        saveButton.addActionListener(e -> salvarAlteracoes());
        buttonPanel.add(saveButton);

        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        frame.add(mainPanel);
    }

    private void adicionarExercicio() {
        String nome = JOptionPane.showInputDialog(frame, "Nome do Exercício:");
        if (nome != null && !nome.trim().isEmpty()) {
            String grupo = JOptionPane.showInputDialog(frame, "Grupo Muscular:");
            if (grupo != null && !grupo.trim().isEmpty()) {
                exerciciosModel.addElement(nome + " (" + grupo + ")");
            }
        }
    }

    private void removerExercicio() {
        int selectedIndex = exerciciosList.getSelectedIndex();
        if (selectedIndex != -1) {
            exerciciosModel.remove(selectedIndex);
        }
    }

    private void salvarAlteracoes() {
        try {
            List<Exercicio> novosExercicios = new ArrayList<>();
            for (int i = 0; i < exerciciosModel.size(); i++) {
                String[] parts = exerciciosModel.get(i).split(" \\(");
                String grupo = parts[1].substring(0, parts[1].length() - 1);
                novosExercicios.add(new Exercicio(parts[0], grupo));
            }

            Fachada.getInstancia().atualizarTreino(
                    instrutor.getCpf(),
                    aluno.getCpf(),
                    treino.getNome(),
                    novosExercicios
            );

            JOptionPane.showMessageDialog(frame, "Treino atualizado com sucesso!");
            frame.dispose();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(frame,
                    "Erro ao atualizar treino: " + e.getMessage(),
                    "Erro",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    public void setVisible(boolean visible) {
        frame.setVisible(visible);
    }
}