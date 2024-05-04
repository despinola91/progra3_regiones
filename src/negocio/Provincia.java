package negocio;

public class Provincia {

    private int codigo;
    private String nombre;

    public Provincia (String nombre) {
        this.codigo = Mapa.obtenerCantidadProvincias() + 1;
        this.nombre = nombre;
    }

    public int obtenerCodigo() {
        return codigo;
    }

    public String obtenerNombre() {
        return this.nombre;
    }
}