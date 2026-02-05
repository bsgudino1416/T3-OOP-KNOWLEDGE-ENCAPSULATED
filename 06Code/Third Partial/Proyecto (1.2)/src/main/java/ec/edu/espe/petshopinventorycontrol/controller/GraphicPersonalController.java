package ec.edu.espe.petshopinventorycontrol.controller;

import ec.edu.espe.petshopinventorycontrol.model.PersonalRecord;
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

public final class GraphicPersonalController {

    private final PersonalGraphicService service;
    private List<PersonalRecord> cached = List.of();

    public GraphicPersonalController(PersonalGraphicService service) {
        this.service = service;
    }

    public void onInit(GraphicPersonalView view) {
        try {
            cached = service.fetchAll();
            view.setFilterFieldOptions(PersonalField.labels());
            view.setChartFieldOptions(PersonalField.labels());
            view.setChartTypeOptions(List.of("Pastel", "Barras"));
            view.setFilterValueOptions1(buildValueOptions(cached, PersonalField.CI));
            view.setFilterValueOptions2(buildValueOptions(cached, PersonalField.SCHEDULE));
            applyFilters(view);
        } catch (Exception ex) {
            view.showMessage(
                    "Error al cargar datos: " + ex.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    public void onFilterFieldChanged(GraphicPersonalView view, int filterIndex) {
        PersonalField field = filterIndex == 1
                ? PersonalField.fromLabel(view.getFilterField1())
                : PersonalField.fromLabel(view.getFilterField2());
        List<String> values = buildValueOptions(cached, field);
        if (filterIndex == 1) {
            view.setFilterValueOptions1(values);
        } else {
            view.setFilterValueOptions2(values);
        }
    }

    public void onApplyFilters(GraphicPersonalView view) {
        applyFilters(view);
    }

    private void applyFilters(GraphicPersonalView view) {
        PersonalField field1 = PersonalField.fromLabel(view.getFilterField1());
        PersonalField field2 = PersonalField.fromLabel(view.getFilterField2());
        String value1 = view.getFilterValue1();
        String value2 = view.getFilterValue2();

        List<PersonalRecord> filtered = new ArrayList<>();
        for (PersonalRecord record : cached) {
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

    private void updateChart(GraphicPersonalView view, List<PersonalRecord> data) {
        PersonalField field = PersonalField.fromLabel(view.getChartField());
        if (field == null) {
            view.setChartMessage("Seleccione un campo para la grafica.");
            return;
        }

        Map<String, Integer> counts = new LinkedHashMap<>();
        for (PersonalRecord record : data) {
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

    private boolean matchesFilter(PersonalField field, String value, PersonalRecord record) {
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

    private List<String> buildValueOptions(List<PersonalRecord> records, PersonalField field) {
        Set<String> values = new LinkedHashSet<>();
        values.add("Todos");
        if (field != null) {
            for (PersonalRecord record : records) {
                String value = field.extract(record);
                if (value != null && !value.trim().isEmpty()) {
                    values.add(value.trim());
                }
            }
        }
        return new ArrayList<>(values);
    }

    private DefaultTableModel buildTableModel(List<PersonalRecord> records) {
        String[] columns = {
            "Id",
            "Ci",
            "Nombre",
            "Cargo",
            "Turno",
            "Dia",
            "Direccion",
            "Estado",
            "Fecha Incorporacion"
        };

        DefaultTableModel model = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        for (PersonalRecord record : records) {
            model.addRow(new Object[]{
                record.getId(),
                record.getCi(),
                record.getName(),
                record.getPost(),
                record.getSchedule(),
                record.getDay(),
                record.getAddress(),
                record.getState(),
                record.getDateIncorporated()
            });
        }

        return model;
    }
}
