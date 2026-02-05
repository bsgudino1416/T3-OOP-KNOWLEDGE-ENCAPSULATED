package ec.edu.espe.petshopinventorycontrol.controller;

import java.util.List;
import javax.swing.Icon;
import javax.swing.table.TableModel;

public interface GraphicPersonalView {

    String getFilterField1();

    String getFilterValue1();

    String getFilterField2();

    String getFilterValue2();

    String getChartType();

    String getChartField();

    int getChartWidth();

    int getChartHeight();

    void setFilterFieldOptions(List<String> options);

    void setFilterValueOptions1(List<String> options);

    void setFilterValueOptions2(List<String> options);

    void setChartTypeOptions(List<String> options);

    void setChartFieldOptions(List<String> options);

    void setTableModel(TableModel model);

    void setChartIcon(Icon icon);

    void setChartMessage(String message);

    void showMessage(String message, String title, int messageType);
}
