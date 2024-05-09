package negocio;

import java.util.Arrays;

public class Prim {

    int[][] matrizRegiones;

	public static void crearAlgoritmoPrim() {
		// TODO Auto-generated method stub
		
	}

    public static int[][] generarAGM(int[][] graph) {
        // TODO Auto-generated method stub
        //throw new UnsupportedOperationException("Unimplemented method 'obtenerRegiones'");

        
        int[] parent = new int[graph.length]; // Array para almacenar el Árbol Generador Mínimo
        int[] key = new int[graph.length]; // Claves utilizadas para seleccionar el mínimo borde del corte

        boolean[] mstSet = new boolean[graph.length]; // Conjunto para representar los vértices aún no incluidos en el Árbol Generador Mínimo

        // Inicializar todas las claves como infinito
        Arrays.fill(key, Integer.MAX_VALUE);
        // Inicializar todos los vértices como no incluidos en el Árbol Generador Mínimo
        Arrays.fill(mstSet, false);

        // El primer vértice siempre se incluye en el Árbol Generador Mínimo
        key[0] = 0; // La clave del vértice 0 es 0
        parent[0] = -1; // El primer nodo no tiene padre

        // El Árbol Generador Mínimo tendrá V-1 aristas
        for (int count = 0; count < graph.length - 1; count++) {
            // Seleccionar el vértice con la clave mínima del conjunto de vértices aún no incluidos en el MST
            int u = minKey(key, mstSet, graph.length);
            // Agregar el vértice seleccionado al conjunto MSTSet
            mstSet[u] = true;

            // Actualizar el valor de clave y el array de padres de los vértices adyacentes del vértice seleccionado
            // Solo si el valor de la matriz de adyacencia es menor que la clave actual del vértice y el vértice adyacente no está aún en el Árbol Generador Mínimo
            for (int v = 0; v < graph.length; v++) {
                if (graph[u][v] != 0 && !mstSet[v] && graph[u][v] < key[v]) {
                    parent[v] = u;
                    key[v] = graph[u][v];
                }
            }
        }

        // Crear la matriz de adyacencia para el Árbol Generador Mínimo resultante
        int[][] mst = new int[graph.length][graph.length];
        for (int i = 1; i < graph.length; i++) {
            mst[parent[i]][i] = graph[i][parent[i]];
            mst[i][parent[i]] = graph[i][parent[i]];
        }

        // Devolver la matriz de adyacencia del Árbol Generador Mínimo resultante
        return mst;
        
    }

    private static int minKey(int[] key, boolean[] mstSet, int V) {
        int min = Integer.MAX_VALUE, minIndex = -1;

        for (int v = 0; v < V; v++) {
            if (!mstSet[v] && key[v] < min) {
                min = key[v];
                minIndex = v;
            }
        }

        return minIndex;
    }
    // public static int[][] obtenerRegiones(int[][] matrizDeRelacion, int cantidadRegiones) {
    //     // TODO Auto-generated method stub
    //     throw new UnsupportedOperationException("Unimplemented method 'obtenerRegiones'");


        
    // }

}
