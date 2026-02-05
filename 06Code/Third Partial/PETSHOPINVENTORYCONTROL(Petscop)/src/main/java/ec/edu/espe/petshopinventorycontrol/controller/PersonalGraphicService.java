package ec.edu.espe.petshopinventorycontrol.controller;

import ec.edu.espe.petshopinventorycontrol.model.PersonalRecord;
import ec.edu.espe.petshopinventorycontrol.model.mongo.MongoPersonalGateway;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.bson.Document;

public final class PersonalGraphicService {

    private final MongoPersonalGateway gateway;
    private final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    public PersonalGraphicService(MongoPersonalGateway gateway) {
        this.gateway = gateway;
    }

    public List<PersonalRecord> fetchAll() {
        List<Document> docs = gateway.findAllOrdered();
        List<PersonalRecord> records = new ArrayList<>();

        for (Document doc : docs) {
            PersonalRecord record = new PersonalRecord();
            record.setId(readString(doc, "IdPersonal", "txtIdPersonal"));
            record.setCi(readString(doc, "CiPersonal", "txtCiPersonal"));
            record.setName(readString(doc, "NamePersonal", "txtName"));
            record.setPost(readString(doc, "Post", "cmbPost"));
            record.setSchedule(readString(doc, "Schedule", "cmbSchedule"));
            record.setDay(readString(doc, "Day", "cmbDay"));
            record.setAddress(readString(doc, "Address", "txtAdress"));
            record.setState(readString(doc, "State", "cmbState"));
            record.setDateIncorporated(formatDate(doc.get("DateIncorporated")));
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
