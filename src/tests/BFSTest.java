package tests;

//import static org.junit.Assert.assertFalse;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.Test;

import negocio.BFS;

public class BFSTest {
    @Test
    public void aplicarAlgoritmoTest() {
        int[][] matrizAdyacencia = {
            {0, 2, 0, 6, 0},
            {2, 0, 3, 8, 5},
            {0, 3, 0, 0, 7},
            {6, 8, 0, 0, 9},
            {0, 5, 7, 9, 0}
        };

        assertTrue(BFS.grafoEsConexo(matrizAdyacencia));

        int[][] matrizAdyacencia2 = {
            {0, 2, 0, 0, 0},
            {2, 0, 3, 0, 5},
            {0, 3, 0, 0, 0},
            {0, 0, 0, 0, 0},
            {0, 5, 0, 0, 0}
        };

        assertFalse(BFS.grafoEsConexo(matrizAdyacencia2));
    }
}
