package irrazabal_elias.tp4;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Factura {
    private LocalDate fecha;
    private long nroFactura; // CORREGIDO: Usar long para el número de factura
    private char letra;
    private Cliente cliente;
    private final List<Item> items = new ArrayList<>();
    
    // CONSTRUCTOR CORREGIDO: La fecha es siempre LocalDate.now() (Requisito 2.c.3).
    public Factura(long nroFactura, char letra, Cliente cli){ 
        this.fecha = LocalDate.now(); 
        this.nroFactura = nroFactura;
        this.letra = letra;
        this.cliente = cli;
    }
    
    public LocalDate getFecha(){ return fecha; }
    public long getNroFactura(){ return nroFactura;}
    public char getLetra(){ return letra;} // CORREGIDO: Convención getLetra()
    public Cliente getCliente(){ return cliente; }
    public List<Item> getItems() { return items; }
    
    public void agregarItem(Item item) {
        items.add(item);
    }
    
    public double informarTotalPagar() {
        double aux = 0;
        for(Item itemm:items){
            aux += itemm.calcularMonto(); // CORRECCIÓN CRÍTICA: Usar += para acumular la suma
        }
        return aux;
    }
}