package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import model.*;
import fachada.Fachada;
import exceptions.LoginException;


public class LoginGUI {
    // componentes da interface
    private JFrame frame;
    private JTextField cpfField;
    private JPasswordField senhaField;

    public LoginGUI() {
        initialize();
    }

    // inicializa os componentes da interface
    private void initialize() {
        frame = new JFrame("Login - Sistema de Academia");
        frame.setSize(400, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

        JLabel titleLabel = new JLabel("Login");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        panel.add(titleLabel, gbc);

        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.EAST;
        gbc.gridy = 1;
        panel.add(new JLabel("CPF:"), gbc);

        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        cpfField = new JTextField(15);
        panel.add(cpfField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.EAST;
        panel.add(new JLabel("Senha:"), gbc);

        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        senhaField = new JPasswordField(15);
        panel.add(senhaField, gbc);

        // botão login
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        JButton loginButton = new JButton("Login");
        loginButton.addActionListener(this::realizarLogin);
        panel.add(loginButton, gbc);

        // botão cadastre-se
        gbc.gridy = 4;
        JButton cadastroButton = new JButton("Cadastre-se");
        cadastroButton.addActionListener(e -> {
            new CadastroGUI().setVisible(true);
            frame.dispose();
        });
        panel.add(cadastroButton, gbc);

        frame.add(panel);
    }

    // executa o login quando o botão é pressionado
    private void realizarLogin(ActionEvent e) {
        String cpf = cpfField.getText().replaceAll("[^0-9]", "");
        String senha = new String(senhaField.getPassword());

        try {
            UsuarioSistema usuario = Fachada.getInstancia().login(cpf, senha);

            // redireciona conforme o tipo de usuário
            if (usuario instanceof Aluno) {
                new AlunoGUI((Aluno) usuario).setVisible(true);
            } else if (usuario instanceof Instrutor) {
                new InstrutorGUI((Instrutor) usuario).setVisible(true);
            }
            frame.dispose();

        } catch (LoginException ex) {
            JOptionPane.showMessageDialog(frame,
                    ex.getMessage(),
                    "Erro no Login",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    public void setVisible(boolean visible) {
        frame.setVisible(visible);
    }
}