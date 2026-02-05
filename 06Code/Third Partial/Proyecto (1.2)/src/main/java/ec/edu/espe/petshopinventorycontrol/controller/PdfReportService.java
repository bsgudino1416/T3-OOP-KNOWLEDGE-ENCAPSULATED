package ec.edu.espe.petshopinventorycontrol.controller;

import com.toedter.calendar.JDateChooser;
import ec.edu.espe.petshopinventorycontrol.utils.ChartUtils;
import java.awt.Component;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.lang.reflect.Field;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import javax.imageio.ImageIO;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JFormattedTextField;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JOptionPane;
import javax.swing.table.TableModel;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.font.Standard14Fonts;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;

public final class PdfReportService {

    private static final DateTimeFormatter TIMESTAMP =
            DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss");
    private static final DateTimeFormatter DATE_FORMAT =
            DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public void exportReport(JFrame owner, String title, ReportTable table) {
        File file = chooseOutputFile(owner, title);
        if (file == null) {
            return;
        }
        try {
            Map<String, String> summary = extractFormValues(owner);
            ReportTable resolvedTable = resolveTable(owner, table);
            BufferedImage chart = buildChart(resolvedTable);
            writePdf(file, title, summary, resolvedTable, chart);
            JOptionPane.showMessageDialog(
                    owner,
                    "Reporte generado en:\n" + file.getAbsolutePath(),
                    "Reporte PDF",
                    JOptionPane.INFORMATION_MESSAGE
            );
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(
                    owner,
                    "No se pudo generar el reporte: " + ex.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    private File chooseOutputFile(Component owner, String title) {
        String base = normalizeFileName(title);
        String fileName = base + "_" + TIMESTAMP.format(LocalDateTime.now()) + ".pdf";
        File defaultDir = new File("reports");
        if (!defaultDir.exists()) {
            defaultDir.mkdirs();
        }
        JFileChooser chooser = new JFileChooser(defaultDir);
        chooser.setDialogTitle("Selecciona carpeta para guardar el PDF");
        chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        chooser.setAcceptAllFileFilterUsed(false);
        int result = chooser.showOpenDialog(owner);
        if (result != JFileChooser.APPROVE_OPTION) {
            return null;
        }
        File directory = chooser.getSelectedFile();
        if (directory == null) {
            return null;
        }
        File target = new File(directory, fileName);
        if (target.exists()) {
            int overwrite = JOptionPane.showConfirmDialog(
                    owner,
                    "El archivo ya existe:\n" + target.getName() + "\n¿Deseas reemplazarlo?",
                    "Confirmar",
                    JOptionPane.YES_NO_OPTION
            );
            if (overwrite != JOptionPane.YES_OPTION) {
                return null;
            }
        }
        return target;
    }

    private String normalizeFileName(String title) {
        if (title == null || title.trim().isEmpty()) {
            return "reporte";
        }
        return title.trim().replaceAll("[^a-zA-Z0-9_-]", "_");
    }

    private void writePdf(File file,
            String title,
            Map<String, String> summary,
            ReportTable table,
            BufferedImage chart) throws Exception {
        try (PDDocument document = new PDDocument()) {
            PdfLayout layout = new PdfLayout(document);
            layout.addTitle(safe(title));
            layout.addText("Generated: " + DATE_FORMAT.format(LocalDateTime.now()));

            if (summary != null && !summary.isEmpty()) {
                layout.addSection("Form Data");
                List<String> headers = List.of("Campo", "Valor");
                List<List<String>> rows = new ArrayList<>();
                for (Map.Entry<String, String> entry : summary.entrySet()) {
                    rows.add(List.of(entry.getKey(), entry.getValue()));
                }
                layout.addTable(headers, rows);
            }

            if (table != null && !table.headers.isEmpty()) {
                layout.addSection("Table Data");
                layout.addTable(table.headers, table.rows);
            }

            if (chart != null) {
                layout.addSection("Chart");
                layout.addImage(chart);
            }

            layout.close();
            document.save(file);
        }
    }

    private ReportTable resolveTable(JFrame owner, ReportTable table) {
        if (table != null && table.rows != null && !table.rows.isEmpty()) {
            return table;
        }
        ReportTable fallback = extractTableFromFrame(owner);
        if (fallback != null && fallback.rows != null && !fallback.rows.isEmpty()) {
            return fallback;
        }
        return table != null ? table : fallback;
    }

    private ReportTable extractTableFromFrame(JFrame frame) {
        if (frame == null) {
            return null;
        }
        List<JTable> tables = new ArrayList<>();
        for (Field field : frame.getClass().getDeclaredFields()) {
            field.setAccessible(true);
            if (!JTable.class.isAssignableFrom(field.getType())) {
                continue;
            }
            try {
                Object value = field.get(frame);
                if (value instanceof JTable table) {
                    tables.add(table);
                }
            } catch (Exception ignored) {
            }
        }
        if (tables.isEmpty()) {
            return null;
        }
        JTable best = tables.stream()
                .max(Comparator.comparingInt(JTable::getRowCount))
                .orElse(tables.get(0));
        return ReportTable.fromJTable(best);
    }

    private BufferedImage buildChart(ReportTable table) {
        if (table == null || table.rows.isEmpty()) {
            return null;
        }
        Map<String, Integer> data = buildChartData(table);
        if (data.isEmpty()) {
            return null;
        }
        return ChartUtils.createBarChart(data, 700, 300);
    }

    private Map<String, Integer> buildChartData(ReportTable table) {
        Map<String, Integer> counts = new LinkedHashMap<>();
        int labelColumn = table.headers.size() > 1 ? 1 : 0;
        for (List<String> row : table.rows) {
            if (row.isEmpty()) {
                continue;
            }
            String key = labelColumn < row.size() ? row.get(labelColumn) : "";
            key = (key == null || key.trim().isEmpty()) ? "Sin dato" : key.trim();
            counts.put(key, counts.getOrDefault(key, 0) + 1);
        }
        if (counts.size() <= 8) {
            return counts;
        }
        Map<String, Integer> limited = new LinkedHashMap<>();
        int index = 0;
        int other = 0;
        for (Map.Entry<String, Integer> entry : counts.entrySet()) {
            if (index < 7) {
                limited.put(entry.getKey(), entry.getValue());
            } else {
                other += entry.getValue();
            }
            index++;
        }
        limited.put("Otros", other);
        return limited;
    }

    private Map<String, String> extractFormValues(JFrame frame) {
        Map<String, String> values = new LinkedHashMap<>();
        if (frame == null) {
            return values;
        }
        Field[] fields = frame.getClass().getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);
            String fieldName = field.getName();
            if (shouldSkipField(fieldName)) {
                continue;
            }
            Object value = readComponentValue(field, frame);
            if (value == null) {
                continue;
            }
            String text = value.toString().trim();
            if (text.isEmpty()) {
                continue;
            }
            String label = friendlyName(fieldName);
            values.put(label, text);
        }
        return values;
    }

    private boolean shouldSkipField(String name) {
        if (name == null) {
            return true;
        }
        String lower = name.toLowerCase();
        return lower.contains("pass")
                || lower.startsWith("jpanel")
                || lower.startsWith("jlabel")
                || lower.startsWith("jscroll")
                || lower.startsWith("jmenu")
                || lower.startsWith("jmenubar")
                || lower.startsWith("jseparator")
                || lower.contains("menu");
    }

    private Object readComponentValue(Field field, JFrame frame) {
        try {
            Object component = field.get(frame);
            if (component instanceof JTextField textField) {
                return textField.getText();
            }
            if (component instanceof JFormattedTextField formatted) {
                return formatted.getText();
            }
            if (component instanceof JPasswordField) {
                return null;
            }
            if (component instanceof JTextArea area) {
                return area.getText();
            }
            if (component instanceof JComboBox<?> combo) {
                Object selected = combo.getSelectedItem();
                return selected == null ? "" : String.valueOf(selected);
            }
            if (component instanceof JDateChooser chooser) {
                java.util.Date date = chooser.getDate();
                if (date == null) {
                    return "";
                }
                return DATE_FORMAT.format(date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
            }
            if (component instanceof JTable table) {
                return table.getRowCount() + " filas";
            }
            if (component instanceof JScrollPane) {
                return null;
            }
        } catch (Exception ex) {
            return null;
        }
        return null;
    }

    private String friendlyName(String name) {
        if (name == null || name.trim().isEmpty()) {
            return "Campo";
        }
        String cleaned = name.replaceFirst("^(txt|cmb|jDate|date|Date|spn)", "");
        cleaned = cleaned.replaceAll("([a-z])([A-Z])", "$1 $2");
        cleaned = cleaned.replace('_', ' ').trim();
        if (cleaned.isEmpty()) {
            return name;
        }
        return cleaned.substring(0, 1).toUpperCase() + cleaned.substring(1);
    }

    private String safe(String value) {
        return value == null ? "" : value;
    }

    public static final class ReportTable {
        private final List<String> headers;
        private final List<List<String>> rows;

        public ReportTable(List<String> headers, List<List<String>> rows) {
            this.headers = headers == null ? List.of() : headers;
            this.rows = rows == null ? List.of() : rows;
        }

        public static ReportTable of(String[] headers, List<String[]> rows) {
            List<String> headerList = new ArrayList<>();
            if (headers != null) {
                for (String header : headers) {
                    headerList.add(header == null ? "" : header);
                }
            }
            List<List<String>> rowList = new ArrayList<>();
            if (rows != null) {
                for (String[] row : rows) {
                    List<String> values = new ArrayList<>();
                    if (row != null) {
                        for (String value : row) {
                            values.add(value == null ? "" : value);
                        }
                    }
                    rowList.add(values);
                }
            }
            return new ReportTable(headerList, rowList);
        }

        public static ReportTable fromJTable(JTable table) {
            if (table == null) {
                return new ReportTable(List.of(), List.of());
            }
            TableModel model = table.getModel();
            List<String> headers = new ArrayList<>();
            for (int col = 0; col < model.getColumnCount(); col++) {
                headers.add(safeColumnName(model.getColumnName(col)));
            }
            List<List<String>> rows = new ArrayList<>();
            for (int row = 0; row < model.getRowCount(); row++) {
                List<String> values = new ArrayList<>();
                for (int col = 0; col < model.getColumnCount(); col++) {
                    Object value = model.getValueAt(row, col);
                    values.add(value == null ? "" : value.toString());
                }
                rows.add(values);
            }
            return new ReportTable(headers, rows);
        }

        private static String safeColumnName(String value) {
            return value == null ? "" : value;
        }
    }

    private static final class PdfLayout {
        private static final float MARGIN = 40f;
        private static final float LINE_HEIGHT = 14f;
        private static final float ROW_HEIGHT = 18f;
        private static final float FONT_SIZE = 10f;
        private static final float TITLE_SIZE = 16f;
        private static final PDType1Font FONT_REGULAR =
                new PDType1Font(Standard14Fonts.FontName.HELVETICA);
        private static final PDType1Font FONT_BOLD =
                new PDType1Font(Standard14Fonts.FontName.HELVETICA_BOLD);

        private final PDDocument document;
        private PDPage page;
        private PDPageContentStream content;
        private float y;

        private PdfLayout(PDDocument document) throws Exception {
            this.document = document;
            newPage();
        }

        private void newPage() throws Exception {
            closeStream();
            page = new PDPage(PDRectangle.A4);
            document.addPage(page);
            content = new PDPageContentStream(document, page);
            y = page.getMediaBox().getHeight() - MARGIN;
        }

        private void addTitle(String text) throws Exception {
            addText(text, FONT_BOLD, TITLE_SIZE);
            y -= 4;
        }

        private void addSection(String text) throws Exception {
            y -= 6;
            addText(text, FONT_BOLD, 12f);
        }

        private void addText(String text) throws Exception {
            addText(text, FONT_REGULAR, FONT_SIZE);
        }

        private void addText(String text, PDType1Font font, float size) throws Exception {
            for (String line : wrapText(text, font, size, getWidth())) {
                ensureSpace(LINE_HEIGHT);
                content.beginText();
                content.setFont(font, size);
                content.newLineAtOffset(MARGIN, y);
                content.showText(line);
                content.endText();
                y -= LINE_HEIGHT;
            }
        }

        private void addTable(List<String> headers, List<List<String>> rows) throws Exception {
            if (headers == null || headers.isEmpty()) {
                return;
            }
            float tableWidth = getWidth();
            int cols = headers.size();
            float colWidth = tableWidth / cols;

            drawRow(headers, cols, colWidth, FONT_BOLD);
            for (List<String> row : rows) {
                drawRow(row, cols, colWidth, FONT_REGULAR);
            }
        }

        private void drawRow(List<String> row, int cols, float colWidth, PDType1Font font) throws Exception {
            ensureSpace(ROW_HEIGHT);
            float x = MARGIN;
            float yTop = y;
            for (int i = 0; i < cols; i++) {
                content.addRect(x, yTop - ROW_HEIGHT, colWidth, ROW_HEIGHT);
                content.stroke();
                String value = "";
                if (row != null && i < row.size()) {
                    value = row.get(i);
                }
                String text = fitText(value, font, FONT_SIZE, colWidth - 4);
                content.beginText();
                content.setFont(font, FONT_SIZE);
                content.newLineAtOffset(x + 2, yTop - 13);
                content.showText(text);
                content.endText();
                x += colWidth;
            }
            y -= ROW_HEIGHT;
        }

        private void addImage(BufferedImage image) throws Exception {
            if (image == null) {
                return;
            }
            byte[] bytes = toPngBytes(image);
            PDImageXObject pdImage = PDImageXObject.createFromByteArray(document, bytes, "chart");
            float maxWidth = getWidth();
            float maxHeight = 250f;
            float scale = Math.min(maxWidth / pdImage.getWidth(), maxHeight / pdImage.getHeight());
            float w = pdImage.getWidth() * scale;
            float h = pdImage.getHeight() * scale;
            ensureSpace(h + 10);
            content.drawImage(pdImage, MARGIN, y - h, w, h);
            y -= (h + 10);
        }

        private void ensureSpace(float needed) throws Exception {
            if (y - needed < MARGIN) {
                newPage();
            }
        }

        private float getWidth() {
            return page.getMediaBox().getWidth() - 2 * MARGIN;
        }

        private List<String> wrapText(String text, PDType1Font font, float size, float maxWidth)
                throws Exception {
            List<String> lines = new ArrayList<>();
            if (text == null || text.trim().isEmpty()) {
                lines.add("");
                return lines;
            }
            String[] words = text.split("\\s+");
            StringBuilder line = new StringBuilder();
            for (String word : words) {
                String candidate = line.length() == 0 ? word : line + " " + word;
                if (textWidth(candidate, font, size) <= maxWidth) {
                    line = new StringBuilder(candidate);
                } else {
                    lines.add(line.toString());
                    line = new StringBuilder(word);
                }
            }
            if (line.length() > 0) {
                lines.add(line.toString());
            }
            return lines;
        }

        private String fitText(String text, PDType1Font font, float size, float maxWidth)
                throws Exception {
            String safe = text == null ? "" : text.replaceAll("[\\r\\n]+", " ");
            if (textWidth(safe, font, size) <= maxWidth) {
                return safe;
            }
            String trimmed = safe;
            while (trimmed.length() > 0 && textWidth(trimmed + "...", font, size) > maxWidth) {
                trimmed = trimmed.substring(0, trimmed.length() - 1);
            }
            return trimmed.isEmpty() ? "" : trimmed + "...";
        }

        private float textWidth(String text, PDType1Font font, float size) throws Exception {
            return font.getStringWidth(text) / 1000f * size;
        }

        private byte[] toPngBytes(BufferedImage image) throws Exception {
            ByteArrayOutputStream output = new ByteArrayOutputStream();
            ImageIO.write(image, "png", output);
            return output.toByteArray();
        }

        private void close() throws Exception {
            closeStream();
        }

        private void closeStream() throws Exception {
            if (content != null) {
                content.close();
                content = null;
            }
        }
    }
}
