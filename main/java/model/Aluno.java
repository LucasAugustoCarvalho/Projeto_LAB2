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

    @Override
    public boolean autenticar(String senha) {
        return this.senha.equals(senha);
    }

    public String getPlano() {
        return plano;
    }

    public List<Treino> getTreinos() {
        return new ArrayList<>(treinos);
    }

    public List<Instrutor> getInstrutores() {
        return new ArrayList<>(instrutores);
    }

    public String getSenha() {
        return senha;
    }

    public void adicionarTreino(Treino treino) {
        if (treino != null && !treinos.contains(treino)) {
            treinos.add(treino);
            // adiciona o instrutor se não estiver associado
            if (!instrutores.contains(treino.getInstrutor())) {
                adicionarInstrutor(treino.getInstrutor());
            }
        }
    }

    public void adicionarInstrutor(Instrutor instrutor) {
        if (instrutor != null && !instrutores.contains(instrutor)) {
            instrutores.add(instrutor);
            // garante a associação bidirecional
            if (!instrutor.getAlunos().contains(this)) {
                instrutor.adicionarAluno(this);
            }
        }
    }
}