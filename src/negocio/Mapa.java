package negocio;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

public class Mapa {

    private HashMap<String, Integer> provincias = new HashMap<>();
    //private static int cantidadProvincias = 0;
    private int[][] matrizDeRelacion;
    
    public Mapa()
	{
        matrizDeRelacion = new int[0][0];
	}

    public void agregarRelacion(String nombreProvincia1, String nombreProvincia2, int similitud)
	{
		validarRelacion(nombreProvincia1, nombreProvincia2);

        matrizDeRelacion[provincias.get(nombreProvincia1)][provincias.get(nombreProvincia2)] = similitud;
        matrizDeRelacion[provincias.get(nombreProvincia2)][provincias.get(nombreProvincia1)] = similitud;
        
	}

    public void eliminarRelacion(String nombreProvincia1, String nombreProvincia2)
	{
		validarProvincia(nombreProvincia1);
		validarProvincia(nombreProvincia2);
		validarRelacion(nombreProvincia1, nombreProvincia2);

		matrizDeRelacion[provincias.get(nombreProvincia1)][provincias.get(nombreProvincia2)] = 0;
        matrizDeRelacion[provincias.get(nombreProvincia2)][provincias.get(nombreProvincia1)] = 0;
	}

    private void validarRelacion (String nombreProvincia1, String nombreProvincia2) {
        if (!existeProvincia(nombreProvincia1) || !existeProvincia(nombreProvincia2) || nombreProvincia1 == nombreProvincia2) {
            throw new IllegalArgumentException("La relación es inválida");
        }
    }

    public boolean existeRelacion(String nombreProvincia1, String nombreProvincia2)
	{
		validarProvincia(nombreProvincia1);
		validarProvincia(nombreProvincia2);
		validarRelacion(nombreProvincia1, nombreProvincia2);

		return matrizDeRelacion[provincias.get(nombreProvincia1)][provincias.get(nombreProvincia2)] > 0;
	}

    public void agregarProvincia (String nombreProvincia) {
        provincias.put(nombreProvincia, provincias.size());

        int tamanioActual = matrizDeRelacion.length;
        int nuevoTamanio = matrizDeRelacion.length + 1;
        int[][] nuevaMatrizRelacion = new int[nuevoTamanio][nuevoTamanio];

        for (int i = 0; i < tamanioActual; i++) {
            for (int j = 0; j < tamanioActual; j++) {
                nuevaMatrizRelacion[i][j] = matrizDeRelacion[i][j];
            }
        }

        // Update the adjacency matrix and the number of vertices
        matrizDeRelacion = nuevaMatrizRelacion;
    }

    public void eliminarProvincia (String nombreProvincia) {
        validarProvincia(nombreProvincia);
        provincias.remove(nombreProvincia);

        int nuevoTamanio = matrizDeRelacion.length - 1;
        int[][] nuevaMatrizRelacion = new int[nuevoTamanio][nuevoTamanio];

        for (int i = 0; i < nuevoTamanio; i++) {
            for (int j = 0; j < nuevoTamanio; j++) {
                nuevaMatrizRelacion[i][j] = matrizDeRelacion[i][j];
            }
        }

        matrizDeRelacion = nuevaMatrizRelacion;
    }

    public void validarProvincia(String nombreProvincia) {
        if (!provincias.containsKey(nombreProvincia)) {
            throw new IllegalArgumentException("La provincia es inválida");
        }
    }

    public boolean existeProvincia (String nombreProvincia) {
        return provincias.containsKey(nombreProvincia);
    }

    public int obtenerDimensionMatrizRelacion() {
        return matrizDeRelacion.length;
    }

    public ArrayList<String> obtenerProvincias() {
        ArrayList<String> listaProvincias = new ArrayList<>();
        listaProvincias.addAll(provincias.keySet());
        Collections.sort(listaProvincias);
        
        return listaProvincias;
    }

    public int[][] obtenerRegiones(int cantidadRegiones, String algoritmo) {
        int[][] regiones = new int[provincias.size()][provincias.size()];
        if (algoritmo == "Prim") {
            regiones = Prim.obtenerRegiones(matrizDeRelacion, cantidadRegiones);
        }
        else if (algoritmo == "Kruskal") {
            regiones = Kruskal.obtenerRegiones(matrizDeRelacion, cantidadRegiones);
        } 
        else {
            throw new IllegalArgumentException("El algoritmo seleccionado no es válido.");
        }
        
        return regiones;
    }
}
