package ec.edu.espe.petshopinventorycontrol.controller;

import ec.edu.espe.petshopinventorycontrol.view.FrmEmployeeSummary;


/**
 *
 * @author Mikael Hidalgo, KNOWLEDGE ENCAPSULATE, @ESPE
 */
public class ActiveSale {
    
    private static FrmEmployeeSummary summary;

    private ActiveSale() {
    }

    public static FrmEmployeeSummary getSummary() {
        if (summary == null) {
            summary = FrmEmployeeSummary.getInstance();
        }
        return summary;
    }
    
}
