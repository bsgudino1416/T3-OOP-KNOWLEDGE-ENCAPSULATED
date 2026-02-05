package ec.edu.espe.petshopinventorycontrol.controller;

import java.util.LinkedHashMap;
import java.util.Map;

public final class SupplierValidator {

    public Map<String, String> validate(String typeSupplier,
                                        String phoneSupplier,
                                        String phone2Supplier,
                                        String nameSupplier,
                                        String citySupplier,
                                        String stateSupplier,
                                        String enterpriseSupplier,
                                        String emailSupplier,
                                        java.util.Date entryDate) {

        Map<String, String> errors = new LinkedHashMap<>();

        require(typeSupplier, "txtTypeSupplier", errors);
        require(phoneSupplier, "txtPhoneSupplier", errors);
        require(phone2Supplier, "txtPhone2Supplier", errors);
        require(nameSupplier, "txtNameSupplier", errors);
        require(citySupplier, "txtCitySupplier", errors);
        require(stateSupplier, "txtStateSupplier", errors);
        require(enterpriseSupplier, "txtEnterpriseSupplier", errors);
        require(emailSupplier, "txtemailSupplier", errors);

        if (entryDate == null) {
            errors.put("jDateEntry", "Entry date is required");
        }

        return errors;
    }

    private void require(String value, String fieldKey, Map<String, String> errors) {
        if (value == null || value.trim().isEmpty()) {
            errors.put(fieldKey, "Field is required");
        }
    }
}

