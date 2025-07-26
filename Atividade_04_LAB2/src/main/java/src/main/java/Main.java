package main;

import fachada.Fachada;
import model.*;
import exceptions.*;

import java.util.List;

/**
 * Classe principal para testar o sistema de academia.
 * Demonstra o uso da fachada e testa os principais cenários.
 */
public class Main {
    public static void main(String[] args) {
        try {
            Fachada sistema = Fachada.getInstancia();
            
            testarCadastroAluno(sistema);
            testarTreino(sistema);
            testarValidacoes();
            
        } catch (Exception e) {
            System.err.println("Erro: " + e.getMessage());
        }
    }

    private static void testarCadastroAluno(Fachada sistema) throws Exception {
        System.out.println("=== TESTE CADASTRO ALUNO ===");
        Aluno aluno1 = new Aluno("Lucas", "12345678900", 25, "Mensal");
        sistema.cadastrarAluno(aluno1);
        System.out.println("Aluno cadastrado com sucesso: " + aluno1.getNome());
        
        // Testar duplicado
        try {
            sistema.cadastrarAluno(aluno1);
        } catch (AlunoJaCadastradoException e) {
            System.out.println("Esperado: " + e.getMessage());
        }
    }

    private static void testarTreino(Fachada sistema) throws Exception {
        System.out.println("\n=== TESTE TREINO ===");
        Aluno aluno = sistema.buscarAluno("12345678900");
        Instrutor instrutor = new Instrutor("João", "00011122233", 30, "Musculação");

        List<Exercicio> exercicios = List.of(
            new Exercicio("Supino", "Peitoral"),
            new Exercicio("Agachamento", "Pernas")
        );

        Treino treino = sistema.criarTreino("Treino A", instrutor, exercicios);
        sistema.atribuirTreino(aluno.getCpf(), treino);
        System.out.println("Treino atribuído com sucesso ao aluno " + aluno.getNome());

        // Testar visualização
        System.out.println("\nDetalhes do aluno:");
        System.out.println("Aluno: " + aluno.getNome());
        for (Treino t : aluno.getTreinos()) {
            System.out.println("- " + t.getNome() + " (Instrutor: " + t.getInstrutor().getNome() + ")");
            for (Exercicio e : t.getExercicios()) {
                System.out.println("  > " + e.getNome() + " (" + e.getGrupoMuscular() + ")");
            }
        }
        
        // Testar aluno não encontrado
        try {
            sistema.atribuirTreino("00000000000", treino);
        } catch (AlunoNaoEncontradoException e) {
            System.out.println("\nEsperado: " + e.getMessage());
        }
    }

    private static void testarValidacoes() {
        System.out.println("\n=== TESTE VALIDAÇÕES ===");
        try {
            new Aluno("", "12345678901", 25, "Mensal");
        } catch (IllegalArgumentException e) {
            System.out.println("Esperado: " + e.getMessage());
        }

        try {
            new Aluno("Maria", "123", 25, "Mensal");
        } catch (IllegalArgumentException e) {
            System.out.println("Esperado: " + e.getMessage());
        }

        try {
            new Aluno("Carlos", "98765432100", -5, "Anual");
        } catch (IllegalArgumentException e) {
            System.out.println("Esperado: " + e.getMessage());
        }
    }
}