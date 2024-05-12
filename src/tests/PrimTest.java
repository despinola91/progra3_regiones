package tests;

import static org.junit.Assert.assertArrayEquals;

import org.junit.Test;

import negocio.Prim;

public class PrimTest {
	@Test
    public void generarAGMSimpleTest() {
        int[][] matrizAdyacencia = {
            {0, 2, 0, 6, 0},
            {2, 0, 3, 8, 5},
            {0, 3, 0, 0, 7},
            {6, 8, 0, 0, 9},
            {0, 5, 7, 9, 0}
        };
        int[][] arbolGeneradorMinimoEsperado = {
            {0, 2, 0, 6, 0},
            {2, 0, 3, 0, 5},
            {0, 3, 0, 0, 0},
            {6, 0, 0, 0, 0},
            {0, 5, 0, 0, 0}
        };
        assertArrayEquals(arbolGeneradorMinimoEsperado, Prim.generarAGM(matrizAdyacencia));
    }

    @Test
    public void generarAGMCompletoTest() {
        int[][] matrizAdyacencia = {
            {0, 2, 3, 4},
            {2, 0, 5, 6},
            {3, 5, 0, 7},
            {4, 6, 7, 0}
        };
        int[][] arbolGeneradorMinimoEsperado = {
            {0, 2, 3, 4},
            {2, 0, 0, 0},
            {3, 0, 0, 0},
            {4, 0, 0, 0}
        };
        assertArrayEquals(arbolGeneradorMinimoEsperado, Prim.generarAGM(matrizAdyacencia));
    }
}
