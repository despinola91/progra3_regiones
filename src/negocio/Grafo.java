package negocio;


public class Grafo {
	//Representamos el grafo por su matriz de adyacencia es cuadrada: A
	private boolean[][] A;
	//SUponemos que un grafo se construye por su cantidad de vertices
	//cantidad de vertices predeterminada desde el constructor
	public Grafo (int vertices) {
		//suponemos q un constructor genera un grafo con todos sus vertices aislados
		//grafo con 7 vertices pero ninguna arista: entonces matriz de adyacencia con todos falsos
		A=new boolean[vertices][vertices];
	}
	
	public Grafo() {
		// TODO Auto-generated constructor stub
	}

	//metodo para agregar arista:
	public void agregarArista(int i, int j) {
		//sería poner un true en la posicion ij de la matriz y la ji para q sean simétricas
		A[i][j]=true;
		A[j][i]=true;
	}
	//consultar si existe arista 
	public boolean existeArista(int i, int j) {
		return A[i][j];
	}
	


}

