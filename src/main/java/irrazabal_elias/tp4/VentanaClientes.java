package irrazabal_elias.tp4;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class VentanaClientes extends JFrame {
    private SistemaVentas sistema; 
    private JTextField txtNombre, txtApellido, txtDni, txtDireccion, txtTelefono, txtBuscar;
    private JButton btnAgregar, btnEditar, btnEliminar, btnBuscar;
    private JTable tablaClientes;

    public VentanaClientes() {
        this.sistema = SistemaVentas.getInstancia(); // Conexión al controlador
        
        setTitle("Gestión de Clientes (CRUD)");
        setSize(800, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        // --- Panel de Formulario ---
        JPanel panelForm = new JPanel(new GridLayout(2, 5, 5, 5));
        txtNombre = new JTextField(); 
        txtApellido = new JTextField();
        txtDni = new JTextField();
        txtDireccion = new JTextField();
        txtTelefono = new JTextField();

        panelForm.add(new JLabel("Nombre"));
        panelForm.add(new JLabel("Apellido"));
        panelForm.add(new JLabel("DNI"));
        panelForm.add(new JLabel("Dirección"));
        panelForm.add(new JLabel("Teléfono"));
        panelForm.add(txtNombre);
        panelForm.add(txtApellido);
        panelForm.add(txtDni);
        panelForm.add(txtDireccion);
        panelForm.add(txtTelefono);
        
        // --- Panel de Botones CRUD ---
        JPanel panelBotones = new JPanel();
        btnAgregar = new JButton("Agregar");
        btnEditar = new JButton("Editar");
        btnEliminar = new JButton("Eliminar");
        panelBotones.add(btnAgregar);
        panelBotones.add(btnEditar);
        panelBotones.add(btnEliminar);
        
        // --- Tabla ---
        tablaClientes = new JTable(new DefaultTableModel(
                new Object[]{"Nombre", "Apellido", "DNI", "Dirección", "Teléfono"}, 0));
        JScrollPane scroll = new JScrollPane(tablaClientes);
        
        // --- Buscar ---
        JPanel panelBuscar = new JPanel();
        txtBuscar = new JTextField(20);
        btnBuscar = new JButton("Buscar");
        panelBuscar.add(new JLabel("Buscar por Nombre/DNI:"));
        panelBuscar.add(txtBuscar);
        panelBuscar.add(btnBuscar);
        
        // --- Ensamblaje ---
        JPanel panelTop = new JPanel(new BorderLayout());
        panelTop.add(panelForm, BorderLayout.NORTH);
        panelTop.add(panelBotones, BorderLayout.CENTER);
        panelTop.add(panelBuscar, BorderLayout.SOUTH);

        add(panelTop, BorderLayout.NORTH);
        add(scroll, BorderLayout.CENTER);

        // --- Carga y Eventos ---
        asignarEventos();
        refrescarTabla();
    }
    
    private void asignarEventos() {
        // Evento de selección de fila (para editar y eliminar)
        tablaClientes.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                int fila = tablaClientes.getSelectedRow();
                if (fila >= 0) {
                    DefaultTableModel model = (DefaultTableModel) tablaClientes.getModel();
                    txtNombre.setText(model.getValueAt(fila, 0).toString());
                    txtApellido.setText(model.getValueAt(fila, 1).toString());
                    txtDni.setText(model.getValueAt(fila, 2).toString());
                    txtDireccion.setText(model.getValueAt(fila, 3).toString());
                    txtTelefono.setText(model.getValueAt(fila, 4).toString());
                }
            }
        });

        // Acción AGREGAR
        btnAgregar.addActionListener(e -> {
            try {
                Cliente c = new Cliente(
                    txtNombre.getText(),
                    txtApellido.getText(),
                    txtDni.getText(), // String
                    txtDireccion.getText(),
                    txtTelefono.getText() // String
                );
                sistema.agregarCliente(c);
                refrescarTabla();
                limpiarCampos();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error al agregar cliente: " + ex.getMessage());
            }
        });

        // Acción EDITAR
        btnEditar.addActionListener(e -> {
            int fila = tablaClientes.getSelectedRow();
            if (fila >= 0) {
                // Obtiene el DNI original para buscar el objeto en la lista
                String dniOriginal = tablaClientes.getModel().getValueAt(fila, 2).toString();
                Cliente c = sistema.buscarClientePorDni(dniOriginal);

                if (c != null) {
                    c.setNombre(txtNombre.getText());
                    c.setApellido(txtApellido.getText());
                    c.setDni(txtDni.getText()); // Actualiza el DNI
                    c.setDireccion(txtDireccion.getText());
                    c.setTelefono(txtTelefono.getText());
                    refrescarTabla();
                    limpiarCampos();
                }
            }
        });

        // Acción ELIMINAR
        btnEliminar.addActionListener(e -> {
            int fila = tablaClientes.getSelectedRow();
            if (fila >= 0) {
                String dniOriginal = tablaClientes.getModel().getValueAt(fila, 2).toString();
                Cliente c = sistema.buscarClientePorDni(dniOriginal);
                if (c != null) {
                    sistema.eliminarCliente(c);
                    refrescarTabla();
                    limpiarCampos();
                }
            }
        });
        
        // Acción BUSCAR
        btnBuscar.addActionListener(e -> {
            String texto = txtBuscar.getText().toLowerCase();
            DefaultTableModel model = (DefaultTableModel) tablaClientes.getModel();
            model.setRowCount(0); 
            
            for (Cliente c : sistema.getClientes()) {
                if (c.getNombre().toLowerCase().contains(texto) || c.getDni().toLowerCase().contains(texto)) {
                    model.addRow(new Object[]{c.getNombre(), c.getApellido(), c.getDni(), c.getDireccion(), c.getTelefono()});
                }
            }
            if(texto.isEmpty()) refrescarTabla(); 
        });
    }
    
    private void refrescarTabla() {
        DefaultTableModel model = (DefaultTableModel) tablaClientes.getModel();
        model.setRowCount(0); 
        for (Cliente c : sistema.getClientes()) {
            model.addRow(new Object[]{c.getNombre(), c.getApellido(), c.getDni(), c.getDireccion(), c.getTelefono()});
        }
    }
    
    private void limpiarCampos() {
        txtNombre.setText("");
        txtApellido.setText("");
        txtDni.setText("");
        txtDireccion.setText("");
        txtTelefono.setText("");
    }
}