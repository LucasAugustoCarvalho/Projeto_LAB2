package repository;

import model.Treino;
import java.util.*;

public class TreinoRepository {
    private static TreinoRepository instancia;
    private List<Treino> treinos = new ArrayList<>();

    private TreinoRepository() {}

    public static TreinoRepository getInstancia() {
        if (instancia == null) {
            instancia = new TreinoRepository();
        }
        return instancia;
    }

    public void adicionar(Treino treino) {
        if (treino != null && !treinos.contains(treino)) {
            treinos.add(treino);
        }
    }

    public void atualizar(Treino treino) {
        treinos.removeIf(t -> t.getNome().equals(treino.getNome()) &&
                t.getInstrutor().getCpf().equals(treino.getInstrutor().getCpf()));
        treinos.add(treino);
    }

    public void remover(Treino treino) {
        treinos.removeIf(t -> t.getNome().equals(treino.getNome()) &&
                t.getInstrutor().getCpf().equals(treino.getInstrutor().getCpf()));
    }

    public List<Treino> listarTodos() {
        return new ArrayList<>(treinos);
    }

    public Optional<Treino> buscarPorNomeEInstrutor(String nome, String cpfInstrutor) {
        return treinos.stream()
                .filter(t -> t.getNome().equals(nome) &&
                        t.getInstrutor().getCpf().equals(cpfInstrutor))
                .findFirst();
    }
}