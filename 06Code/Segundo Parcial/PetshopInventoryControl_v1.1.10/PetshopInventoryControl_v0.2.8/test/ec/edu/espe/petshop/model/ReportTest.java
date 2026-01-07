/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit4TestClass.java to edit this template
 */
package ec.edu.espe.petshop.model;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

/**
 *
 * @author Steven Loza @ESPE
 */
public class ReportTest {
    
   @Test
   public void testFailWrongInventoryReference() {
        Inventory inv = new Inventory();
        Inventory inv2 = new Inventory();
        Report r = new Report();
        r.generateInventoryReport(inv);
        assertSame(inv, inv2);
    }
}
