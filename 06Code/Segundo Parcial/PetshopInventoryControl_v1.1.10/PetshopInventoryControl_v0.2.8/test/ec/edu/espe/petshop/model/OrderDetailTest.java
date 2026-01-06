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
public class OrderDetailTest {
    
    public OrderDetailTest() {
    }

    @Test
    public void testFailNullProductExpected() {
        OrderDetail od = new OrderDetail(null, 2);
        assertNull(od.toString());
    }
}
