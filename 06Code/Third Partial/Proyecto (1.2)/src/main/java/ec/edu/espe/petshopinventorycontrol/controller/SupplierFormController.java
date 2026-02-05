package ec.edu.espe.petshopinventorycontrol.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

public final class SupplierFormController {

    private final SupplierService supplierService;

    public SupplierFormController(SupplierService supplierService) {
        this.supplierService = supplierService;
    }

    public void onInit(SupplierFormView view, Date now) {
        generateAndSetNewSupplierId(view, now);
    }

    public void onNewRegister(SupplierFormView view, Date now) {
        view.clearSupplierFields();
        generateAndSetNewSupplierId(view, now);
    }

    public void onSaveRegister(SupplierFormView view, Date now) {
        Map<String, String> errors = supplierService.validateFields(
                view.getTypeSupplier(),
                view.getPhoneSupplier(),
                view.getPhone2Supplier(),
                view.getNameSupplier(),
                view.getCitySupplier(),
                view.getStateSupplier(),
                view.getEnterpriseSupplier(),
                view.getEmailSupplier(),
                view.getEntryDate()
        );

        view.applyErrors(errors);
        if (!errors.isEmpty()) {
            view.showMessage(
                    "Completa todos los campos requeridos.",
                    "Error de validacion",
                    javax.swing.JOptionPane.WARNING_MESSAGE
            );
            return;
        }

        try {
            supplierService.saveSupplier(
                    view.getSupplierId(),
                    view.getTypeSupplier(),
                    view.getPhoneSupplier(),
                    view.getPhone2Supplier(),
                    view.getNameSupplier(),
                    view.getCitySupplier(),
                    view.getStateSupplier(),
                    view.getEnterpriseSupplier(),
                    view.getEmailSupplier(),
                    view.getEntryDate()
            );

            view.showMessage(
                    "Proveedor guardado correctamente.",
                    "Exito",
                    javax.swing.JOptionPane.INFORMATION_MESSAGE
            );

            view.clearSupplierFields();
            generateAndSetNewSupplierId(view, now);
        } catch (Exception ex) {
            view.showMessage(
                    "Error inesperado: " + ex.getMessage(),
                    "Error",
                    javax.swing.JOptionPane.ERROR_MESSAGE
            );
        }
    }

    public void onDuplicateRegister(SupplierFormView view, Date now) {
        Map<String, String> errors = supplierService.validateFields(
                view.getTypeSupplier(),
                view.getPhoneSupplier(),
                view.getPhone2Supplier(),
                view.getNameSupplier(),
                view.getCitySupplier(),
                view.getStateSupplier(),
                view.getEnterpriseSupplier(),
                view.getEmailSupplier(),
                view.getEntryDate()
        );

        view.applyErrors(errors);
        if (!errors.isEmpty()) {
            view.showMessage(
                    "No se puede duplicar. Completa todos los campos requeridos primero.",
                    "Error de validacion",
                    javax.swing.JOptionPane.WARNING_MESSAGE
            );
            return;
        }

        String oldId = view.getSupplierId();
        String newId = supplierService.generateNextSupplierId(now);
        view.setSupplierId(newId);

        view.showMessage(
                "Registro Duplicado exitosamente.\nNuevo ID: " + newId + "\nID anterior: " + oldId,
                "Duplicado",
                javax.swing.JOptionPane.INFORMATION_MESSAGE
        );
    }

    private void generateAndSetNewSupplierId(SupplierFormView view, Date now) {
        try {
            String nextId = supplierService.generateNextSupplierId(now);
            view.setSupplierId(nextId);
        } catch (Exception ex) {
            String prefix = new SimpleDateFormat("ddMMyy").format(now);
            String fallback = prefix + "-001";
            view.setSupplierId(fallback);
            view.showMessage(
                    "MongoDB no esta disponible.\nUsando ID alterno: " + fallback
                            + "\nVerifica la conexion a MongoDB.",
                    "Error de conexion MongoDB",
                    javax.swing.JOptionPane.WARNING_MESSAGE
            );
        }
    }
}
