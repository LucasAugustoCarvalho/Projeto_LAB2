package repository;

import exceptions.InstrutorJaCadastradoException;
import model.Instrutor;
import java.util.HashMap;
import java.util.Map;

public class InstrutorRepository {
    private static InstrutorRepository instancia;
    private Map<String, Instrutor> instrutores = new HashMap<>();

    private InstrutorRepository() {}

    public static InstrutorRepository getInstancia() {
        if (instancia == null) {
            instancia = new InstrutorRepository();
        }
        return instancia;
    }

    public void adicionar(Instrutor instrutor) throws InstrutorJaCadastradoException {
        if (!instrutores.containsKey(instrutor.getCpf())) {
            instrutores.put(instrutor.getCpf(), instrutor);
        } else {
            throw new InstrutorJaCadastradoException(instrutor.getCpf());
        }
    }

    public Instrutor buscar(String cpf) {
        return instrutores.get(cpf);
    }

    public void remover(String cpf) {
        instrutores.remove(cpf);
    }
}