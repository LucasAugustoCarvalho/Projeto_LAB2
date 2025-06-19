package main;

import model.*;
import repository.*;
import service.*;

import java.util.*;

public class Main {
    public static void main(String[] args) {
        AlunoRepository repo = new AlunoRepository();
        TreinoService treinoService = new TreinoService();

        Aluno a1 = new Aluno("Lucas", "12345678900", 25, "Mensal");
        repo.adicionar(a1);

        Instrutor instrutor = new Instrutor("João", "00011122233", 30, "Musculação");
        List<Exercicio> exercicios = Arrays.asList(
            new Exercicio("Supino", "Peitoral"),
            new Exercicio("Agachamento", "Pernas")
        );

        Treino treino = treinoService.criarTreino("Treino A", instrutor, exercicios);
        treinoService.atribuirTreino(a1, treino);

        System.out.println("Aluno: " + a1.getNome());
        System.out.println("Treinos:");
        for (Treino t : a1.getTreinos()) {
            System.out.println("- " + t.getNome());
            for (Exercicio e : t.getExercicios()) {
                System.out.println("  > " + e.getNome() + " (" + e.getGrupoMuscular() + ")");
            }
        }
    }
}
