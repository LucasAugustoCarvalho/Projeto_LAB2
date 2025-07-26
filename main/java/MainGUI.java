package main;

import gui.LoginGUI;
import model.*;
import fachada.Fachada;
import exceptions.*;
import java.util.List;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class MainGUI {
    private JFrame frame;

    public MainGUI() {
        inicializarDadosTeste();
        initialize();
    }

    private static void inicializarDadosTeste() {
        try {
            Fachada fachada = Fachada.getInstancia();

            // Create test student
            Aluno maria = new Aluno("Maria Silva", "11122233344", 25, "Plano Anual", "senha123");
            fachada.cadastrarAluno(maria);

            // Create test instructor
            Instrutor carlos = new Instrutor("Carlos Magno", "55544433322", 35, "Musculação", "senha456");
            fachada.cadastrarInstrutor(carlos);

            // Create and assign trainings
            List<Exercicio> exerciciosA = List.of(
                    new Exercicio("Supino Reto", "Peitoral"),
                    new Exercicio("Agachamento", "Pernas")
            );
            Treino treinoA = fachada.criarTreino("Treino A", carlos, exerciciosA);
            fachada.atribuirTreino(maria.getCpf(), treinoA);

            // Explicitly associate student with instructor
            fachada.associarAlunoInstrutor(maria.getCpf(), carlos.getCpf());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initialize() {
        frame = new JFrame("Sistema de Academia - Menu Principal");
        frame.setSize(600, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Header
        JLabel headerLabel = new JLabel("Menu Principal", SwingConstants.CENTER);
        headerLabel.setFont(new Font("Arial", Font.BOLD, 24));
        mainPanel.add(headerLabel, BorderLayout.NORTH);

        // Center Panel
        JPanel centerPanel = new JPanel(new GridLayout(2, 1, 10, 10));

        // Login Button
        JButton loginButton = new JButton("Login");
        loginButton.addActionListener(e -> abrirLogin());
        centerPanel.add(loginButton);

        // Information Button
        JButton infoButton = new JButton("Sobre o Sistema");
        infoButton.addActionListener(e -> mostrarInformacoes());
        centerPanel.add(infoButton);

        mainPanel.add(centerPanel, BorderLayout.CENTER);

        // Footer
        JButton sairButton = new JButton("Sair");
        sairButton.addActionListener(e -> System.exit(0));
        JPanel footerPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        footerPanel.add(sairButton);
        mainPanel.add(footerPanel, BorderLayout.SOUTH);

        frame.add(mainPanel);
    }

    private void abrirLogin() {
        new LoginGUI().setVisible(true);
        frame.dispose();
    }

    private void mostrarInformacoes() {
        JOptionPane.showMessageDialog(frame,
                "Sistema de Gestão Acadêmica\n" +
                        "Versão 1.0\n\n" +
                        "Funcionalidades:\n" +
                        "- Login para Alunos e Instrutores\n" +
                        "- Gestão de Treinos\n" +
                        "- Associação Aluno-Instrutor (após login)",
                "Informações do Sistema",
                JOptionPane.INFORMATION_MESSAGE);
    }

    public void setVisible(boolean visible) {
        frame.setVisible(visible);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception e) {
                e.printStackTrace();
            }
            new MainGUI().setVisible(true);
        });
    }
}