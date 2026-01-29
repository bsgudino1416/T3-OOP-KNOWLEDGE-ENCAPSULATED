package ec.edu.espe.petshopinventorycontrol.controller;

import java.util.Date;
import java.util.Map;

public interface SupplierFormView {
    String getSupplierId();
    String getTypeSupplier();
    String getPhoneSupplier();
    String getPhone2Supplier();
    String getNameSupplier();
    String getCitySupplier();
    String getStateSupplier();
    String getEnterpriseSupplier();
    String getEmailSupplier();
    Date getEntryDate();

    void setSupplierId(String id);
    void clearSupplierFields();
    void applyErrors(Map<String, String> errors);
    void showMessage(String message, String title, int messageType);
}
