package ec.edu.espe.petshopinventorycontrol.employee.view;

import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.JFormattedTextField;
import javax.swing.JOptionPane;
import javax.swing.JSpinner.NumberEditor;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import ec.edu.espe.petshopinventorycontrol.employee.view.FrmEmployee;


//import ec.edu.espe.petshopinventorycontrol.employee.model.PurchaseProduct;
//import ec.edu.espe.petshopinventorycontrol.employee.model.PurchaseSession;


/**
 *
 * @author Bryan Gudino, KNOWLEDGE ENCAPSULATE, @ESPE
 */
public class FrmAddProduct extends javax.swing.JFrame {
    
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(FrmAddProduct.class.getName());

    /**
     * Creates new form FrmAddProduct
     */
    public FrmAddProduct() {
        initComponents();
        
    // ===== ID AUTOMÁTICO =====
    configureProductId();
    txtProductId.setText("");

    // ===== SPINNER CANTIDAD =====
    SpinnerNumberModel qtyModel = new SpinnerNumberModel(1, 1, 10, 1);
    spinnerPurchaseQuantity.setModel(qtyModel);
    spinnerPurchaseQuantity.setEnabled(false);

    JSpinner.NumberEditor editor = new JSpinner.NumberEditor(spinnerPurchaseQuantity, "0");
    spinnerPurchaseQuantity.setEditor(editor);
    ((JFormattedTextField) editor.getTextField()).setEditable(false);

    // ===== CAMPOS SOLO LECTURA =====
    txtCostPerPound.setEditable(false);
    txtCostPerPound.setBackground(new java.awt.Color(230, 230, 230));

    txtCostPerKg.setEditable(false);
    txtCostPerKg.setBackground(new java.awt.Color(230, 230, 230));

    attachTotalCostListener();
    
    // ===== LISTENERS =====
    spinnerPurchaseQuantity.addChangeListener(e -> tryCalculateCosts());

    // ===== BLOQUEAR TODO AL INICIO =====
    lockInitialFields();

    cmbPurchaseUnit.setEnabled(false);
    
    //========PRECIO DE VENTA==============
    
    //UNIDAD DE VENTA

    cmbSaleUnit.setEnabled(false);
    cmbSaleUnit.removeAllItems();
    //-------

    SpinnerNumberModel profitModel = new SpinnerNumberModel(0.0, 0.0, 100.0, 0.1);
    spinnerProfitPercent.setModel(profitModel);

    // Editor con formato US para punto decimal
    DecimalFormatSymbols symbols = new DecimalFormatSymbols(Locale.US);
    JSpinner.NumberEditor profitEditor = new JSpinner.NumberEditor(spinnerProfitPercent, "0.##");
    profitEditor.getFormat().setDecimalFormatSymbols(symbols);
    spinnerProfitPercent.setEditor(profitEditor);

    // Validación: solo números y un solo punto decimal
    JFormattedTextField txtProfit = profitEditor.getTextField();
    txtProfit.addKeyListener(new KeyAdapter() {
    @Override
    public void keyTyped(KeyEvent e) {
        char c = e.getKeyChar();
        if (!Character.isDigit(c) && c != KeyEvent.VK_BACK_SPACE && c != '.') {
            e.consume();
        }
        if (c == '.' && txtProfit.getText().contains(".")) {
            e.consume();
        }
    }
});

// ---------- ACTUALIZAR PRECIO SUGERIDO ----------
    spinnerProfitPercent.addChangeListener(e -> updateSuggestedPrice());


//------------PRECIO CON IVA-------------

    txtFinalPrice.setEditable(false);
    
//-----------Stock calculate-----------
    cmbSaleUnit.addActionListener(e -> updateStockCalculate());
    cmbPurchaseUnit.addActionListener(e -> updateStockCalculate());
    spinnerPurchaseQuantity.addChangeListener(e -> updateStockCalculate());

    txtStockCalculate.setEditable(false);
    txtStockCalculate.setFocusable(false);
    txtStockCalculate.setBackground(new java.awt.Color(230, 230, 230));

//-----------BUTTONS------------
//View report
btnViewReport.addActionListener(evt -> {

    if (!isFormComplete()) {
        javax.swing.JOptionPane.showMessageDialog(
                this,
                "Faltan campos por completar.",
                "Formulario incompleto",
                javax.swing.JOptionPane.WARNING_MESSAGE
        );
        return;
    }

    // Guardar producto en memoria / lista
    saveCurrentProduct();

    //FrmInventoryPurchase frm = new FrmInventoryPurchase();
    //frm.setVisible(true);

});

//Save
btnSaveProduct.addActionListener(evt -> {

    if (!isFormComplete()) {
        javax.swing.JOptionPane.showMessageDialog(
                this,
                "Faltan campos por completar.",
                "Formulario incompleto",
                javax.swing.JOptionPane.WARNING_MESSAGE
        );
        return;
    }

    saveCurrentProduct();

    int option = javax.swing.JOptionPane.showOptionDialog(
            this,
            "Producto guardado.\n¿Qué deseas hacer?",
            "Confirmación",
            javax.swing.JOptionPane.YES_NO_OPTION,
            javax.swing.JOptionPane.QUESTION_MESSAGE,
            null,
            new String[]{"Ir a reporte", "Seguir comprando"},
            "Ir a reporte"
    );

    if (option == 0) {
        //new FrmInventoryPurchase().setVisible(true);
    } else {
        btnClearFormActionPerformed(null);
    }
});
//----------//-----------//--------




















}
    
//public FrmAddProduct(FrmInventoryPurchase inventoryWindow) {
    //initComponents();
    //this.inventoryWindow = inventoryWindow; // guardamos la referencia
//}
    
//========PRECIO DE VENTA==============
    
//UNIDAD DE VENTA 
    
private void updateSaleUnitOptions() {
    cmbSaleUnit.removeAllItems();
    cmbSaleUnit.setEnabled(false);

    if (cmbProductType.getSelectedItem() == null) return;

    String type = cmbProductType.getSelectedItem().toString();

    switch(type) {
        case "Comida":
            cmbSaleUnit.addItem("Libra");
            cmbSaleUnit.addItem("Kilo");
            break;
        case "Medicina":
        case "Accesorio":
        case "Juguete":
        case "Snack":
            cmbSaleUnit.addItem("Unidad");
            cmbSaleUnit.addItem("Docena");
            break;
        default:
            cmbSaleUnit.addItem("Unidad");
            break;
    }

    cmbSaleUnit.setEnabled(true);
    cmbSaleUnit.setSelectedIndex(0);
}

private void updateSaleUnitAfterCost() {
   
cmbSaleUnit.removeAllItems();

    String productType = cmbProductType.getSelectedItem().toString();
    String purchaseUnit = cmbPurchaseUnit.getSelectedItem().toString();

    if (productType.equals("Comida")) {
        cmbSaleUnit.addItem("Libra");
        cmbSaleUnit.addItem("Kilo");
    }
    else if (productType.equals("Accesorio")
          || productType.equals("Juguete")
          || productType.equals("Snack")) {

        cmbSaleUnit.addItem("Unidad");
        cmbSaleUnit.addItem("Docena");
    }
    else {
        cmbSaleUnit.addItem("Unidad");
    }

    cmbSaleUnit.setEnabled(true);
}

private void updateSuggestedPrice() {

    Object selected = cmbSaleUnit.getSelectedItem();
    if (selected == null) return;

    try {
        double profitPercent = ((Number) spinnerProfitPercent.getValue()).doubleValue();

        double totalCost = Double.parseDouble(txtTotalCost.getText());
        int quantity = (int) spinnerPurchaseQuantity.getValue();
        double costPerPurchaseUnit = totalCost / quantity;

        String saleUnit = selected.toString();
        String purchaseUnit = cmbPurchaseUnit.getSelectedItem().toString();

        double costPerUnit = 0;

        switch (saleUnit) {

            case "Unidad":
                if (purchaseUnit.equals("Docena")) {
                    costPerUnit = costPerPurchaseUnit / 12.0;
                } else {
                    costPerUnit = costPerPurchaseUnit;
                }
                break;

            case "Docena":
                costPerUnit = costPerPurchaseUnit;
                break;

            case "Libra":
                costPerUnit = Double.parseDouble(txtCostPerPound.getText());
                break;

            case "Kilo":
                costPerUnit = Double.parseDouble(txtCostPerKg.getText());
                break;
        }

        double suggestedPrice = costPerUnit + (costPerUnit * profitPercent / 100);

        txtSuggestedPrice.setText(String.format(Locale.US, "%.2f", suggestedPrice));

        updateFinalPriceWithIVA();
        } 
        catch (Exception e) {
        txtSuggestedPrice.setText("");
    }

}    


private void updateFinalPriceWithIVA() {

    if (txtSuggestedPrice.getText().isEmpty()) {
        txtFinalPrice.setText("");
        return;
    }

    try {
        double suggestedPrice = Double.parseDouble(txtSuggestedPrice.getText().replace(",", "."));

        double finalPrice = suggestedPrice * 1.15;

        txtFinalPrice.setText(String.format(Locale.US, "%.2f", finalPrice));

    } 
    catch (NumberFormatException e) {
        txtFinalPrice.setText("");
    }
}


//=============================================


//===============ID==================
private void updateProductId() {


    // Validaciones mínimas necesarias
    if (cmbProductType.getSelectedItem() == null) return;
    if (cmbAnimal.getSelectedItem() == null) return;

    String type = cmbProductType.getSelectedItem().toString();
    String animal = cmbAnimal.getSelectedItem().toString();

    String sizeCode = "";

    if (cmbSizeStage.isEnabled() && cmbSizeStage.getSelectedItem() != null) {
        sizeCode = "-" + getSizeStageCode(cmbSizeStage.getSelectedItem().toString());
    }

    long timestamp = System.currentTimeMillis() % 100000; // corto y legible

    String id = getProductTypeCode(type)
              + "-" + getAnimalCode(animal)
              + sizeCode
              + "-" + String.format("%05d", timestamp);

    txtProductId.setText(id);
}

private String getProductTypeCode(String type) {
    switch (type) {
        case "Comida": return "COM";
        case "Accesorio": return "ACC";
        case "Juguete": return "JUG";
        case "Medicina": return "MED";
        case "Snack": return "SNK";
        default: return "OTR";
    }
}

private String getAnimalCode(String animal) {
    switch (animal) {
        case "Perro": return "PER";
        case "Gato": return "GAT";
        case "Conejo": return "CON";
        case "Hámster": return "HAM";
        case "Cuy": return "CUY";
        case "Cerdo": return "CER";
        case "Caballo": return "CAB";
        case "Pollo": return "POL";
        case "Vaca": return "VAC";
        default: return "OTR";
    }
}

private String getSizeStageCode(String size) {
    switch (size) {
        case "Pequeño": return "P";
        case "Mediano": return "M";
        case "Grande": return "G";
        case "Gatito": return "K";
        case "Adulto": return "A";
        case "Senior": return "S";
        case "Juvenil": return "J";
        default: return "";
    }
}

//===================================


//===========STOCK CALCULATE=============
private void updateStockCalculate() {

    Object saleObj = cmbSaleUnit.getSelectedItem();
    Object purchaseObj = cmbPurchaseUnit.getSelectedItem();

    if (saleObj == null || purchaseObj == null) {
        txtStockCalculate.setText("");
        return;
    }

    try {
        String saleUnit = saleObj.toString();
        String purchaseUnit = purchaseObj.toString();
        int quantity = (int) spinnerPurchaseQuantity.getValue();

        double stock = 0;

        // ---------- COMIDA ----------
        if (saleUnit.equals("Libra")) {

            if (purchaseUnit.equals("Libra"))
                stock = quantity;

            else if (purchaseUnit.equals("Kilo"))
                stock = quantity * 2.20462;

            else if (purchaseUnit.equals("Quintal"))
                stock = quantity * 100;
        }

        else if (saleUnit.equals("Kilo")) {

            if (purchaseUnit.equals("Libra"))
                stock = quantity * 0.453592;

            else if (purchaseUnit.equals("Kilo"))
                stock = quantity;

            else if (purchaseUnit.equals("Quintal"))
                stock = quantity * 45.3592;
        }

        // ---------- ACCESORIOS / JUGUETES ----------
        else if (saleUnit.equals("Unidad")) {

            if (purchaseUnit.equals("Docena"))
                stock = quantity * 12;
            else
                stock = quantity;
        }

        else if (saleUnit.equals("Docena")) {
            stock = quantity;
        }

        txtStockCalculate.setText(
                String.format(Locale.US, "%.2f", stock)
        );

    } catch (Exception e) {
        txtStockCalculate.setText("");
    }
}

//======================================
    
private boolean isFlavorValidSilently() {
    return txtFlavor.getText().matches("[a-zA-ZáéíóúÁÉÍÓÚñÑ ]+");
}

private boolean validateFlavorFinal() {
    if (!isFlavorValidSilently()) {
        JOptionPane.showMessageDialog(this,
            "El sabor es obligatorio y solo debe contener sabores");
        txtFlavor.requestFocus();
        return false;
    }
    return true;
}

    
    
private void attachTotalCostListener() {
    txtTotalCost.getDocument().addDocumentListener(
        new javax.swing.event.DocumentListener() {

            private void update() {
                tryCalculateCosts();
            }

            @Override
            public void insertUpdate(javax.swing.event.DocumentEvent e) {
                update();
            }

            @Override
            public void removeUpdate(javax.swing.event.DocumentEvent e) {
                update();
            }

            @Override
            public void changedUpdate(javax.swing.event.DocumentEvent e) {
                update();
            }
        }
    );
}
    
  
private void updatePurchaseUnitOptions() {

    cmbPurchaseUnit.removeAllItems();

    String productType = cmbProductType.getSelectedItem().toString();

    if (productType.equals("Comida")) {
        cmbPurchaseUnit.addItem("Quintal");
        cmbPurchaseUnit.addItem("Kilo");
        cmbPurchaseUnit.addItem("Libra");
    } else {
        cmbPurchaseUnit.addItem("Unidad");
        cmbPurchaseUnit.addItem("Docena");
    }

    cmbPurchaseUnit.setEnabled(true);
}
    
    
    private void tryCalculateCosts() {

    if (!cmbProductType.getSelectedItem().toString().equals("Comida")) {
        txtCostPerPound.setText("");
        txtCostPerKg.setText("");
        return;
    }

    if (!isValidDecimal(txtTotalCost)) return;
    if (cmbPurchaseUnit.getSelectedItem() == null) return;

    int quantity = (int) spinnerPurchaseQuantity.getValue();
    if (quantity <= 0) return;

    calculateCostPerPound();
    calculateCostPerKg();
    }
    
    
    private boolean isValidDecimal(javax.swing.JTextField txt) {
    return txt.getText().matches("\\d+(\\.\\d+)?");
    }

//////////////////////////////////////////////    
    private void calculateCostPerKg() {

    if (txtCostPerPound.getText().isEmpty()) {
        txtCostPerKg.setText("");
        return;
    }

    try {
        double costPerPound = Double.parseDouble(txtCostPerPound.getText());

        // 1 lb = 0.453592 kg
        double costPerKg = costPerPound / 0.453592;

        // Formato con punto decimal
        java.text.DecimalFormatSymbols symbols = new java.text.DecimalFormatSymbols(java.util.Locale.US);
        java.text.DecimalFormat df = new java.text.DecimalFormat("0.##", symbols);

        txtCostPerKg.setText(df.format(costPerKg));
    } 
    catch (NumberFormatException e) {
        txtCostPerKg.setText("");
    }

    }
/////////////////////////////////////////
    
    
    private void calculateCostPerPound() {

    if (!cmbProductType.getSelectedItem().toString().equals("Comida")) {
        txtCostPerPound.setText("");
        txtCostPerKg.setText("");
        return;
    }

    if (!isValidDecimal(txtTotalCost)) return;

    int quantity = (int) spinnerPurchaseQuantity.getValue();
    if (quantity <= 0) return;

    double totalCost = Double.parseDouble(txtTotalCost.getText());
    String unit = cmbPurchaseUnit.getSelectedItem().toString();

    double costPerPound;

    switch (unit) {
        case "Libra":
            costPerPound = totalCost / quantity;
            break;

        case "Kilo":
            costPerPound = (totalCost / quantity) / 2.20462;
            break;

        case "Quintal":
            costPerPound = (totalCost / quantity) / 100;
            break;

        default:
            txtCostPerPound.setText("");
            txtCostPerKg.setText("");
            return;
    }

    txtCostPerPound.setText(String.format(java.util.Locale.US, "%.2f", costPerPound));

    
    calculateCostPerKg();

    }
    
    
    private void lockInitialFields() {
    cmbProductType.setEnabled(false);
    cmbAnimal.setEnabled(false);
    cmbSizeStage.setEnabled(false);
    txtFlavor.setEnabled(false);
    txtOtherAnimal.setEnabled(false);
    cmbPurchaseUnit.setEnabled(false);
    }
    
