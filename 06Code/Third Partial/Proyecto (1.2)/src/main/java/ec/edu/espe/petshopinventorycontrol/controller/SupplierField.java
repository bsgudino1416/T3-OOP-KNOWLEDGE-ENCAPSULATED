package ec.edu.espe.petshopinventorycontrol.controller;

import ec.edu.espe.petshopinventorycontrol.model.SupplierRecord;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public enum SupplierField {
    ID("Id", SupplierRecord::getId),
    TYPE("Tipo", SupplierRecord::getType),
    PHONE("Telefono", SupplierRecord::getPhone),
    PHONE2("Telefono 2", SupplierRecord::getPhone2),
    NAME("Nombre", SupplierRecord::getName),
    CITY("Ciudad", SupplierRecord::getCity),
    STATE("Estado", SupplierRecord::getState),
    ENTERPRISE("Empresa", SupplierRecord::getEnterprise),
    EMAIL("Email", SupplierRecord::getEmail),
    DATE_ENTRY("Fecha Ingreso", SupplierRecord::getDateEntry);

    private final String label;
    private final Function<SupplierRecord, String> extractor;

    SupplierField(String label, Function<SupplierRecord, String> extractor) {
        this.label = label;
        this.extractor = extractor;
    }

    public String getLabel() {
        return label;
    }

    public String extract(SupplierRecord record) {
        return record == null ? null : extractor.apply(record);
    }

    public static SupplierField fromLabel(String label) {
        if (label == null) {
            return null;
        }
        for (SupplierField field : values()) {
            if (field.label.equalsIgnoreCase(label.trim())) {
                return field;
            }
        }
        return null;
    }

    public static List<String> labels() {
        return Arrays.stream(values())
                .map(SupplierField::getLabel)
                .collect(Collectors.toList());
    }
}
