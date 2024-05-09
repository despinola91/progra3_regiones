package negocio;

import org.openstreetmap.gui.jmapviewer.Coordinate;

/**
 * Provincia
 */
public class Provincia {

    private int id;
    private String nombre;
    private Coordinate coordenadas;
    private double latitud;
    private double longitud;   
    
    public Provincia(int id, String nombre, Coordinate coordenadas) {
        this.id = id;
        this.nombre = nombre;
        this.coordenadas = coordenadas;
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

    public Coordinate obtenerCoordenadas() {
        return coordenadas;
    }
}
