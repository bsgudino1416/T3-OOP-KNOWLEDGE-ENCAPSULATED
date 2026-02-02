package ec.edu.espe.petshopinventorycontrol.controller;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

public final class ProductValidator {

    public Map<String, String> validate(
            String idProduct,
            String supplier,
            String name,
            String typeProduct,
            String animalType,
            String brand,
            String costText,
            String unit,
            Object quantityValue,
            String investmentText,
            String poundsText,
            String totalPoundsText,
            Date entryDate,
            Date expiryDate
    ) {
        Map<String, String> errors = new LinkedHashMap<>();

        if (isBlank(idProduct)) errors.put("txtIdProduct", "Required");

        if (isBlank(supplier)
                || supplier.equalsIgnoreCase("MongoDB no disponible")
                || supplier.equalsIgnoreCase("No hay proveedores")) {
            errors.put("cmbSupplier", "Required");
        }

        if (isBlank(name)) errors.put("txtNameProduct", "Required");
        if (isBlank(typeProduct)) errors.put("cmbTypeProduct", "Required");

        // subcategoría ahora es combobox
        if (isBlank(animalType)) errors.put("cmbTypeAnimal", "Required");

        if (isBlank(brand)) errors.put("txtBrandProduct", "Required");

        if (isBlank(costText) || !isNumber(costText)) errors.put("txtCostProduct", "Invalid");
        if (isBlank(unit)) errors.put("cmbUnit", "Required");
        if (!isSpinnerNumber(quantityValue)) errors.put("splQuiantity", "Invalid");

        if (isBlank(investmentText) || !isNumber(investmentText)) errors.put("jTextField13", "Invalid");

        if (entryDate == null) errors.put("jDateChooser1", "Required");

        // caducidad solo para COMIDA y MEDICINA
        if (isExpiryRequired(typeProduct) && expiryDate == null) {
            errors.put("jDateChooser2", "Required");
        }

        // libras solo si unit == "Libras"
        if (isPoundsRequired(unit)) {
            if (isBlank(poundsText) || !isNumber(poundsText)) errors.put("txtPounds", "Invalid");
            if (isBlank(totalPoundsText) || !isNumber(totalPoundsText)) errors.put("txtTotalPounds", "Invalid");
        }

        return errors;
    }

    public Map<String, String> validateUpdateFields(
            String unit,
            Object quantityValue,
            String investmentText,
            String poundsText,
            String totalPoundsText
    ) {
        Map<String, String> errors = new LinkedHashMap<>();

        if (isBlank(unit)) errors.put("cmbUnit", "Required");
        if (!isSpinnerNumber(quantityValue)) errors.put("splQuiantity", "Invalid");
        if (isBlank(investmentText) || !isNumber(investmentText)) errors.put("jTextField13", "Invalid");

        if (isPoundsRequired(unit)) {
            if (isBlank(poundsText) || !isNumber(poundsText)) errors.put("txtPounds", "Invalid");
            if (isBlank(totalPoundsText) || !isNumber(totalPoundsText)) errors.put("txtTotalPounds", "Invalid");
        }

        return errors;
    }

    private boolean isExpiryRequired(String typeProduct) {
        if (typeProduct == null) return false;
        String t = typeProduct.trim().toUpperCase();
        return t.equals("COMIDA") || t.equals("MEDICINA");
    }

    private boolean isPoundsRequired(String unit) {
        if (unit == null) return false;
        return unit.trim().equalsIgnoreCase("Libras");
    }

    private boolean isBlank(String s) {
        return s == null || s.trim().isEmpty();
    }

    private boolean isNumber(String s) {
        try {
            Double.valueOf(s.trim()); // permite negativos
            return true;
        } catch (NumberFormatException ex) {
            return false;
        }
    }

    private boolean isSpinnerNumber(Object v) {
        return v instanceof Number;
    }
}
