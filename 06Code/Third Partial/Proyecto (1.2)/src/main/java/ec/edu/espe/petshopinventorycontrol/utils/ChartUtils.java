package ec.edu.espe.petshopinventorycontrol.utils;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public final class ChartUtils {

    private static final Color[] PALETTE = {
        new Color(0x4E79A7),
        new Color(0xF28E2B),
        new Color(0xE15759),
        new Color(0x76B7B2),
        new Color(0x59A14F),
        new Color(0xEDC948),
        new Color(0xB07AA1),
        new Color(0xFF9DA7),
        new Color(0x9C755F),
        new Color(0xBAB0AC)
    };

    private ChartUtils() {
    }

    public static BufferedImage createPieChart(Map<String, Integer> data, int width, int height) {
        int w = Math.max(width, 300);
        int h = Math.max(height, 200);
        BufferedImage image = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = image.createGraphics();
        configureGraphics(g);
        clearBackground(g, w, h);

        if (data == null || data.isEmpty()) {
            drawNoData(g, w, h);
            g.dispose();
            return image;
        }

        int padding = 20;
        int diameter = Math.min(w, h) - padding * 2;
        int x = padding;
        int y = (h - diameter) / 2;

        int total = data.values().stream().mapToInt(Integer::intValue).sum();
        int startAngle = 0;
        int index = 0;
        for (Map.Entry<String, Integer> entry : data.entrySet()) {
            int value = entry.getValue() == null ? 0 : entry.getValue();
            int angle = total == 0 ? 0 : Math.round((value * 360f) / total);
            g.setColor(PALETTE[index % PALETTE.length]);
            g.fillArc(x, y, diameter, diameter, startAngle, angle);
            startAngle += angle;
            index++;
        }

        drawLegend(g, data, w, h);
        g.dispose();
        return image;
    }

    public static BufferedImage createBarChart(Map<String, Integer> data, int width, int height) {
        int w = Math.max(width, 300);
        int h = Math.max(height, 200);
        BufferedImage image = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = image.createGraphics();
        configureGraphics(g);
        clearBackground(g, w, h);

        if (data == null || data.isEmpty()) {
            drawNoData(g, w, h);
            g.dispose();
            return image;
        }

        int padding = 40;
        int chartWidth = w - padding * 2;
        int chartHeight = h - padding * 2;
        int x0 = padding;
        int y0 = h - padding;

        int max = data.values().stream().mapToInt(v -> v == null ? 0 : v).max().orElse(1);
        int count = data.size();
        int barWidth = Math.max(10, chartWidth / Math.max(count, 1) - 10);

        g.setColor(new Color(0x333333));
        g.setStroke(new BasicStroke(1));
        g.drawLine(x0, y0, x0 + chartWidth, y0);
        g.drawLine(x0, y0, x0, y0 - chartHeight);

        int i = 0;
        List<String> labels = new ArrayList<>(data.keySet());
        for (String label : labels) {
            int value = data.get(label) == null ? 0 : data.get(label);
            int barHeight = (int) ((value / (double) max) * (chartHeight - 10));
            int x = x0 + i * (barWidth + 10);
            int y = y0 - barHeight;
            g.setColor(PALETTE[i % PALETTE.length]);
            g.fillRect(x, y, barWidth, barHeight);
            g.setColor(new Color(0x333333));
            g.drawRect(x, y, barWidth, barHeight);
            i++;
        }

        drawLegend(g, data, w, h);
        g.dispose();
        return image;
    }

    private static void configureGraphics(Graphics2D g) {
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g.setFont(new Font("SansSerif", Font.PLAIN, 12));
    }

    private static void clearBackground(Graphics2D g, int w, int h) {
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, w, h);
    }

    private static void drawNoData(Graphics2D g, int w, int h) {
        g.setColor(new Color(0x666666));
        g.drawString("Sin datos para graficar", w / 2 - 70, h / 2);
    }

    private static void drawLegend(Graphics2D g, Map<String, Integer> data, int w, int h) {
        int x = w - 180;
        int y = 20;
        int i = 0;
        for (Map.Entry<String, Integer> entry : data.entrySet()) {
            g.setColor(PALETTE[i % PALETTE.length]);
            g.fillRect(x, y + i * 18, 12, 12);
            g.setColor(new Color(0x333333));
            g.drawRect(x, y + i * 18, 12, 12);
            String label = entry.getKey() + " (" + entry.getValue() + ")";
            g.drawString(label, x + 18, y + 11 + i * 18);
            i++;
        }
    }
}
