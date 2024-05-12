package negocio;

import java.util.*;

class Kruskal {
    static class Arista implements Comparable<Arista> {
        int origen, destino, peso;

        public Arista(int origen, int destino, int peso) {
            this.origen = origen;
            this.destino = destino;
            this.peso = peso;
        }
        
        // Se redefine para ordenarlo por peso con collections.sort
        @Override
        public int compareTo(Arista otraArista) {
            return this.peso - otraArista.peso;
        }
    }

    public static int[][] generarAGM(int[][] matrizAdyacencia) {
        int cantVertices = matrizAdyacencia.length;

        // Crear la lista de aristas a partir de la matriz de adyacencia
        ArrayList<Arista> aristas = new ArrayList<>();
        for (int i = 0; i < cantVertices; i++) {
            for (int j = i + 1; j < cantVertices; j++) {
                if (matrizAdyacencia[i][j] != 0) {
                    aristas.add(new Arista(i, j, matrizAdyacencia[i][j]));
                }
            }
        }

        // Aplicar el algoritmo de Kruskal
        Collections.sort(aristas); // Ordeno por su peso
        ArrayList<Arista> resultado = new ArrayList<>(); // AGM representado en aristas
        int[] padre = new int[cantVertices]; //Estructura para union-find deteccion de ciclos
        
        for (int v = 0; v < cantVertices; v++)
            padre[v] = v;

        int e = 0;
        
        for (Arista arista : aristas) {
            int x = buscar(padre, arista.origen);
            int y = buscar(padre, arista.destino);
            
            // Si el nodo padre es igual en origen y destino entonces no se agrega, generaria ciclo
            if (x != y) {
                resultado.add(arista);
                e++;
                unir(padre, x, y);
            }
            if (e >= cantVertices - 1)
                break; // Salir si ya hemos añadido suficientes aristas para formar el AGM
        }

        // Crear la matriz de adyacencia del AGM resultante
        int[][] agm = new int[cantVertices][cantVertices];
        for (Arista arista : resultado) {
            agm[arista.origen][arista.destino] = arista.peso;
            agm[arista.destino][arista.origen] = arista.peso;
        }

        return agm;
    }

    
    // Aplico metodos de union find
    
    //Busco recursivamente al padre del vertice
    private static int buscar(int[] padre, int i) {
        if (padre[i] != i)
            padre[i] = buscar(padre, padre[i]);
        return padre[i];
    }

    private static void unir(int[] padre, int x, int y) {
        int raizX = buscar(padre, x);
        int raizY = buscar(padre, y);
        padre[raizX] = raizY;
    }
}