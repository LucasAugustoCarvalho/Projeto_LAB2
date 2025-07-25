package exceptions;

public class AlunoNaoEncontradoException extends Exception {
    public AlunoNaoEncontradoException(String cpf) {
        super("Aluno com CPF " + cpf + " não encontrado");
    }
}