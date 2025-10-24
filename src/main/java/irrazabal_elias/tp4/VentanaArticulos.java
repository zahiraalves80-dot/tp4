package irrazabal_elias.tp4;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class VentanaArticulos extends JFrame {
    private JComboBox<String> comboTipo;
    private JTextField txtNombre, txtPrecio, txtCampoExtra1, txtCampoExtra2;
    private JTable tablaArticulos;
    private JButton btnAgregar, btnEditar, btnEliminar, btnBuscar;
    private JTextField txtBuscar;

    public VentanaArticulos() {
        setTitle("Gestión de Artículos");
        setSize(750, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        // Formulario
        JPanel panelForm = new JPanel(new GridLayout(3, 4, 5, 5));
        comboTipo = new JComboBox<>(new String[]{
                "Electricidad Industrial", "Electricidad Domiciliaria", "Herramientas"
        });
        txtNombre = new JTextField();
        txtPrecio = new JTextField();
        txtCampoExtra1 = new JTextField();
        txtCampoExtra2 = new JTextField();

        panelForm.add(new JLabel("Tipo de Artículo"));
        panelForm.add(comboTipo);
        panelForm.add(new JLabel("Nombre"));
        panelForm.add(txtNombre);
        panelForm.add(new JLabel("Precio"));
        panelForm.add(txtPrecio);
        panelForm.add(new JLabel("Campo extra 1"));
        panelForm.add(txtCampoExtra1);
        panelForm.add(new JLabel("Campo extra 2"));
        panelForm.add(txtCampoExtra2);

        // Botones
        JPanel panelBotones = new JPanel();
        btnAgregar = new JButton("Agregar");
        btnEditar = new JButton("Editar");
        btnEliminar = new JButton("Eliminar");
        panelBotones.add(btnAgregar);
        panelBotones.add(btnEditar);
        panelBotones.add(btnEliminar);

        // Tabla
        tablaArticulos = new JTable(new DefaultTableModel(
                new Object[]{"Tipo", "Nombre", "Precio", "Extras"}, 0));
        JScrollPane scroll = new JScrollPane(tablaArticulos);

        // Buscar
        JPanel panelBuscar = new JPanel();
        txtBuscar = new JTextField(20);
        btnBuscar = new JButton("Buscar");
        panelBuscar.add(new JLabel("Buscar artículo:"));
        panelBuscar.add(txtBuscar);
        panelBuscar.add(btnBuscar);

        add(panelForm, BorderLayout.NORTH);
        add(scroll, BorderLayout.CENTER);
        add(panelBotones, BorderLayout.SOUTH);
        add(panelBuscar, BorderLayout.PAGE_END);
    }
}

