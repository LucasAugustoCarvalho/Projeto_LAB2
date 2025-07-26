package main;

import fachada.Fachada;
import model.*;
import exceptions.*;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        try {
            Fachada sistema = Fachada.getInstancia();

            // 1. Teste de Cadastro do Aluno com senha
            testarCadastroAluno(sistema);

            // 2. Teste de Login do Aluno
            testarLoginAluno(sistema);

            // 3. Teste de Cadastro do Instrutor com senha
            testarCadastroInstrutor(sistema);

            // 4. Teste de Login do Instrutor
            testarLoginInstrutor(sistema);

            // 5. Teste de Detalhes do Aluno (após login)
            testarDetalhesAluno(sistema);

            // 6. Teste de Visualizar Instrutor (após login)
            testarVisualizarInstrutor(sistema);

            // 7. Teste de Cadastrar Treino (após login)
            testarCadastrarTreino(sistema);

            // 8. Teste de Exclusão de Aluno
            testarExcluirAluno(sistema);

            // 9. Teste de Exclusão de Instrutor
            testarExcluirInstrutor(sistema);

            // 10. Teste tentando excluir aluno não existente
            testarExcluirAlunoInexistente(sistema);

            // 11. Teste tentando excluir instrutor não existente
            testarExcluirInstrutorInexistente(sistema);

            // 12. Teste de login inválido
            testarLoginInvalido(sistema);

        } catch (Exception e) {
            System.err.println("Erro durante os testes: " + e.getMessage());
        }
    }

    // 1. Teste de Cadastro do Aluno com senha
    private static void testarCadastroAluno(Fachada sistema) throws Exception {
        System.out.println("\n=== 1. TESTE CADASTRO ALUNO COM SENHA ===");

        Aluno aluno1 = new Aluno("Maria Silva", "11122233344", 28, "Plano Anual", "senhaMaria");
        sistema.cadastrarAluno(aluno1);
        System.out.println("   Aluno cadastrado com sucesso:");
        System.out.println("   Nome: " + aluno1.getNome());
        System.out.println("   CPF: " + aluno1.getCpf());
        System.out.println("   Plano: " + aluno1.getPlano());
    }

    // 2. Teste de Login do Aluno
    private static void testarLoginAluno(Fachada sistema) throws Exception {
        System.out.println("\n=== 2. TESTE LOGIN ALUNO ===");

        UsuarioSistema usuario = sistema.login("11122233344", "senhaMaria");

        if (usuario instanceof Aluno) {
            Aluno aluno = (Aluno) usuario;
            System.out.println("   Login bem-sucedido:");
            System.out.println("   Nome: " + aluno.getNome());
            System.out.println("   Tipo: Aluno");
        }
    }

    // 3. Teste de Cadastro do Instrutor com senha
    private static void testarCadastroInstrutor(Fachada sistema) throws Exception {
        System.out.println("\n=== 3. TESTE CADASTRO INSTRUTOR COM SENHA ===");

        Instrutor instrutor = new Instrutor("Carlos Magno", "55544433322", 35, "Musculação", "senhaCarlos");
        sistema.cadastrarInstrutor(instrutor);
        System.out.println("   Instrutor cadastrado com sucesso:");
        System.out.println("   Nome: " + instrutor.getNome());
        System.out.println("   Especialidade: " + instrutor.getEspecialidade());
        System.out.println("   CPF: " + instrutor.getCpf());
    }

    // 4. Teste de Login do Instrutor
    private static void testarLoginInstrutor(Fachada sistema) throws Exception {
        System.out.println("\n=== 4. TESTE LOGIN INSTRUTOR ===");

        UsuarioSistema usuario = sistema.login("55544433322", "senhaCarlos");

        if (usuario instanceof Instrutor) {
            Instrutor instrutor = (Instrutor) usuario;
            System.out.println("   Login bem-sucedido:");
            System.out.println("   Nome: " + instrutor.getNome());
            System.out.println("   Tipo: Instrutor");
            System.out.println("   Especialidade: " + instrutor.getEspecialidade());
        }
    }

    // 5. Teste de Detalhes do Aluno (após login)
    private static void testarDetalhesAluno(Fachada sistema) throws Exception {
        System.out.println("\n=== 5. TESTE DETALHES DO ALUNO (APÓS LOGIN) ===");

        // Faz login primeiro
        sistema.login("11122233344", "senhaMaria");

        Aluno aluno = sistema.buscarAluno("11122233344");
        System.out.println("   Detalhes do aluno:");
        System.out.println("   Nome: " + aluno.getNome());
        System.out.println("   Idade: " + aluno.getIdade());
        System.out.println("   CPF: " + aluno.getCpf());
        System.out.println("   Plano: " + aluno.getPlano());
        System.out.println("   Quantidade de treinos: " + aluno.getTreinos().size());
    }

    // 6. Teste de Visualizar Instrutor (após login)
    private static void testarVisualizarInstrutor(Fachada sistema) throws Exception {
        System.out.println("\n=== 6. TESTE VISUALIZAR INSTRUTOR (APÓS LOGIN) ===");

        // Faz login primeiro
        sistema.login("55544433322", "senhaCarlos");

        Instrutor instrutor = sistema.buscarInstrutor("55544433322");
        System.out.println("   Detalhes do instrutor:");
        System.out.println("   Nome: " + instrutor.getNome());
        System.out.println("   Especialidade: " + instrutor.getEspecialidade());
        System.out.println("   Idade: " + instrutor.getIdade());
        System.out.println("   CPF: " + instrutor.getCpf());
    }

    // 7. Teste de Cadastrar Treino (após login)
    private static void testarCadastrarTreino(Fachada sistema) throws Exception {
        System.out.println("\n=== 7. TESTE CADASTRAR TREINO (APÓS LOGIN) ===");

        // Faz login como instrutor
        sistema.login("55544433322", "senhaCarlos");

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

        System.out.println("   Treino cadastrado com sucesso:");
        System.out.println("   Nome do treino: " + treino.getNome());
        System.out.println("   Instrutor responsável: " + treino.getInstrutor().getNome());
        System.out.println("   Quantidade de exercícios: " + treino.getExercicios().size());

        // Mostra detalhes do treino atribuído ao aluno
        System.out.println("\n   Detalhes do treino atribuído:");
        for (Exercicio ex : treino.getExercicios()) {
            System.out.println("   - " + ex.getNome() + " (" + ex.getGrupoMuscular() + ")");
        }
    }

    // 8. Teste de Exclusão de Aluno
    private static void testarExcluirAluno(Fachada sistema) throws Exception {
        System.out.println("\n=== 8. TESTE EXCLUIR ALUNO ===");

        // Primeiro verifica se o aluno existe
        Aluno aluno = sistema.buscarAluno("11122233344");
        System.out.println("   Aluno encontrado: " + aluno.getNome());

        // Exclui o aluno
        sistema.removerAluno("11122233344");
        System.out.println("   Aluno excluído com sucesso!");

        try {
            // Tenta buscar o aluno excluído (deve lançar exceção)
            sistema.buscarAluno("11122233344");
        } catch (AlunoNaoEncontradoException e) {
            System.out.println("   OK: " + e.getMessage());
        }
    }

    // 9. Teste de Exclusão de Instrutor
    private static void testarExcluirInstrutor(Fachada sistema) throws Exception {
        System.out.println("\n=== 9. TESTE EXCLUIR INSTRUTOR ===");

        // Primeiro verifica se o instrutor existe
        Instrutor instrutor = sistema.buscarInstrutor("55544433322");
        System.out.println("   Instrutor encontrado: " + instrutor.getNome());

        // Exclui o instrutor
        sistema.removerInstrutor("55544433322");
        System.out.println("   Instrutor excluído com sucesso!");

        try {
            // Tenta buscar o instrutor excluído (deve lançar exceção)
            sistema.buscarInstrutor("55544433322");
        } catch (InstrutorNaoEncontradoException e) {
            System.out.println("   OK: " + e.getMessage());
        }
    }

    // 10. Teste tentando excluir aluno não existente
    private static void testarExcluirAlunoInexistente(Fachada sistema) {
        System.out.println("\n=== 10. TESTE EXCLUIR ALUNO INEXISTENTE ===");

        try {
            sistema.removerAluno("99988877766");
        } catch (AlunoNaoEncontradoException e) {
            System.out.println("   OK: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("   ERRO INESPERADO: " + e.getMessage());
        }
    }

    // 11. Teste tentando excluir instrutor não existente
    private static void testarExcluirInstrutorInexistente(Fachada sistema) {
        System.out.println("\n=== 11. TESTE EXCLUIR INSTRUTOR INEXISTENTE ===");

        try {
            sistema.removerInstrutor("99988877766");
        } catch (InstrutorNaoEncontradoException e) {
            System.out.println("   OK: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("   ERRO INESPERADO: " + e.getMessage());
        }
    }

    // 12. Teste de login inválido
    private static void testarLoginInvalido(Fachada sistema) {
        System.out.println("\n=== 12. TESTE LOGIN INVÁLIDO ===");

        try {
            // Tentativa com senha errada
            sistema.login("11122233344", "senhaErrada");
            System.err.println("   ERRO: Login com senha inválida não lançou exceção");
        } catch (LoginException e) {
            System.out.println("   OK (senha errada): " + e.getMessage());
        }

        try {
            // Tentativa com CPF não cadastrado
            sistema.login("00000000000", "qualquerSenha");
            System.err.println("   ERRO: Login com CPF não cadastrado não lançou exceção");
        } catch (LoginException e) {
            System.out.println("   OK (CPF não cadastrado): " + e.getMessage());
        }
    }
}