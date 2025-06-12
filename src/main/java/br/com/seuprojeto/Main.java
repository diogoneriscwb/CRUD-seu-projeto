package br.com.seuprojeto;

import br.com.seuprojeto.view.TelaUsuarios;
import javax.swing.SwingUtilities;

public class Main {
    public static void main(String[] args) {
        // Garante que a UI será executada na thread de eventos do Swing
        SwingUtilities.invokeLater(() -> new TelaUsuarios().setVisible(true));
    }
}