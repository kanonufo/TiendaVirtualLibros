package co.uptc.edu.principal;

import javax.swing.SwingUtilities;

import co.uptc.edu.gui.VentanaLogin;

public class Principal {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new VentanaLogin().setVisible(true));
    }
}
