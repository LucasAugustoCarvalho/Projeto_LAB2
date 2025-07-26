package gui;

import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import model.Instrutor;

public class InstrutorGUI {
    private JFrame frame;
    private Instrutor instrutor;

    public InstrutorGUI(Instrutor instrutor) {
        this.instrutor = instrutor;
        initialize();
        carregarDados();
    }

    private void initialize() {
        frame = new JFrame("Área do Instrutor - " + instrutor.getNome());
        frame.setSize(1000, 700);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);

        JTabbedPane tabbedPane = new JTabbedPane();

        // Aba de Alunos
        JPanel alunosPanel = new JPanel(new BorderLayout());
        tabbedPane.addTab("Meus Alunos", alunosPanel);

        // Aba de Treinos
        JPanel treinosPanel = new JPanel(new BorderLayout());
        tabbedPane.addTab("Meus Treinos", treinosPanel);

        // Rodapé
        JButton logoutButton = new JButton("Sair");
        logoutButton.addActionListener(e -> {
            new LoginGUI().setVisible(true);
            frame.dispose();
        });

        frame.add(tabbedPane, BorderLayout.CENTER);
        frame.add(logoutButton, BorderLayout.SOUTH);
    }

    private void carregarDados() {
        // Implementação para carregar dados do instrutor
        // Você precisará integrar com a Fachada para obter:
        // - Lista de alunos associados
        // - Lista de treinos criados
    }

    public void setVisible(boolean visible) {
        frame.setVisible(visible);
    }
}