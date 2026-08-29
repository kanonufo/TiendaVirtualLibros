package co.uptc.edu.persistencia;

import java.util.List;

import co.uptc.edu.negocio.CarritoCompras;
import co.uptc.edu.negocio.Cliente;
import co.uptc.edu.negocio.Compra;
import co.uptc.edu.negocio.Libro;

public class GestorPersistencia {

    private GestorCarritoJSON gestorCarritoJSON;
    private RegistroOperacionesTXT registroOperacionesTXT;

    public GestorPersistencia() {
        this.gestorCarritoJSON = new GestorCarritoJSON();
        this.registroOperacionesTXT = new RegistroOperacionesTXT();
    }

    public void guardarCarritoJSON(CarritoCompras carrito) {
    }

    public CarritoCompras cargarCarritoJSON() {
        return null;
    }

    public void registrarOperacionTXT(String operacion) {
    }

    public void guardarClientes(List<Cliente> clientes) {
    }

    public List<Cliente> cargarClientes() {
        return null;
    }

    public void guardarLibros(List<Libro> libros) {
    }

    public List<Libro> cargarLibros() {
        return null;
    }

    public void guardarCompras(List<Compra> compras) {
    }

    public List<Compra> cargarCompras() {
        return null;
    }
}
