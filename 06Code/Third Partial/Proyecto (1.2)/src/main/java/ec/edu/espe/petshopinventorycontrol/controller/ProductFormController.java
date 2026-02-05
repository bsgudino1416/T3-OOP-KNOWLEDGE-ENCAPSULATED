package ec.edu.espe.petshopinventorycontrol.controller;

import java.util.Date;
import java.util.List;
import java.util.Map;

public final class ProductFormController {

    private final SupplierService supplierService;
    private final ProductService productService;
    private boolean lastSaveSuccessful;

    public ProductFormController(SupplierService supplierService, ProductService productService) {
        this.supplierService = supplierService;
        this.productService = productService;
    }

    public void onInit(ProductFormView view, Date now) {
        updateExpiryStateByType(view);
        updatePoundsStateByUnit(view);
        generateAndSetNewProductId(view, now);
        loadSuppliers(view);
        updateCalculations(view);
    }

    public void onNewRegister(ProductFormView view, Date now) {
        if (!lastSaveSuccessful) {
            view.clearProductFields(true);
            view.showMessage(
                    "El registro actual no se guardo.\nCompleta y guarda antes de generar un nuevo ID.",
                    "Informacion",
                    javax.swing.JOptionPane.INFORMATION_MESSAGE
            );
            return;
        }

        view.clearProductFields(true);
        generateAndSetNewProductId(view, now);
        lastSaveSuccessful = false;
    }

    public void onSaveRegister(ProductFormView view, Date now) {
        Map<String, String> errors = productService.validateFields(
                view.getProductId(),
                view.getSupplier(),
                view.getProductName(),
                view.getTypeProduct(),
                view.getAnimalType(),
                view.getBrand(),
                view.getCostText(),
                view.getUnit(),
                view.getQuantityValue(),
                view.getInvestmentText(),
                view.getPoundsText(),
                view.getTotalPoundsText(),
                view.getEntryDate(),
                isExpiryRequired(view.getTypeProduct()) ? view.getExpiryDate() : null
        );

        view.applyErrors(errors);
        if (!errors.isEmpty()) {
            view.showMessage(
                    "Todos los campos deben estar completos.",
                    "Error de validacion",
                    javax.swing.JOptionPane.WARNING_MESSAGE
            );
            return;
        }

        try {
            productService.saveProduct(
                    view.getProductId(),
                    view.getSupplier(),
                    view.getProductName(),
                    view.getTypeProduct(),
                    view.getAnimalType(),
                    view.getBrand(),
                    view.getCostText(),
                    view.getUnit(),
                    (Number) view.getQuantityValue(),
                    view.getInvestmentText(),
                    view.getPoundsText(),
                    view.getTotalPoundsText(),
                    view.getEntryDate(),
                    isExpiryRequired(view.getTypeProduct()) ? view.getExpiryDate() : null
            );

            view.showMessage(
                    "Producto guardado correctamente.",
                    "Exito",
                    javax.swing.JOptionPane.INFORMATION_MESSAGE
            );

            lastSaveSuccessful = true;
            view.clearProductFields(false);
            generateAndSetNewProductId(view, now);
        } catch (Exception ex) {
            view.showMessage(
                    "Error inesperado: " + ex.getMessage(),
                    "Error",
                    javax.swing.JOptionPane.ERROR_MESSAGE
            );
        }
    }

    public void onTypeChanged(ProductFormView view) {
        updateExpiryStateByType(view);
        generateAndSetNewProductId(view, new Date());
    }

    public void onUnitChanged(ProductFormView view) {
        updatePoundsStateByUnit(view);
        updateCalculations(view);
    }

    public void onRecalculate(ProductFormView view) {
        updateCalculations(view);
    }

    private void updateExpiryStateByType(ProductFormView view) {
        boolean required = isExpiryRequired(view.getTypeProduct());
        view.setExpiryEnabled(required);
        if (!required) {
            view.clearExpiryDate();
        }
    }

    private void updatePoundsStateByUnit(ProductFormView view) {
        boolean isPounds = isPoundsRequired(view.getUnit());
        view.setPoundsEnabled(isPounds);
        if (!isPounds) {
            view.clearPoundsFields();
        }
    }

    private void updateCalculations(ProductFormView view) {
        view.setInvestmentText(calculateInvestment(view.getCostText(), view.getQuantityValue()));
        view.setTotalPoundsText(calculateTotalPounds(view.getPoundsText(), view.getQuantityValue(), view.getUnit()));
    }

    private String calculateInvestment(String costText, Object qtyValue) {
        Double qty = toDouble(qtyValue);
        if (qty == null) {
            return "";
        }
        try {
            double cost = Double.parseDouble(costText.trim());
            return String.valueOf(cost * qty);
        } catch (Exception ex) {
            return "";
        }
    }

    private String calculateTotalPounds(String poundsText, Object qtyValue, String unit) {
        if (!isPoundsRequired(unit)) {
            return "";
        }
        Double qty = toDouble(qtyValue);
        if (qty == null) {
            return "";
        }
        try {
            double pounds = Double.parseDouble(poundsText.trim());
            return String.valueOf(pounds * qty);
        } catch (Exception ex) {
            return "";
        }
    }

    private Double toDouble(Object value) {
        if (value instanceof Number) {
            return ((Number) value).doubleValue();
        }
        return null;
    }

    private void generateAndSetNewProductId(ProductFormView view, Date now) {
        try {
            String nextId = productService.generateNextProductId(
                    now,
                    view.getTypeProduct(),
                    view.getAnimalType()
            );
            view.setProductId(nextId);
        } catch (Exception ex) {
            String prefix = productService.buildProductIdPrefix(
                    now,
                    view.getTypeProduct(),
                    view.getAnimalType()
            );
            String fallback = prefix + "-001";
            view.setProductId(fallback);
            view.showMessage(
                    "MongoDB no esta disponible.\nUsando ID alterno: " + fallback,
                    "Error de conexion MongoDB",
                    javax.swing.JOptionPane.WARNING_MESSAGE
            );
        }
    }

    private void loadSuppliers(ProductFormView view) {
        try {
            List<String> enterprises = supplierService.getEnterpriseOptions();
            if (enterprises.isEmpty()) {
                view.setSupplierOptions(List.of("No hay proveedores"));
            } else {
                view.setSupplierOptions(enterprises);
            }
        } catch (Exception ex) {
            view.setSupplierOptions(List.of("MongoDB no disponible"));
            view.showMessage(
                    "MongoDB no esta disponible. No se puede cargar la lista de proveedores.\n" + ex.getMessage(),
                    "Error de conexion MongoDB",
                    javax.swing.JOptionPane.WARNING_MESSAGE
            );
        }
    }

    private boolean isExpiryRequired(String typeProduct) {
        if (typeProduct == null) {
            return false;
        }
        String t = typeProduct.trim().toUpperCase();
        return t.equals("COMIDA") || t.equals("MEDICINA");
    }

    private boolean isPoundsRequired(String unit) {
        if (unit == null) {
            return false;
        }
        return unit.trim().equalsIgnoreCase("Libras");
    }
}
