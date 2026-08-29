package co.uptc.edu.negocio;

import java.util.ArrayList;
import java.util.List;

public abstract class Cliente {

    protected String id;
    protected String nombre;
    protected String email;
    protected String direccion;
    protected String telefono;
    protected String contrasena;
    protected List<Compra> compras;

    public Cliente(String id, String nombre, String email, String direccion,
                   String telefono, String contrasena) {
        this.id = id;
        this.nombre = nombre;
        this.email = email;
        this.direccion = direccion;
        this.telefono = telefono;
        this.contrasena = contrasena;
        this.compras = new ArrayList<>();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    public List<Compra> getCompras() {
        return compras;
    }

    public abstract double calcularDescuento(double total);

    public void agregarCompra(Compra compra) {
    }
}
