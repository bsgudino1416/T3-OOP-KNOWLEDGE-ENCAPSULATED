package ec.edu.espe.view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class PetShopView extends JFrame {
    
    // Componentes visuales
    private JTextArea displayArea;
    private JTextField nombreField, cantidadField;
    private JComboBox<String> categoriaBox;
    private JButton btnAgregar, btnActualizar, btnVender;

    public PetShopView() {
        super("PetShop MVC - Control de Inventario");
        
        // Configuración de la ventana
        setSize(600, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Centrar en la pantalla
        setLayout(new BorderLayout());

        // --- PANEL DE ENTRADA (FORMULARIO) ---
        JPanel inputPanel = new JPanel(new GridLayout(5, 2, 10, 10)); // 5 filas, 2 columnas, espacios de 10px
        inputPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Margen alrededor

        // Fila 1: Nombre
        inputPanel.add(new JLabel("Nombre Producto:"));
        nombreField = new JTextField();
        inputPanel.add(nombreField);

        // Fila 2: Categoría
        inputPanel.add(new JLabel("Categoría:"));
        String[] categorias = {"Alimento", "Juguetes", "Medicina", "Accesorios"};
        categoriaBox = new JComboBox<>(categorias);
        inputPanel.add(categoriaBox);

        // Fila 3: Cantidad
        inputPanel.add(new JLabel("Cantidad:"));
        cantidadField = new JTextField();
        inputPanel.add(cantidadField);

        // Fila 4: Botón Agregar
        btnAgregar = new JButton("Agregar al Inventario");
        inputPanel.add(btnAgregar);
        
        // Botón Refrescar
        btnActualizar = new JButton("Refrescar Lista");
        inputPanel.add(btnActualizar);
        
        // Fila 5: Botón Vender (Destacado en ROJO)
        btnVender = new JButton("Vender (Restar Stock)");
        btnVender.setForeground(Color.RED);
        btnVender.setFont(new Font("Arial", Font.BOLD, 12));
        inputPanel.add(btnVender);
        
        inputPanel.add(new JLabel("")); // Espacio vacío para rellenar la cuadrícula

        add(inputPanel, BorderLayout.NORTH);

        // --- PANEL CENTRAL (VISUALIZACIÓN DE DATOS) ---
        displayArea = new JTextArea();
        displayArea.setEditable(false); // El usuario no puede escribir aquí manualmente
        displayArea.setFont(new Font("Monospaced", Font.PLAIN, 12)); // Fuente tipo consola para que se alinee bien
        add(new JScrollPane(displayArea), BorderLayout.CENTER);
    }

    // --- MÉTODOS PARA OBTENER DATOS (Getters) ---
    // El Controlador usa estos métodos para saber qué escribió el usuario

    public String getNombre() {
        // .trim() borra los espacios en blanco al inicio y al final por si acaso
        return nombreField.getText().trim();
    }
    
    public String getCategoria() {
        return (String) categoriaBox.getSelectedItem();
    }
    
    public int getCantidad() throws NumberFormatException {
        String texto = cantidadField.getText().trim();
        if (texto.isEmpty()) {
            throw new NumberFormatException("Campo vacío");
        }
        return Integer.parseInt(texto);
    }

    // --- MÉTODOS PARA MODIFICAR LA VISTA ---
    
    public void setDisplay(String texto) {
        displayArea.setText(texto);
    }
    
    public void limpiarCampos() {
        nombreField.setText("");
        cantidadField.setText("");
        nombreField.requestFocus(); // Pone el cursor en "Nombre" para seguir trabajando rápido
    }
    
    // Ventana de Error (Icono Rojo)
    public void mostrarError(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje, "Error", JOptionPane.ERROR_MESSAGE);
    }
    
    // Ventana de Alerta (Icono Amarillo - Triángulo)
    public void mostrarAlertaStock(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje, "¡ALERTA DE STOCK BAJO!", JOptionPane.WARNING_MESSAGE);
    }

    // --- LISTENERS (Conexiones para el Controlador) ---
    // Estos métodos permiten que el Controlador "escuche" los clics
    
    public void addAgregarListener(ActionListener listen) {
        btnAgregar.addActionListener(listen);
    }

    public void addActualizarListener(ActionListener listen) {
        btnActualizar.addActionListener(listen);
    }
    
    public void addVenderListener(ActionListener listen) {
        btnVender.addActionListener(listen);
    }
}