package repository;

import model.Instrutor;
import exceptions.InstrutorJaCadastradoException;
import exceptions.InstrutorNaoEncontradoException;
import java.util.*;

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
        if (instrutor == null) {
            throw new IllegalArgumentException("Instrutor não pode ser nulo");
        }
        if (instrutores.containsKey(instrutor.getCpf())) {
            throw new InstrutorJaCadastradoException(instrutor.getCpf());
        }
        instrutores.put(instrutor.getCpf(), instrutor);
    }

    public Instrutor buscar(String cpf) {
        return instrutores.get(cpf);
    }

    public boolean remover(String cpf) {
        return instrutores.remove(cpf) != null;
    }

    // Método atualizar único (sem duplicação)
    public void atualizar(Instrutor instrutor) throws InstrutorNaoEncontradoException {
        if (instrutor == null) {
            throw new IllegalArgumentException("Instrutor não pode ser nulo");
        }
        if (!instrutores.containsKey(instrutor.getCpf())) {
            throw new InstrutorNaoEncontradoException(instrutor.getCpf());
        }
        instrutores.put(instrutor.getCpf(), instrutor);
    }

    public Collection<Instrutor> listarTodos() {
        return new ArrayList<>(instrutores.values());
    }
}