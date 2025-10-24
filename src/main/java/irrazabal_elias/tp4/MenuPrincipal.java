package irrazabal_elias.tp4;

import javax.swing.*;
import java.awt.*;

public class MenuPrincipal extends JFrame {
    public MenuPrincipal() {
        setTitle("Sistema de Ventas - TP4 Irrazabal Elias");
        setSize(400, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(4, 1, 10, 10));

        JButton btnClientes = new JButton("Gestión de Clientes");
        JButton btnArticulos = new JButton("Gestión de Artículos");
        JButton btnFacturas = new JButton("Gestión de Facturas");

        add(btnClientes);
        add(btnArticulos);
        add(btnFacturas);

        // Acciones
        btnClientes.addActionListener(e -> new VentanaClientes().setVisible(true));
        btnArticulos.addActionListener(e -> new VentanaArticulos().setVisible(true));
        btnFacturas.addActionListener(e -> new VentanaFacturas().setVisible(true));
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new MenuPrincipal().setVisible(true));
    }
}

