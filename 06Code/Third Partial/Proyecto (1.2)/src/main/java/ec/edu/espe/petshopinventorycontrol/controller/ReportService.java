package ec.edu.espe.petshopinventorycontrol.controller;

import ec.edu.espe.petshopinventorycontrol.model.ExpiredProductRecord;
import ec.edu.espe.petshopinventorycontrol.model.PersonalRecord;
import ec.edu.espe.petshopinventorycontrol.model.ProductRecord;
import ec.edu.espe.petshopinventorycontrol.model.RegisterRecord;
import ec.edu.espe.petshopinventorycontrol.model.StockRecord;
import ec.edu.espe.petshopinventorycontrol.model.SupplierRecord;
import ec.edu.espe.petshopinventorycontrol.model.mongo.MongoBillGateway;
import ec.edu.espe.petshopinventorycontrol.model.mongo.MongoPersonalGateway;
import ec.edu.espe.petshopinventorycontrol.model.mongo.MongoProductGateway;
import ec.edu.espe.petshopinventorycontrol.model.mongo.MongoStockGateway;
import ec.edu.espe.petshopinventorycontrol.model.mongo.MongoSupplierGateway;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import org.bson.Document;

public final class ReportService {

    private static final int EXPIRED_RANGE_DAYS = 30;
    private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    private final PdfReportService pdfService;

    public ReportService(PdfReportService pdfService) {
        this.pdfService = pdfService;
    }

    public static ReportService defaultService() {
        return new ReportService(new PdfReportService());
    }

    public void exportPersonalReport(javax.swing.JFrame owner) {
        PersonalGraphicService service = new PersonalGraphicService(new MongoPersonalGateway());
        List<PersonalRecord> records = service.fetchAll();
        PdfReportService.ReportTable table = PdfReportService.ReportTable.of(
                new String[]{
                    "Id", "Ci", "Nombre", "Cargo", "Turno", "Dia", "Direccion", "Estado", "Fecha Incorporacion"
                },
                mapPersonal(records)
        );
        pdfService.exportReport(owner, "Reporte de Personal", table);
    }

    public void exportProductReport(javax.swing.JFrame owner) {
        ProductGraphicService service = new ProductGraphicService(new MongoProductGateway());
        List<ProductRecord> records = service.fetchAll();
        PdfReportService.ReportTable table = PdfReportService.ReportTable.of(
                new String[]{
                    "Id", "Proveedor", "Nombre", "Categoria", "Animal", "Marca",
                    "Costo", "Unidad", "Cantidad", "Inversion", "Libras", "Total Libras",
                    "Fecha Ingreso", "Fecha Caducidad"
                },
                mapProduct(records)
        );
        pdfService.exportReport(owner, "Reporte de Productos", table);
    }

    public void exportRegisterReport(javax.swing.JFrame owner) {
        RegisterGraphicService service = RegisterGraphicService.defaultService();
        List<RegisterRecord> records = service.fetchAll();
        PdfReportService.ReportTable table = PdfReportService.ReportTable.of(
                new String[]{
                    "Id", "Nombre", "Apellido", "Direccion", "Email", "Genero", "Usuario"
                },
                mapRegister(records)
        );
        pdfService.exportReport(owner, "Reporte de Registros", table);
    }

    public void exportStockReport(javax.swing.JFrame owner) {
        StockGraphicService service = new StockGraphicService(new MongoStockGateway());
        List<StockRecord> records = service.fetchAll();
        PdfReportService.ReportTable table = PdfReportService.ReportTable.of(
                new String[]{
                    "Id", "Categoria", "Nombre", "Marca", "Costo", "Unidades",
                    "Ganancia %", "Precio Final", "Ganancia Valor", "Fecha"
                },
                mapStock(records)
        );
        pdfService.exportReport(owner, "Reporte de Stock", table);
    }

    public void exportSupplierReport(javax.swing.JFrame owner) {
        SupplierGraphicService service = new SupplierGraphicService(new MongoSupplierGateway());
        List<SupplierRecord> records = service.fetchAll();
        PdfReportService.ReportTable table = PdfReportService.ReportTable.of(
                new String[]{
                    "Id", "Tipo", "Telefono", "Telefono 2", "Nombre",
                    "Ciudad", "Estado", "Empresa", "Email", "Fecha Ingreso"
                },
                mapSupplier(records)
        );
        pdfService.exportReport(owner, "Reporte de Proveedores", table);
    }

    public void exportExpiredProductsReport(javax.swing.JFrame owner) {
        ExpiredProductsService service = new ExpiredProductsService(
                new MongoProductGateway(),
                new MongoStockGateway()
        );
        List<ExpiredProductRecord> all = service.fetchAll();
        LocalDate today = LocalDate.now();
        LocalDate limit = today.plusDays(EXPIRED_RANGE_DAYS);

        List<ExpiredProductRecord> filtered = all.stream()
                .filter(r -> r.getExpiryDate() != null)
                .filter(r -> !r.getExpiryDate().isAfter(limit))
                .sorted(Comparator.comparing(ExpiredProductRecord::getExpiryDate))
                .toList();

        PdfReportService.ReportTable table = PdfReportService.ReportTable.of(
                new String[]{
                    "Id", "Nombre", "Categoria", "Marca", "Fecha Caducidad", "Fuente"
                },
                mapExpired(filtered, today)
        );
        pdfService.exportReport(owner, "Reporte de Productos Expirados", table);
    }

