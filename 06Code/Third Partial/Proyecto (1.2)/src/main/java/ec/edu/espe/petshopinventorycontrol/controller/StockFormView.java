package ec.edu.espe.petshopinventorycontrol.controller;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface StockFormView {
    String getStockId();
    String getCategory();
    String getProductName();
    String getBrand();
    String getCostText();
    String getUnitCostText();
    String getUnitEntryText();
    String getGainSelection();
    String getFinalPriceText();
    String getGainValueText();

    void setStockId(String id);
    void setCategories(List<String> categories);
    void setNames(List<String> names);
    void setBrands(List<String> brands);
    void setCostText(String value);
    void setUnitCostText(String value);
    void setFinalPriceText(String value);
    void setGainValueText(String value);
    void applyErrors(Map<String, String> errors);
    void clearStockFields(boolean keepId);
    void showMessage(String message, String title, int messageType);
    Date getNow();
}
