package ec.edu.espe.petshopinventorycontrol.view;

import ec.edu.espe.petshopinventorycontrol.controller.EventManager;
import ec.edu.espe.petshopinventorycontrol.controller.IEventListener;
import ec.edu.espe.petshopinventorycontrol.controller.SystemEvent;
import javax.swing.*;
import java.awt.*;

public class FrmSystemMonitor extends JFrame implements IEventListener {

    private JTextArea txtLogArea;

    public FrmSystemMonitor() {
        super("Monitor del Sistema (Clean Architecture)");
        setSize(500, 350);
        
        setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE); 
        setLocationRelativeTo(null);
        initComponents();
        
       
        EventManager.getInstance().subscribe(this);
    }

    private void initComponents() {
        txtLogArea = new JTextArea();
        txtLogArea.setEditable(false);
        txtLogArea.setFont(new Font("Consolas", Font.PLAIN, 12));
        txtLogArea.setBackground(new Color(30, 30, 30)); // Modo oscuro
        txtLogArea.setForeground(Color.GREEN);
        
        JScrollPane scrollPane = new JScrollPane(txtLogArea);
        add(scrollPane, BorderLayout.CENTER);
    }

    @Override
    public void onEvent(SystemEvent event) {
        
        SwingUtilities.invokeLater(() -> {
            txtLogArea.append(event.toString() + "\n");
            txtLogArea.setCaretPosition(txtLogArea.getDocument().getLength());
        });
    }
}