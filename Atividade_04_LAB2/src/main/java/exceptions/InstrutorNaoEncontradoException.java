package exceptions;

public class InstrutorNaoEncontradoException extends Exception {
    public InstrutorNaoEncontradoException(String cpf) {
        super("Instrutor com CPF " + cpf + " não encontrado");
    }
}