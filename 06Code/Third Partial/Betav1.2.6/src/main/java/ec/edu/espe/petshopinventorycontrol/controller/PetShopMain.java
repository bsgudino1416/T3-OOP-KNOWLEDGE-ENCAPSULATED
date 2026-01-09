package ec.edu.espe.petshopinventorycontrol.controller;

import ec.edu.espe.petshopinventorycontrol.view.FrmLoginPetshop; 

public class PetShopMain {

    public static void main(String[] args) {
        
        // 1. Opcional: Iniciar conexión a Base de Datos (si usas Mongo o archivos globales)
        DataEmployee.conectar(); 

        // 2. Configurar el diseño visual (Look and Feel) - Opcional para que se vea moderno
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(PetShopMain.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }

        // 3. ¡LO IMPORTANTE!: Arrancar la Ventana de Login
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                // Aquí creamos y mostramos la ventana de Login
                new FrmLoginPetshop().setVisible(true);
            }
        });
        
        System.out.println("Sistema Gráfico Iniciado.");
    }
}