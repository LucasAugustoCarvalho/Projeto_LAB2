package model;

/**
 * Classe abstrata que representa uma Pessoa no sistema.
 * Contém informações básicas como nome, CPF e idade.
 */
public abstract class Pessoa {
    protected String nome;
    protected String cpf;
    protected int idade;

    /**
     * Construtor da classe Pessoa
     * @param nome Nome da pessoa (não pode ser vazio)
     * @param cpf CPF da pessoa (deve conter 11 dígitos)
     * @param idade Idade da pessoa (deve ser positiva)
     * @throws IllegalArgumentException se algum parâmetro for inválido
     */
    public Pessoa(String nome, String cpf, int idade) {
        if (nome == null || nome.trim().isEmpty()) {
            throw new IllegalArgumentException("Nome não pode ser vazio");
        }
        if (cpf == null || !cpf.matches("\\d{11}")) {
            throw new IllegalArgumentException("CPF deve conter 11 dígitos");
        }
        if (idade <= 0) {
            throw new IllegalArgumentException("Idade deve ser positiva");
        }
        this.nome = nome;
        this.cpf = cpf;
        this.idade = idade;
    }

    public String getNome() { return nome; }
    public String getCpf() { return cpf; }
    public int getIdade() { return idade; }
}