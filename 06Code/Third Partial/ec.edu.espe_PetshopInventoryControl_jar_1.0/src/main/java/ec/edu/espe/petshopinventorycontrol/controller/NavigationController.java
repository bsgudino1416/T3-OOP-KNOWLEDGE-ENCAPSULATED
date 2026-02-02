package ec.edu.espe.petshopinventorycontrol.controller;

import ec.edu.espe.petshopinventorycontrol.view.FrmModernDashboard;
import javax.swing.JFrame;

/**
 * PATRÓN CONTROLLER / MEDIATOR:
 * Centraliza la lógica de movimiento entre ventanas.
 * La Vista (FrmLogin) no necesita saber que existe un Dashboard, solo pide "entrar al sistema".
 */
public class NavigationController {

    // Método para ir del Login al Dashboard Principal
    public static void enterSystem(JFrame currentLoginWindow) {
        if (currentLoginWindow != null) {
            currentLoginWindow.dispose(); // Cierra el Login
        }
        // Abre el nuevo Dashboard Moderno
        new FrmModernDashboard().setVisible(true);
    }

    // Método para cerrar sesión (del Dashboard al Login)
    public static void logout(JFrame currentDashboard) {
        if (currentDashboard != null) {
            currentDashboard.dispose(); // Cierra el Dashboard
        }
        // Vuelve al Login
        new FrmLogin().setVisible(true);
    }
}