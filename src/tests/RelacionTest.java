package tests;

import java.util.ArrayList;
import java.util.Collections;

import org.junit.Test;

import static org.junit.jupiter.api.Assertions.*;
import org.openstreetmap.gui.jmapviewer.Coordinate;

import negocio.Provincia;
import negocio.Relacion;

public class RelacionTest {
    
    @Test
    public void compareToTest() {
        
        Provincia provinciaA = new Provincia(0, "Buenos Aires", new Coordinate(10.00, 20.00));
        Provincia provinciaB = new Provincia(1, "La Pampa", new Coordinate(20.00, 10.00));
        Relacion relacion1 = new Relacion(provinciaA, provinciaB, 20);

        Provincia provinciaC = new Provincia(2, "Corrientes", new Coordinate(10.00, 20.00));
        Provincia provinciaD = new Provincia(3, "Santa Fe", new Coordinate(30.00, 40.00));
        Relacion relacion2 = new Relacion(provinciaC, provinciaD, 10);

        Provincia provinciaE = new Provincia(2, "Chaco", new Coordinate(-50.00, 70.00));
        Provincia provinciaF = new Provincia(3, "Formosa", new Coordinate(-30.00, 80.00));
        Relacion relacion3 = new Relacion(provinciaE, provinciaF, 30);

        ArrayList<Relacion> relaciones = new ArrayList<>();
        relaciones.add(relacion1);
        relaciones.add(relacion2);
        relaciones.add(relacion3);
        Collections.sort(relaciones); //Ordenamos de mayor a menor similitud

        assertTrue(relaciones.get(0) == relacion3);
        assertTrue(relaciones.get(1) == relacion1);
        assertTrue(relaciones.get(2) == relacion2);
    }
}
