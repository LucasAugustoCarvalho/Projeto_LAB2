package model;

public class Instrutor extends Pessoa {
    private final String especialidade;

    public Instrutor(String nome, String cpf, int idade, String especialidade) {
        super(nome, cpf, idade);
        this.especialidade = especialidade;
    }

    public String getEspecialidade() { return especialidade; }
}