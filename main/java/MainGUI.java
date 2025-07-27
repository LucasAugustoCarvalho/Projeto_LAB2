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

            // cria aluno de teste
            Aluno maria = new Aluno("Maria Silva", "11111111111", 25, "Plano Anual", "123");
            fachada.cadastrarAluno(maria);
            // cria aluno de teste
            Aluno joao = new Aluno("joao Silva", "22222222222", 25, "Plano Anual", "123");
            fachada.cadastrarAluno(joao);

            // cria instrutor de teste
            Instrutor carlos = new Instrutor("Carlos Magno", "55555555555", 35, "Musculação", "456");
            fachada.cadastrarInstrutor(carlos);

            // cria e vincula treino
            List<Exercicio> exerciciosA = List.of(
                    new Exercicio("Supino Reto", "Peitoral"),
                    new Exercicio("Agachamento", "Pernas")
            );
            Treino treinoA = fachada.criarTreino("Treino A", carlos, exerciciosA);
            fachada.atribuirTreino(maria.getCpf(), treinoA);


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

        // cabeçalho
        JLabel headerLabel = new JLabel("Menu Principal", SwingConstants.CENTER);
        headerLabel.setFont(new Font("Arial", Font.BOLD, 24));
        mainPanel.add(headerLabel, BorderLayout.NORTH);


        JPanel centerPanel = new JPanel(new GridLayout(2, 1, 10, 10));

        // botão login
        JButton loginButton = new JButton("Login");
        loginButton.addActionListener(e -> abrirLogin());
        centerPanel.add(loginButton);

        // botão informação
        JButton infoButton = new JButton("Sobre o Sistema");
        infoButton.addActionListener(e -> mostrarInformacoes());
        centerPanel.add(infoButton);

        mainPanel.add(centerPanel, BorderLayout.CENTER);

        // redape
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
                        "Feito por: Lucas Augusto \n" +
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