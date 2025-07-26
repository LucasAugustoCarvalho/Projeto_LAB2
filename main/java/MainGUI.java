package main;

import gui.LoginGUI;
import model.*;
import fachada.Fachada;
import exceptions.*;
import java.util.List;
import javax.swing.SwingUtilities;

public class MainGUI {
    public static void main(String[] args) {
        inicializarDadosTeste();
        SwingUtilities.invokeLater(() -> new LoginGUI());
    }

    private static void inicializarDadosTeste() {
        try {
            Fachada fachada = Fachada.getInstancia();

            // 1. Cadastra Aluno (Maria)
            Aluno maria = cadastrarAlunoTeste(
                    fachada,
                    "Maria Silva",
                    "11122233344",
                    25,
                    "Plano Anual",
                    "senha123"
            );

            // 2. Cadastra Instrutor (Carlos)
            Instrutor carlos = cadastrarInstrutorTeste(
                    fachada,
                    "Carlos Magno",
                    "55544433322",
                    35,
                    "Musculação",
                    "senha456"
            );

            // 3. Associa Maria ao Carlos e cria treinos
            if (maria != null && carlos != null) {
                associarAlunoInstrutor(fachada, maria, carlos);
                criarTreinosTeste(fachada, maria, carlos);
            }

        } catch (Exception e) {
            System.err.println("Erro na inicialização: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private static Aluno cadastrarAlunoTeste(Fachada fachada, String nome, String cpf,
                                             int idade, String plano, String senha) {
        try {
            Aluno aluno = new Aluno(nome, cpf, idade, plano, senha);
            fachada.cadastrarAluno(aluno);
            System.out.println("Aluno cadastrado: " + nome);
            return aluno;
        } catch (AlunoJaCadastradoException e) {
            System.out.println("Aluno já existente: " + nome);
            try {
                return fachada.buscarAluno(cpf);
            } catch (AlunoNaoEncontradoException ex) {
                return null;
            }
        }
    }

    private static Instrutor cadastrarInstrutorTeste(Fachada fachada, String nome, String cpf,
                                                     int idade, String especialidade, String senha) {
        try {
            Instrutor instrutor = new Instrutor(nome, cpf, idade, especialidade, senha);
            fachada.cadastrarInstrutor(instrutor);
            System.out.println("Instrutor cadastrado: " + nome);
            return instrutor;
        } catch (InstrutorJaCadastradoException e) {
            System.out.println("Instrutor já existente: " + nome);
            try {
                return fachada.buscarInstrutor(cpf);
            } catch (InstrutorNaoEncontradoException ex) {
                return null;
            }
        }
    }

    private static void associarAlunoInstrutor(Fachada fachada, Aluno aluno, Instrutor instrutor) {
        // Implemente este método na Fachada se necessário
        System.out.println("Associação implícita via treinos (cadastro de treinos já associa)");
    }

    private static void criarTreinosTeste(Fachada fachada, Aluno aluno, Instrutor instrutor) {
        try {
            // Treino A
            Treino treinoA = fachada.criarTreino(
                    "Treino Inicial",
                    instrutor,
                    List.of(
                            new Exercicio("Supino Reto", "Peitoral"),
                            new Exercicio("Agachamento", "Pernas"),
                            new Exercicio("Barra Fixa", "Costas")
                    )
            );
            fachada.atribuirTreino(aluno.getCpf(), treinoA);

            // Treino B
            Treino treinoB = fachada.criarTreino(
                    "Treino Avançado",
                    instrutor,
                    List.of(
                            new Exercicio("Leg Press", "Pernas"),
                            new Exercicio("Rosca Direta", "Bíceps"),
                            new Exercicio("Tríceps Corda", "Tríceps")
                    )
            );
            fachada.atribuirTreino(aluno.getCpf(), treinoB);

            System.out.println("2 treinos criados e associados para " + aluno.getNome());

        } catch (Exception e) {
            System.err.println("Erro ao criar treinos: " + e.getMessage());
        }
    }
}