package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

// construtor da classe
public class CadastroGUI {
    private JFrame frame;

    public CadastroGUI() {
        initialize();
    }
//inicializa a interface
    private void initialize() {
        frame = new JFrame("Cadastro - Sistema de Academia");
        frame.setSize(400, 300);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        JLabel titleLabel = new JLabel("Cadastro");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        panel.add(titleLabel, gbc);

        gbc.gridwidth = 1;
        gbc.gridy = 1;

        // botão cadastro de aluno
        JButton alunoButton = new JButton("Sou Aluno");
        alunoButton.addActionListener(e -> {
            new CadastroAlunoGUI().setVisible(true);
            frame.dispose();
        });
        panel.add(alunoButton, gbc);

        // botão cadastro de instrutor
        gbc.gridx = 1;
        JButton instrutorButton = new JButton("Sou Instrutor");
        instrutorButton.addActionListener(e -> {
            new CadastroInstrutorGUI().setVisible(true);
            frame.dispose();
        });
        panel.add(instrutorButton, gbc);

        // Botão para voltar à tela de login
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        JButton voltarButton = new JButton("Voltar");
        voltarButton.addActionListener(e -> {
            new LoginGUI().setVisible(true);
            frame.dispose();
        });
        panel.add(voltarButton, gbc);

        frame.add(panel);
        frame.setVisible(true);
    }

    public void setVisible(boolean visible) {
        frame.setVisible(visible);
    }
}