package ec.edu.espe.petshopinventorycontrol.controller;

import java.util.Date;
import java.util.List;
import java.util.Map;

public final class StockFormController {

    private final StockService stockService;

    public StockFormController(StockService stockService) {
        this.stockService = stockService;
    }

    public void onInit(StockFormView view) {
        generateAndSetNewStockId(view, view.getNow());
        loadCategories(view);
        updateGainCalculation(view);
    }

    public void onNewRegister(StockFormView view) {
        view.clearStockFields(true);
    }

    public void onSaveRegister(StockFormView view) {
        updateGainCalculation(view);

        Map<String, String> errors = stockService.validateFields(
                view.getStockId(),
                view.getCategory(),
                view.getProductName(),
                view.getBrand(),
                view.getCostText(),
                view.getUnitEntryText(),
                view.getGainSelection(),
                view.getFinalPriceText(),
                view.getGainValueText()
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
            stockService.saveStock(
                    view.getStockId(),
                    view.getCategory(),
                    view.getProductName(),
                    view.getBrand(),
                    view.getCostText(),
                    view.getUnitCostText(),
                    view.getUnitEntryText(),
                    view.getGainSelection(),
                    view.getFinalPriceText(),
                    view.getGainValueText(),
                    view.getNow()
            );

            view.showMessage(
                    "Stock guardado correctamente.",
                    "Exito",
                    javax.swing.JOptionPane.INFORMATION_MESSAGE
            );

            view.clearStockFields(false);
            generateAndSetNewStockId(view, view.getNow());
        } catch (Exception ex) {
            view.showMessage(
                    "Error inesperado: " + ex.getMessage(),
                    "Error",
                    javax.swing.JOptionPane.ERROR_MESSAGE
            );
        }
    }

    public void onCategoryChanged(StockFormView view) {
        generateAndSetNewStockId(view, view.getNow());
        String category = view.getCategory();
        if (isBlank(category) || category.startsWith("No ")) {
            return;
        }
        try {
            List<String> names = stockService.getProductNamesByCategory(category);
            view.setNames(names);
            view.setBrands(List.of());
            view.setCostText("");
            view.setUnitCostText("");
            view.setFinalPriceText("");
            view.setGainValueText("");
        } catch (Exception ex) {
            view.showMessage(
                    "Error al cargar productos: " + ex.getMessage(),
                    "Error",
                    javax.swing.JOptionPane.ERROR_MESSAGE
            );
        }
    }

    public void onNameChanged(StockFormView view) {
        String category = view.getCategory();
        String name = view.getProductName();
        if (isBlank(category) || isBlank(name) || category.startsWith("No ")) {
            return;
        }
        try {
            List<String> brands = stockService.getBrandsByCategoryAndName(category, name);
            view.setBrands(brands);
            view.setCostText("");
            view.setUnitCostText("");
            view.setFinalPriceText("");
            view.setGainValueText("");
        } catch (Exception ex) {
            view.showMessage(
                    "Error al cargar marcas: " + ex.getMessage(),
                    "Error",
                    javax.swing.JOptionPane.ERROR_MESSAGE
            );
        }
    }

    public void onBrandChanged(StockFormView view) {
        String category = view.getCategory();
        String name = view.getProductName();
        String brand = view.getBrand();
        if (isBlank(category) || isBlank(name) || isBlank(brand) || category.startsWith("No ")) {
            return;
        }

        StockService.ProductCostResult cost = stockService.getCostForSelectionOrNull(category, name, brand);
        if (cost == null) {
            view.showMessage(
                    "Estos datos no coinciden con la Base de datos.",
                    "Validacion",
                    javax.swing.JOptionPane.WARNING_MESSAGE
            );
            view.setCostText("");
            view.setUnitCostText("");
            view.setFinalPriceText("");
            view.setGainValueText("");
            return;
        }

        view.setCostText(String.valueOf(cost.cost));
        view.setUnitCostText(String.valueOf(cost.unitCost));
        updateGainCalculation(view);
    }

    public void onGainChanged(StockFormView view) {
        updateGainCalculation(view);
    }

    public void onUnitCostChanged(StockFormView view) {
        updateGainCalculation(view);
    }

    private void loadCategories(StockFormView view) {
        try {
            List<String> categories = stockService.getCategoriesFromProducts();
            if (categories.isEmpty()) {
                view.setCategories(List.of("No hay categorias"));
            } else {
                view.setCategories(categories);
            }
            view.setNames(List.of());
            view.setBrands(List.of());
            view.setCostText("");
            view.setUnitCostText("");
            view.setFinalPriceText("");
            view.setGainValueText("");
        } catch (Exception ex) {
            view.showMessage(
                    "MongoDB no esta disponible. No se pueden cargar categorias.\n" + ex.getMessage(),
                    "Error de conexion MongoDB",
                    javax.swing.JOptionPane.WARNING_MESSAGE
            );
        }
    }

    private void updateGainCalculation(StockFormView view) {
        String costText = view.getCostText();
        String unitText = view.getUnitCostText();
        String gainSel = view.getGainSelection();

        if (isBlank(costText) || isBlank(unitText) || isBlank(gainSel)
                || gainSel.trim().equals("%") || gainSel.trim().isEmpty()) {
            view.setFinalPriceText("");
            view.setGainValueText("");
            return;
        }

        try {
            double costTotal = Double.parseDouble(costText.trim());
            double unitPrice = Double.parseDouble(unitText.trim());
            double income = unitPrice * 100.0;
            double profit = income - costTotal;

            view.setFinalPriceText(String.format(java.util.Locale.US, "%.2f", income));
            view.setGainValueText(String.format(java.util.Locale.US, "%.2f", profit));
        } catch (Exception ex) {
            view.setFinalPriceText("");
            view.setGainValueText("");
        }
    }

    private void generateAndSetNewStockId(StockFormView view, Date now) {
        String nextId = stockService.generateNextStockId(now, view.getCategory());
        view.setStockId(nextId);
    }

    private boolean isBlank(String v) {
        return v == null || v.trim().isEmpty();
    }
}
