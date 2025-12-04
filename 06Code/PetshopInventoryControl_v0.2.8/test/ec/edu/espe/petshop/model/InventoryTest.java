/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit4TestClass.java to edit this template
 */
package ec.edu.espe.petshop.model;

import java.io.File;
import java.util.List;
import java.util.Scanner;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Steven Loza @ESPE
 */
public class InventoryTest {

    @Test
     public void testFindProductsByNameStrictMatch() {
        Inventory inv = new Inventory();
        inv.addProduct(new Product("1", "Super Snack", 1.0, 10, "FOOD", "Dog", "S", "BrandX"));
        List<Product> res = inv.findProductsByName("Snack");
        assertEquals(0, res.size());  // Falla: contains("Snack") sí coincide
    }
}
