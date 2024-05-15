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

    /**
     * Agrega una relación al mapa entre dos provincias.
     * @param nombreProvincia1
     * @param nombreProvincia2
     * @param similitud
     */
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

    /**
     * Elimina una relación entre dos provincias.
     * @param nombreProvincia1
     * @param nombreProvincia2
     */
    public void eliminarRelacion(String nombreProvincia1, String nombreProvincia2)
	{
		validarRelacion(nombreProvincia1, nombreProvincia2);

        Provincia provinciaA = provincias.get(nombreProvincia1);
        Provincia provinciaB = provincias.get(nombreProvincia2);

        int idProv1 = provinciaA.obtenerId();
        int idProv2 = provinciaB.obtenerId();

		matrizDeRelacion[idProv1][idProv2] = 0;
        matrizDeRelacion[idProv2][idProv1] = 0;
        
        relaciones.remove(Relacion.obtenerRelacion(relaciones, provinciaA, provinciaB));
	}

    /**
     * Valida si una relación entre dos provincias es correcta.
     * @param nombreProvincia1
     * @param nombreProvincia2
     */
    private void validarRelacion (String nombreProvincia1, String nombreProvincia2) {
        if (!existeProvincia(nombreProvincia1) || !existeProvincia(nombreProvincia2) || nombreProvincia1 == nombreProvincia2) {
            throw new IllegalArgumentException("La relación es inválida");
        }

        if (existeRelacion(nombreProvincia1, nombreProvincia2)) {
            throw new IllegalArgumentException("La relación ya existe");
        }
    }

    /**
     * Informa si ya existe una relación entre dos provincias.
     * @param nombreProvincia1
     * @param nombreProvincia2
     * @return booleano indicando si existe la relación.
     */
    public boolean existeRelacion(String nombreProvincia1, String nombreProvincia2)
	{
        int idProv1 = provincias.get(nombreProvincia1).obtenerId();
        int idProv2 = provincias.get(nombreProvincia2).obtenerId();

		return matrizDeRelacion[idProv1][idProv2] > 0;
	}

    /**
     * Informa si ya existe una relación entre dos provincias dentro de las regiones.
     * @param nombreProvincia1
     * @param nombreProvincia2
     * @return booleano indicando si existe la relación.
     */
    public boolean existeRelacionRegiones(String nombreProvincia1, String nombreProvincia2)
	{
        int idProv1 = provincias.get(nombreProvincia1).obtenerId();
        int idProv2 = provincias.get(nombreProvincia2).obtenerId();

		return matrizDeRegiones[idProv1][idProv2] > 0;
	}

    /**
     * Agrega una provincia al mapa.
     * @param nombreProvincia
     * @param coordenadas
     */
    public void agregarProvincia (String nombreProvincia, Coordinate coordenadas) {
        
        if (existeProvincia(nombreProvincia)) {
            throw new IllegalArgumentException("La provincia ya existe");
        }

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

        matrizDeRelacion = nuevaMatrizRelacion;
    }

    /**
     * Elimina provincia del mapa.
     * @param nombreProvincia
     */
    public void eliminarProvincia (String nombreProvincia) {
        if (!existeProvincia(nombreProvincia)) {
            throw new IllegalArgumentException("La provincia no existe");
        }
        
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

    /**
     * Informa si ya existe la provincia dentro del mapa.
     * @param nombreProvincia
     * @return booleano indicando si la provincia ya existe.
     */
    public boolean existeProvincia (String nombreProvincia) {
        return provincias.containsKey(nombreProvincia);
    }

    /**
     * Obtiene la dimensión de la matriz relación actual.
     * @return dimensión de la matriz.
     */
    public int obtenerDimensionMatrizRelacion() {
        return matrizDeRelacion.length;
    }

    public Provincia obtenerProvincia(String nombreProvincia) {
        Provincia provincia = provincias.get(nombreProvincia);
        return provincia;
    }

    /**
     * Obtiene lista provincias del mapa.
     * @return  lista de provincias agregadas al mapa.
     */
    public ArrayList<String> obtenerProvincias() {
        ArrayList<String> listaProvincias = new ArrayList<>();
        listaProvincias.addAll(provincias.keySet());
        Collections.sort(listaProvincias);
        
        return listaProvincias;
    }

    /**
     * Obtiene el objeto provincia a partir de un nombre de provincia.
     * @param nombreProvincia
     * @return objeto provincia.
     */
    public Provincia obtenerProvinciaPorNombre(String nombreProvincia) {
        return provincias.get(nombreProvincia);
    }

    /**
     * Obtiene provincia por id.
     * @param id
     * @return  objeto provincia.
     */
    public Provincia obtenerProvinciaPorId(int id) {
        
        for (Provincia provincia : provincias.values()) {
            if (provincia.obtenerId() == id) {
                return provincia;
            }
        }

        throw new IllegalArgumentException("Provincia not found for id: " + id);
    }

    /**
     * Genera las regiones dentro del mapa.
     * @param cantidadRegiones
     * @param algoritmo
     */
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
    
    /**
     * Divida el mapa en la cantidad de regiones solicitadas.
     * @param matrizDeRegiones
     * @param cantidadRegiones
     * @return matriz resultante luego de dividir en regiones.
     */
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

    /**
     * Obtiene matriz de regiones.
     * @return matriz de regiones.
     */
    public int[][] obtenerMatrizRegiones() {
        return matrizDeRegiones;
    }

    /**
     * Obtiene matriz de relación.
     * @return matriz de relación.
     */
    public int[][] obtenerMatrizRelacion() {
        return matrizDeRelacion;
    }

    /**
     * Indica si el mapa es conexo utilizando BFS.
     * @param matriz
     * @return booleano indicando si el mapa es conexo o no.
     */
    public boolean esMapaConexo(int[][] matriz) {
        
        return BFS.grafoEsConexo(matriz);
    }
    
    /**
     * Obtiene lista de relaciones creadas hasta el momento por el usuario.
     * @return lista de objetos de tipo Relación.
     */
    public ArrayList<Relacion> obtenerRelaciones() {
        return relaciones;
    }
    
    /**
     * Obtiene una lista de relaciones resultantes luego de crear las regiones
     * @return lista de relaciones
     */
    public ArrayList<Relacion> obtenerRelacionesRegiones() {
        
        ArrayList<Relacion> relaciones = new ArrayList<>();

        for (int i = 0; i < matrizDeRegiones.length; i++) {
            for (int j = i+1; j < matrizDeRegiones.length; j++) {
                if (matrizDeRegiones[i][j] > 0){
                    
                    Provincia provinciaA = obtenerProvinciaPorId(i);
                    Provincia provinciaB = obtenerProvinciaPorId(j);

                    relaciones.add(new Relacion(provinciaA, provinciaB, matrizDeRegiones[i][j]));
                }
            }
        }
        return relaciones;
    }

    /**
     * Reinicia el mapa.
     */
    public void reiniciarMapa() {

        matrizDeRelacion = new int[0][0];
        matrizDeRegiones = new int[0][0];
        provincias.clear();
        relaciones.clear();
    }

}
