package irrazabal_elias.tp4;

public class Item {
    private Articulo articulo;
    private int cantidad;
    
    public Item(Articulo articulo, int cantidad){
        this.cantidad = cantidad;
        this.articulo = articulo;
    }

    public Articulo getArticulo() { return articulo; }
    public void setArticulo(Articulo articulo) { this.articulo = articulo; }
    public int getCantidad() { return cantidad; }
    public void setCantidad(int cantidad) { this.cantidad = cantidad; }
    
    public double calcularMonto(){
        return (articulo.getPrecio() * cantidad);
    }
}