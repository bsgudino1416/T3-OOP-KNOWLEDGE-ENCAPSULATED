package ec.edu.espe.petshopinventorycontrol.controller;

import ec.edu.espe.petshopinventorycontrol.model.ExpiredProductRecord;
import java.util.List;
import javax.swing.table.TableModel;

public interface ExpiredProductsView {

    void setTableModel(TableModel model, List<ExpiredProductRecord> records);

    void showMessage(String message, String title, int messageType);
}
