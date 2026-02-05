package ec.edu.espe.petshopinventorycontrol.controller;

import ec.edu.espe.petshopinventorycontrol.model.StockRecord;
import ec.edu.espe.petshopinventorycontrol.utils.ChartUtils;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public final class GraphicStockController {

    private final StockGraphicService service;
    private List<StockRecord> cached = List.of();

    public GraphicStockController(StockGraphicService service) {
        this.service = service;
    }

    public void onInit(GraphicStockView view) {
        try {
            cached = service.fetchAll();
            view.setFilterFieldOptions(StockField.labels());
            view.setChartFieldOptions(StockField.labels());
            view.setChartTypeOptions(List.of("Pastel", "Barras"));
            view.setFilterValueOptions1(buildValueOptions(cached, StockField.ID));
            view.setFilterValueOptions2(buildValueOptions(cached, StockField.NAME));
            applyFilters(view);
        } catch (Exception ex) {
            view.showMessage(
                    "Error al cargar datos: " + ex.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    public void onFilterFieldChanged(GraphicStockView view, int filterIndex) {
        StockField field = filterIndex == 1
                ? StockField.fromLabel(view.getFilterField1())
                : StockField.fromLabel(view.getFilterField2());
        List<String> values = buildValueOptions(cached, field);
        if (filterIndex == 1) {
            view.setFilterValueOptions1(values);
        } else {
            view.setFilterValueOptions2(values);
        }
    }

    public void onApplyFilters(GraphicStockView view) {
        applyFilters(view);
    }

    private void applyFilters(GraphicStockView view) {
        StockField field1 = StockField.fromLabel(view.getFilterField1());
        StockField field2 = StockField.fromLabel(view.getFilterField2());
        String value1 = view.getFilterValue1();
        String value2 = view.getFilterValue2();

        List<StockRecord> filtered = new ArrayList<>();
        for (StockRecord record : cached) {
            if (!matchesFilter(field1, value1, record)) {
                continue;
            }
            if (!matchesFilter(field2, value2, record)) {
                continue;
            }
            filtered.add(record);
        }

        view.setTableModel(buildTableModel(filtered));
        updateChart(view, filtered);
    }

    private void updateChart(GraphicStockView view, List<StockRecord> data) {
        StockField field = StockField.fromLabel(view.getChartField());
        if (field == null) {
            view.setChartMessage("Seleccione un campo para la grafica.");
            return;
        }

        Map<String, Integer> counts = new LinkedHashMap<>();
        for (StockRecord record : data) {
            String key = field.extract(record);
            if (key == null || key.trim().isEmpty()) {
                key = "Sin dato";
            }
            counts.put(key, counts.getOrDefault(key, 0) + 1);
        }

        int width = view.getChartWidth();
        int height = view.getChartHeight();

        BufferedImage chart = "Barras".equalsIgnoreCase(view.getChartType())
                ? ChartUtils.createBarChart(counts, width, height)
                : ChartUtils.createPieChart(counts, width, height);

        view.setChartIcon(new ImageIcon(chart));
    }

    private boolean matchesFilter(StockField field, String value, StockRecord record) {
        if (field == null) {
            return true;
        }
        if (value == null) {
            return true;
        }
        String trimmed = value.trim();
        if (trimmed.isEmpty() || trimmed.equalsIgnoreCase("Todos")) {
            return true;
        }
        String fieldValue = field.extract(record);
        if (fieldValue == null) {
            return false;
        }
        return fieldValue.toLowerCase().contains(trimmed.toLowerCase());
    }

    private List<String> buildValueOptions(List<StockRecord> records, StockField field) {
        Set<String> values = new LinkedHashSet<>();
        values.add("Todos");
        if (field != null) {
            for (StockRecord record : records) {
                String value = field.extract(record);
                if (value != null && !value.trim().isEmpty()) {
                    values.add(value.trim());
                }
            }
        }
        return new ArrayList<>(values);
    }

    private DefaultTableModel buildTableModel(List<StockRecord> records) {
        String[] columns = {
            "Id",
            "Categoria",
            "Nombre",
            "Marca",
            "Costo",
            "Unidades",
            "Ganancia %",
            "Precio Final",
            "Ganancia Valor",
            "Fecha"
        };

        DefaultTableModel model = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        for (StockRecord record : records) {
            model.addRow(new Object[]{
                record.getId(),
                record.getCategory(),
                record.getName(),
                record.getBrand(),
                record.getCost(),
                record.getUnitEntry(),
                record.getGainPercent(),
                record.getFinalPrice(),
                record.getGainValue(),
                record.getCreatedAt()
            });
        }

        return model;
    }
}
