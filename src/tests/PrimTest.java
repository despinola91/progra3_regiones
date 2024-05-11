package tests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import negocio.Prim;

class PrimTest {

	@Test
	void generarAGMTest() {
		
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

}
