package ec.edu.espe.petshopinventorycontrol.controller;

import ec.edu.espe.petshopinventorycontrol.model.SupplierRecord;
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

public final class GraphicSupplierController {

    private final SupplierGraphicService service;
    private List<SupplierRecord> cached = List.of();

    public GraphicSupplierController(SupplierGraphicService service) {
        this.service = service;
    }

    public void onInit(GraphicSupplierView view) {
        try {
            cached = service.fetchAll();
            view.setFilterFieldOptions(SupplierField.labels());
            view.setChartFieldOptions(SupplierField.labels());
            view.setChartTypeOptions(List.of("Pastel", "Barras"));
            view.setFilterValueOptions1(buildValueOptions(cached, SupplierField.ID));
            view.setFilterValueOptions2(buildValueOptions(cached, SupplierField.NAME));
            applyFilters(view);
        } catch (Exception ex) {
            view.showMessage(
                    "Error al cargar datos: " + ex.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    public void onFilterFieldChanged(GraphicSupplierView view, int filterIndex) {
        SupplierField field = filterIndex == 1
                ? SupplierField.fromLabel(view.getFilterField1())
                : SupplierField.fromLabel(view.getFilterField2());
        List<String> values = buildValueOptions(cached, field);
        if (filterIndex == 1) {
            view.setFilterValueOptions1(values);
        } else {
            view.setFilterValueOptions2(values);
        }
    }

    public void onApplyFilters(GraphicSupplierView view) {
        applyFilters(view);
    }

    private void applyFilters(GraphicSupplierView view) {
        SupplierField field1 = SupplierField.fromLabel(view.getFilterField1());
        SupplierField field2 = SupplierField.fromLabel(view.getFilterField2());
        String value1 = view.getFilterValue1();
        String value2 = view.getFilterValue2();

        List<SupplierRecord> filtered = new ArrayList<>();
        for (SupplierRecord record : cached) {
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

    private void updateChart(GraphicSupplierView view, List<SupplierRecord> data) {
        SupplierField field = SupplierField.fromLabel(view.getChartField());
        if (field == null) {
            view.setChartMessage("Seleccione un campo para la grafica.");
            return;
        }

        Map<String, Integer> counts = new LinkedHashMap<>();
        for (SupplierRecord record : data) {
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

    private boolean matchesFilter(SupplierField field, String value, SupplierRecord record) {
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

    private List<String> buildValueOptions(List<SupplierRecord> records, SupplierField field) {
        Set<String> values = new LinkedHashSet<>();
        values.add("Todos");
        if (field != null) {
            for (SupplierRecord record : records) {
                String value = field.extract(record);
                if (value != null && !value.trim().isEmpty()) {
                    values.add(value.trim());
                }
            }
        }
        return new ArrayList<>(values);
    }

    private DefaultTableModel buildTableModel(List<SupplierRecord> records) {
        String[] columns = {
            "Id",
            "Tipo",
            "Telefono",
            "Telefono 2",
            "Nombre",
            "Ciudad",
            "Estado",
            "Empresa",
            "Email",
            "Fecha Ingreso"
        };

        DefaultTableModel model = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        for (SupplierRecord record : records) {
            model.addRow(new Object[]{
                record.getId(),
                record.getType(),
                record.getPhone(),
                record.getPhone2(),
                record.getName(),
                record.getCity(),
                record.getState(),
                record.getEnterprise(),
                record.getEmail(),
                record.getDateEntry()
            });
        }

        return model;
    }
}
