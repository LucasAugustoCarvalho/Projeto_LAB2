package model;

import java.util.ArrayList;
import java.util.List;

public class Aluno extends Pessoa implements UsuarioSistema {
    private final String plano;
    private final List<Treino> treinos = new ArrayList<>();

    public Aluno(String nome, String cpf, int idade, String plano) {
        super(nome, cpf, idade);
        this.plano = plano;
    }

    public String getPlano() { return plano; }
    public List<Treino> getTreinos() { return treinos; }
    public void adicionarTreino(Treino treino) { treinos.add(treino); }
    @Override
    public String getCpf() { return this.cpf; }
}