    public void exportBillReport(javax.swing.JFrame owner) {
        MongoBillGateway gateway = new MongoBillGateway();
        List<Document> docs = gateway.findAllOrdered();
        PdfReportService.ReportTable table = PdfReportService.ReportTable.of(
                new String[]{
                    "Id", "Cliente", "CI", "Telefono", "Email",
                    "Direccion", "Fecha", "Encargado", "Total"
                },
                mapBill(docs)
        );
        pdfService.exportReport(owner, "Reporte de Facturas", table);
    }

    private List<String[]> mapPersonal(List<PersonalRecord> records) {
        List<String[]> rows = new ArrayList<>();
        for (PersonalRecord record : records) {
            rows.add(new String[]{
                safe(record.getId()),
                safe(record.getCi()),
                safe(record.getName()),
                safe(record.getPost()),
                safe(record.getSchedule()),
                safe(record.getDay()),
                safe(record.getAddress()),
                safe(record.getState()),
                safe(record.getDateIncorporated())
            });
        }
        return rows;
    }

    private List<String[]> mapProduct(List<ProductRecord> records) {
        List<String[]> rows = new ArrayList<>();
        for (ProductRecord record : records) {
            rows.add(new String[]{
                safe(record.getId()),
                safe(record.getSupplier()),
                safe(record.getName()),
                safe(record.getTypeProduct()),
                safe(record.getAnimalType()),
                safe(record.getBrand()),
                safe(record.getCost()),
                safe(record.getUnit()),
                safe(record.getQuantity()),
                safe(record.getInvestment()),
                safe(record.getPounds()),
                safe(record.getTotalPounds()),
                safe(record.getDateEntry()),
                safe(record.getDateExit())
            });
        }
        return rows;
    }

    private List<String[]> mapRegister(List<RegisterRecord> records) {
        List<String[]> rows = new ArrayList<>();
        for (RegisterRecord record : records) {
            rows.add(new String[]{
                safe(record.getId()),
                safe(record.getFirstName()),
                safe(record.getLastName()),
                safe(record.getAddress()),
                safe(record.getEmail()),
                safe(record.getGender()),
                safe(record.getUsername())
            });
        }
        return rows;
    }

    private List<String[]> mapStock(List<StockRecord> records) {
        List<String[]> rows = new ArrayList<>();
        for (StockRecord record : records) {
            rows.add(new String[]{
                safe(record.getId()),
                safe(record.getCategory()),
                safe(record.getName()),
                safe(record.getBrand()),
                safe(record.getCost()),
                safe(record.getUnitEntry()),
                safe(record.getGainPercent()),
                safe(record.getFinalPrice()),
                safe(record.getGainValue()),
                safe(record.getCreatedAt())
            });
        }
        return rows;
    }

    private List<String[]> mapSupplier(List<SupplierRecord> records) {
        List<String[]> rows = new ArrayList<>();
        for (SupplierRecord record : records) {
            rows.add(new String[]{
                safe(record.getId()),
                safe(record.getType()),
                safe(record.getPhone()),
                safe(record.getPhone2()),
                safe(record.getName()),
                safe(record.getCity()),
                safe(record.getState()),
                safe(record.getEnterprise()),
                safe(record.getEmail()),
                safe(record.getDateEntry())
            });
        }
        return rows;
    }

    private List<String[]> mapExpired(List<ExpiredProductRecord> records, LocalDate today) {
        List<String[]> rows = new ArrayList<>();
        for (ExpiredProductRecord record : records) {
            long days = ChronoUnit.DAYS.between(today, record.getExpiryDate());
            record.setDaysRemaining(days);
            rows.add(new String[]{
                safe(record.getId()),
                safe(record.getName()),
                safe(record.getCategory()),
                safe(record.getBrand()),
                record.getExpiryDate() == null ? "" : DATE_FORMAT.format(record.getExpiryDate()),
                safe(record.getSource())
            });
        }
        return rows;
    }

    private List<String[]> mapBill(List<Document> docs) {
        List<String[]> rows = new ArrayList<>();
        for (Document doc : docs) {
            String id = readString(doc, "txtidBill", "IdBill", "idBill", "id");
            String name = readString(doc, "txtName", "Name", "customerName", "Nombre");
            String ci = readString(doc, "txtCI", "CI", "ci");
            String phone = readString(doc, "txtPhone", "Phone", "phone");
            String email = readString(doc, "txtEmail", "Email", "email");
            String address = readString(doc, "txtAdress", "Address", "address");
            String personal = readString(doc, "txtPersonal", "Personal", "seller");
            String total = readString(doc, "txttotalBill", "totalBill", "TotalBill", "total");
            String date = formatDate(doc.get("DateBill"), doc.get("dateBill"), doc.get("Date"), doc.get("date"));
            rows.add(new String[]{
                safe(id),
                safe(name),
                safe(ci),
                safe(phone),
                safe(email),
                safe(address),
                safe(date),
                safe(personal),
                safe(total)
            });
        }
        return rows;
    }

    private String formatDate(Object... values) {
        for (Object value : values) {
            if (value == null) {
                continue;
            }
            if (value instanceof java.util.Date date) {
                return DATE_FORMAT.format(date.toInstant()
                        .atZone(java.time.ZoneId.systemDefault())
                        .toLocalDate());
            }
            String text = String.valueOf(value).trim();
            if (!text.isEmpty()) {
                return text;
            }
        }
        return "";
    }

    private String readString(Document doc, String... keys) {
        if (doc == null || keys == null) {
            return "";
        }
        for (String key : keys) {
            Object value = doc.get(key);
            if (value == null) {
                continue;
            }
            String text = String.valueOf(value).trim();
            if (!text.isEmpty()) {
                return text;
            }
        }
        return "";
    }

    private String safe(String value) {
        return value == null ? "" : value;
    }
}
