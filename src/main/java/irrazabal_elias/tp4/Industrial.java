package irrazabal_elias.tp4;

public class Industrial extends Electricidad{
    private final double temperaturaMinima;
    private final double temperaturaMaxima;
    
    public Industrial(String nom, double precio, double potenciaMax, double tempMin, double tempMax){
        super(nom,precio,potenciaMax);
        this.temperaturaMaxima = tempMax;
        this.temperaturaMinima = tempMin;
    }
    
    @Override
    public String getTipo() { return "Electricidad Industrial"; }

    public double getTemperaturaMinima() { return temperaturaMinima; }
    public double getTemperaturaMaxima() { return temperaturaMaxima; }
    
    @Override
    public String toString() {
        return super.toString() + " - Industrial (" + temperaturaMinima + "°C a " + temperaturaMaxima + "°C)";
    }
}