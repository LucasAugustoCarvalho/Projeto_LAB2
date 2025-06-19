package model;

public class Exercicio {
    private String nome;
    private String grupoMuscular;

    public Exercicio(String nome, String grupoMuscular) {
        this.nome = nome;
        this.grupoMuscular = grupoMuscular;
    }

    public String getNome() { return nome; }
    public String getGrupoMuscular() { return grupoMuscular; }
}

// UsuarioSistema.java (Interface)
package model;

public interface UsuarioSistema {
    String getCpf();
}