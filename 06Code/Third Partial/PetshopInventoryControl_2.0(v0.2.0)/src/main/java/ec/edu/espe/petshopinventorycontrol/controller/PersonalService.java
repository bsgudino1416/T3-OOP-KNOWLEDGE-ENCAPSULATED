package ec.edu.espe.petshopinventorycontrol.controller;

import ec.edu.espe.petshopinventorycontrol.model.mongo.MongoPersonalGateway;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.regex.Pattern;
import org.bson.Document;

public final class PersonalService {

    private final MongoPersonalGateway gateway;
    private final PersonalValidator validator;

    public PersonalService(MongoPersonalGateway gateway, PersonalValidator validator) {
        this.gateway = gateway;
        this.validator = validator;
    }

    public String generateNextPersonalId(Date now) {
        String prefix = new SimpleDateFormat("ddMMyy").format(now);
        String lastId = gateway.findLastIdByPrefix(prefix);

        int next = 1;
        if (lastId != null && lastId.matches("^" + Pattern.quote(prefix) + "-\\d{3}$")) {
            next = Integer.parseInt(lastId.substring(lastId.length() - 3)) + 1;
        }
        return prefix + "-" + String.format("%03d", next);
    }

    public Map<String, String> validateFields(
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
        return validator.validate(
                idPersonal,
                ci,
                name,
                post,
                schedule,
                day,
                address,
                state,
                dateIncorporated
        );
    }

    public void savePersonal(
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
        Document doc = new Document()
                .append("IdPersonal", idPersonal)
                .append("CiPersonal", ci)
                .append("NamePersonal", name)
                .append("Post", post)
                .append("Schedule", schedule)
                .append("Day", day)
                .append("Address", address)
                .append("State", state)
                .append("DateIncorporated", dateIncorporated);

        gateway.insert(doc);
    }

    public boolean isNumericCi(String value) {
        return validator.isNumeric(value);
    }
}
