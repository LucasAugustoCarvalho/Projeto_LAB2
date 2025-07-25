package exceptions;

public class InstrutorJaCadastradoException extends Exception {
  public InstrutorJaCadastradoException(String cpf) {
    super("Instrutor com CPF " + cpf + " já está cadastrado no sistema");
  }
}