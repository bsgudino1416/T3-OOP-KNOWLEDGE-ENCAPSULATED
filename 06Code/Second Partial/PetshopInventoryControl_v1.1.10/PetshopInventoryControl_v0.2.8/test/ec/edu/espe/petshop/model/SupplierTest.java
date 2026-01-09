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
public class SupplierTest {
    
     @Test
   public void test10ToStringContainsProveedor() {
        Supplier s = new Supplier(10, "PetPlus", "Rosa", "088888888");
        boolean result = s.toString().startsWith("Proveedor:");
        System.out.println("Test 10: " + (result ? "PASA" : "FALLA"));
        assertTrue(result); 
    }
    
}
