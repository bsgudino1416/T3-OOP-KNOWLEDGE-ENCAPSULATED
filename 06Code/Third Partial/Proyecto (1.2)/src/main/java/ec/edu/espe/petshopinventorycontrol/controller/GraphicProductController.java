package ec.edu.espe.petshopinventorycontrol.controller;

import ec.edu.espe.petshopinventorycontrol.model.ProductRecord;
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

public final class GraphicProductController {

    private final ProductGraphicService service;
    private List<ProductRecord> cached = List.of();
    private static final ProductField DEFAULT_CHART_FIELD = ProductField.TYPE_PRODUCT;

    public GraphicProductController(ProductGraphicService service) {
        this.service = service;
    }

    public void onInit(GraphicProductView view) {
        try {
            cached = service.fetchAll();
            view.setFilterFieldOptions(ProductField.labels());
            view.setChartFieldOptions(ProductField.labels());
            view.setChartTypeOptions(List.of("Pastel", "Barras"));
            view.setFilterValueOptions1(buildValueOptions(cached, ProductField.ID));
            view.setFilterValueOptions2(buildValueOptions(cached, ProductField.NAME));
            applyFilters(view);
        } catch (Exception ex) {
            view.showMessage(
                    "Error al cargar datos: " + ex.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    public void onFilterFieldChanged(GraphicProductView view, int filterIndex) {
        ProductField field = filterIndex == 1
                ? ProductField.fromLabel(view.getFilterField1())
                : ProductField.fromLabel(view.getFilterField2());
        List<String> values = buildValueOptions(cached, field);
        if (filterIndex == 1) {
            view.setFilterValueOptions1(values);
        } else {
            view.setFilterValueOptions2(values);
        }
    }

    public void onApplyFilters(GraphicProductView view) {
        applyFilters(view);
    }

    private void applyFilters(GraphicProductView view) {
        ProductField field1 = ProductField.fromLabel(view.getFilterField1());
        ProductField field2 = ProductField.fromLabel(view.getFilterField2());
        String value1 = view.getFilterValue1();
        String value2 = view.getFilterValue2();

        List<ProductRecord> filtered = new ArrayList<>();
        for (ProductRecord record : cached) {
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

    public void onShowAll(GraphicProductView view) {
        view.setTableModel(buildTableModel(cached));
        updateChart(view, cached);
    }

    public void onShowGraphic(GraphicProductView view) {
        updateChart(view, cached);
    }

    private void updateChart(GraphicProductView view, List<ProductRecord> data) {
        ProductField field = ProductField.fromLabel(view.getChartField());
        if (field == null) {
            field = DEFAULT_CHART_FIELD;
        }
        if (field == null) {
            view.setChartMessage("Seleccione un campo para la grafica.");
            return;
        }

        Map<String, Integer> counts = new LinkedHashMap<>();
        for (ProductRecord record : data) {
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

    private boolean matchesFilter(ProductField field, String value, ProductRecord record) {
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

    private List<String> buildValueOptions(List<ProductRecord> records, ProductField field) {
        Set<String> values = new LinkedHashSet<>();
        values.add("Todos");
        if (field != null) {
            for (ProductRecord record : records) {
                String value = field.extract(record);
                if (value != null && !value.trim().isEmpty()) {
                    values.add(value.trim());
                }
            }
        }
        return new ArrayList<>(values);
    }

    private DefaultTableModel buildTableModel(List<ProductRecord> records) {
        String[] columns = {
            "Id",
            "Proveedor",
            "Nombre",
            "Categoria",
            "Animal",
            "Marca",
            "Costo",
            "Unidad",
            "Cantidad",
            "Inversion",
            "Libras",
            "Total Libras",
            "Fecha Ingreso",
            "Fecha Caducidad"
        };

        DefaultTableModel model = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        for (ProductRecord record : records) {
            model.addRow(new Object[]{
                record.getId(),
                record.getSupplier(),
                record.getName(),
                record.getTypeProduct(),
                record.getAnimalType(),
                record.getBrand(),
                record.getCost(),
                record.getUnit(),
                record.getQuantity(),
                record.getInvestment(),
                record.getPounds(),
                record.getTotalPounds(),
                record.getDateEntry(),
                record.getDateExit()
            });
        }

        return model;
    }
}
