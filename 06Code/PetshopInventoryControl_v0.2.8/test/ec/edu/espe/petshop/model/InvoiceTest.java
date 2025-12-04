/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit4TestClass.java to edit this template
 */
package ec.edu.espe.petshop.model;

import java.util.Date;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Steven Loza @ESPE
 */
public class InvoiceTest {
    
     @Test
   void testIdIncorrectoEsperado() {
        Customer c = new Customer(5, "Mario", "0992", "mario@x.com");
        Invoice inv = new Invoice(10, new Date(), 15.0, c, null, null);
        assertEquals("Factura #0", inv.toString());
    }

    
}
