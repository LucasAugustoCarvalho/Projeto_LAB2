package main;

import fachada.Fachada;
import model.*;
import exceptions.*;

import java.util.List;

// main para testes no terminaal
public class Main {
    public static void main(String[] args) {
        try {
            Fachada sistema = Fachada.getInstancia();

            // teste de cadastro do aluno com senha
            testarCadastroAluno(sistema);

            // teste de login do aluno
            testarLoginAluno(sistema);

            // teste de cadastro do instrutor com senha
            testarCadastroInstrutor(sistema);

            //teste de login do instrutor
            testarLoginInstrutor(sistema);

            // teste de detalhes do aluno (apos login)
            testarDetalhesAluno(sistema);

            //teste de visualizar instrutor (apos login)
            testarVisualizarInstrutor(sistema);

            // teste de cadastrar treino (apos login)
            testarCadastrarTreino(sistema);

            // teste de exclusão de aluno
            testarExcluirAluno(sistema);

            // teste de exclusão de instrutor
            testarExcluirInstrutor(sistema);

            // teste tentando excluir aluno não existente
            testarExcluirAlunoInexistente(sistema);

            // teste tentando excluir instrutor não existente
            testarExcluirInstrutorInexistente(sistema);

            // teste de login inválido
            testarLoginInvalido(sistema);

        } catch (Exception e) {
            System.err.println("Erro durante os testes: " + e.getMessage());
        }
    }

    // teste de cadastro do aluno com senha
    private static void testarCadastroAluno(Fachada sistema) throws Exception {
        System.out.println("\n=== 1. TESTE CADASTRO ALUNO COM SENHA ===");

        Aluno aluno1 = new Aluno("Maria Silva", "11122233344", 28, "Plano Anual", "senhaMaria");
        sistema.cadastrarAluno(aluno1);
        System.out.println("   Aluno cadastrado com sucesso:");
        System.out.println("   Nome: " + aluno1.getNome());
        System.out.println("   CPF: " + aluno1.getCpf());
        System.out.println("   Plano: " + aluno1.getPlano());
    }

    // teste de login do Aluno
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

    // teste de cadastro do instrutor com senha
    private static void testarCadastroInstrutor(Fachada sistema) throws Exception {
        System.out.println("\n=== 3. TESTE CADASTRO INSTRUTOR COM SENHA ===");

        Instrutor instrutor = new Instrutor("Carlos Magno", "55544433322", 35, "Musculação", "senhaCarlos");
        sistema.cadastrarInstrutor(instrutor);
        System.out.println("   Instrutor cadastrado com sucesso:");
        System.out.println("   Nome: " + instrutor.getNome());
        System.out.println("   Especialidade: " + instrutor.getEspecialidade());
        System.out.println("   CPF: " + instrutor.getCpf());
    }

    // teste de login do instrutor
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

    // teste de detalhes do aluno apos login
    private static void testarDetalhesAluno(Fachada sistema) throws Exception {
        System.out.println("\n=== 5. TESTE DETALHES DO ALUNO (APÓS LOGIN) ===");

        // login primeiro
        sistema.login("11122233344", "senhaMaria");

        Aluno aluno = sistema.buscarAluno("11122233344");
        System.out.println("   Detalhes do aluno:");
        System.out.println("   Nome: " + aluno.getNome());
        System.out.println("   Idade: " + aluno.getIdade());
        System.out.println("   CPF: " + aluno.getCpf());
        System.out.println("   Plano: " + aluno.getPlano());
        System.out.println("   Quantidade de treinos: " + aluno.getTreinos().size());
    }

    // teste de visualizar instrutor apos login
    private static void testarVisualizarInstrutor(Fachada sistema) throws Exception {
        System.out.println("\n=== 6. TESTE VISUALIZAR INSTRUTOR (APÓS LOGIN) ===");

        // primeiro login
        sistema.login("55544433322", "senhaCarlos");

        Instrutor instrutor = sistema.buscarInstrutor("55544433322");
        System.out.println("   Detalhes do instrutor:");
        System.out.println("   Nome: " + instrutor.getNome());
        System.out.println("   Especialidade: " + instrutor.getEspecialidade());
        System.out.println("   Idade: " + instrutor.getIdade());
        System.out.println("   CPF: " + instrutor.getCpf());
    }

