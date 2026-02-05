package ec.edu.espe.petshopinventorycontrol.controller;

import ec.edu.espe.petshopinventorycontrol.model.ProductRecord;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public enum ProductField {
    ID("Id", ProductRecord::getId),
    SUPPLIER("Proveedor", ProductRecord::getSupplier),
    NAME("Nombre", ProductRecord::getName),
    TYPE_PRODUCT("Categoria", ProductRecord::getTypeProduct),
    ANIMAL_TYPE("Animal", ProductRecord::getAnimalType),
    BRAND("Marca", ProductRecord::getBrand),
    COST("Costo", ProductRecord::getCost),
    UNIT("Unidad", ProductRecord::getUnit),
    QUANTITY("Cantidad", ProductRecord::getQuantity),
    INVESTMENT("Inversion", ProductRecord::getInvestment),
    POUNDS("Libras", ProductRecord::getPounds),
    TOTAL_POUNDS("Total Libras", ProductRecord::getTotalPounds),
    DATE_ENTRY("Fecha Ingreso", ProductRecord::getDateEntry),
    DATE_EXIT("Fecha Caducidad", ProductRecord::getDateExit);

    private final String label;
    private final Function<ProductRecord, String> extractor;

    ProductField(String label, Function<ProductRecord, String> extractor) {
        this.label = label;
        this.extractor = extractor;
    }

    public String getLabel() {
        return label;
    }

    public String extract(ProductRecord record) {
        return record == null ? null : extractor.apply(record);
    }

    public static ProductField fromLabel(String label) {
        if (label == null) {
            return null;
        }
        for (ProductField field : values()) {
            if (field.label.equalsIgnoreCase(label.trim())) {
                return field;
            }
        }
        return null;
    }

    public static List<String> labels() {
        return Arrays.stream(values())
                .map(ProductField::getLabel)
                .collect(Collectors.toList());
    }
}
