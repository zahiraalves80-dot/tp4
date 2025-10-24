package irrazabal_elias.tp4;

public class Herramienta extends Articulo {
    private String descripcion;
    
    public Herramienta(String nom, double precio, String descripcion){
        super(nom, precio);
        this.descripcion = descripcion;
    }
    
    public String getDescripcion(){
        return descripcion;
    }
    public void setDescription(String descrip){
        this.descripcion = descrip;
    }
    
    @Override
    public String getTipo(){
        return "Herramienta";
    }
    
    @Override
    public String toString() {
        return super.toString() + " - Herramienta (" + descripcion + ")";
    }
    
}