/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit4TestClass.java to edit this template
 */
package ec.edu.espe.petshop.controller;

import ec.edu.espe.petshop.model.Customer;
import ec.edu.espe.petshop.model.Employee;
import ec.edu.espe.petshop.model.Inventory;
import ec.edu.espe.petshop.model.Product;
import ec.edu.espe.petshop.model.Store;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;
import java.util.Scanner;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Steven Loza @ESPE
 */
public class PetShopMainTest {
    
    @Test
      public void testMenuOptionInvalid() {
        String input = "99\n0\n";  
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        PetShopMain.main(new String[]{});

        assertEquals(1, 2); // falla garantizado
    }
}
