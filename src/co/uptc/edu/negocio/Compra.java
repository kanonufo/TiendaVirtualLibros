package co.uptc.edu.negocio;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Compra {

    private String idCompra;
    private Date fecha;
    private Cliente cliente;
    private MetodoPago metodoPago;
    private List<ItemCompra> items;
    private double totalFinal;

    public Compra(Cliente cliente, MetodoPago metodoPago) {
        this.cliente = cliente;
        this.metodoPago = metodoPago;
        this.fecha = new Date();
        this.items = new ArrayList<>();
    }

    public String getIdCompra() {
        return idCompra;
    }

    public void setIdCompra(String idCompra) {
        this.idCompra = idCompra;
    }

    public Date getFecha() {
        return fecha;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public MetodoPago getMetodoPago() {
        return metodoPago;
    }

    public void setMetodoPago(MetodoPago metodoPago) {
        this.metodoPago = metodoPago;
    }

    public List<ItemCompra> getItems() {
        return items;
    }

    public double getTotalFinal() {
        return totalFinal;
    }

    public double calcularTotal() {
        return 0;
    }

    public String generarDetalle() {
        return "";
    }
}
