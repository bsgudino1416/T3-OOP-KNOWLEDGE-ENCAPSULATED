package ec.edu.espe.sibgletonpetshopinventory;

// Importamos las piezas que vamos a conectar
import ec.edu.espe.view.PetShopView;
import ec.edu.espe.controller.PetShopController;

public class Main {

    public static void main(String[] args) {
        System.out.println("Iniciando sistema PetShop...");

        // 1. Crear la VISTA (La ventana)
        PetShopView vista = new PetShopView();
        
        // 2. Crear el CONTROLADOR
        // Al crear el controlador, este llama internamente al Singleton.
        // El Singleton, al despertarse, cargará automáticamente tu archivo JSON.
        PetShopController controlador = new PetShopController(vista);
        
        // 3. Mostrar la ventana
        vista.setVisible(true);
        
        System.out.println("Sistema cargado y listo.");
    }
}

