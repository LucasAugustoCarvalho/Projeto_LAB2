package fachada;

import model.*;
import repository.*;
import exceptions.*;
import java.util.List;
import java.util.stream.Collectors;

public class Fachada {
    private static Fachada instancia;
    private AlunoRepository alunoRepository;
    private InstrutorRepository instrutorRepository;
    private TreinoRepository treinoRepository;

    private Fachada() {
        this.alunoRepository = AlunoRepository.getInstancia();
        this.instrutorRepository = InstrutorRepository.getInstancia();
        this.treinoRepository = TreinoRepository.getInstancia();
    }

    public static Fachada getInstancia() {
        if (instancia == null) {
            instancia = new Fachada();
        }
        return instancia;
    }

    // Métodos para Aluno
    public void cadastrarAluno(Aluno aluno) throws AlunoJaCadastradoException {
        alunoRepository.adicionar(aluno);
    }

    public Aluno buscarAluno(String cpf) throws AlunoNaoEncontradoException {
        Aluno aluno = alunoRepository.buscar(cpf);
        if (aluno == null) {
            throw new AlunoNaoEncontradoException(cpf);
        }
        return aluno;
    }

    public void removerAluno(String cpf) throws AlunoNaoEncontradoException {
        Aluno aluno = buscarAluno(cpf);
        alunoRepository.remover(cpf);
    }

    // Métodos para Instrutor
    public void cadastrarInstrutor(Instrutor instrutor) throws InstrutorJaCadastradoException {
        instrutorRepository.adicionar(instrutor);
    }

    public Instrutor buscarInstrutor(String cpf) throws InstrutorNaoEncontradoException {
        Instrutor instrutor = instrutorRepository.buscar(cpf);
        if (instrutor == null) {
            throw new InstrutorNaoEncontradoException(cpf);
        }
        return instrutor;
    }

    public void removerInstrutor(String cpf) throws InstrutorNaoEncontradoException {
        Instrutor instrutor = buscarInstrutor(cpf);
        instrutorRepository.remover(cpf);
    }

    // Métodos para Treino
    public Treino criarTreino(String nome, Instrutor instrutor, List<Exercicio> exercicios) {
        return new Treino(nome, instrutor, exercicios);
    }

    public void atribuirTreino(String cpfAluno, Treino treino) throws AlunoNaoEncontradoException {
        Aluno aluno = buscarAluno(cpfAluno);
        aluno.adicionarTreino(treino);
        treinoRepository.adicionar(treino);
    }

    public void atualizarTreino(String cpfInstrutor, String cpfAluno, String nomeTreino, List<Exercicio> exercicios)
            throws InstrutorNaoEncontradoException, AlunoNaoEncontradoException {
        Instrutor instrutor = buscarInstrutor(cpfInstrutor);
        Aluno aluno = buscarAluno(cpfAluno);

        Treino treinoAtualizado = new Treino(nomeTreino, instrutor, exercicios);
        treinoRepository.atualizar(treinoAtualizado);

        // Atualiza na lista de treinos do aluno
        aluno.getTreinos().removeIf(t -> t.getNome().equals(nomeTreino) &&
                t.getInstrutor().getCpf().equals(cpfInstrutor));
        aluno.adicionarTreino(treinoAtualizado);
    }

    public void removerTreino(String cpfInstrutor, String cpfAluno, String nomeTreino)
            throws InstrutorNaoEncontradoException, AlunoNaoEncontradoException {
        Instrutor instrutor = buscarInstrutor(cpfInstrutor);
        Aluno aluno = buscarAluno(cpfAluno);

        aluno.getTreinos().removeIf(t -> t.getNome().equals(nomeTreino) &&
                t.getInstrutor().getCpf().equals(cpfInstrutor));

        treinoRepository.remover(new Treino(nomeTreino, instrutor, null));
    }

    // Métodos para associação entre Aluno e Instrutor
    public void associarAlunoInstrutor(String cpfAluno, String cpfInstrutor)
            throws AlunoNaoEncontradoException, InstrutorNaoEncontradoException {

        Aluno aluno = buscarAluno(cpfAluno);
        Instrutor instrutor = buscarInstrutor(cpfInstrutor);

        aluno.adicionarInstrutor(instrutor);
        instrutor.adicionarAluno(aluno);
    }

    public List<Aluno> listarAlunosPorInstrutor(String cpfInstrutor) throws InstrutorNaoEncontradoException {
        Instrutor instrutor = buscarInstrutor(cpfInstrutor);
        return instrutor.getAlunos();
    }

    // Método de login
    public UsuarioSistema login(String cpf, String senha) throws LoginException {
        // Tenta encontrar como aluno primeiro
        try {
            Aluno aluno = buscarAluno(cpf);
            if (aluno.autenticar(senha)) {
                return aluno;
            }
        } catch (AlunoNaoEncontradoException e) {
            // Não faz nada, tenta como instrutor
        }

        // Tenta encontrar como instrutor
        try {
            Instrutor instrutor = buscarInstrutor(cpf);
            if (instrutor.autenticar(senha)) {
                return instrutor;
            }
        } catch (InstrutorNaoEncontradoException e) {
            // Não faz nada, lança exceção abaixo
        }

        throw new LoginException("CPF ou senha inválidos");
    }
}