package ec.edu.espe.petshopinventorycontrol.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public final class PersonalValidator {

    public Map<String, String> validate(
            String idPersonal,
            String ci,
            String name,
            String post,
            String schedule,
            String day,
            String address,
            String state,
            Date dateIncorporated
    ) {
        Map<String, String> errors = new HashMap<>();

        if (isBlank(idPersonal)) {
            errors.put("txtIdPersonal", "Required");
        }
        if (isBlank(ci) || !isNumeric(ci)) {
            errors.put("txtCiPersonal", "Invalid");
        }
        if (isBlank(name) || !isLettersOnly(name)) {
            errors.put("txtName", "Invalid");
        }
        if (isBlank(address) || !isLettersOnly(address)) {
            errors.put("txtAdress", "Invalid");
        }
        if (isBlankSelection(post)) {
            errors.put("cmbPost", "Required");
        }
        if (isBlankSelection(schedule)) {
            errors.put("cmbSchedule", "Required");
        }
        if (isBlankSelection(day)) {
            errors.put("cmbDay", "Required");
        }
        if (isBlankSelection(state)) {
            errors.put("cmbState", "Required");
        }
        if (dateIncorporated == null) {
            errors.put("DateofIncorporated", "Required");
        }

        return errors;
    }

    public boolean isNumeric(String value) {
        if (isBlank(value)) {
            return false;
        }
        return value.trim().matches("\\d+");
    }

    public boolean isLettersOnly(String value) {
        if (isBlank(value)) {
            return false;
        }
        return value.trim().matches("[A-Za-z\\s]+");
    }

    private boolean isBlank(String value) {
        return value == null || value.trim().isEmpty();
    }

    private boolean isBlankSelection(String value) {
        return isBlank(value) || value.trim().equals("-");
    }
}
