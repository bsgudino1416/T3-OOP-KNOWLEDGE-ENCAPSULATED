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
public class OrderTest {

    @Test
   public void testOrderIdBugFails() {
        Customer c = new Customer(2, "Pedro", "0888", "Guayaquil");
        Employee e = new Employee(3, "Maria", "pass", "Admin");

        Order o = new Order(999, c, e, new Date());

        // Debería empezar con Pedido #999
        assertTrue(o.toString().startsWith("Pedido #999"));
    }
}
