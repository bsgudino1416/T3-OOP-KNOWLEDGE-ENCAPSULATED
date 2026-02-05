package ec.edu.espe.petshopinventorycontrol.controller;

import ec.edu.espe.petshopinventorycontrol.model.RegisterRecord;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public enum RegisterField {
    ID("Id", RegisterRecord::getId),
    NAME("Nombre", RegisterRecord::getFirstName),
    LAST_NAME("Apellido", RegisterRecord::getLastName),
    ADDRESS("Direccion", RegisterRecord::getAddress),
    EMAIL("Email", RegisterRecord::getEmail),
    GENDER("Genero", RegisterRecord::getGender),
    USERNAME("Usuario", RegisterRecord::getUsername);

    private final String label;
    private final Function<RegisterRecord, String> extractor;

    RegisterField(String label, Function<RegisterRecord, String> extractor) {
        this.label = label;
        this.extractor = extractor;
    }

    public String getLabel() {
        return label;
    }

    public String extract(RegisterRecord record) {
        return record == null ? null : extractor.apply(record);
    }

    public static RegisterField fromLabel(String label) {
        if (label == null) {
            return null;
        }
        for (RegisterField field : values()) {
            if (field.label.equalsIgnoreCase(label.trim())) {
                return field;
            }
        }
        return null;
    }

    public static List<String> labels() {
        return Arrays.stream(values())
                .map(RegisterField::getLabel)
                .collect(Collectors.toList());
    }
}
