package gui;

import model.Aluno;
import fachada.Fachada;
import exceptions.AlunoJaCadastradoException;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;


public class CadastroAlunoGUI {
    private JFrame frame;
    private JTextField nomeField;
    private JTextField cpfField;
    private JTextField idadeField;
    private JComboBox<String> planoCombo;
    private JPasswordField senhaField;

    public CadastroAlunoGUI() {
        initialize();
    }

    private void initialize() {
        frame = new JFrame("Cadastro de Aluno");
        frame.setSize(500, 450);
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
        panel.add(new JLabel("Nome:*"), gbc);
        gbc.gridx = 1;
        nomeField = new JTextField(20);
        panel.add(nomeField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        panel.add(new JLabel("CPF:*"), gbc);
        gbc.gridx = 1;
        cpfField = new JTextField(20);

        cpfField.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                char c = evt.getKeyChar();
                if (!Character.isDigit(c)) {
                    evt.consume();
                }
                if (cpfField.getText().length() >= 11) {
                    evt.consume();
                }
            }
        });
        panel.add(cpfField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        panel.add(new JLabel("Idade:*"), gbc);
        gbc.gridx = 1;
        idadeField = new JTextField(20);

        idadeField.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                char c = evt.getKeyChar();
                if (!Character.isDigit(c)) {
                    evt.consume();
                }
            }
        });
        panel.add(idadeField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        panel.add(new JLabel("Plano:*"), gbc);
        gbc.gridx = 1;
        planoCombo = new JComboBox<>(new String[]{"Mensal", "Trimestral", "Semestral", "Anual"});
        panel.add(planoCombo, gbc);

        gbc.gridx = 0;
        gbc.gridy = 5;
        panel.add(new JLabel("Senha:*"), gbc);
        gbc.gridx = 1;
        senhaField = new JPasswordField(20);
        panel.add(senhaField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 6;
        gbc.gridwidth = 2;
        JButton cadastrarButton = new JButton("Cadastrar");
        cadastrarButton.addActionListener(this::realizarCadastro);
        panel.add(cadastrarButton, gbc);

        gbc.gridy = 7;
        JButton voltarButton = new JButton("Voltar");
        voltarButton.addActionListener(e -> {
            new LoginGUI().setVisible(true);
            frame.dispose();
        });
        panel.add(voltarButton, gbc);

        // indicdor de campo obrigatorio
        gbc.gridy = 8;
        JLabel obrigatorioLabel = new JLabel("* Campos obrigatórios");
        obrigatorioLabel.setFont(new Font("Arial", Font.ITALIC, 10));
        panel.add(obrigatorioLabel, gbc);

        frame.add(panel);
        frame.setVisible(true);
    }

    private void realizarCadastro(ActionEvent e) {
        try {
            // pega os valores
            String nome = nomeField.getText().trim();
            String cpf = cpfField.getText().trim();
            String idadeText = idadeField.getText().trim();
            String plano = (String) planoCombo.getSelectedItem();
            String senha = new String(senhaField.getPassword());

            // validaçãao
            if (nome.isEmpty()) {
                throw new IllegalArgumentException("O nome é obrigatório");
            }

            if (cpf.isEmpty()) {
                throw new IllegalArgumentException("O CPF é obrigatório");
            } else if (cpf.length() != 11) {
                throw new IllegalArgumentException("CPF deve conter exatamente 11 dígitos");
            }

            if (idadeText.isEmpty()) {
                throw new IllegalArgumentException("A idade é obrigatória");
            }

            int idade;
            try {
                idade = Integer.parseInt(idadeText);
            } catch (NumberFormatException ex) {
                throw new IllegalArgumentException("Idade deve ser um número válido");
            }

            if (idade <= 0) {
                throw new IllegalArgumentException("Idade deve ser maior que zero");
            }

            if (senha.isEmpty()) {
                throw new IllegalArgumentException("A senha é obrigatória");
            }

            // cria e resgistra
            Aluno novoAluno = new Aluno(nome, cpf, idade, plano, senha);
            Fachada.getInstancia().cadastrarAluno(novoAluno);

            JOptionPane.showMessageDialog(frame,
                    "Aluno cadastrado com sucesso!",
                    "Sucesso",
                    JOptionPane.INFORMATION_MESSAGE);

            new LoginGUI().setVisible(true);
            frame.dispose();

        } catch (AlunoJaCadastradoException ex) {
            JOptionPane.showMessageDialog(frame,
                    "Erro: " + ex.getMessage(),
                    "Erro no Cadastro",
                    JOptionPane.ERROR_MESSAGE);
        } catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(frame,
                    ex.getMessage(),
                    "Dados Inválidos",
                    JOptionPane.WARNING_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(frame,
                    "Erro inesperado: " + ex.getMessage(),
                    "Erro",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    public void setVisible(boolean visible) {
        frame.setVisible(visible);
    }
}