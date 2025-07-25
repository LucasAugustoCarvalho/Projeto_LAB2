package repository;

import model.Aluno;
import exceptions.AlunoJaCadastradoException;

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
        if (!alunos.containsKey(aluno.getCpf())) {
            alunos.put(aluno.getCpf(), aluno);
        } else {
            throw new AlunoJaCadastradoException(aluno.getCpf());
        }
    }

    public Aluno buscar(String cpf) {
        return alunos.get(cpf);
    }

    public void remover(String cpf) {
        alunos.remove(cpf);
    }

    public Collection<Aluno> listarTodos() {
        return alunos.values();
    }
}