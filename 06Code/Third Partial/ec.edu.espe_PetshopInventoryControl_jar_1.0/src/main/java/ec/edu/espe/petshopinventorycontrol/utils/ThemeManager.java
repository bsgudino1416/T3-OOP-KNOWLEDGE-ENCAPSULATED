package ec.edu.espe.petshopinventorycontrol.utils;

import java.awt.Color;
import java.awt.Font;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class ThemeManager {

    private static ThemeManager instance;

    // Colores corporativos
    public final Color COLOR_PRIMARY = new Color(0, 0, 119);
    public final Color COLOR_SECONDARY = new Color(0, 0, 150);
    public final Color COLOR_BACKGROUND = new Color(245, 245, 245);
    public final Color COLOR_TEXT_WHITE = Color.WHITE;

    // Fuentes
    public final Font FONT_TITLE = new Font("Segoe UI", Font.BOLD, 24);
    public final Font FONT_BUTTON = new Font("Segoe UI", Font.PLAIN, 14);

    private ThemeManager() {}

    public static synchronized ThemeManager getInstance() {
        if (instance == null) {
            instance = new ThemeManager();
        }
        return instance;
    }

    public void styleButton(JButton btn) {
        btn.setBackground(COLOR_PRIMARY);
        btn.setForeground(COLOR_TEXT_WHITE);
        btn.setFont(FONT_BUTTON);
        btn.setFocusPainted(false);
    }

    public void styleHeader(JPanel panel) {
        panel.setBackground(COLOR_PRIMARY);
    }
    
    public void styleTitle(JLabel label) {
        label.setFont(FONT_TITLE);
        label.setForeground(COLOR_TEXT_WHITE);
    }
}