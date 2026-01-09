/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit4TestClass.java to edit this template
 */
package ec.edu.espe.petshop.model;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Steven Loza @ESPE
 */
public class ProductTest {
    
    @Test
  public void testFailIdMustBeInteger() {
        Product p = new Product("ABC1", "Pelota", 3.0, 2, "TOY", "Dog", "S", "BrandY");
        assertEquals("1", p.getId());
    }
    
}
