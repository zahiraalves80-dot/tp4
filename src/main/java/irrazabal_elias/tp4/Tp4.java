package irrazabal_elias.tp4;

import javax.swing.SwingUtilities;

public class Tp4 {

    public static void main(String[] args) {
        // Asegura que la interfaz se ejecute en el hilo de Swing (obligatorio para Swing).
        SwingUtilities.invokeLater(() -> {
            new MenuPrincipal().setVisible(true);
        });
    }
}