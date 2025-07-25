package fachada;

import exceptions.*;
import java.util.List;
import model.*;
import repository.AlunoRepository;
import repository.InstrutorRepository;
import service.TreinoService;

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
        return instrutorRepository.buscar(cpf);
    }

    public void removerInstrutor(String cpf) throws InstrutorNaoEncontradoException {
        instrutorRepository.remover(cpf);
    }

    // Métodos para Treino
    public Treino criarTreino(String nome, Instrutor instrutor, List<Exercicio> exercicios)
            throws InstrutorNaoEncontradoException {
        // Valida se o instrutor existe
        instrutorRepository.buscar(instrutor.getCpf());
        return treinoService.criarTreino(nome, instrutor, exercicios);
    }

    /**
     * Atribui um treino a um aluno
     * @param cpfAluno CPF do aluno que receberá o treino
     * @param treino Treino a ser atribuído
     * @throws AlunoNaoEncontradoException se o aluno não for encontrado
     * @throws InstrutorNaoEncontradoException se o instrutor do treino não for encontrado
     */
    public void atribuirTreino(String cpfAluno, Treino treino)
            throws AlunoNaoEncontradoException, InstrutorNaoEncontradoException {
        // Valida se o aluno existe
        Aluno aluno = alunoRepository.buscar(cpfAluno);
        if (aluno == null) {
            throw new AlunoNaoEncontradoException(cpfAluno);
        }

        // Valida se o instrutor do treino existe
        instrutorRepository.buscar(treino.getInstrutor().getCpf());

        treinoService.atribuirTreino(aluno, treino);
    }
}