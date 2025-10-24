package irrazabal_elias.tp4;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class VentanaFacturas extends JFrame {
    private SistemaVentas sistema;
    private JComboBox<Cliente> comboClientes;
    private JComboBox<String> comboLetra;
    private JComboBox<Articulo> comboArticulos; // Usar Articulo para simplificar el manejo
    private JTextField txtCantidad;
    private JButton btnAgregarItem, btnCrearFactura;
    private JTable tablaItems, tablaFacturas;
    private JLabel lblFecha, lblNumero, lblTotal; 
    private DefaultTableModel modeloItems, modeloFacturas;
    private List<Item> itemsFacturaActual = new ArrayList<>(); // Lista temporal

    public VentanaFacturas() {
        this.sistema = SistemaVentas.getInstancia();
        
        setTitle("Gestión de Facturas");
        setSize(1000, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        // --- Cabecera de la Factura ---
        JPanel panelDatos = new JPanel(new GridLayout(2, 4, 5, 5));
        comboClientes = new JComboBox<>(); 
        comboLetra = new JComboBox<>(new String[]{"A", "B", "C"}); 
        lblFecha = new JLabel("Fecha: " + LocalDate.now()); 
        lblNumero = new JLabel("Factura N°: " + sistema.getSiguienteNroFactura()); 
        lblTotal = new JLabel("TOTAL: $0.00");
        
        panelDatos.add(new JLabel("Cliente:"));
        panelDatos.add(comboClientes);
        panelDatos.add(new JLabel("Letra:"));
        panelDatos.add(comboLetra);
        panelDatos.add(lblFecha);
        panelDatos.add(lblNumero);
        panelDatos.add(new JLabel("")); 
        panelDatos.add(lblTotal);
        
        // --- Formulario de Ítems ---
        JPanel panelItems = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));
        comboArticulos = new JComboBox<>();
        txtCantidad = new JTextField(5);
        btnAgregarItem = new JButton("Agregar Ítem");
        
        panelItems.add(new JLabel("Artículo:"));
        panelItems.add(comboArticulos);
        panelItems.add(new JLabel("Cantidad:"));
        panelItems.add(txtCantidad);
        panelItems.add(btnAgregarItem);

        // --- Tabla ÍTEMS (Detalle de la factura actual/seleccionada) ---
        modeloItems = new DefaultTableModel(
                new Object[]{"Artículo", "Tipo", "Precio", "Cantidad", "Subtotal", "Detalle Extra"}, 0);
        tablaItems = new JTable(modeloItems);
        JScrollPane scrollItems = new JScrollPane(tablaItems);

        // --- Botón CREAR FACTURA ---
        btnCrearFactura = new JButton("CREAR FACTURA");
        JPanel panelBotonFactura = new JPanel(new FlowLayout(FlowLayout.CENTER));
        panelBotonFactura.add(btnCrearFactura);

        // --- Tabla FACTURAS (Listado) ---
        modeloFacturas = new DefaultTableModel(
                new Object[]{"N°", "Letra", "Fecha", "Cliente", "Monto Total"}, 0);
        tablaFacturas = new JTable(modeloFacturas);
        JScrollPane scrollFacturas = new JScrollPane(tablaFacturas);
        scrollFacturas.setPreferredSize(new Dimension(400, 0)); 
        scrollFacturas.setBorder(BorderFactory.createTitledBorder("Facturas Creadas (Ordenadas por Fecha)"));

        // --- ARMADO FINAL ---
        JPanel panelTop = new JPanel(new BorderLayout());
        panelTop.add(panelDatos, BorderLayout.NORTH);
        panelTop.add(panelItems, BorderLayout.SOUTH);

        add(panelTop, BorderLayout.NORTH);
        add(scrollItems, BorderLayout.CENTER);
        add(panelBotonFactura, BorderLayout.SOUTH);
        add(scrollFacturas, BorderLayout.EAST);

        // --- Carga y Eventos ---
        cargarCombos();
        asignarEventos();
        refrescarTablaFacturas();
        actualizarTotal();
    }
    
    private void cargarCombos() {
        // Carga de Clientes (Req. 2.c.1)
        comboClientes.removeAllItems();
        for (Cliente c : sistema.getClientes()) {
            comboClientes.addItem(c); 
        }

        // Carga de Artículos (Req. 2.c.2)
        comboArticulos.removeAllItems();
        for (Articulo a : sistema.getArticulos()) {
            comboArticulos.addItem(a);
        }
    }
    
    private void asignarEventos() {
        // Evento Agregar Ítem
        btnAgregarItem.addActionListener(e -> {
            try {
                Articulo a = (Articulo) comboArticulos.getSelectedItem();
                int cantidad = Integer.parseInt(txtCantidad.getText());

                if (a == null || cantidad <= 0) {
                    JOptionPane.showMessageDialog(this, "Seleccione un artículo y/o ingrese una cantidad válida.");
                    return;
                }
                
                Item nuevoItem = new Item(a, cantidad);
                itemsFacturaActual.add(nuevoItem);
                agregarItemATabla(nuevoItem);
                actualizarTotal();
                txtCantidad.setText("");
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "La cantidad debe ser un número entero.");
            }
        });
        
        // Evento Crear Factura
        btnCrearFactura.addActionListener(e -> {
            if (comboClientes.getSelectedItem() == null) {
                JOptionPane.showMessageDialog(this, "Debe seleccionar un cliente.");
                return;
            }
            if (itemsFacturaActual.isEmpty()) {
                JOptionPane.showMessageDialog(this, "La factura debe tener al menos un ítem.");
                return;
            }

            Cliente cliente = (Cliente) comboClientes.getSelectedItem();
            char letra = comboLetra.getSelectedItem().toString().charAt(0);
            long nroFactura = sistema.getSiguienteNroFactura(); // Req. 2.c.5

            Factura nuevaFactura = new Factura(nroFactura, letra, cliente);
            for (Item item : itemsFacturaActual) {
                nuevaFactura.agregarItem(item);
            }

            sistema.agregarFactura(nuevaFactura);
            
            // Limpiar y actualizar
            itemsFacturaActual.clear();
            modeloItems.setRowCount(0);
            lblNumero.setText("Factura N°: " + sistema.getSiguienteNroFactura());
            refrescarTablaFacturas();
            actualizarTotal();
            JOptionPane.showMessageDialog(this, "Factura N° " + nroFactura + " creada con éxito.");
        });
        
        // Evento de selección en la tabla de Facturas (Req. 2.c.6)
        tablaFacturas.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                int fila = tablaFacturas.getSelectedRow();
                if (fila >= 0) {
                    // El N° de Factura está en la columna 0.
                    long nroFactura = (long) modeloFacturas.getValueAt(fila, 0); 
                    mostrarDetalleFactura(nroFactura);
                }
            }
        });
    }

    private void agregarItemATabla(Item item) {
         Articulo a = item.getArticulo();
         double subtotal = item.calcularMonto();
         // Obtiene el detalle extra específico para el requisito 2.c.6
         String detalleExtra = obtenerDetalleExtras(a); 
         
         modeloItems.addRow(new Object[]{
             a.getNombre(), 
             a.getTipo(), 
             String.format("%.2f", a.getPrecio()), 
             item.getCantidad(), 
             String.format("%.2f", subtotal),
             detalleExtra
         });
    }
    
    // Método auxiliar reutilizado de VentanaArticulos para mostrar detalles de jerarquía
    private String obtenerDetalleExtras(Articulo a) {
        if (a instanceof Herramienta) {
            return "Descripción: " + ((Herramienta) a).getDescripcion();
        } else if (a instanceof Industrial) {
            Industrial ind = (Industrial) a;
            return String.format("Pot: %.1fW, Temp: %.1f°C/%.1f°C", ind.getPotenciaMaxima(), ind.getTemperaturaMinima(), ind.getTemperaturaMaxima());
        } else if (a instanceof Domiciliaria) {
            return "Potencia: " + ((Domiciliaria) a).getPotenciaMaxima() + "W";
        }
        return "";
    }
    
    private void actualizarTotal() {
        double total = 0;
        for (Item item : itemsFacturaActual) {
            total += item.calcularMonto();
        }
        lblTotal.setText(String.format("TOTAL: $%.2f", total));
    }
    
    // Refresca la tabla de facturas ordenadas por fecha (Req. 2.c.6)
    private void refrescarTablaFacturas() {
        modeloFacturas.setRowCount(0);
        for (Factura f : sistema.getFacturasOrdenadasPorFecha()) { 
            modeloFacturas.addRow(new Object[]{
                f.getNroFactura(), 
                f.getLetra(), 
                f.getFecha(), 
                f.getCliente().toString(), 
                String.format("%.2f", f.informarTotalPagar())
            });
        }
    }
    
    // Muestra el detalle de Ítems de la factura seleccionada (Req. 2.c.6)
    private void mostrarDetalleFactura(long nroFactura) {
        Factura factura = sistema.buscarFactura(nroFactura); 
        
        modeloItems.setRowCount(0);
        itemsFacturaActual.clear();
        
        if (factura != null) {
            // Carga los ítems de la factura seleccionada en el modelo de la tabla
            for (Item item : factura.getItems()) {
                agregarItemATabla(item);
            }
             // Pone el total de la factura seleccionada en el label (opcional, pero útil)
            lblTotal.setText(String.format("TOTAL: $%.2f (Factura %d)", factura.informarTotalPagar(), factura.getNroFactura()));
        }
    }
}