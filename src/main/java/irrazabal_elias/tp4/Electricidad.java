package irrazabal_elias.tp4;
public abstract class Electricidad extends Articulo {
    protected double potenciaMaxima;

    public Electricidad(String nombre, double precio, double potenciaMaxima) {
        super(nombre, precio);
        this.potenciaMaxima = potenciaMaxima;
    }

    public double getPotenciaMaxima() { return potenciaMaxima; }
    public void setPotenciaMaxima(double potenciaMaxima) { this.potenciaMaxima = potenciaMaxima; }
}