package negocio;

import java.util.LinkedList;
import java.util.Queue;

public class BFS {
    
    public static boolean grafoEsConexo(int[][] matrizAdyacencia) {
        int n = matrizAdyacencia.length;
        boolean[] provinciasVisitadas = new boolean[n];

        Queue<Integer> cola = new LinkedList<>();
        provinciasVisitadas[0] = true;
        cola.offer(0);

        while (!cola.isEmpty()) {
            int actual = cola.poll();
            for (int i = 0; i < matrizAdyacencia.length; i++) {
                if (matrizAdyacencia[actual][i] > 0 && !provinciasVisitadas[i]) {
                    provinciasVisitadas[i] = true;
                    cola.offer(i);
                }
            }
        }

        for (boolean provinciaVisitada : provinciasVisitadas) {
            if (!provinciaVisitada) {
                return false;
            }
        }
        return true;
    }
}
