package co.uptc.edu.negocio;

public class ClientePremium extends Cliente {

    private String nroMembresia;
    private double acumuladoCompras;

    public ClientePremium(String id, String nombre, String email, String direccion,
                          String telefono, String contrasena, String nroMembresia) {
        super(id, nombre, email, direccion, telefono, contrasena);
        this.nroMembresia = nroMembresia;
        this.acumuladoCompras = 0;
    }

    public String getNroMembresia() {
        return nroMembresia;
    }

    public void setNroMembresia(String nroMembresia) {
        this.nroMembresia = nroMembresia;
    }

    public double getAcumuladoCompras() {
        return acumuladoCompras;
    }

    public void setAcumuladoCompras(double acumuladoCompras) {
        this.acumuladoCompras = acumuladoCompras;
    }

    @Override
    public double calcularDescuento(double total) {
        return 0;
    }
}
