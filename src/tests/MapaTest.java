package tests;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.openstreetmap.gui.jmapviewer.Coordinate;

import negocio.Mapa;
import negocio.Provincia;

class MapaTest {

	@Test
	void agregarProvinciaTest() {

		Mapa mapa = new Mapa();
		mapa.agregarProvincia("Buenos Aires", new Coordinate(-43, 64));
		mapa.agregarProvincia("Santa Fe", new Coordinate(87, -93));
		mapa.agregarProvincia("Corrientes", new Coordinate(12, -65));

		assertEquals(3, mapa.obtenerDimensionMatrizRelacion());
		assertTrue((mapa.existeProvincia("Buenos Aires")));
		assertTrue((mapa.existeProvincia("Santa Fe")));
		assertTrue((mapa.existeProvincia("Corrientes")));
		assertFalse((mapa.existeProvincia("Chaco")));

		Assertions.assertThrows(IllegalArgumentException.class, () -> mapa.agregarProvincia("Buenos Aires", new Coordinate(50., 50)));
	}

	@Test
	void agregarRelacionTest() {

		Mapa mapa = new Mapa();
		mapa.agregarProvincia("Buenos Aires", new Coordinate(-43, 64));
		mapa.agregarProvincia("Santa Fe", new Coordinate(87, -93));

		mapa.agregarRelacion("Buenos Aires", "Santa Fe", 5);
		assertTrue(mapa.existeRelacion("Buenos Aires", "Santa Fe"));
		assertTrue(mapa.existeRelacion("Santa Fe", "Buenos Aires"));

		Assertions.assertThrows(IllegalArgumentException.class, () -> mapa.agregarRelacion("Buenos Aires", "Buenos Aires", 5));
		Assertions.assertThrows(IllegalArgumentException.class, () -> mapa.agregarRelacion("Buenos Aires", "Chaco", 5));
	}

	@Test
	void eliminarProvinciaTest() {

		Mapa mapa = new Mapa();
		mapa.agregarProvincia("Buenos Aires", new Coordinate(87, -34));
		mapa.agregarProvincia("Santa Fe", new Coordinate(-12, 65));

		mapa.eliminarProvincia("Santa Fe");
		assertFalse(mapa.existeProvincia("Santa Fe"));
		Assertions.assertThrows(IllegalArgumentException.class, () -> mapa.eliminarProvincia("Chaco"));

	}

	@Test
	void eliminarRelacionTest() {
		Mapa mapa = new Mapa();
		mapa.agregarProvincia("Buenos Aires", new Coordinate(98, 89));
		mapa.agregarProvincia("Santa Fe", new Coordinate(-93, 72));

		mapa.agregarRelacion("Santa Fe", "Buenos Aires", 1);
		assertTrue(mapa.existeRelacion("Santa Fe", "Buenos Aires"));
		mapa.eliminarRelacion("Buenos Aires", "Santa Fe");
		assertFalse(mapa.existeRelacion("Santa Fe", "Buenos Aires"));
	}

	@Test
	void obtenerProvinciasTest() {
		Mapa mapa = new Mapa();

		mapa.agregarProvincia("Salta", new Coordinate(72, 12));
		mapa.agregarProvincia("Tucuman", new Coordinate(-12, 23));
		mapa.agregarProvincia("Catamarca", new Coordinate(-12, 12));
		mapa.agregarProvincia("Buenos Aires", new Coordinate(-34, 34));

		ArrayList<String> provinciasResultado = mapa.obtenerProvincias();

		ArrayList<String> provinciasEsperadas = new ArrayList<>();
        provinciasEsperadas.add("Buenos Aires");
		provinciasEsperadas.add("Catamarca");
		provinciasEsperadas.add("Salta");
		provinciasEsperadas.add("Tucuman");


 		assertTrue(provinciasEsperadas.equals(provinciasResultado));
	}

	@Test
	void obtenerRegionesTest() {
		Mapa mapa = new Mapa();
		Assertions.assertThrows(IllegalArgumentException.class, () -> mapa.generarRegiones(3, "kruskal"));
		Assertions.assertThrows(IllegalArgumentException.class, () -> mapa.generarRegiones(3, "prim"));
	}

	@Test
	void obtenerProvinciaporNombreTest() {
	
		Mapa mapa = new Mapa();
		mapa.agregarProvincia("Buenos Aires", new Coordinate(50, 60));
		Provincia provincia = mapa.obtenerProvinciaPorNombre("Buenos Aires");
		
		assertTrue(provincia.obtenerId() ==0);
		assertTrue(provincia.obtenerNombre() == "Buenos Aires");
		assertTrue(provincia.obtenerCoordenadas().getLat() == 50.00);
		assertTrue(provincia.obtenerCoordenadas().getLon() == 60.00);
	}

	@Test
	void obtenerProvinciaporIdTest() {
	
		Mapa mapa = new Mapa();
		mapa.agregarProvincia("Buenos Aires", new Coordinate(50, 60));
		Provincia provincia = mapa.obtenerProvinciaPorId(0);

		assertTrue(provincia.obtenerId() ==0);
		assertTrue(provincia.obtenerNombre() == "Buenos Aires");
		assertTrue(provincia.obtenerCoordenadas().getLat() == 50);
		assertTrue(provincia.obtenerCoordenadas().getLon() == 60);
		Assertions.assertThrows(IllegalArgumentException.class, () -> mapa.obtenerProvinciaPorId(1));
	}	

	@Test
	void obtenerMatrizRelacionTest() {

		Mapa mapa = new Mapa();
		mapa.agregarProvincia("Buenos Aires", new Coordinate(-43, 64));
		mapa.agregarProvincia("Santa Fe", new Coordinate(87, -93));
		mapa.agregarProvincia("Chaco", new Coordinate(37, -73));

		mapa.agregarRelacion("Buenos Aires", "Santa Fe", 5);
		int[][] expectedMatrix = {{0, 5, 0}, 
								  {5, 0, 0},
								  {0, 0, 0}};
		assertArrayEquals(expectedMatrix, mapa.obtenerMatrizRelacion());
	}

	@Test
	void dividirRegionesTest() {
		Mapa mapa = new Mapa();
		
		int[][] matrizDeRegiones = {
			{0, 2, 0, 6, 0},
			{2, 0, 3, 0, 5},
			{0, 3, 0, 0, 0},
			{6, 0, 0, 0, 0},
			{0, 5, 0, 0, 0}
		};

		int[][] matrizEsperada = {
			{0, 2, 0, 0, 0},
			{2, 0, 3, 0, 5},
			{0, 3, 0, 0, 0},
			{0, 0, 0, 0, 0},
			{0, 5, 0, 0, 0}
		};

		assertArrayEquals(matrizEsperada, mapa.dividirRegiones(matrizDeRegiones, 2));
	}
}
