package negocio;

/**
 * Provincia
 */
public class Provincia {

    private int id;
    private String nombre;
    private double latitud;
    private double longitud;   
    
    public Provincia(int id, String nombre, double latitud, double longitud) {
        this.id = id;
        this.nombre = nombre;
        this.latitud = latitud;
        this.longitud = longitud;
    }

    public int obtenerId() {
        return id;
    }

    public String obtenerNombre() {
        return nombre;
    }

    public double obtenerLatitud() {
        return latitud;
    }

    public double obtenerLongitud() {
        return longitud;
    }
}
