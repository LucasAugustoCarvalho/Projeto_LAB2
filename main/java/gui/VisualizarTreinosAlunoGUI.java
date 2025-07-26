package gui;

import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import model.Aluno;
import model.Treino;
import model.Exercicio;

public class VisualizarTreinosAlunoGUI {
    private JFrame frame;
    private Aluno aluno;

    public VisualizarTreinosAlunoGUI(Aluno aluno) {
        this.aluno = aluno;
        initialize();
        carregarTreinos();
    }

    private void initialize() {
        frame = new JFrame("Treinos do Aluno: " + aluno.getNome());
        frame.setSize(900, 600);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // tabela de treinos
        JTable treinosTable = new JTable();
        treinosTable.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value,
                                                           boolean isSelected, boolean hasFocus, int row, int column) {
                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                c.setFont(new Font("Arial", Font.PLAIN, 14));
                return c;
            }
        });

        JScrollPane scrollPane = new JScrollPane(treinosTable);
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        // botão Fechar
        JButton closeButton = new JButton("Fechar");
        closeButton.addActionListener(e -> frame.dispose());
        mainPanel.add(closeButton, BorderLayout.SOUTH);

        frame.add(mainPanel);
    }

    private void carregarTreinos() {
        DefaultTableModel model = new DefaultTableModel(
                new Object[]{"Treino", "Instrutor", "Exercícios"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        for (Treino treino : aluno.getTreinos()) {
            StringBuilder exerciciosStr = new StringBuilder();
            for (Exercicio exercicio : treino.getExercicios()) {
                exerciciosStr.append("• ")
                        .append(exercicio.getNome())
                        .append(" (")
                        .append(exercicio.getGrupoMuscular())
                        .append(")\n");
            }

            model.addRow(new Object[]{
                    treino.getNome(),
                    treino.getInstrutor().getNome(),
                    exerciciosStr.toString()
            });
        }


        JTable table = (JTable)((JScrollPane)frame.getContentPane().getComponent(0)).getViewport().getView();
        table.setModel(model);


        table.getColumnModel().getColumn(0).setPreferredWidth(150);
        table.getColumnModel().getColumn(1).setPreferredWidth(150);
        table.getColumnModel().getColumn(2).setPreferredWidth(500);


        table.setRowHeight(60);
    }

    public void setVisible(boolean visible) {
        frame.setVisible(visible);
    }
}