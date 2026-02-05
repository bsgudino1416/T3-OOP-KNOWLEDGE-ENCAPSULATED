package ec.edu.espe.petshopinventorycontrol.controller;

import ec.edu.espe.petshopinventorycontrol.model.RegisterRecord;
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

public final class GraphicRegisterController {

    private final RegisterGraphicService service;
    private List<RegisterRecord> cached = List.of();

    public GraphicRegisterController(RegisterGraphicService service) {
        this.service = service;
    }

    public void onInit(GraphicRegisterView view) {
        try {
            cached = service.fetchAll();
            view.setFilterFieldOptions(RegisterField.labels());
            view.setChartFieldOptions(RegisterField.labels());
            view.setChartTypeOptions(List.of("Pastel", "Barras"));
            view.setFilterValueOptions1(buildValueOptions(cached, RegisterField.ID));
            view.setFilterValueOptions2(buildValueOptions(cached, RegisterField.NAME));
            applyFilters(view);
        } catch (Exception ex) {
            view.showMessage(
                    "Error al cargar datos: " + ex.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    public void onFilterFieldChanged(GraphicRegisterView view, int filterIndex) {
        RegisterField field = filterIndex == 1
                ? RegisterField.fromLabel(view.getFilterField1())
                : RegisterField.fromLabel(view.getFilterField2());
        List<String> values = buildValueOptions(cached, field);
        if (filterIndex == 1) {
            view.setFilterValueOptions1(values);
        } else {
            view.setFilterValueOptions2(values);
        }
    }

    public void onApplyFilters(GraphicRegisterView view) {
        applyFilters(view);
    }

    private void applyFilters(GraphicRegisterView view) {
        RegisterField field1 = RegisterField.fromLabel(view.getFilterField1());
        RegisterField field2 = RegisterField.fromLabel(view.getFilterField2());
        String value1 = view.getFilterValue1();
        String value2 = view.getFilterValue2();

        List<RegisterRecord> filtered = new ArrayList<>();
        for (RegisterRecord record : cached) {
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

    private void updateChart(GraphicRegisterView view, List<RegisterRecord> data) {
        RegisterField field = RegisterField.fromLabel(view.getChartField());
        if (field == null) {
            view.setChartMessage("Seleccione un campo para la grafica.");
            return;
        }

        Map<String, Integer> counts = new LinkedHashMap<>();
        for (RegisterRecord record : data) {
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

    private boolean matchesFilter(RegisterField field, String value, RegisterRecord record) {
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

    private List<String> buildValueOptions(List<RegisterRecord> records, RegisterField field) {
        Set<String> values = new LinkedHashSet<>();
        values.add("Todos");
        if (field != null) {
            for (RegisterRecord record : records) {
                String value = field.extract(record);
                if (value != null && !value.trim().isEmpty()) {
                    values.add(value.trim());
                }
            }
        }
        return new ArrayList<>(values);
    }

    private DefaultTableModel buildTableModel(List<RegisterRecord> records) {
        String[] columns = {
            "Id",
            "Nombre",
            "Apellido",
            "Direccion",
            "Email",
            "Genero",
            "Usuario"
        };

        DefaultTableModel model = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        for (RegisterRecord record : records) {
            model.addRow(new Object[]{
                record.getId(),
                record.getFirstName(),
                record.getLastName(),
                record.getAddress(),
                record.getEmail(),
                record.getGender(),
                record.getUsername()
            });
        }

        return model;
    }
}
