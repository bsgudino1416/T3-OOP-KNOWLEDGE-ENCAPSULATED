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
public class StoreTest {
    
     public void testAddEmployeeReturnFails() {
        Store store = new Store("PetShop");
        Employee e = new Employee(5, "Mario", "abc", "Gerente");

    }
}
