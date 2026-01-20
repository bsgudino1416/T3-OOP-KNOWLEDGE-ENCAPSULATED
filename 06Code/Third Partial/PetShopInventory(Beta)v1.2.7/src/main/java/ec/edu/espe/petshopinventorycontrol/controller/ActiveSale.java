package ec.edu.espe.petshopinventorycontrol.controller;

/**
 *
 * @author Mikael Hidalgo, KNOWLEDGE ENCAPSULATE, @ESPE
 */
public class ActiveSale {

    private static EmployeeSummaryView summary;

    private ActiveSale() {}

    public static void setSummary(EmployeeSummaryView view) {
        summary = view;
    }

    public static EmployeeSummaryView getSummary() {
        return summary;
    }
}
