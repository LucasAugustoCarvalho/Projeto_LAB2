package main;

import gui.LoginGUI;
import model.*;
import fachada.Fachada;
import exceptions.*;
import java.util.List;
import javax.swing.SwingUtilities;

public class MainGUI {
    public static void main(String[] args) {
        inicializarDadosTeste();
        SwingUtilities.invokeLater(() -> new LoginGUI());
    }

    private static void inicializarDadosTeste() {
        try {
            Fachada fachada = Fachada.getInstancia();

            // Create test student
            Aluno maria = new Aluno("Maria Silva", "11122233344", 25, "Plano Anual", "senha123");
            fachada.cadastrarAluno(maria);

            // Create test instructor
                Instrutor carlos = new Instrutor("Carlos Magno", "55544433322", 35, "Musculação", "senha456");
            fachada.cadastrarInstrutor(carlos);

            // Create and assign trainings
            List<Exercicio> exerciciosA = List.of(
                    new Exercicio("Supino Reto", "Peitoral"),
                    new Exercicio("Agachamento", "Pernas")
            );
            Treino treinoA = fachada.criarTreino("Treino A", carlos, exerciciosA);
            fachada.atribuirTreino(maria.getCpf(), treinoA);

            // Explicitly associate student with instructor
            fachada.associarAlunoInstrutor(maria.getCpf(), carlos.getCpf());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}