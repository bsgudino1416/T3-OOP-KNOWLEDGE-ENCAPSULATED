package ec.edu.espe.petshopinventorycontrol.data;

import ec.edu.espe.petshopinventorycontrol.employee.view.FrmEmployeeSummary;


/**
 *
 * @author Bryan Gudino, KNOWLEDGE ENCAPSULATE, @ESPE
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
