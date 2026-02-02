package ec.edu.espe.petshopinventorycontrol.controller;

import ec.edu.espe.petshopinventorycontrol.model.PersonalRecord;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public enum PersonalField {
    ID("Id", PersonalRecord::getId),
    CI("Ci", PersonalRecord::getCi),
    NAME("Nombre", PersonalRecord::getName),
    POST("Cargo", PersonalRecord::getPost),
    SCHEDULE("Turno", PersonalRecord::getSchedule),
    DAY("Dia", PersonalRecord::getDay),
    ADDRESS("Direccion", PersonalRecord::getAddress),
    STATE("Estado", PersonalRecord::getState),
    DATE_INCORPORATED("Fecha Incorporacion", PersonalRecord::getDateIncorporated);

    private final String label;
    private final Function<PersonalRecord, String> extractor;

    PersonalField(String label, Function<PersonalRecord, String> extractor) {
        this.label = label;
        this.extractor = extractor;
    }

    public String getLabel() {
        return label;
    }

    public String extract(PersonalRecord record) {
        return record == null ? null : extractor.apply(record);
    }

    public static PersonalField fromLabel(String label) {
        if (label == null) {
            return null;
        }
        for (PersonalField field : values()) {
            if (field.label.equalsIgnoreCase(label.trim())) {
                return field;
            }
        }
        return null;
    }

    public static List<String> labels() {
        return Arrays.stream(values())
                .map(PersonalField::getLabel)
                .collect(Collectors.toList());
    }
}
