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
public class EmployeeTest {
    
   
    @Test
  public void testPasswordNullEsperado() {
        Employee e = new Employee(10, "Eva", "Manager", "eva", "admin1");
        assertTrue(e.login("eva", null)); // ❌ FALLA (null genera false)
    }
}
