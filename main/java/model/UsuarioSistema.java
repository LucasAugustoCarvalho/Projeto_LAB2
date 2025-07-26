package model;

public interface UsuarioSistema {
    String getCpf();
    String getSenha();
    boolean autenticar(String senha);
}