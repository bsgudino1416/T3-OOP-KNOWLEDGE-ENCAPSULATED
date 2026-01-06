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
public class CustomerTest {
    
    public CustomerTest() {
    }

    @Test
   public void testIdMismatch_Fail() {
        Customer c = new Customer(10, "Luisa", "Quito", "0992223333");
        
        assertEquals("10 - Luisa", c.toString()); 
    }

}
