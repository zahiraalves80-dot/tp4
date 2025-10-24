package irrazabal_elias.tp4;

public class Domiciliaria extends Electricidad{
    
    public Domiciliaria(String nombre, double precio, double potenciaMaxima) {
        super(nombre, precio, potenciaMaxima);
    }
    
    @Override
    public String getTipo() { return "Electricidad Domiciliaria"; }
    
    @Override
    public String toString() {
        return super.toString() + " - Domiciliaria (" + potenciaMaxima + "W)";
    }
}