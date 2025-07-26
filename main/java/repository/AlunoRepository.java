package repository;

import model.Aluno;
import exceptions.AlunoJaCadastradoException;
import exceptions.AlunoNaoEncontradoException;
import java.util.*;

public class AlunoRepository {
    private static AlunoRepository instancia;
    private Map<String, Aluno> alunos = new HashMap<>();

    private AlunoRepository() {}

    public static AlunoRepository getInstancia() {
        if (instancia == null) {
            instancia = new AlunoRepository();
        }
        return instancia;
    }

    public void adicionar(Aluno aluno) throws AlunoJaCadastradoException {
        if (aluno == null) {
            throw new IllegalArgumentException("Aluno não pode ser nulo");
        }
        if (alunos.containsKey(aluno.getCpf())) {
            throw new AlunoJaCadastradoException(aluno.getCpf());
        }
        alunos.put(aluno.getCpf(), aluno);
    }

    public Aluno buscar(String cpf) {
        return alunos.get(cpf);
    }

    public boolean remover(String cpf) {
        return alunos.remove(cpf) != null;
    }

    // metodo de atualizar
    public void atualizar(Aluno aluno) throws AlunoNaoEncontradoException {
        if (aluno == null) {
            throw new IllegalArgumentException("Aluno não pode ser nulo");
        }
        if (!alunos.containsKey(aluno.getCpf())) {
            throw new AlunoNaoEncontradoException(aluno.getCpf());
        }
        alunos.put(aluno.getCpf(), aluno);
    }

    public Collection<Aluno> listarTodos() {
        return new ArrayList<>(alunos.values());
    }
}