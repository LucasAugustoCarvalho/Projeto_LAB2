package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import model.*;
import fachada.Fachada;
import exceptions.*;
import java.util.ArrayList;
import java.util.List;

public class CriarTreinoGUI {
    private JFrame frame;
    private Instrutor instrutor;
    private Aluno aluno;
    private DefaultListModel<String> exerciciosModel;
    private JList<String> exerciciosList;

    public CriarTreinoGUI(Instrutor instrutor, Aluno aluno) {
        this.instrutor = instrutor;
        this.aluno = aluno;
        initialize();
    }

    private void initialize() {
        frame = new JFrame("Criar Novo Treino");
        frame.setSize(600, 500);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // campos do treino
        JPanel formPanel = new JPanel(new GridLayout(2, 2, 5, 5));
        formPanel.add(new JLabel("Nome do Treino:"));
        JTextField nomeField = new JTextField();
        formPanel.add(nomeField);
        mainPanel.add(formPanel, BorderLayout.NORTH);

        // lista de exercícios
        exerciciosModel = new DefaultListModel<>();
        exerciciosList = new JList<>(exerciciosModel);
        JScrollPane scrollPane = new JScrollPane(exerciciosList);
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        // botões
        JPanel buttonPanel = new JPanel(new GridLayout(1, 3, 5, 5));

        JButton addButton = new JButton("Adicionar Exercício");
        addButton.addActionListener(e -> adicionarExercicio());
        buttonPanel.add(addButton);

        JButton removeButton = new JButton("Remover Selecionado");
        removeButton.addActionListener(e -> removerExercicio());
        buttonPanel.add(removeButton);

        JButton createButton = new JButton("Criar Treino");
        createButton.addActionListener(e -> criarTreino(nomeField.getText()));
        buttonPanel.add(createButton);

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

    private void criarTreino(String nomeTreino) {
        if (nomeTreino == null || nomeTreino.trim().isEmpty()) {
            JOptionPane.showMessageDialog(frame, "Informe um nome para o treino", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        if (exerciciosModel.isEmpty()) {
            JOptionPane.showMessageDialog(frame, "Adicione pelo menos um exercício", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            List<Exercicio> exercicios = new ArrayList<>();
            for (int i = 0; i < exerciciosModel.size(); i++) {
                String[] parts = exerciciosModel.get(i).split(" \\(");
                String grupo = parts[1].substring(0, parts[1].length() - 1);
                exercicios.add(new Exercicio(parts[0], grupo));
            }

            Fachada.getInstancia().criarTreino(
                    nomeTreino,
                    instrutor,
                    exercicios
            );

            Fachada.getInstancia().atribuirTreino(
                    aluno.getCpf(),
                    new Treino(nomeTreino, instrutor, exercicios)
            );

            JOptionPane.showMessageDialog(frame, "Treino criado com sucesso!");
            frame.dispose();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(frame,
                    "Erro ao criar treino: " + e.getMessage(),
                    "Erro",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    public void setVisible(boolean visible) {
        frame.setVisible(visible);
    }
}