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

    // Implementação do método da interface UsuarioSistema
    @Override
    public boolean autenticar(String senha) {
        return this.senha.equals(senha);
    }

    // Getters e Setters
    public String getEspecialidade() { return especialidade; }
    public List<Aluno> getAlunos() { return alunos; }
    public String getSenha() { return senha; }

    public void adicionarAluno(Aluno aluno) {
        if (!alunos.contains(aluno)) {
            alunos.add(aluno);
        }
    }
}