package ec.edu.espe.petshopinventorycontrol.model;

import java.util.List;
public abstract class ReportGenerator {

   
    public final void generateReport(List<Product> products) {
        printHeader();
        printBody(products);
        printFooter(products.size());
    }

   
    protected abstract void printHeader();
    protected abstract void printBody(List<Product> products);
    protected abstract void printFooter(int totalItems);
    
   
    protected String formatProduct(Product p) {
        return String.format("[%s] %s - Stock: %d - $%.2f", p.getId(), p.getName(), p.getStock(), p.getPrice());
    }
}