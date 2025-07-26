// Instrutor.java
package model;

public class Instrutor extends Pessoa implements UsuarioSistema {
    private String especialidade;
    private String senha;

    public Instrutor(String nome, String cpf, int idade, String especialidade, String senha) {
        super(nome, cpf, idade);
        this.especialidade = especialidade;
        this.senha = senha;
    }

    public String getEspecialidade() { return especialidade; }

    @Override
    public String getSenha() {
        return senha;
    }

    @Override
    public boolean autenticar(String senha) {
        return this.senha.equals(senha);
    }
}