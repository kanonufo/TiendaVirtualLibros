package co.uptc.edu.negocio;

import java.util.HashMap;
import java.util.Map;

public class CarritoCompras {

    private Map<Libro, Integer> items;

    public CarritoCompras() {
        this.items = new HashMap<>();
    }

    public Map<Libro, Integer> getItems() {
        return items;
    }

    public void agregarLibro(Libro libro, int cantidad) {
    }

    public void removerLibro(Libro libro) {
    }

    public double calcularSubtotal() {
        return 0;
    }

    public double calcularIVA19() {
        return 0;
    }

    public double calcularIVA5() {
        return 0;
    }

    public double calcularTotal() {
        return 0;
    }

    public void limpiar() {
    }
}
