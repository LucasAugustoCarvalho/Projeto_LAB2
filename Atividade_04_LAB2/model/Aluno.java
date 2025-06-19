package model;

import java.util.ArrayList;
import java.util.List;

public class Aluno extends Pessoa implements UsuarioSistema {
    private String plano;
    private List<Treino> treinos = new ArrayList<>();

    public Aluno(String nome, String cpf, int idade, String plano) {
        super(nome, cpf, idade);
        this.plano = plano;
    }

    public String getPlano() { return plano; }
    public List<Treino> getTreinos() { return treinos; }
    public void adicionarTreino(Treino treino) { treinos.add(treino); }
}

// Instrutor.java
package model;

public class Instrutor extends Pessoa {
    private String especialidade;

    public Instrutor(String nome, String cpf, int idade, String especialidade) {
        super(nome, cpf, idade);
        this.especialidade = especialidade;
    }

    public String getEspecialidade() { return especialidade; }
}