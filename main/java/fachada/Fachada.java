package fachada;

import exceptions.*;
import model.*;
import repository.AlunoRepository;
import repository.InstrutorRepository;
import service.TreinoService;
import java.util.List;

public class Fachada {
    private static Fachada instance;
    private AlunoRepository alunoRepository;
    private InstrutorRepository instrutorRepository;
    private TreinoService treinoService;

    private Fachada() {
        this.alunoRepository = AlunoRepository.getInstancia();
        this.instrutorRepository = InstrutorRepository.getInstancia();
        this.treinoService = new TreinoService();
    }

    public static Fachada getInstancia() {
        if (instance == null) {
            instance = new Fachada();
        }
        return instance;
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
        if (alunoRepository.buscar(cpf) == null) {
            throw new AlunoNaoEncontradoException(cpf);
        }
        alunoRepository.remover(cpf);
    }

    public List<Aluno> listarAlunos() {
        return List.copyOf(alunoRepository.listarTodos());
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
        if (instrutorRepository.buscar(cpf) == null) {
            throw new InstrutorNaoEncontradoException(cpf);
        }
        instrutorRepository.remover(cpf);
    }

    // Métodos para Treino
    public Treino criarTreino(String nome, Instrutor instrutor, List<Exercicio> exercicios)
            throws InstrutorNaoEncontradoException {
        // Valida se o instrutor existe
        buscarInstrutor(instrutor.getCpf());
        return treinoService.criarTreino(nome, instrutor, exercicios);
    }

    public void atribuirTreino(String cpfAluno, Treino treino)
            throws AlunoNaoEncontradoException, InstrutorNaoEncontradoException {
        // Valida se o aluno existe
        Aluno aluno = buscarAluno(cpfAluno);
        // Valida se o instrutor do treino existe
        buscarInstrutor(treino.getInstrutor().getCpf());
        treinoService.atribuirTreino(aluno, treino);
    }

    // Métodos de autenticação
    public UsuarioSistema login(String cpf, String senha) throws LoginException {
        // Primeiro tenta como aluno
        try {
            Aluno aluno = buscarAluno(cpf);
            if (aluno != null && aluno.autenticar(senha)) {
                return aluno;
            }
        } catch (AlunoNaoEncontradoException e) {
            // Continua para tentar como instrutor
        }

        // Se não encontrou como aluno, tenta como instrutor
        try {
            Instrutor instrutor = buscarInstrutor(cpf);
            if (instrutor != null && instrutor.autenticar(senha)) {
                return instrutor;
            }
        } catch (InstrutorNaoEncontradoException e) {
            // Lança exceção de login
        }

        throw new LoginException("CPF ou senha inválidos");
    }

    public void logout() {
        // Implementação para limpar sessão se necessário
    }
}