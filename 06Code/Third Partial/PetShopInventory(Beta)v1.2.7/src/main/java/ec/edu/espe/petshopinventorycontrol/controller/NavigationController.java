package ec.edu.espe.petshopinventorycontrol.controller;

import ec.edu.espe.petshopinventorycontrol.view.FrmEmployee;
import javax.swing.JFrame;

/**
 *
 * @author Bryan Gudino, KNOWLEDGE ENCAPSULATE, @ESPE
 */
public class NavigationController {
    
        public void goToEmployeeMenu(JFrame currentFrame) {
        FrmEmployee employeeMenu = new FrmEmployee();
        employeeMenu.setVisible(true);
        currentFrame.dispose();
    }
    
}
