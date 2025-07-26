package model;

import java.util.ArrayList;
import java.util.List;

public class Instrutor extends Pessoa implements UsuarioSistema {
    private String especialidade;
    private List<Aluno> alunos = new ArrayList<>();
    private String senha;

    public Instrutor(String nome, String cpf, int idade, String especialidade, String senha) {
        super(nome, cpf, idade);
        this.especialidade = especialidade;
        this.senha = senha;
    }

    @Override
    public boolean autenticar(String senha) {
        return this.senha.equals(senha);
    }

    public String getEspecialidade() {
        return especialidade;
    }

    public List<Aluno> getAlunos() {
        return new ArrayList<>(alunos); // Retorna cópia para encapsulamento
    }

    public String getSenha() {
        return senha;
    }

    // Método único (removida a duplicação)
    public void adicionarAluno(Aluno aluno) {
        if (aluno != null && !alunos.contains(aluno)) {
            alunos.add(aluno);
            // Garante a associação bidirecional
            if (!aluno.getInstrutores().contains(this)) {
                aluno.adicionarInstrutor(this);
            }
        }
    }

    public void removerAluno(Aluno aluno) {
        if (aluno != null && alunos.contains(aluno)) {
            alunos.remove(aluno);
            // Remove a associação bidirecional
            if (aluno.getInstrutores().contains(this)) {
                aluno.getInstrutores().remove(this);
            }
        }
    }
}