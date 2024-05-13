package negocio;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

import org.openstreetmap.gui.jmapviewer.Coordinate;

public class Mapa {

    private HashMap<String, Provincia> provincias = new HashMap<>();
    private ArrayList<Relacion> relaciones = new ArrayList<>();
    private int[][] matrizDeRelacion;
    private int[][] matrizDeRegiones;
    
    public Mapa()
	{
        matrizDeRelacion = new int[0][0];
	}

    public void agregarRelacion(String nombreProvincia1, String nombreProvincia2, int similitud)
	{
		validarRelacion(nombreProvincia1, nombreProvincia2);

        int idProv1 = provincias.get(nombreProvincia1).obtenerId();
        int idProv2 = provincias.get(nombreProvincia2).obtenerId();

        matrizDeRelacion[idProv1][idProv2] = similitud;
        matrizDeRelacion[idProv2][idProv1] = similitud;

        Relacion relacion = new Relacion(provincias.get(nombreProvincia1), provincias.get(nombreProvincia2), similitud);
        relaciones.add(relacion);
	}

    public void eliminarRelacion(String nombreProvincia1, String nombreProvincia2)
	{
		validarProvincia(nombreProvincia1);
		validarProvincia(nombreProvincia2);
		validarRelacion(nombreProvincia1, nombreProvincia2);

        Provincia provinciaA = provincias.get(nombreProvincia1);
        Provincia provinciaB = provincias.get(nombreProvincia2);

        int idProv1 = provinciaA.obtenerId();
        int idProv2 = provinciaB.obtenerId();

		matrizDeRelacion[idProv1][idProv2] = 0;
        matrizDeRelacion[idProv2][idProv1] = 0;
        
        relaciones.remove(Relacion.obtenerRelacion(relaciones, provinciaA, provinciaB));
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

        int idProv1 = provincias.get(nombreProvincia1).obtenerId();
        int idProv2 = provincias.get(nombreProvincia2).obtenerId();

		return matrizDeRelacion[idProv1][idProv2] > 0;
	}

    public void agregarProvincia (String nombreProvincia, Coordinate coordenadas) {
        Provincia provincia = new Provincia(provincias.size(), nombreProvincia, coordenadas);
        provincias.put(nombreProvincia, provincia);

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

    public Provincia obtenerProvincia(String nombreProvincia) {
        Provincia provincia = provincias.get(nombreProvincia);
        return provincia;
    }
    
    public ArrayList<String> obtenerProvincias() {
        ArrayList<String> listaProvincias = new ArrayList<>();
        listaProvincias.addAll(provincias.keySet());
        Collections.sort(listaProvincias);
        
        return listaProvincias;
    }

    public Provincia obtenerProvinciaPorNombre(String nombreProvincia) {
        return provincias.get(nombreProvincia);
    }

    public Provincia obtenerProvinciaPorId(int id) {
        
        for (Provincia provincia : provincias.values()) {
            if (provincia.obtenerId() == id) {
                return provincia;
            }
        }

        throw new IllegalArgumentException("Provincia not found for id: " + id);
    }

    public void generarRegiones(int cantidadRegiones, String algoritmo) {
        matrizDeRegiones = new int[provincias.size()][provincias.size()];
        if (algoritmo == "Prim") {
            matrizDeRegiones = Prim.generarAGM(matrizDeRelacion);
        }
        else if (algoritmo == "Kruskal") {
            matrizDeRegiones = Kruskal.generarAGM(matrizDeRelacion);
        } 
        else {
            throw new IllegalArgumentException("El algoritmo seleccionado no es válido.");
        }

        matrizDeRegiones = dividirRegiones(matrizDeRegiones, cantidadRegiones);
    }
    
    public int[][] dividirRegiones(int[][] matrizDeRegiones, int cantidadRegiones) {
        int[][] matrizResultado = new int[matrizDeRegiones.length][matrizDeRegiones.length];

        // Copiar la matriz original a la matriz resultado
        for (int i = 0; i < matrizDeRegiones.length; i++) {
            for (int j = 0; j < matrizDeRegiones[0].length; j++) {
                matrizResultado[i][j] = matrizDeRegiones[i][j];
            }
        }

        // Poner en 0 los valores más altos de la matriz resultado
        for (int k = 0; k < cantidadRegiones-1; k++) {
            int maximo = Integer.MIN_VALUE;
            int filaMaximo = -1;
            int columnaMaximo = -1;

            // Encontrar el valor más alto en la matriz resultado
            for (int i = 0; i < matrizResultado.length; i++) {
                for (int j = 0; j < matrizResultado[0].length; j++) {
                    if (matrizResultado[i][j] > maximo) {
                        maximo = matrizResultado[i][j];
                        filaMaximo = i;
                        columnaMaximo = j;
                    }
                }
            }

            // Poner en 0 el valor más alto encontrado
            matrizResultado[filaMaximo][columnaMaximo] = 0;
            matrizResultado[columnaMaximo][filaMaximo] = 0;
        }

        return matrizResultado;
    }

    public int[][] obtenerMatrizRegiones() {
        return matrizDeRegiones;
    }

    public int[][] obtenerMatrizRelacion() {
        return matrizDeRelacion;
    }

    public boolean esMapaConexo(int[][] matriz) {
        
        return BFS.grafoEsConexo(matriz);
    }
    
    public ArrayList<Relacion> obtenerRelaciones() {
        return relaciones;
    }

}
