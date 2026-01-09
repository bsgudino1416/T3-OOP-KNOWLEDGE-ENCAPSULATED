package ec.edu.espe.petshopinventorycontrol.utils;

public class UnitConversionUtil {

    private UnitConversionUtil() {}

    public static double calculateStock(
            String saleUnit,
            String purchaseUnit,
            int quantity
    ) {

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

        return stock;
    }
}
