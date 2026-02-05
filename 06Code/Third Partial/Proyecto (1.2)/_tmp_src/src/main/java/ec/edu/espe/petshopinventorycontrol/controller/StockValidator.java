package ec.edu.espe.petshopinventorycontrol.controller;

import java.util.*;
import java.util.HashMap;


public class StockValidator {

    public Map<String, String> validate(
            String idStock,
            String category,
            String name,
            String brand,
            String costText,
            String costUnitText,
            String gain,
            String calculatedGainText,
            String calculatedPercentText
    ) {
        Map<String, String> errors = new HashMap<>();

        if (isBlank(idStock)) {
            errors.put("IdStock", "Required");
        }
        if (isBlank(category)) {
            errors.put("cmbCategory", "Required");
        }
        if (isBlank(name)) {
            errors.put("cmbNameofProduct", "Required");
        }
        if (isBlank(brand)) {
            errors.put("cmbBrand", "Required");
        }

        if (isBlank(costText) || !isNumeric(costText)) {
            errors.put("txtCost", "Invalid");
        }
        if (isBlank(costUnitText) || !isNumeric(costUnitText)) {
            errors.put("cmbCostUnit", "Invalid");
        }

        if (isBlank(gain)) {
            errors.put("cmbGain", "Required");
        }
        if (isBlank(calculatedGainText) || !isNumeric(calculatedGainText)) {
            errors.put("cmbCalculateGain", "Invalid");
        }
        if (isBlank(calculatedPercentText) || !isNumeric(calculatedPercentText)) {
            errors.put("txtCalculatePercentageGain", "Invalid");
        }

        return errors;
    }

    private boolean isBlank(String v) {
        return v == null || v.trim().isEmpty();
    }

    private boolean isNumeric(String v) {
        try {
            Double.parseDouble(v.trim());
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
