package negocio;

import org.openstreetmap.gui.jmapviewer.Coordinate;

/**
 * Provincia
 */
public class Provincia {

    private int id;
    private String nombre;
    private Coordinate coordenadas;
    
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

    public Coordinate obtenerCoordenadas() {
        return coordenadas;
    }
}
