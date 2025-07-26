package service;

import model.*;
import java.util.List;

public class TreinoService {
    public Treino criarTreino(String nome, Instrutor instrutor, List<Exercicio> exercicios) {
        return new Treino(nome, instrutor, exercicios);
    }

    public void atribuirTreino(Aluno aluno, Treino treino) {
        aluno.adicionarTreino(treino);
    }
}