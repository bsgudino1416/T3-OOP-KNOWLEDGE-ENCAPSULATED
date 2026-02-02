package ec.edu.espe.petshopinventorycontrol.view;

import ec.edu.espe.petshopinventorycontrol.controller.ViewFactory;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.*;

public class FrmModernDashboard extends javax.swing.JFrame {

    
    private final Color COLOR_PRIMARY = new Color(0, 0, 119); // Tu azul oscuro
    private final Color COLOR_HOVER = new Color(50, 50, 180);
    private final Color COLOR_BG = new Color(245, 245, 245);

    public FrmModernDashboard() {
        initComponents();
    }

    private void initComponents() {
        this.setTitle("PetShop Inventory Control - Menú Principal");
        this.setSize(1100, 650);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(new BorderLayout());

       
        JPanel pnlSidebar = new JPanel();
        pnlSidebar.setPreferredSize(new Dimension(260, 650));
        pnlSidebar.setBackground(COLOR_PRIMARY);
        pnlSidebar.setLayout(null); 

        
        JLabel lblTitle = new JLabel("PETSHOP CONTROL");
        lblTitle.setForeground(Color.WHITE);
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 22));
        lblTitle.setBounds(30, 40, 250, 30);
        pnlSidebar.add(lblTitle);

        JSeparator sep = new JSeparator();
        sep.setBounds(30, 80, 200, 10);
        pnlSidebar.add(sep);

       
        int y = 120;
        int gap = 60;

        addButton(pnlSidebar, "📦  Productos", y, e -> ViewFactory.showView("PRODUCTOS"));
        addButton(pnlSidebar, "📊  Inventario", y + gap, e -> ViewFactory.showView("INVENTARIO"));
        addButton(pnlSidebar, "🚚  Proveedores", y + gap*2, e -> ViewFactory.showView("PROVEEDORES"));
        addButton(pnlSidebar, "💰  Facturación", y + gap*3, e -> ViewFactory.showView("FACTURACION"));
        addButton(pnlSidebar, "👥  Personal", y + gap*4, e -> ViewFactory.showView("PERSONAL"));
        addButton(pnlSidebar, "📈  Reportes", y + gap*5, e -> ViewFactory.showView("REPORTES"));

        // Botón Salir
        JButton btnExit = new JButton("Cerrar Sesión");
        btnExit.setBounds(30, 550, 200, 40);
        btnExit.setBackground(new Color(200, 60, 60));
        btnExit.setForeground(Color.WHITE);
        btnExit.setFocusPainted(false);
        btnExit.addActionListener(e -> {
            this.dispose();
            new FrmLogin().setVisible(true);
        });
        pnlSidebar.add(btnExit);

        // --- 3. PANEL CENTRAL (CONTENIDO) ---
        JPanel pnlContent = new JPanel();
        pnlContent.setBackground(COLOR_BG);
        pnlContent.setLayout(new GridBagLayout());
        
        JLabel lblWelcome = new JLabel("Bienvenido al Sistema");
        lblWelcome.setFont(new Font("Segoe UI", Font.PLAIN, 36));
        lblWelcome.setForeground(Color.GRAY);
        pnlContent.add(lblWelcome);

        // Agregar paneles a la ventana
        this.add(pnlSidebar, BorderLayout.WEST);
        this.add(pnlContent, BorderLayout.CENTER);
    }

    // Método auxiliar para crear botones estilizados (DRY - Don't Repeat Yourself)
    private void addButton(JPanel panel, String text, int y, java.awt.event.ActionListener action) {
        JButton btn = new JButton(text);
        btn.setBounds(0, y, 260, 45);
        btn.setBackground(COLOR_PRIMARY);
        btn.setForeground(Color.WHITE);
        btn.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        btn.setHorizontalAlignment(SwingConstants.LEFT);
        btn.setBorder(BorderFactory.createEmptyBorder(0, 30, 0, 0)); // Margen izquierdo
        btn.setFocusPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        // Evento Click
        btn.addActionListener(action);

        // Efecto Hover (Pasar el mouse)
        btn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                btn.setBackground(COLOR_HOVER);
            }
            @Override
            public void mouseExited(MouseEvent e) {
                btn.setBackground(COLOR_PRIMARY);
            }
        });

        panel.add(btn);
    }

    // Main para probar solo esta ventana
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {}
        
        java.awt.EventQueue.invokeLater(() -> new FrmModernDashboard().setVisible(true));
    }

    public void setVisible(boolean b) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}
