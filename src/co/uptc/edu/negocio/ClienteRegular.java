package co.uptc.edu.negocio;

public class ClienteRegular extends Cliente {

    public ClienteRegular(String id, String nombre, String email, String direccion,
                          String telefono, String contrasena) {
        super(id, nombre, email, direccion, telefono, contrasena);
    }

    @Override
    public double calcularDescuento(double total) {
        return 0;
    }
}
