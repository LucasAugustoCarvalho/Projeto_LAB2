package model;

import java.util.List;

public class Treino {
    private String nome;
    private Instrutor instrutor;
    private List<Exercicio> exercicios;

    public Treino(String nome, Instrutor instrutor, List<Exercicio> exercicios) {
        this.nome = nome;
        this.instrutor = instrutor;
        this.exercicios = exercicios;
    }

    public String getNome() { return nome; }
    public Instrutor getInstrutor() { return instrutor; }
    public List<Exercicio> getExercicios() { return exercicios; }
}
