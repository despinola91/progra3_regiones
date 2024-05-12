package negocio;

import java.util.ArrayList;

public class Relacion implements Comparable<Relacion> {
    
    ArrayList<Provincia> provincias = new ArrayList<>();
    int similitud;

    @Override
    public int compareTo(Relacion otra) {
        return otra.obtenerSimilitud() - this.similitud; // Orden descendente por similitud
    }

    public Relacion(Provincia provinciaA, Provincia provinciaB, int similitud) {
        this.provincias.add(provinciaA);
        this.provincias.add(provinciaB);
        this.similitud = similitud;
    }

    public ArrayList<Provincia> obtenerProvincias() {
        return this.provincias;
    }

    public int obtenerSimilitud() {
        return similitud;
    }

    public static Relacion obtenerRelacion(ArrayList<Relacion> relaciones, Provincia provinciaA, Provincia provinciaB) {
        ArrayList<Provincia> provincias = new ArrayList<>();
        provincias.add(provinciaA);
        provincias.add(provinciaB);

        for (Relacion relacion : relaciones) {

            if (relacion.obtenerProvincias().containsAll(provincias)) {
                return relacion;
            }
        }
        return null;
    }
}
