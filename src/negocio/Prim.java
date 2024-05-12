package negocio;

import java.util.Arrays;

public class Prim {

    public static int[][] generarAGM(int[][] matrizAdyacencia) {

        int tamanio = matrizAdyacencia.length;

        int[] nodosPadres = new int[tamanio];
        int[] aristas = new int[tamanio];
        boolean[] nodosVisitados = new boolean[tamanio];

        // Inicializamos valores
        Arrays.fill(aristas, Integer.MAX_VALUE);
        Arrays.fill(nodosVisitados, false);

        // El primer vértice siempre se incluye en el AGM
        aristas[0] = 0; // El peso de la arista desde el nodo 0 hasta el nodo 0 es 0.
        nodosPadres[0] = -1; // El primer nodo no tiene padre

        for (int i = 0; i < tamanio - 1; i++) {

            int nodo = obtenerSiguienteNodo(aristas, nodosVisitados, tamanio);
            nodosVisitados[nodo] = true;

            // Actualizar el valor de clave y el array de padres de los vértices adyacentes del vértice seleccionado
            // Solo si el valor de la matriz de adyacencia es menor que la clave actual del vértice y el vértice adyacente no está aún en el Árbol Generador Mínimo
            for (int j = 0; j < tamanio; j++) {
                if (matrizAdyacencia[nodo][j] != 0 && !nodosVisitados[j] && matrizAdyacencia[nodo][j] < aristas[j]) {
                    nodosPadres[j] = nodo;
                    aristas[j] = matrizAdyacencia[nodo][j];
                }
            }
        }

        // Crear la matriz de adyacencia para el Árbol Generador Mínimo resultante
        int[][] matrizAGM = new int[tamanio][tamanio];
        for (int i = 1; i < tamanio; i++) {
            matrizAGM[nodosPadres[i]][i] = matrizAdyacencia[i][nodosPadres[i]];
            matrizAGM[i][nodosPadres[i]] = matrizAdyacencia[i][nodosPadres[i]];
        }

        // Devolver la matriz de adyacencia del Árbol Generador Mínimo resultante
        return matrizAGM;
    }

    private static int obtenerSiguienteNodo(int[] key, boolean[] nodosVisitados, int V) {
        int min = Integer.MAX_VALUE, minIndex = -1;

        for (int v = 0; v < V; v++) {
            if (!nodosVisitados[v] && key[v] < min) {
                min = key[v];
                minIndex = v;
            }
        }
        return minIndex;
    }
}
