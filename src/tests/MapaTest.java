package tests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import negocio.Mapa;

class MapaTest {

	@Test
	void agregarProvinciaTest() {

		Mapa mapa = new Mapa();
		mapa.agregarProvincia("Buenos Aires");
		mapa.agregarProvincia("Santa Fe");
		mapa.agregarProvincia("Corrientes");

		assertEquals(3, mapa.obtenerDimensionMatrizRelacion());
		assertTrue((mapa.existeProvincia("Buenos Aires")));
		assertTrue((mapa.existeProvincia("Santa Fe")));
		assertTrue((mapa.existeProvincia("Corrientes")));
		assertFalse((mapa.existeProvincia("Chaco")));
	}

	@Test
	void agregarRelacionTest() {

		Mapa mapa = new Mapa();
		mapa.agregarProvincia("Buenos Aires");
		mapa.agregarProvincia("Santa Fe");

		mapa.agregarRelacion("Buenos Aires", "Santa Fe", 5);
		assertTrue(mapa.existeRelacion("Buenos Aires", "Santa Fe"));
		assertTrue(mapa.existeRelacion("Santa Fe", "Buenos Aires"));

		Assertions.assertThrows(IllegalArgumentException.class, () -> mapa.agregarRelacion("Buenos Aires", "Buenos Aires", 5));
	}

	@Test
	void eliminarProvinciaTest() {

		Mapa mapa = new Mapa();
		mapa.agregarProvincia("Buenos Aires");
		mapa.agregarProvincia("Santa Fe");

		mapa.eliminarProvincia("Santa Fe");
		assertFalse(mapa.existeProvincia("Santa Fe"));
		Assertions.assertThrows(IllegalArgumentException.class, () -> mapa.eliminarProvincia("Chaco"));

	}

	@Test
	void eliminarRelacionTest() {
		Mapa mapa = new Mapa();
		mapa.agregarProvincia("Buenos Aires");
		mapa.agregarProvincia("Santa Fe");

		mapa.agregarRelacion("Santa Fe", "Buenos Aires", 1);
		assertTrue(mapa.existeRelacion("Santa Fe", "Buenos Aires"));
		mapa.eliminarRelacion("Buenos Aires", "Santa Fe");
		assertFalse(mapa.existeRelacion("Santa Fe", "Buenos Aires"));
	}
}
