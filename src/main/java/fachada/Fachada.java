package fachada;

import exceptions.*;
import java.util.List;
import model.*;
import repository.AlunoRepository;
import service.TreinoService;

/**
 * Classe Fachada que implementa o padrão Facade para fornecer uma interface simplificada
 * para o sistema de academia, escondendo a complexidade das operações.
 * Implementa também o padrão Singleton para garantir uma única instância.
 */
public class Fachada {
    private static Fachada instance;
    private AlunoRepository alunoRepository;
    private TreinoService treinoService;

    private Fachada() {
        this.alunoRepository = AlunoRepository.getInstancia();
        this.treinoService = new TreinoService();
    }

    public static Fachada getInstancia() {
        if (instance == null) {
            instance = new Fachada();
        }
        return instance;
    }

    /**
     * Cadastra um novo aluno no sistema
     * @param aluno Objeto Aluno a ser cadastrado
     * @throws AlunoJaCadastradoException se o aluno já estiver cadastrado
     */
    public void cadastrarAluno(Aluno aluno) throws AlunoJaCadastradoException {
        alunoRepository.adicionar(aluno);
    }

    public Aluno buscarAluno(String cpf) {
        return alunoRepository.buscar(cpf);
    }

    public void removerAluno(String cpf) {
        alunoRepository.remover(cpf);
    }

    public List<Aluno> listarAlunos() {
        return List.copyOf(alunoRepository.listarTodos());
    }

    public Treino criarTreino(String nome, Instrutor instrutor, List<Exercicio> exercicios) {
        return treinoService.criarTreino(nome, instrutor, exercicios);
    }

    /**
     * Atribui um treino a um aluno
     * @param cpfAluno CPF do aluno que receberá o treino
     * @param treino Treino a ser atribuído
     * @throws AlunoNaoEncontradoException se o aluno não for encontrado
     */
    public void atribuirTreino(String cpfAluno, Treino treino) throws AlunoNaoEncontradoException {
        Aluno aluno = alunoRepository.buscar(cpfAluno);
        if (aluno != null) {
            treinoService.atribuirTreino(aluno, treino);
        } else {
            throw new AlunoNaoEncontradoException(cpfAluno);
        }
    }
}