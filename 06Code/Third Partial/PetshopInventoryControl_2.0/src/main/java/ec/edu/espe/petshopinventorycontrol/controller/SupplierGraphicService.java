package ec.edu.espe.petshopinventorycontrol.controller;

import ec.edu.espe.petshopinventorycontrol.model.SupplierRecord;
import ec.edu.espe.petshopinventorycontrol.model.mongo.MongoSupplierGateway;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.bson.Document;

public final class SupplierGraphicService {

    private final MongoSupplierGateway gateway;
    private final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    public SupplierGraphicService(MongoSupplierGateway gateway) {
        this.gateway = gateway;
    }

    public List<SupplierRecord> fetchAll() {
        List<Document> docs = gateway.findAllOrdered();
        List<SupplierRecord> records = new ArrayList<>();

        for (Document doc : docs) {
            SupplierRecord record = new SupplierRecord();
            record.setId(readString(doc, "idSupplier"));
            record.setType(readString(doc, "TypeSupplier", "typeSupplier"));
            record.setPhone(readString(doc, "PhoneSupplier", "phoneSupplier"));
            record.setPhone2(readString(doc, "Phone2Supplier", "phone2Supplier"));
            record.setName(readString(doc, "NameSupplier", "nameSupplier"));
            record.setCity(readString(doc, "CitySupplier", "citySupplier"));
            record.setState(readString(doc, "StateSupplier", "stateSupplier"));
            record.setEnterprise(readString(doc, "EnterpriselSupplier", "enterpriseSupplier"));
            record.setEmail(readString(doc, "emailSupplier", "EmailSupplier"));
            record.setDateEntry(formatDate(doc.get("DateEntry")));
            records.add(record);
        }

        return records;
    }

    private String readString(Document doc, String... keys) {
        if (doc == null || keys == null) {
            return "";
        }
        for (String key : keys) {
            Object value = doc.get(key);
            if (value == null) {
                continue;
            }
            String text = String.valueOf(value).trim();
            if (!text.isEmpty()) {
                return text;
            }
        }
        return "";
    }

    private String formatDate(Object value) {
        if (value == null) {
            return "";
        }
        if (value instanceof Date date) {
            return dateFormat.format(date);
        }
        return String.valueOf(value);
    }
}
