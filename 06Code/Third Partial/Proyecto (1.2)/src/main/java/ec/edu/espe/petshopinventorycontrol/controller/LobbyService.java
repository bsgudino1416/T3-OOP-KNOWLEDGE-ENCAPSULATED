package ec.edu.espe.petshopinventorycontrol.controller;

import ec.edu.espe.petshopinventorycontrol.model.mongo.MongoBillGateway;
import ec.edu.espe.petshopinventorycontrol.model.mongo.MongoProductGateway;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Locale;
import java.util.List;
import org.bson.Document;

public final class LobbyService {

    private final MongoProductGateway productGateway;
    private final MongoBillGateway billGateway;

    private final DateTimeFormatter[] formatters = new DateTimeFormatter[]{
        DateTimeFormatter.ofPattern("yyyy-MM-dd"),
        DateTimeFormatter.ofPattern("dd/MM/yyyy"),
        DateTimeFormatter.ofPattern("yyyy/MM/dd"),
        DateTimeFormatter.ofPattern("dd-MM-yyyy")
    };

    public LobbyService(MongoProductGateway productGateway, MongoBillGateway billGateway) {
        this.productGateway = productGateway;
        this.billGateway = billGateway;
    }

    public LobbyTotals totalsForYear(int year) {
        double inventoryTotal = sumInventoryForYear(year);
        double billTotal = sumBillsForYear(year);
        return new LobbyTotals(inventoryTotal, billTotal);
    }

    private double sumInventoryForYear(int year) {
        double total = 0;
        List<Document> docs = productGateway.findAllOrdered();
        for (Document doc : docs) {
            LocalDate entryDate = parseDate(doc.get("DateEntry"));
            if (entryDate == null || entryDate.getYear() != year) {
                continue;
            }
            Double investment = productGateway.readInvestmentCost(doc);
            if (investment != null) {
                total += investment;
            }
        }
        return total;
    }

    private double sumBillsForYear(int year) {
        double total = 0;
        List<Document> docs = billGateway.findAllOrdered();
        for (Document doc : docs) {
            LocalDate billDate = parseDate(doc.get("DateBill"), doc.get("dateBill"), doc.get("Date"), doc.get("date"));
            if (billDate == null || billDate.getYear() != year) {
                continue;
            }
            Double value = readNumber(doc, "txttotalBill", "totalBill", "TotalBill", "total");
            if (value != null) {
                total += value;
            }
        }
        return total;
    }

    private LocalDate parseDate(Object... values) {
        if (values == null) {
            return null;
        }
        for (Object value : values) {
            LocalDate parsed = parseDateValue(value);
            if (parsed != null) {
                return parsed;
            }
        }
        return null;
    }

    private LocalDate parseDateValue(Object value) {
        if (value == null) {
            return null;
        }
        if (value instanceof Date date) {
            return Instant.ofEpochMilli(date.getTime())
                    .atZone(ZoneId.systemDefault())
                    .toLocalDate();
        }
        String text = String.valueOf(value).trim();
        if (text.isEmpty() || text.equalsIgnoreCase("no aplica")) {
            return null;
        }
        String cleaned = cleanupDateText(text);
        for (DateTimeFormatter formatter : formatters) {
            try {
                return LocalDate.parse(cleaned, formatter);
            } catch (Exception ignored) {
            }
        }
        try {
            return LocalDate.parse(cleaned);
        } catch (Exception ignored) {
        }
        try {
            DateFormat df = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", Locale.ENGLISH);
            Date parsed = df.parse(text);
            if (parsed != null) {
                return Instant.ofEpochMilli(parsed.getTime())
                        .atZone(ZoneId.systemDefault())
                        .toLocalDate();
            }
        } catch (Exception ignored) {
        }
        return null;
    }

    private String cleanupDateText(String text) {
        String cleaned = text;
        int tIndex = cleaned.indexOf('T');
        if (tIndex > 0) {
            cleaned = cleaned.substring(0, tIndex);
        }
        int spaceIndex = cleaned.indexOf(' ');
        if (spaceIndex > 0) {
            cleaned = cleaned.substring(0, spaceIndex);
        }
        return cleaned.trim();
    }

    private Double readNumber(Document doc, String... keys) {
        if (doc == null || keys == null) {
            return null;
        }
        for (String key : keys) {
            Object value = doc.get(key);
            if (value instanceof Number number) {
                return number.doubleValue();
            }
            if (value != null) {
                Double parsed = parseNumber(value.toString());
                if (parsed != null) {
                    return parsed;
                }
            }
        }
        return null;
    }

    private Double parseNumber(String text) {
        if (text == null) {
            return null;
        }
        String normalized = text.trim()
                .replace("$", "")
                .replace("USD", "")
                .replace(" ", "");
        if (normalized.isEmpty()) {
            return null;
        }
        if (normalized.contains(",") && !normalized.contains(".")) {
            normalized = normalized.replace(",", ".");
        } else {
            normalized = normalized.replace(",", "");
        }
        try {
            return Double.parseDouble(normalized);
        } catch (Exception ex) {
            return null;
        }
    }

    public static final class LobbyTotals {

        public final double totalInventory;
        public final double totalBill;

        public LobbyTotals(double totalInventory, double totalBill) {
            this.totalInventory = totalInventory;
            this.totalBill = totalBill;
        }
    }
}
