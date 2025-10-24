package irrazabal_elias.tp4;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class SistemaVentas {
    
    // Implementación del patrón Singleton
    private static SistemaVentas instancia;
    
    // Repositorios de datos inicializados
    private final List<Cliente> clientes = new ArrayList<>();
    private final List<Articulo> articulos = new ArrayList<>();
    private final List<Factura> facturas = new ArrayList<>();
    
    private long siguienteNroFactura = 1; // Contador automático (Requisito 2.c.5)

    private SistemaVentas() {
        // Inicialización de datos de prueba para que las ventanas no estén vacías al inicio.
        agregarDatosDePrueba(); 
    }
    
    // Método para obtener la instancia única
    public static SistemaVentas getInstancia() {
        if (instancia == null) {
            instancia = new SistemaVentas();
        }
        return instancia;
    }
    
    // === MÉTODOS DE CLIENTES ===
    public void agregarCliente(Cliente c) { clientes.add(c); }
    public void eliminarCliente(Cliente c) { clientes.remove(c); }
    public List<Cliente> getClientes() { return clientes; }
    
    // Busca un cliente por DNI (necesario para la ventana de facturas)
    public Cliente buscarClientePorDni(String dni) {
        for (Cliente c : clientes) {
            if (c.getDni().equals(dni)) {
                return c;
            }
        }
        return null;
    }

    // === MÉTODOS DE ARTÍCULOS ===
    public void agregarArticulo(Articulo a) { articulos.add(a); }
    public void eliminarArticulo(Articulo a) { articulos.remove(a); }
    public List<Articulo> getArticulos() { return articulos; }
    
    // === MÉTODOS DE FACTURAS ===
    public void agregarFactura(Factura f) { 
        facturas.add(f); 
        siguienteNroFactura++; // Actualiza el contador
    }
    
    public Factura buscarFactura(long nro) {
        for(Factura f : facturas) {
            if (f.getNroFactura() == nro) {
                return f;
            }
        }
        return null;
    }
    
    public long getSiguienteNroFactura() {
        return siguienteNroFactura;
    }
    
    // Requisito 2.c.6: Listar facturas ordenadas por fecha.
    public List<Factura> getFacturasOrdenadasPorFecha() {
        List<Factura> listaOrdenada = new ArrayList<>(facturas);
        // Ordena por fecha ascendente.
        listaOrdenada.sort(Comparator.comparing(Factura::getFecha));
        return listaOrdenada;
    }
    
    // Datos de prueba para iniciar la app con contenido
    private void agregarDatosDePrueba() {
        // Clientes
        clientes.add(new Cliente("Elias", "Irrazabal", "30123456", "Calle Falsa 123", "3764555555"));
        clientes.add(new Cliente("Maria", "Gomez", "25678901", "Av. Principal 456", "3764888888"));
        
        // Artículos
        articulos.add(new Herramienta("Destornillador", 500.0, "Cruz y plana"));
        articulos.add(new Industrial("Motor Trifásico", 15000.0, 500.0, 10.0, 80.0));
        articulos.add(new Domiciliaria("Lampara LED", 150.0, 15.0));
    }
}