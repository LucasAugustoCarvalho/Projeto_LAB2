package fachada;

import model.*;
import repository.*;
import exceptions.*;
import java.util.List;

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

    public void atualizarAluno(Aluno aluno) throws AlunoNaoEncontradoException {
        alunoRepository.atualizar(aluno);
    }

    public void removerAluno(String cpf) throws AlunoNaoEncontradoException {
        if (!alunoRepository.remover(cpf)) {
            throw new AlunoNaoEncontradoException(cpf);
        }
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

    public void atualizarInstrutor(Instrutor instrutor) throws InstrutorNaoEncontradoException {
        instrutorRepository.atualizar(instrutor);
    }

    public void removerInstrutor(String cpf) throws InstrutorNaoEncontradoException {
        if (!instrutorRepository.remover(cpf)) {
            throw new InstrutorNaoEncontradoException(cpf);
        }
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

    public void atualizarTreino(String cpfInstrutor, String cpfAluno, String nomeTreino,
                                List<Exercicio> exercicios)
            throws InstrutorNaoEncontradoException, AlunoNaoEncontradoException {

        Instrutor instrutor = buscarInstrutor(cpfInstrutor);
        Aluno aluno = buscarAluno(cpfAluno);

        Treino treinoAtualizado = new Treino(nomeTreino, instrutor, exercicios);
        treinoRepository.atualizar(treinoAtualizado);

        aluno.getTreinos().removeIf(t -> t.getNome().equals(nomeTreino) &&
                t.getInstrutor().getCpf().equals(cpfInstrutor));
        aluno.adicionarTreino(treinoAtualizado);
        alunoRepository.atualizar(aluno);
    }

    public void removerTreino(String cpfInstrutor, String cpfAluno, String nomeTreino)
            throws InstrutorNaoEncontradoException, AlunoNaoEncontradoException {

        Instrutor instrutor = buscarInstrutor(cpfInstrutor);
        Aluno aluno = buscarAluno(cpfAluno);

        aluno.getTreinos().removeIf(t -> t.getNome().equals(nomeTreino) &&
                t.getInstrutor().getCpf().equals(cpfInstrutor));
        alunoRepository.atualizar(aluno);

        treinoRepository.remover(new Treino(nomeTreino, instrutor, null));
    }

    // Métodos para Associação Aluno-Instrutor
    public void associarAlunoInstrutor(String cpfAluno, String cpfInstrutor)
            throws AlunoNaoEncontradoException, InstrutorNaoEncontradoException {

        Aluno aluno = buscarAluno(cpfAluno);
        Instrutor instrutor = buscarInstrutor(cpfInstrutor);

        aluno.adicionarInstrutor(instrutor);
        alunoRepository.atualizar(aluno);
        instrutorRepository.atualizar(instrutor);
    }

    public List<Aluno> listarAlunosPorInstrutor(String cpfInstrutor) throws InstrutorNaoEncontradoException {
        Instrutor instrutor = buscarInstrutor(cpfInstrutor);
        return instrutor.getAlunos();
    }

    // Método de Autenticação
    public UsuarioSistema login(String cpf, String senha) throws LoginException {
        // Tenta autenticar como aluno
        try {
            Aluno aluno = buscarAluno(cpf);
            if (aluno.autenticar(senha)) {
                return aluno;
            }
        } catch (AlunoNaoEncontradoException e) {
            // Continua para tentar como instrutor
        }

        // Tenta autenticar como instrutor
        try {
            Instrutor instrutor = buscarInstrutor(cpf);
            if (instrutor.autenticar(senha)) {
                return instrutor;
            }
        } catch (InstrutorNaoEncontradoException e) {
            // Lança exceção de login
        }

        throw new LoginException("CPF ou senha inválidos");
    }
}