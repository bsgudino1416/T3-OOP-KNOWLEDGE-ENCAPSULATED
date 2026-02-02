package ec.edu.espe.petshopinventorycontrol.controller;

import ec.edu.espe.petshopinventorycontrol.model.StockRecord;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public enum StockField {
    ID("Id", StockRecord::getId),
    CATEGORY("Categoria", StockRecord::getCategory),
    NAME("Nombre", StockRecord::getName),
    BRAND("Marca", StockRecord::getBrand),
    COST("Costo", StockRecord::getCost),
    UNIT_ENTRY("Unidades", StockRecord::getUnitEntry),
    GAIN_PERCENT("Ganancia %", StockRecord::getGainPercent),
    FINAL_PRICE("Precio Final", StockRecord::getFinalPrice),
    GAIN_VALUE("Ganancia Valor", StockRecord::getGainValue),
    CREATED_AT("Fecha", StockRecord::getCreatedAt);

    private final String label;
    private final Function<StockRecord, String> extractor;

    StockField(String label, Function<StockRecord, String> extractor) {
        this.label = label;
        this.extractor = extractor;
    }

    public String getLabel() {
        return label;
    }

    public String extract(StockRecord record) {
        return record == null ? null : extractor.apply(record);
    }

    public static StockField fromLabel(String label) {
        if (label == null) {
            return null;
        }
        for (StockField field : values()) {
            if (field.label.equalsIgnoreCase(label.trim())) {
                return field;
            }
        }
        return null;
    }

    public static List<String> labels() {
        return Arrays.stream(values())
                .map(StockField::getLabel)
                .collect(Collectors.toList());
    }
}
