package co.uptc.edu.negocio;

public class ItemCompra {

    private Libro libro;
    private int cantidad;
    private double subtotal;
    private double impuestos;

    public ItemCompra(Libro libro, int cantidad) {
        this.libro = libro;
        this.cantidad = cantidad;
    }

    public Libro getLibro() {
        return libro;
    }

    public void setLibro(Libro libro) {
        this.libro = libro;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public double getSubtotal() {
        return subtotal;
    }

    public double getImpuestos() {
        return impuestos;
    }

    public void calcularSubtotal() {
    }

    public void calcularImpuestos() {
    }
}
