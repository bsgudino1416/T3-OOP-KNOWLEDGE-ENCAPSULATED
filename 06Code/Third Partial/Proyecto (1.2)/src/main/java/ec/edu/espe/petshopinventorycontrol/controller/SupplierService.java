package ec.edu.espe.petshopinventorycontrol.controller;

import ec.edu.espe.petshopinventorycontrol.model.mongo.MongoProductGateway;
import ec.edu.espe.petshopinventorycontrol.model.mongo.MongoSupplierGateway;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.*;
import org.bson.Document;

public final class SupplierService {

    private static final int COUNTER_WIDTH = 3;

    private final MongoSupplierGateway mongo;
    private final SupplierValidator validator;

    public SupplierService(MongoSupplierGateway mongo, SupplierValidator validator) {
        this.mongo = mongo;
        this.validator = validator;
    }

    public String generateNextSupplierId(Date now) {
        String prefix = new SimpleDateFormat("ddMMyy").format(now); // ddmmyy
        String lastId = mongo.findLastIdByPrefix(prefix);

        int next = 1;
        if (lastId != null && lastId.startsWith(prefix + "-")) {
            String[] parts = lastId.split("-");
            if (parts.length == 2) {
                try {
                    next = Integer.parseInt(parts[1]) + 1;
                } catch (NumberFormatException ignored) {
                    next = 1;
                }
            }
        }

        return prefix + "-" + leftPad(next, COUNTER_WIDTH);
    }

    public java.util.Map<String, String> validateFields(String typeSupplier,
            String phoneSupplier,
            String phone2Supplier,
            String nameSupplier,
            String citySupplier,
            String stateSupplier,
            String enterpriseSupplier,
            String emailSupplier,
            Date entryDate) {
        return validator.validate(typeSupplier, phoneSupplier, phone2Supplier, nameSupplier,
                citySupplier, stateSupplier, enterpriseSupplier, emailSupplier, entryDate);
    }

    public void saveSupplier(String idSupplier,
            String typeSupplier,
            String phoneSupplier,
            String phone2Supplier,
            String nameSupplier,
            String citySupplier,
            String stateSupplier,
            String enterpriseSupplier,
            String emailSupplier,
            Date entryDate) {

        // Guardado con los nombres EXACTOS que pediste:
        Document doc = new Document()
                .append("idSupplier", idSupplier)
                .append("TypeSupplier", safe(typeSupplier))
                .append("PhoneSupplier", safe(phoneSupplier))
                .append("Phone2Supplier", safe(phone2Supplier))
                .append("NameSupplier", safe(nameSupplier))
                .append("CitySupplier", safe(citySupplier))
                .append("StateSupplier", safe(stateSupplier))
                .append("EnterpriselSupplier", safe(enterpriseSupplier))
                .append("emailSupplier", safe(emailSupplier))
                .append("DateEntry", entryDate);

        mongo.insertSupplier(doc);
    }

    private String safe(String v) {
        return v == null ? "" : v.trim();
    }

    private String leftPad(int n, int width) {
        String s = String.valueOf(n);
        while (s.length() < width) {
            s = "0" + s;
        }
        return s;
    }

    public List<String> getEnterpriseOptions() {
        return mongo.findDistinctEnterprises();
    }

}
