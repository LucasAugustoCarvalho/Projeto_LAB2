package model;

public class Exercicio {
    private final String nome;
    private final String grupoMuscular;

    public Exercicio(String nome, String grupoMuscular) {
        this.nome = nome;
        this.grupoMuscular = grupoMuscular;
    }

    public String getNome() { return nome; }
    public String getGrupoMuscular() { return grupoMuscular; }
}