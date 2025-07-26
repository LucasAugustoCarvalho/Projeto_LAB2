package main;

import fachada.Fachada;
import model.*;
import exceptions.*;

import java.util.List;

/**
 * Classe principal para testar o sistema de academia.
 * Demonstra os principais cenários de cadastro e visualização.
 */
public class Main {
    public static void main(String[] args) {
        try {
            Fachada sistema = Fachada.getInstancia();

            // 1. Teste de Cadastro do Aluno
            testarCadastroAluno(sistema);

            // 2. Teste de Detalhes do Aluno
            testarDetalhesAluno(sistema);

            // 3. Teste de Cadastro do Instrutor
            testarCadastroInstrutor(sistema);

            // 4. Teste de Visualizar Instrutor
            testarVisualizarInstrutor(sistema);

            // 5. Teste de Cadastrar Treino
            testarCadastrarTreino(sistema);

        } catch (Exception e) {
            System.err.println("Erro durante os testes: " + e.getMessage());
        }
    }

    // 1. Teste de Cadastro do Aluno
    private static void testarCadastroAluno(Fachada sistema) throws Exception {
        System.out.println("\n=== 1. TESTE CADASTRO ALUNO ===");

        Aluno aluno1 = new Aluno("Maria Silva", "11122233344", 28, "Plano Anual");
        sistema.cadastrarAluno(aluno1);
        System.out.println("✅ Aluno cadastrado com sucesso:");
        System.out.println("   Nome: " + aluno1.getNome());
        System.out.println("   CPF: " + aluno1.getCpf());
        System.out.println("   Plano: " + aluno1.getPlano());

        // Tentativa de cadastro duplicado
        try {
            sistema.cadastrarAluno(aluno1);
        } catch (AlunoJaCadastradoException e) {
            System.out.println("✅ Comportamento esperado (duplicado): " + e.getMessage());
        }
    }


    // 3. Teste de Cadastro do Instrutor
    private static void testarCadastroInstrutor(Fachada sistema) throws Exception {
        System.out.println("\n=== 3. TESTE CADASTRO INSTRUTOR ===");

        Instrutor instrutor = new Instrutor("Carlos Magno", "55544433322", 35, "Musculação");
        sistema.cadastrarInstrutor(instrutor);
        System.out.println("✅ Instrutor cadastrado com sucesso:");
        System.out.println("   Nome: " + instrutor.getNome());
        System.out.println("   Especialidade: " + instrutor.getEspecialidade());
        System.out.println("   CPF: " + instrutor.getCpf());

        // Tentativa de cadastro duplicado
        try {
            sistema.cadastrarInstrutor(instrutor);
        } catch (InstrutorJaCadastradoException e) {
            System.out.println("✅ Comportamento esperado (duplicado): " + e.getMessage());
        }
    }
    // 2. Teste de Detalhes do Aluno
    private static void testarDetalhesAluno(Fachada sistema) throws Exception {
        System.out.println("\n=== 2. TESTE DETALHES DO ALUNO ===");

        Aluno aluno = sistema.buscarAluno("11122233344");
        System.out.println("✅ Detalhes do aluno encontrado:");
        System.out.println("   Nome: " + aluno.getNome());
        System.out.println("   Idade: " + aluno.getIdade());
        System.out.println("   CPF: " + aluno.getCpf());
        System.out.println("   Plano: " + aluno.getPlano());
        System.out.println("   Quantidade de treinos: " + aluno.getTreinos().size());
    }

    // 4. Teste de Visualizar Instrutor
    private static void testarVisualizarInstrutor(Fachada sistema) throws Exception {
        System.out.println("\n=== 4. TESTE VISUALIZAR INSTRUTOR ===");

        Instrutor instrutor = sistema.buscarInstrutor("55544433322");
        System.out.println("✅ Detalhes do instrutor:");
        System.out.println("   Nome: " + instrutor.getNome());
        System.out.println("   Especialidade: " + instrutor.getEspecialidade());
        System.out.println("   Idade: " + instrutor.getIdade());
        System.out.println("   CPF: " + instrutor.getCpf());
    }

    // 5. Teste de Cadastrar Treino
    private static void testarCadastrarTreino(Fachada sistema) throws Exception {
        System.out.println("\n=== 5. TESTE CADASTRAR TREINO ===");

        // Busca os registros necessários
        Aluno aluno = sistema.buscarAluno("11122233344");
        Instrutor instrutor = sistema.buscarInstrutor("55544433322");

        // Cria exercícios
        List<Exercicio> exercicios = List.of(
                new Exercicio("Supino Reto", "Peitoral"),
                new Exercicio("Leg Press", "Pernas"),
                new Exercicio("Puxada Alta", "Costas")
        );

        // Cria e atribui o treino
        Treino treino = sistema.criarTreino("Treino Inicial", instrutor, exercicios);
        sistema.atribuirTreino(aluno.getCpf(), treino);

        System.out.println("✅ Treino cadastrado com sucesso:");
        System.out.println("   Nome do treino: " + treino.getNome());
        System.out.println("   Instrutor responsável: " + treino.getInstrutor().getNome());
        System.out.println("   Quantidade de exercícios: " + treino.getExercicios().size());

        // Mostra detalhes do treino atribuído ao aluno
        System.out.println("\n📋 Detalhes do treino atribuído:");
        for (Exercicio ex : treino.getExercicios()) {
            System.out.println("   - " + ex.getNome() + " (" + ex.getGrupoMuscular() + ")");
        }
    }
}