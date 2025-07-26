package exceptions;

public class AlunoJaCadastradoException extends Exception {
    public AlunoJaCadastradoException(String cpf) {
        super("Aluno com CPF " + cpf + " já está cadastrado no sistema");
    }
}