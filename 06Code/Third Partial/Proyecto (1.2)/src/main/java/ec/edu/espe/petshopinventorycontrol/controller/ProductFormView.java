package ec.edu.espe.petshopinventorycontrol.controller;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface ProductFormView {
    String getProductId();
    String getSupplier();
    String getProductName();
    String getTypeProduct();
    String getAnimalType();
    String getBrand();
    String getCostText();
    String getUnit();
    Object getQuantityValue();
    String getInvestmentText();
    String getPoundsText();
    String getTotalPoundsText();
    Date getEntryDate();
    Date getExpiryDate();

    void setProductId(String id);
    void setInvestmentText(String value);
    void setTotalPoundsText(String value);
    void setExpiryEnabled(boolean enabled);
    void clearExpiryDate();
    void setPoundsEnabled(boolean enabled);
    void clearPoundsFields();
    void setSupplierOptions(List<String> options);
    void applyErrors(Map<String, String> errors);
    void clearProductFields(boolean keepId);
    void showMessage(String message, String title, int messageType);
}
