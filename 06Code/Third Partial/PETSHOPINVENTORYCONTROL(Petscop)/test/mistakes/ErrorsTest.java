import static org.junit.jupiter.api.Assertions.*;

import ec.edu.espe.petshopinventorycontrol.controller.ProductValidator;
import ec.edu.espe.petshopinventorycontrol.controller.StockFormController;
import ec.edu.espe.petshopinventorycontrol.controller.StockFormView;
import ec.edu.espe.petshopinventorycontrol.model.Employee;
import ec.edu.espe.petshopinventorycontrol.model.Order;
import ec.edu.espe.petshopinventorycontrol.model.OrderDetail;
import ec.edu.espe.petshopinventorycontrol.model.Product;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Date;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Test;

public class ErroresTest {

    @Test
    void orderConstructorIgnoresProvidedId() {
        Order order = new Order(42, null, null, new Date());
        assertTrue(order.toString().contains("Pedido #1"));
    }

    @Test
    void employeeAltConstructorThrowsUnsupported() {
        assertThrows(UnsupportedOperationException.class,
                () -> newEmployeeViaAltCtor(1, "Luis", "clave123", "vendedor"));
    }

    @Test
    void employeeValidatePasswordThrowsUnsupported() {
        Employee employee = new Employee(1, "Name", "Role", "user", "secret");
        assertThrows(UnsupportedOperationException.class, () -> employee.validatePassword("secret"));
    }

    @Test
    void stockFormControllerUsesWrongGainFormula() {
        StockFormController controller = new StockFormController(null);
        FakeStockFormView view = new FakeStockFormView();
        view.setCostText("100");
        view.setUnitCostText("10");
        view.setGainSelection("20%");

        controller.onGainChanged(view);

        assertEquals("1000.00", view.getFinalPriceText());
        assertEquals("900.00", view.getGainValueText());
    }

    @Test
    void orderDetailAllowsNegativeQuantity() {
        Product product = new Product("P1", "Dog Food", 10.0, 5, "FOOD", "DOG", "M", "Brand");
        OrderDetail detail = new OrderDetail(product, -2);
        assertEquals(-20.0, detail.getSubtotal(), 0.0001);
    }

    @Test
    void productValidatorAcceptsNegativeCost() {
        ProductValidator validator = new ProductValidator();
        Map<String, String> errors = validator.validate(
                "ID1",
                "Supplier",
                "Product",
                "COMIDA",
                "PERRO",
                "Brand",
                "-5",
                "Unidad",
                5,
                "10",
                "",
                "",
                new Date(),
                new Date()
        );

        assertFalse(errors.containsKey("txtCostProduct"));
    }

    private static Employee newEmployeeViaAltCtor(int id, String name, String password, String role) {
        try {
            Constructor<Employee> ctor = Employee.class.getDeclaredConstructor(
                    int.class, String.class, String.class, String.class);
            ctor.setAccessible(true);
            return ctor.newInstance(id, name, password, role);
        } catch (InvocationTargetException ex) {
            Throwable cause = ex.getCause();
            if (cause instanceof RuntimeException re) {
                throw re;
            }
            throw new RuntimeException(cause);
        } catch (ReflectiveOperationException ex) {
            throw new RuntimeException(ex);
        }
    }

    private static final class FakeStockFormView implements StockFormView {
        private String stockId;
        private String category;
        private String productName;
        private String brand;
        private String costText;
        private String unitCostText;
        private String unitEntryText;
        private String gainSelection;
        private String finalPriceText;
        private String gainValueText;
        private final Date now = new Date();

        @Override
        public String getStockId() {
            return stockId;
        }

        @Override
        public String getCategory() {
            return category;
        }

        @Override
        public String getProductName() {
            return productName;
        }

        @Override
        public String getBrand() {
            return brand;
        }

        @Override
        public String getCostText() {
            return costText;
        }

        @Override
        public String getUnitCostText() {
            return unitCostText;
        }

        @Override
        public String getUnitEntryText() {
            return unitEntryText;
        }

        @Override
        public String getGainSelection() {
            return gainSelection;
        }

        @Override
        public String getFinalPriceText() {
            return finalPriceText;
        }

        @Override
        public String getGainValueText() {
            return gainValueText;
        }

        @Override
        public void setStockId(String id) {
            this.stockId = id;
        }

        @Override
        public void setCategories(List<String> categories) {
        }

        @Override
        public void setNames(List<String> names) {
        }

        @Override
        public void setBrands(List<String> brands) {
        }

        @Override
        public void setCostText(String value) {
            this.costText = value;
        }

        @Override
        public void setUnitCostText(String value) {
            this.unitCostText = value;
        }

        @Override
        public void setFinalPriceText(String value) {
            this.finalPriceText = value;
        }

        @Override
        public void setGainValueText(String value) {
            this.gainValueText = value;
        }

        @Override
        public void applyErrors(Map<String, String> errors) {
        }

        @Override
        public void clearStockFields(boolean keepId) {
        }

        @Override
        public void showMessage(String message, String title, int messageType) {
        }

        @Override
        public Date getNow() {
            return now;
        }

        void setGainSelection(String value) {
            this.gainSelection = value;
        }
    }
}