    private void configureProductId() {
    txtProductId.setEditable(false);
    txtProductId.setBackground(new java.awt.Color(230, 230, 230));
    }

    private String generateProductId() {
    long timestamp = System.currentTimeMillis();
    return "PRD-" + timestamp;
    }

    private boolean validateMakeName() {
    String make = txtMakeName.getText().trim();

    // 1. Obligatorio
    if (make.isEmpty()) {
        showError("La marca es obligatoria");
        txtMakeName.requestFocus();
        return false;
    }

    // 2. Longitud
    if (make.length() < 2 || make.length() > 30) {
        showError("La marca debe tener entre 2 y 30 caracteres");
        txtMakeName.requestFocus();
        return false;
    }

    // 3. Solo letras y espacios
    if (!make.matches("[a-zA-ZáéíóúÁÉÍÓÚñÑ ]+")) {
        showError("La marca solo puede contener letras");
        txtMakeName.requestFocus();
        return false;
    }

    // 4. Groserías
    if (containsBadWords(make)) {
        showError("La marca contiene palabras no permitidas");
        txtMakeName.requestFocus();
        return false;
    }

    return true;
}

private void showError(String message) {
    javax.swing.JOptionPane.showMessageDialog(
        this,
        message,
        "Validación",
        javax.swing.JOptionPane.ERROR_MESSAGE
    );
}
    
private boolean containsBadWords(String text) {
    String[] badWords = {
        "puta", "mierda", "carajo", "verga", "pendejo", "idiota"
    };

    String lowerText = text.toLowerCase();

    for (String word : badWords) {
        if (lowerText.contains(word)) {
            return true;
        }
    }
    return false;
}

private boolean validateProductType() {
    if (cmbProductType.getSelectedItem() == null) {
        showError("Debe seleccionar un tipo de producto");
        cmbProductType.requestFocus();
        return false;
    }
    return true;
}

private void updateFlavorAvailability() {
    String type = cmbProductType.getSelectedItem().toString();

    boolean hasFlavor = type.equals("Comida") || type.equals("Snack");

    txtFlavor.setEnabled(hasFlavor);

    if (!hasFlavor) {
        txtFlavor.setText("");
    }
}

private boolean validateAnimal() {
    if (cmbAnimal.getSelectedItem() == null) {
        showError("Debe seleccionar un animal");
        cmbAnimal.requestFocus();
        return false;
    }

    if (cmbAnimal.getSelectedItem().toString().equals("Otro")) {
        String other = txtOtherAnimal.getText().trim();
        if (other.isEmpty()) {
            showError("Debe especificar el otro animal");
            txtOtherAnimal.requestFocus();
            return false;
        }

        if (!other.matches("[a-zA-ZáéíóúÁÉÍÓÚñÑ ]+")) {
            showError("El nombre del animal solo puede contener letras");
            txtOtherAnimal.requestFocus();
            return false;
        }

        if (containsBadWords(other)) {
            showError("El nombre del animal contiene palabras no permitidas");
            txtOtherAnimal.requestFocus();
            return false;
        }
    }

    return true;
}

private void updateOtherAnimalField() {
    boolean isOther = cmbAnimal.getSelectedItem().toString().equals("Otro");

    txtOtherAnimal.setEnabled(isOther);

    if (!isOther) {
        txtOtherAnimal.setText("");
    }
}

private void updateSizeStageOptions() {
    cmbSizeStage.removeAllItems();

    String animal = cmbAnimal.getSelectedItem().toString();

    switch (animal) {
        case "Perro":
            cmbSizeStage.addItem("Pequeño");
            cmbSizeStage.addItem("Mediano");
            cmbSizeStage.addItem("Grande");
            cmbSizeStage.setEnabled(true);
            break;

        case "Gato":
            cmbSizeStage.addItem("Gatito");
            cmbSizeStage.addItem("Adulto");
            cmbSizeStage.addItem("Senior");
            cmbSizeStage.setEnabled(true);
            break;

        case "Conejo":
        case "Hámster":
        case "Cuy":
            cmbSizeStage.addItem("Juvenil");
            cmbSizeStage.addItem("Adulto");
            cmbSizeStage.setEnabled(true);
            break;

        default:
            cmbSizeStage.setEnabled(false);
            cmbSizeStage.removeAllItems();
            break;
    }
}

private boolean validateSizeStage() {
    if (!cmbSizeStage.isEnabled()) {
        return true; // No aplica, no se valida
    }

    if (cmbSizeStage.getSelectedItem() == null) {
        showError("Debe seleccionar tamaño o etapa");
        cmbSizeStage.requestFocus();
        return false;
    }

    return true;
}

private boolean validateFlavor() {
    String flavor = txtFlavor.getText().trim();

    if (flavor.isEmpty()) {
        showError("El campo sabor no puede estar vacío");
        txtFlavor.requestFocus();
        return false;
    }

    // SOLO letras y espacios
    if (!flavor.matches("[a-zA-ZáéíóúÁÉÍÓÚñÑ ]+")) {
        showError("El sabor solo puede contener letras");
        txtFlavor.requestFocus();
        return false;
    }

    if (containsBadWords(flavor)) {
        showError("El sabor contiene palabras no permitidas");
        txtFlavor.requestFocus();
        return false;
    }

    return true;
}


private boolean validateForm() {
    // Llamar todas las validaciones previas
    if (!validateMakeName()
        || !validateProductType()
        || !validateAnimal()
        || !validateSizeStage()
        || (txtFlavor.isEnabled() && !validateFlavor()) 
        || !validateTotalCost()){
    return false;
    }
    // Si todas las validaciones son correctas
    return true;
}

private boolean validateTotalCost() {
    String text = txtTotalCost.getText().trim();

    if (text.isEmpty()) {
        showError("Ingrese el total pagado");
        txtTotalCost.requestFocus();
        return false;
    }

    try {
        double value = Double.parseDouble(text);
        if (value <= 0) {
            showError("El total debe ser mayor a 0");
            txtTotalCost.requestFocus();
            return false;
        }
    } catch (NumberFormatException e) {
        showError("El total debe ser un número válido");
        txtTotalCost.requestFocus();
        return false;
    }

    return true;
}

private void updateFieldsByProductType() {

    String type = cmbProductType.getSelectedItem().toString();

    // SABOR
    boolean hasFlavor = type.equals("Comida") || type.equals("Snack");
    txtFlavor.setEnabled(hasFlavor);

    // TAMAÑO / ETAPA
    boolean hasSize = type.equals("Comida");
    cmbSizeStage.setEnabled(hasSize);

    if (!hasSize) {
        cmbSizeStage.setSelectedIndex(-1);
    }

    // UNIDADES DE COMPRA
    cmbPurchaseUnit.removeAllItems();

    switch (type) {
        case "Comida":
            cmbPurchaseUnit.addItem("Quintal");
            cmbPurchaseUnit.addItem("Kilo");
            cmbPurchaseUnit.addItem("Libra");
            break;

        case "Accesorio":
            cmbPurchaseUnit.addItem("Unidad");
            cmbPurchaseUnit.addItem("Docena");
            break;

        case "Juguete":
        case "Medicina":
            cmbPurchaseUnit.addItem("Unidad");
            break;

        case "Snack":
            cmbPurchaseUnit.addItem("Unidad");
            cmbPurchaseUnit.addItem("Libra");
            break;
    }

    cmbPurchaseUnit.setEnabled(true);
}

//============Buttons====================
private boolean isFormComplete() {

    if (txtMakeName.getText().trim().isEmpty()) return false;
    if (cmbProductType.getSelectedItem() == null) return false;
    if (cmbAnimal.getSelectedItem() == null) return false;

    if (cmbAnimal.getSelectedItem().toString().equals("Otro")
            && txtOtherAnimal.getText().trim().isEmpty()) {
        return false;
    }

    if (cmbSizeStage.isEnabled() && cmbSizeStage.getSelectedItem() == null) {
        return false;
    }

    if (txtFlavor.isEnabled() && txtFlavor.getText().trim().isEmpty()) {
        return false;
    }

    if (cmbPurchaseUnit.getSelectedItem() == null) return false;

    if ((int) spinnerPurchaseQuantity.getValue() <= 0) return false;

    if (txtTotalCost.getText().trim().isEmpty()) return false;

    if (txtProductId.getText().trim().isEmpty()) return false;

    return true;
}

private void updateButtonsState() {

    boolean hasAnyData =
            !txtMakeName.getText().trim().isEmpty()
            || !txtTotalCost.getText().trim().isEmpty()
            || (int) spinnerPurchaseQuantity.getValue() > 0;

    btnClearForm.setEnabled(hasAnyData);

    boolean complete = isFormComplete();
    btnSaveProduct.setEnabled(complete);
    btnViewReport.setEnabled(complete);
}

//private FrmInventoryPurchase inventoryWindow;

private void clearForm() {
    txtProductId.setText("");
    txtMakeName.setText("");
    cmbAnimal.setSelectedIndex(0);
    cmbSizeStage.setSelectedIndex(0);
    cmbPurchaseUnit.setSelectedIndex(0);
    spinnerPurchaseQuantity.setValue(1);
    txtCostPerPound.setText("");
    txtCostPerKg.setText("");
    txtTotalCost.setText("");
    txtStockCalculate.setText("");
    txtFinalPrice.setText("");
    spinnerProfitPercent.setValue(0.0);
}




private void saveCurrentProduct() {

    String id = txtProductId.getText();
    String product = txtMakeName.getText();
    String animal = cmbAnimal.getSelectedItem().toString();
    String size = cmbSizeStage.isEnabled()
            ? cmbSizeStage.getSelectedItem().toString()
            : "-";
    String unit = cmbPurchaseUnit.getSelectedItem().toString();
    int quantity = (int) spinnerPurchaseQuantity.getValue();
    int stock = Integer.parseInt(txtStockCalculate.getText());
    double subtotal = Double.parseDouble(txtTotalCost.getText());

//    PurchaseProduct p = new PurchaseProduct(
//            id, product, animal, size,
//            unit, quantity, stock, subtotal
//    );
//
//    PurchaseSession.products.add(p);
//    
//    if (inventoryWindow != null) {     // actualizar tabla si la ventana está abierta
//        inventoryWindow.refreshTable();
//    }

    clearForm();
}









//=======================================





    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        txtProductId = new javax.swing.JTextField();
        jLabel22 = new javax.swing.JLabel();
        txtStockCalculate = new javax.swing.JTextField();
        jPanel3 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        txtMakeName = new javax.swing.JTextField();
        cmbProductType = new javax.swing.JComboBox<>();
        cmbAnimal = new javax.swing.JComboBox<>();
        txtOtherAnimal = new javax.swing.JTextField();
        cmbSizeStage = new javax.swing.JComboBox<>();
        txtFlavor = new javax.swing.JTextField();
        jPanel4 = new javax.swing.JPanel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        cmbPurchaseUnit = new javax.swing.JComboBox<>();
        spinnerPurchaseQuantity = new javax.swing.JSpinner();
        txtTotalCost = new javax.swing.JTextField();
        txtCostPerPound = new javax.swing.JTextField();
        txtCostPerKg = new javax.swing.JTextField();
        jPanel5 = new javax.swing.JPanel();
        btnBackMenu = new javax.swing.JButton();
        btnClearForm = new javax.swing.JButton();
        btnSaveProduct = new javax.swing.JButton();
        btnViewReport = new javax.swing.JButton();
        jPanel6 = new javax.swing.JPanel();
        jLabel14 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        spinnerProfitPercent = new javax.swing.JSpinner();
        txtSuggestedPrice = new javax.swing.JTextField();
        txtFinalPrice = new javax.swing.JTextField();
        cmbSaleUnit = new javax.swing.JComboBox<>();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenu2 = new javax.swing.JMenu();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setFont(new java.awt.Font("Times New Roman", 3, 18)); // NOI18N
        jLabel1.setText("MENÚ EMPLEADO");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(173, 173, 173)
                .addComponent(jLabel1)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(jLabel1)
                .addContainerGap(17, Short.MAX_VALUE))
        );

        jLabel2.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        jLabel2.setText("ID Producto:");

        jLabel4.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        jLabel4.setText("AGREGAR PRODUCTO");

        txtProductId.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtProductIdActionPerformed(evt);
            }
        });

        jLabel22.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        jLabel22.setText("Calculo de stock:");

        txtStockCalculate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtStockCalculateActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel4)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtProductId, javax.swing.GroupLayout.PREFERRED_SIZE, 153, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel22)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtStockCalculate, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel22)
                        .addComponent(txtStockCalculate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel2)
                        .addComponent(txtProductId, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(19, 19, 19))
        );

        jLabel5.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        jLabel5.setText("INF. DEL PRODUCTO");

        jLabel3.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        jLabel3.setText("Marca:");

        jLabel6.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        jLabel6.setText("Tipo:");

        jLabel7.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        jLabel7.setText("Otro:");

        jLabel8.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        jLabel8.setText("Animal:");

        jLabel9.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        jLabel9.setText("Tamaño:");

        jLabel10.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        jLabel10.setText("Sabor:");

        txtMakeName.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtMakeNameFocusLost(evt);
            }
        });
        txtMakeName.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtMakeNameActionPerformed(evt);
            }
        });

        cmbProductType.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Comida", "Accesorio", "Juguete", "Medicina", "Snack" }));
        cmbProductType.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbProductTypeActionPerformed(evt);
            }
        });

        cmbAnimal.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Perro", "Gato", "Conejo", "Hámster", "Cuy", "Cerdo", "Caballo", "Pollo", "Vaca", "Otro" }));
        cmbAnimal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbAnimalActionPerformed(evt);
            }
        });

        cmbSizeStage.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Pequeño", "Mediano", "Grande", "Gatito", "Adulto", "Senior", "Juvenil" }));
        cmbSizeStage.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbSizeStageActionPerformed(evt);
            }
        });

        txtFlavor.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtFlavorFocusLost(evt);
            }
        });
        txtFlavor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtFlavorActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel6, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel8, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel7, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel9, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel10, javax.swing.GroupLayout.Alignment.TRAILING))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtMakeName)
                            .addComponent(cmbProductType, 0, 148, Short.MAX_VALUE)
                            .addComponent(cmbAnimal, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(txtOtherAnimal)
                            .addComponent(cmbSizeStage, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(txtFlavor)))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addComponent(jLabel5)))
                .addContainerGap(17, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(txtMakeName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel6)
                    .addComponent(cmbProductType, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(cmbAnimal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(txtOtherAnimal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(cmbSizeStage, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(txtFlavor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        jLabel11.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        jLabel11.setText("DATOS DE COMPRA");

        jLabel12.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        jLabel12.setText("Unidad:");

        jLabel13.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        jLabel13.setText("Cantidad:");

        jLabel15.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        jLabel15.setText("Costo total:");

        jLabel16.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        jLabel16.setText("Costo/lb:");

        jLabel17.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        jLabel17.setText("Costo/kg:");

        cmbPurchaseUnit.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Quintal", "Kilo", "Libra", "Docena", "Unidad" }));
        cmbPurchaseUnit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbPurchaseUnitActionPerformed(evt);
            }
        });

        txtTotalCost.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtTotalCostFocusLost(evt);
            }
        });
        txtTotalCost.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtTotalCostActionPerformed(evt);
            }
        });
        txtTotalCost.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtTotalCostKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtTotalCostKeyTyped(evt);
            }
        });

        txtCostPerPound.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCostPerPoundActionPerformed(evt);
            }
        });

        txtCostPerKg.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCostPerKgActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel12, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel13, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel15, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel16, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel17, javax.swing.GroupLayout.Alignment.TRAILING))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(cmbPurchaseUnit, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(txtTotalCost)
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtCostPerPound, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtCostPerKg, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(spinnerPurchaseQuantity, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(0, 0, Short.MAX_VALUE))))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addComponent(jLabel11)))
                .addContainerGap(45, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel11)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel12)
                    .addComponent(cmbPurchaseUnit, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel13)
                    .addComponent(spinnerPurchaseQuantity, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel15)
                    .addComponent(txtTotalCost, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel16)
                    .addComponent(txtCostPerPound, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel17)
                    .addComponent(txtCostPerKg, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(28, 28, 28))
        );

        btnBackMenu.setText("Regresar");
        btnBackMenu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBackMenuActionPerformed(evt);
            }
        });

        btnClearForm.setText("Limpiar");
        btnClearForm.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnClearFormActionPerformed(evt);
            }
        });

        btnSaveProduct.setText("Guardar");

        btnViewReport.setText("Ver reporte");

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnBackMenu)
                .addGap(48, 48, 48)
                .addComponent(btnViewReport)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnSaveProduct)
                .addGap(55, 55, 55)
                .addComponent(btnClearForm)
                .addGap(28, 28, 28))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnBackMenu)
                    .addComponent(btnClearForm)
                    .addComponent(btnSaveProduct)
                    .addComponent(btnViewReport))
                .addContainerGap(26, Short.MAX_VALUE))
        );

        jLabel14.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        jLabel14.setText("PRECIO DE VENTA");

        jLabel18.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        jLabel18.setText("Ganancia %:");

        jLabel19.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        jLabel19.setText("Precio sugerido:");

        jLabel20.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        jLabel20.setText("Unidad venta:");

        jLabel21.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        jLabel21.setText("Precio final con IVA:");

        txtSuggestedPrice.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtSuggestedPriceActionPerformed(evt);
            }
        });

        cmbSaleUnit.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Libra", "Kilo", "Unidad", "Docena" }));
        cmbSaleUnit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbSaleUnitActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGap(8, 8, 8)
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel20, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel18, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel19, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel21, javax.swing.GroupLayout.Alignment.TRAILING))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(cmbSaleUnit, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(txtFinalPrice, javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(txtSuggestedPrice, javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(spinnerProfitPercent, javax.swing.GroupLayout.Alignment.LEADING))))
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addComponent(jLabel14)))
                .addContainerGap(254, Short.MAX_VALUE))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel14)
                .addGap(12, 12, 12)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel20)
                    .addComponent(cmbSaleUnit, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel18)
                    .addComponent(spinnerProfitPercent, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel19)
                    .addComponent(txtSuggestedPrice, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel21)
                    .addComponent(txtFinalPrice, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        jMenu1.setText("File");
        jMenuBar1.add(jMenu1);

        jMenu2.setText("Edit");
        jMenuBar1.add(jMenu2);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnBackMenuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBackMenuActionPerformed

    FrmEmployee employeeMenu = new FrmEmployee();
    employeeMenu.setVisible(true);

    this.dispose();
        // TODO add your handling code here:
    }//GEN-LAST:event_btnBackMenuActionPerformed

    private void btnClearFormActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnClearFormActionPerformed

            int option = javax.swing.JOptionPane.showConfirmDialog(
            this,
            "¿Deseas borrar todos los campos?",
            "Confirmar limpieza",
            javax.swing.JOptionPane.YES_NO_OPTION
    );

    if (option != javax.swing.JOptionPane.YES_OPTION) return;

    txtProductId.setText("");
    txtMakeName.setText("");
    txtOtherAnimal.setText("");
    txtFlavor.setText("");
    txtTotalCost.setText("");
    txtCostPerKg.setText("");
    txtCostPerPound.setText("");
    txtSuggestedPrice.setText("");
    txtFinalPrice.setText("");
    txtStockCalculate.setText("");

    spinnerPurchaseQuantity.setValue(0);
    spinnerProfitPercent.setValue(0);

    cmbProductType.setSelectedIndex(0);
    cmbAnimal.setSelectedIndex(0);
    cmbSizeStage.setSelectedIndex(0);
    cmbPurchaseUnit.setSelectedIndex(0);
    cmbSaleUnit.setSelectedIndex(0);

    updateButtonsState();
        
    }//GEN-LAST:event_btnClearFormActionPerformed

    private void txtMakeNameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtMakeNameActionPerformed

    if (validateMakeName()) {
        cmbProductType.setEnabled(true);
        cmbProductType.requestFocus();
    }

    updateButtonsState();
    
    }//GEN-LAST:event_txtMakeNameActionPerformed

    private void txtFlavorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtFlavorActionPerformed

    if (validateFlavor()) {
        cmbPurchaseUnit.setEnabled(true);
        cmbPurchaseUnit.requestFocus();
    }        

    updateButtonsState();
    }//GEN-LAST:event_txtFlavorActionPerformed

    private void txtCostPerPoundActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCostPerPoundActionPerformed

        //tryCalculateCosts();
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCostPerPoundActionPerformed

    private void txtSuggestedPriceActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtSuggestedPriceActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtSuggestedPriceActionPerformed

    private void txtProductIdActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtProductIdActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtProductIdActionPerformed

    private void txtMakeNameFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtMakeNameFocusLost

    if (validateMakeName()) {
        cmbProductType.setEnabled(true);
        cmbProductType.requestFocus();
    }
        // TODO add your handling code here:
    }//GEN-LAST:event_txtMakeNameFocusLost

    private void cmbProductTypeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbProductTypeActionPerformed

        updateFieldsByProductType();
        updateFlavorAvailability();
        updateSaleUnitOptions();
        cmbAnimal.setEnabled(true);
        
        if (!txtProductId.getText().isEmpty()) {
            updateProductId();
        }
        
        updateButtonsState();
        
    }//GEN-LAST:event_cmbProductTypeActionPerformed

    private void cmbSizeStageActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbSizeStageActionPerformed

        if (!txtProductId.getText().isEmpty()) {
            updateProductId();
        }
        
        updateButtonsState();
        
    }//GEN-LAST:event_cmbSizeStageActionPerformed

    private void cmbAnimalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbAnimalActionPerformed
        
    updateOtherAnimalField();
    updateSizeStageOptions();

    if (!txtProductId.getText().isEmpty()) {
        updateProductId();
    }
    
    updateButtonsState();
    
    }//GEN-LAST:event_cmbAnimalActionPerformed

    private void cmbPurchaseUnitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbPurchaseUnitActionPerformed

    spinnerPurchaseQuantity.setEnabled(true);
    txtTotalCost.setEnabled(true);

    tryCalculateCosts();
    
    updateButtonsState();

    }//GEN-LAST:event_cmbPurchaseUnitActionPerformed

    private void txtTotalCostKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtTotalCostKeyTyped

    char c = evt.getKeyChar();

    // Permitir solo números y punto
    if (!Character.isDigit(c) && c != '.') {
        evt.consume();
    }

    // Permitir solo un punto decimal
    if (c == '.' && txtTotalCost.getText().contains(".")) {
        evt.consume();
    }

        // TODO add your handling code here:
    }//GEN-LAST:event_txtTotalCostKeyTyped

    private void txtTotalCostActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtTotalCostActionPerformed

    tryCalculateCosts();
    updateSaleUnitAfterCost();
    
    updateProductId();
    
    cmbSaleUnit.requestFocus();
    
    updateButtonsState();
    }//GEN-LAST:event_txtTotalCostActionPerformed

    private void txtTotalCostKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtTotalCostKeyReleased

    //tryCalculateCosts();
        // TODO add your handling code here:
    }//GEN-LAST:event_txtTotalCostKeyReleased

    private void txtTotalCostFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtTotalCostFocusLost

        tryCalculateCosts();
        // TODO add your handling code here:
    }//GEN-LAST:event_txtTotalCostFocusLost

    private void txtFlavorFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtFlavorFocusLost

    if (isFlavorValidSilently()) {
        cmbPurchaseUnit.setEnabled(true);
        cmbPurchaseUnit.requestFocus();
    }
        // TODO add your handling code here:
    }//GEN-LAST:event_txtFlavorFocusLost

    private void cmbSaleUnitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbSaleUnitActionPerformed

        updateButtonsState();
        
        updateSuggestedPrice();
    }//GEN-LAST:event_cmbSaleUnitActionPerformed

    private void txtCostPerKgActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCostPerKgActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCostPerKgActionPerformed

    private void txtStockCalculateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtStockCalculateActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtStockCalculateActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ReflectiveOperationException | javax.swing.UnsupportedLookAndFeelException ex) {
            logger.log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> new FrmAddProduct().setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnBackMenu;
    private javax.swing.JButton btnClearForm;
    private javax.swing.JButton btnSaveProduct;
    private javax.swing.JButton btnViewReport;
    private javax.swing.JComboBox<String> cmbAnimal;
    private javax.swing.JComboBox<String> cmbProductType;
    private javax.swing.JComboBox<String> cmbPurchaseUnit;
    private javax.swing.JComboBox<String> cmbSaleUnit;
    private javax.swing.JComboBox<String> cmbSizeStage;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JSpinner spinnerProfitPercent;
    private javax.swing.JSpinner spinnerPurchaseQuantity;
    private javax.swing.JTextField txtCostPerKg;
    private javax.swing.JTextField txtCostPerPound;
    private javax.swing.JTextField txtFinalPrice;
    private javax.swing.JTextField txtFlavor;
    private javax.swing.JTextField txtMakeName;
    private javax.swing.JTextField txtOtherAnimal;
    private javax.swing.JTextField txtProductId;
    private javax.swing.JTextField txtStockCalculate;
    private javax.swing.JTextField txtSuggestedPrice;
    private javax.swing.JTextField txtTotalCost;
    // End of variables declaration//GEN-END:variables
}
