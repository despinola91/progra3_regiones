package negocio;

import java.util.Arrays;

public class Prim {

    public static int[][] generarAGM(int[][] matrizAdyacencia) {

        int size = matrizAdyacencia.length;

        int[] parent = new int[size]; // Array para almacenar el Árbol Generador Mínimo
        int[] key = new int[size]; // Claves utilizadas para seleccionar el mínimo borde del corte

        boolean[] mstSet = new boolean[size]; // Conjunto para representar los vértices aún no incluidos en el Árbol Generador Mínimo

        // Inicializar todas las claves como infinito
        Arrays.fill(key, Integer.MAX_VALUE);
        // Inicializar todos los vértices como no incluidos en el Árbol Generador Mínimo
        Arrays.fill(mstSet, false);

        // El primer vértice siempre se incluye en el Árbol Generador Mínimo
        key[0] = 0; // La clave del vértice 0 es 0
        parent[0] = -1; // El primer nodo no tiene padre

        // El Árbol Generador Mínimo tendrá V-1 aristas
        for (int count = 0; count < size - 1; count++) {
            // Seleccionar el vértice con la clave mínima del conjunto de vértices aún no incluidos en el MST
            int u = minKey(key, mstSet, size);
            // Agregar el vértice seleccionado al conjunto MSTSet
            mstSet[u] = true;

            // Actualizar el valor de clave y el array de padres de los vértices adyacentes del vértice seleccionado
            // Solo si el valor de la matriz de adyacencia es menor que la clave actual del vértice y el vértice adyacente no está aún en el Árbol Generador Mínimo
            for (int v = 0; v < size; v++) {
                if (matrizAdyacencia[u][v] != 0 && !mstSet[v] && matrizAdyacencia[u][v] < key[v]) {
                    parent[v] = u;
                    key[v] = matrizAdyacencia[u][v];
                }
            }
        }

        // Crear la matriz de adyacencia para el Árbol Generador Mínimo resultante
        int[][] mst = new int[size][size];
        for (int i = 1; i < size; i++) {
            mst[parent[i]][i] = matrizAdyacencia[i][parent[i]];
            mst[i][parent[i]] = matrizAdyacencia[i][parent[i]];
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
}