    // teste de cadastrar treino apos login
    private static void testarCadastrarTreino(Fachada sistema) throws Exception {
        System.out.println("\n=== 7. TESTE CADASTRAR TREINO (APÓS LOGIN) ===");

        //login como instrutor
        sistema.login("55544433322", "senhaCarlos");

        // busca
        Aluno aluno = sistema.buscarAluno("11122233344");
        Instrutor instrutor = sistema.buscarInstrutor("55544433322");

        // cria exercícios
        List<Exercicio> exercicios = List.of(
                new Exercicio("Supino Reto", "Peitoral"),
                new Exercicio("Leg Press", "Pernas"),
                new Exercicio("Puxada Alta", "Costas")
        );

        // cria e atribui o treino
        Treino treino = sistema.criarTreino("Treino Inicial", instrutor, exercicios);
        sistema.atribuirTreino(aluno.getCpf(), treino);

        System.out.println("   Treino cadastrado com sucesso:");
        System.out.println("   Nome do treino: " + treino.getNome());
        System.out.println("   Instrutor responsável: " + treino.getInstrutor().getNome());
        System.out.println("   Quantidade de exercícios: " + treino.getExercicios().size());

        // mostra treino atribuído ao aluno
        System.out.println("\n   Detalhes do treino atribuído:");
        for (Exercicio ex : treino.getExercicios()) {
            System.out.println("   - " + ex.getNome() + " (" + ex.getGrupoMuscular() + ")");
        }
    }

    // teste de exclusão de aluno
    private static void testarExcluirAluno(Fachada sistema) throws Exception {
        System.out.println("\n=== 8. TESTE EXCLUIR ALUNO ===");

        // primeiro verifica se o aluno existe
        Aluno aluno = sistema.buscarAluno("11122233344");
        System.out.println("   Aluno encontrado: " + aluno.getNome());

        // exclui o aluno
        sistema.removerAluno("11122233344");
        System.out.println("   Aluno excluído com sucesso!");

        try {
            // tenta buscar o aluno excluído
            sistema.buscarAluno("11122233344");
        } catch (AlunoNaoEncontradoException e) {
            System.out.println("   OK: " + e.getMessage());
        }
    }

    // teste de exclusão de instrutor
    private static void testarExcluirInstrutor(Fachada sistema) throws Exception {
        System.out.println("\n=== 9. TESTE EXCLUIR INSTRUTOR ===");

        // primeiro verifica se o instrutor existe
        Instrutor instrutor = sistema.buscarInstrutor("55544433322");
        System.out.println("   Instrutor encontrado: " + instrutor.getNome());

        // exclui o instrutor
        sistema.removerInstrutor("55544433322");
        System.out.println("   Instrutor excluído com sucesso!");

        try {
            // tenta buscar o instrutor excluído
            sistema.buscarInstrutor("55544433322");
        } catch (InstrutorNaoEncontradoException e) {
            System.out.println("   OK: " + e.getMessage());
        }
    }

    // teste tentando excluir aluno não existente
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

    // teste tentando excluir instrutor não existente
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

    // teste de login invalido
    private static void testarLoginInvalido(Fachada sistema) {
        System.out.println("\n=== 12. TESTE LOGIN INVÁLIDO ===");

        try {
            // tentativa com senha errada
            sistema.login("11122233344", "senhaErrada");
            System.err.println("   ERRO: Login com senha inválida não lançou exceção");
        } catch (LoginException e) {
            System.out.println("   OK (senha errada): " + e.getMessage());
        }

        try {
            // tentativa com CPF não cadastrado
            sistema.login("00000000000", "qualquerSenha");
            System.err.println("   ERRO: Login com CPF não cadastrado não lançou exceção");
        } catch (LoginException e) {
            System.out.println("   OK (CPF não cadastrado): " + e.getMessage());
        }
    }
}