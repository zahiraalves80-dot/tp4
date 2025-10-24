package irrazabal_elias.tp4;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public abstract class Articulo {
    private String nombre;
    private double precio;
    public Articulo(String nombre, double precio){
        this.nombre = nombre;
        this.precio = precio;
    }
    
    public abstract String getTipo();
    
    public String getNombre(){ return nombre; }
    public void setNombre(String nombre){ this.nombre = nombre; }
    public double getPrecio(){ return precio; }
    public void setPrecio(double precio){ this.precio = precio; }
    
    @Override
    public String toString() {
        return nombre + " ($" + precio + ")";
    }
}