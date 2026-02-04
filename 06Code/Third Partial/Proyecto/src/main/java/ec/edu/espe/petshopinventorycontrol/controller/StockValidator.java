package ec.edu.espe.petshopinventorycontrol.controller;

import java.util.*;
import java.util.HashMap;

public class StockValidator {

    public Map<String, String> validateFields(
            String idStock, String category, String name, String brand,
            String costText, String unitEntryText,
            String gain, String finalPriceText, String gainValueText
    ) {
        Map<String, String> errors = new HashMap<>();

        if (isBlank(idStock)) {
            errors.put("IdStock", "Required");
        }
        if (isBlank(category) || category.startsWith("No ")) {
            errors.put("cmbCategory", "Required");
        }
        if (isBlank(name) || name.startsWith("No ")) {
            errors.put("cmbNameofProduct", "Required");
        }
        if (isBlank(brand) || brand.startsWith("No ")) {
            errors.put("cmbBrand", "Required");
        }

        if (isBlank(costText) || !isNumeric(costText)) {
            errors.put("txtCost", "Required numeric");
        }
        Double units = parseNumber(unitEntryText);
        if (units == null || units <= 0) {
            errors.put("txtUnitEntry", "Required numeric");
        }
        if (isBlank(gain) || gain.trim().isEmpty() || gain.trim().equals("%")) {
            errors.put("cmbGain", "Required");
        }

        return errors;
    }

    private boolean isBlank(String s) {
        return s == null || s.trim().isEmpty();
    }

    private boolean isNumeric(String s) {
        try {
            Double.parseDouble(s.trim());
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    private Double parseNumber(String s) {
        if (isBlank(s)) {
            return null;
        }
        try {
            return Double.parseDouble(s.trim());
        } catch (Exception e) {
            return null;
        }
    }

}
