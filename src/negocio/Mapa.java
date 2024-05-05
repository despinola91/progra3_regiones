package negocio;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class Mapa {

    private static ArrayList<Provincia> provincias;

    private int[][] matrizDeRelacion;

    public Mapa (int provincias)
	{
		matrizDeRelacion = new int[provincias][provincias];
	}

    public boolean agregarRelacion(Provincia a, Provincia b, int similitud)
	{
		if ( esRelacionValida(a, b) ) {
            matrizDeRelacion[a.obtenerCodigo()][b.obtenerCodigo()] = similitud;
            matrizDeRelacion[b.obtenerCodigo()][a.obtenerCodigo()] = similitud;
            return true;
        }
        else {
            return false;
        }
	}

    public void eliminarRelacion(Provincia a, Provincia b)
	{
		// verificarVertice(i);
		// verificarVertice(j);
		// verificarDistintos(i, j);

		matrizDeRelacion[a.obtenerCodigo()][b.obtenerCodigo()] = 0;
        matrizDeRelacion[b.obtenerCodigo()][a.obtenerCodigo()] = 0;
	}

    private boolean esRelacionValida (Provincia a, Provincia b) {
        return true;
    }

    public boolean existeRelacion(int codigoProvincia1, int codigoProvincia2)
	{
		// verificarVertice(i);
		// verificarVertice(j);
		// verificarDistintos(i, j);

		return matrizDeRelacion[codigoProvincia1][codigoProvincia2] > 0;
	}

    public boolean agregarProvincia (Provincia provincia) {

        provincias.add(provincia);
        return true;
    }

    public boolean eliminarProvincia (Provincia provincia) {

        provincias.remove(provincia);
        return true;
    }

    public Set<Provincia> obtenerProvinciasLimitrofes(int codigoProvincia)
	{
		//verificarVertice(i);
		
		Set<Provincia> provinciasLimitrofes = new HashSet<Provincia>();
		for(int j = 0; j < provincias.size(); ++j) if( codigoProvincia != j )
		{
			if( this.existeRelacion(codigoProvincia,j) )
                provinciasLimitrofes.add(obtenerProvincia(j));
		}
		
		return provinciasLimitrofes;
	}

    private static Provincia obtenerProvincia (int indice) {
        return provincias.get(indice);
    }

    public int obtenerCantidadProvinciasLimitrofes(int i)
	{
		//verificarVertice(i);
		return obtenerProvinciasLimitrofes(i).size();
	}

    public static int obtenerCantidadProvincias() {
        return provincias.size();
    }

	
}
