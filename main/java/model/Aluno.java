package model;

import java.util.ArrayList;
import java.util.List;

public class Aluno extends Pessoa implements UsuarioSistema {
    private String plano;
    private List<Treino> treinos = new ArrayList<>();
    private List<Instrutor> instrutores = new ArrayList<>();
    private String senha;

    public Aluno(String nome, String cpf, int idade, String plano, String senha) {
        super(nome, cpf, idade);
        this.plano = plano;
        this.senha = senha;
    }

    // Implementação do método da interface UsuarioSistema
    @Override
    public boolean autenticar(String senha) {
        return this.senha.equals(senha);
    }

    // Getters e Setters
    public String getPlano() { return plano; }
    public List<Treino> getTreinos() { return treinos; }
    public List<Instrutor> getInstrutores() { return instrutores; }
    public String getSenha() { return senha; }

    public void adicionarTreino(Treino treino) {
        treinos.add(treino);
        if (!instrutores.contains(treino.getInstrutor())) {
            instrutores.add(treino.getInstrutor());
        }
    }

    public void adicionarInstrutor(Instrutor instrutor) {
        if (!instrutores.contains(instrutor)) {
            instrutores.add(instrutor);
        }
    }
}