package repository;

import model.Aluno;
import java.util.*;

public class AlunoRepository {
    private Map<String, Aluno> alunos = new HashMap<>();

    public void adicionar(Aluno aluno) {
        if (!alunos.containsKey(aluno.getCpf())) {
            alunos.put(aluno.getCpf(), aluno);
        } else {
            System.out.println("Aluno já cadastrado.");
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