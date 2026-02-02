package ec.edu.espe.petshopinventorycontrol.controller;

import ec.edu.espe.petshopinventorycontrol.model.Product;

/**
 * PATRÓN FACTORY METHOD:
 * Centraliza la creación de objetos Product.
 */
public class ProductFactory {

    public static Product createProduct(String id, String name, double price, int stock,
                                        String category, String animal, String size, String brand) {
        
        // Aquí podrías agregar lógica compleja de creación si fuera necesario
        // Por ejemplo: if (category.equals("FOOD")) return new FoodProduct(...);
        
        return new Product(id, name, price, stock, category, animal, size, brand);
    }
}