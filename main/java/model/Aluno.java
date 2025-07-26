// Aluno.java
package model;

import java.util.ArrayList;
import java.util.List;

public class Aluno extends Pessoa implements UsuarioSistema {
    private String plano;
    private List<Treino> treinos = new ArrayList<>();
    private String senha;

    public Aluno(String nome, String cpf, int idade, String plano, String senha) {
        super(nome, cpf, idade);
        this.plano = plano;
        this.senha = senha;
    }

    public String getPlano() { return plano; }
    public List<Treino> getTreinos() { return treinos; }
    public void adicionarTreino(Treino treino) { treinos.add(treino); }
    public String getCpf() { return this.cpf; }

    @Override
    public String getSenha() {
        return senha;
    }

    @Override
    public boolean autenticar(String senha) {
        return this.senha.equals(senha);
    }
}