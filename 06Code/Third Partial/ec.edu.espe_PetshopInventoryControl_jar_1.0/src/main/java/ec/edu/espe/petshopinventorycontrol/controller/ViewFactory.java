package ec.edu.espe.petshopinventorycontrol.controller;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import ec.edu.espe.petshopinventorycontrol.view.*;

public class ViewFactory {

    
    public static void showView(String viewType) {
        JFrame view = null;

        switch (viewType) {
            case "PRODUCTOS" -> view = new FrmProduct();
            case "INVENTARIO" -> view = new FrmStock();
            case "PROVEEDORES" -> view = new FrmSupplier();
            case "FACTURACION" -> view = new FrmBill();
            case "PERSONAL" -> view = new FrmNewPersonal();
            case "REPORTES" -> // Puedes usar FrmProductsExpired o crear uno nuevo
                view = new FrmProductsExpired();
            default -> {
                JOptionPane.showMessageDialog(null, "Vista no implementada: " + viewType);
                return;
            }
        }

        if (view != null) {
            view.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // Cierra solo la ventana, no la app
            view.setLocationRelativeTo(null); // Centrar en pantalla
            view.setVisible(true);
        }
    }
}