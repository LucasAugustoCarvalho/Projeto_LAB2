package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CadastroAlunoGUI {
    private JFrame frame;

    public CadastroAlunoGUI() {
        initialize();
    }

    private void initialize() {
        frame = new JFrame("Cadastro de Aluno");
        frame.setSize(500, 400);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel titleLabel = new JLabel("Cadastro de Aluno");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        panel.add(titleLabel, gbc);

        gbc.gridwidth = 1;
        gbc.gridy = 1;
        panel.add(new JLabel("Nome:"), gbc);
        gbc.gridx = 1;
        JTextField nomeField = new JTextField(20);
        panel.add(nomeField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        panel.add(new JLabel("CPF:"), gbc);
        gbc.gridx = 1;
        JTextField cpfField = new JTextField(20);
        panel.add(cpfField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        panel.add(new JLabel("Idade:"), gbc);
        gbc.gridx = 1;
        JTextField idadeField = new JTextField(20);
        panel.add(idadeField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        panel.add(new JLabel("Plano:"), gbc);
        gbc.gridx = 1;
        JComboBox<String> planoCombo = new JComboBox<>(new String[]{"Mensal", "Trimestral", "Semestral", "Anual"});
        panel.add(planoCombo, gbc);

        gbc.gridx = 0;
        gbc.gridy = 5;
        panel.add(new JLabel("Senha:"), gbc);
        gbc.gridx = 1;
        JPasswordField senhaField = new JPasswordField(20);
        panel.add(senhaField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 6;
        gbc.gridwidth = 2;
        JButton cadastrarButton = new JButton("Cadastrar");
        cadastrarButton.addActionListener(e -> {
            // Implement registration logic here
            JOptionPane.showMessageDialog(frame, "Aluno cadastrado com sucesso!");
            new LoginGUI().setVisible(true);
            frame.dispose();
        });
        panel.add(cadastrarButton, gbc);

        gbc.gridy = 7;
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