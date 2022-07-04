package budget;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Comparator;
import java.util.Locale;

public class Product implements Comparable<Product> {
    private String name;
    private double price;
    private PurchaseType type;

    private final DecimalFormat decimalFormat;

    public Product(String name, double price, PurchaseType type) {
        this.name = name;
        this.price = price;
        this.type = type;

        DecimalFormatSymbols otherSymbols = new DecimalFormatSymbols(Locale.getDefault());
        otherSymbols.setDecimalSeparator('.');
        decimalFormat = new DecimalFormat("0.00", otherSymbols);
    }

    public double getPrice() {
        return price;
    }

    public String getName() {
        return name;
    }

    public void printProduct() {
        System.out.printf(
                "%s $%s\n",
                this.name,
                decimalFormat.format(this.price)
        );
    }

    @Override
    public int compareTo(Product otherProduct) {
        return Double.compare(getPrice(), otherProduct.getPrice());
    }
}
