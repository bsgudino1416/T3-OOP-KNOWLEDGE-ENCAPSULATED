package ec.edu.espe.petshopinventorycontrol.controller;

import ec.edu.espe.petshopinventorycontrol.model.ExpiredProductRecord;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public final class ExpiredProductsController {

    private static final int RANGE_DAYS = 30;
    private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    private final ExpiredProductsService service;
    private final ReportService reportService;

    public ExpiredProductsController(ExpiredProductsService service, ReportService reportService) {
        this.service = service;
        this.reportService = reportService;
    }

    public void onInit(ExpiredProductsView view) {
        try {
            List<ExpiredProductRecord> records = service.fetchAll();
            LocalDate today = LocalDate.now();
            LocalDate limit = today.plusDays(RANGE_DAYS);

            List<ExpiredProductRecord> filtered = records.stream()
                    .filter(r -> r.getExpiryDate() != null)
                    .filter(r -> !r.getExpiryDate().isAfter(limit))
                    .peek(r -> r.setDaysRemaining(ChronoUnit.DAYS.between(today, r.getExpiryDate())))
                    .sorted(Comparator.comparing(ExpiredProductRecord::getExpiryDate))
                    .collect(Collectors.toList());

            view.setTableModel(buildTableModel(filtered), filtered);
        } catch (Exception ex) {
            view.showMessage(
                    "Error al cargar datos: " + ex.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    public void onReport(JFrame owner) {
        reportService.exportExpiredProductsReport(owner);
    }

    public void onReturnLobby(JFrame owner) {
        new ec.edu.espe.petshopinventorycontrol.view.FrmLobby().setVisible(true);
        owner.dispose();
    }

    private DefaultTableModel buildTableModel(List<ExpiredProductRecord> records) {
        String[] columns = {
            "Id",
            "Nombre",
            "Categoria",
            "Marca",
            "Fecha Caducidad",
            "Fuente"
        };

        DefaultTableModel model = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        for (ExpiredProductRecord record : records) {
            String dateText = record.getExpiryDate() == null
                    ? ""
                    : DATE_FORMAT.format(record.getExpiryDate());
            model.addRow(new Object[]{
                record.getId(),
                record.getName(),
                record.getCategory(),
                record.getBrand(),
                dateText,
                record.getSource()
            });
        }

        return model;
    }
}
