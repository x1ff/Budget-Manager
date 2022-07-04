package budget;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.*;

public class Budget {
    HashMap<String, ArrayList<Product>> map;
    private double balance;
    final private String FILE_NAME = "purchases.txt";
    final private String SEPARATOR = "----------";
    private final DecimalFormat decimalFormat;


    public Budget() {
        this.map = new HashMap<>();
        this.map.put("Food", new ArrayList<>());
        this.map.put("Clothes", new ArrayList<>());
        this.map.put("Entertainment", new ArrayList<>());
        this.map.put("Other", new ArrayList<>());
        this.balance = 0;

        DecimalFormatSymbols otherSymbols = new DecimalFormatSymbols(Locale.getDefault());
        otherSymbols.setDecimalSeparator('.');
        decimalFormat = new DecimalFormat("0.00", otherSymbols);
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public double getBalance() {
        return balance;
    }

    public void printMap() {
        if (getMapSize() == 0) {
            System.out.println("The purchase list is empty!");
            return;
        }
        System.out.println("\nAll:");
        for (ArrayList<Product> list : map.values()) {
            for (Product product : list) {
                product.printProduct();
            }
        }
        System.out.printf(
                "Total sum: $%s\n",
                this.decimalFormat.format(getTotal())
        );
    }

    public int getMapSize() {
        int sum = 0;
        for (ArrayList<Product> list : map.values()) {
            sum += list.size();
        }
        return sum;
    }

    private double getTotal() {
        double sum = 0;
        for (ArrayList<Product> list : map.values()) {
            for (Product product : list) {
                sum += product.getPrice();
            }
        }
        return sum;
    }

    private double getTotalByType(PurchaseType type) {
        double sum = 0;
        for (Product product : this.map.get(type.typeName)) {
            sum += product.getPrice();
        }
        return sum;
    }

    public void addProduct(String name, double price, PurchaseType type) {
        this.map.get(type.typeName).add(new Product(name, price, type));
    }

    public void printPurchasesByType(PurchaseType type) {
        System.out.printf("%s:\n", type.typeName);
        if (this.map.get(type.typeName).size() == 0) {
            System.out.println("The purchase list is empty!");
        } else {
            for (Product product : this.map.get(type.typeName)) {
                product.printProduct();
            }
            System.out.printf(
                    "Total sum: $%s\n",
                    this.decimalFormat.format(getTotalByType(type))
            );
        }
    }

    public void printBalance() {
        System.out.printf("Balance: $%s\n", this.decimalFormat.format(this.balance));
    }

    public void save() throws IOException {
        File file = new File(FILE_NAME);
        boolean isCreated = file.createNewFile();
        FileWriter writer = new FileWriter(file);
        writeBalance(writer);
        writeByType(writer, PurchaseType.FOOD);
        writeByType(writer, PurchaseType.CLOTHES);
        writeByType(writer, PurchaseType.ENTERTAINMENT);
        writeByType(writer, PurchaseType.OTHER);
        writer.close();
    }

    private void writeBalance(FileWriter writer) throws IOException {
        writer.write(this.balance + "\n");
    }

    private void writeByType(FileWriter writer, PurchaseType type) throws IOException {
        writer.write(String.format("%s\n", type.getTypeName()));
        for (Product product : this.map.get(type.getTypeName())) {
            writer.write(String.format(
                    "%s :=%s\n",
                    product.getName(),
                    this.decimalFormat.format(product.getPrice())
            ));
        }
        writer.write(SEPARATOR + "\n");
    }

    public void load() throws FileNotFoundException {
        File file = new File(FILE_NAME);
        Scanner fileScanner = new Scanner(file);
        this.balance = Double.parseDouble(fileScanner.nextLine());
        while (fileScanner.hasNext()) {
            String currentType = fileScanner.nextLine();
            String currentLine = currentType;
            while (!Objects.equals(currentLine, SEPARATOR)) {
                currentLine = fileScanner.nextLine();
                if (currentLine.equals(SEPARATOR)) {
                    break;
                }
                this.map.get(currentType).add(new Product(
                        currentLine.split(" :=")[0],
                        Double.parseDouble(currentLine.split(" :=")[1]),
                        PurchaseType.findByName(currentType)
                ));
            }
        }
    }

    public void analyze(Scanner sc) {
        final String LEGEND = """
                1) Sort all purchases
                2) Sort by type
                3) Sort certain type
                4) Back
                """;
        int currentCommandNumber = 0;
        while (currentCommandNumber != 4) {
            System.out.println("\nHow do you want to sort?");
            System.out.println(LEGEND);
            currentCommandNumber = Integer.parseInt(sc.nextLine());
            switch (currentCommandNumber) {
                case 1 -> sortAllPurchases();
                case 2 -> sortByType();
                case 3 -> sortCertainType(sc);
                default -> {
                }
            }
        }
    }

    private void sortAllPurchases() {
        if (getTotal() == 0) {
            System.out.println("\nThe purchase list is empty!");
            return;
        }
        ArrayList<Product> allProducts = new ArrayList<>();
        allProducts.addAll(this.map.get("Food"));
        allProducts.addAll(this.map.get("Clothes"));
        allProducts.addAll(this.map.get("Entertainment"));
        allProducts.addAll(this.map.get("Other"));
        allProducts.sort(Collections.reverseOrder());
        System.out.println("All:");
        for (Product product : allProducts) {
            product.printProduct();
        }
        System.out.printf(
                "Total: $%s\n",
                this.decimalFormat.format(getTotal())
        );
    }

    private void sortByType() {
        System.out.println("\nTypes:");
        System.out.printf(
                "Food - $%s\n",
                this.decimalFormat.format(getTotalByType(PurchaseType.FOOD))
        );
        System.out.printf(
                "Entertainment - $%s\n",
                this.decimalFormat.format(getTotalByType(PurchaseType.ENTERTAINMENT))
        );
        System.out.printf(
                "Clothes - $%s\n",
                this.decimalFormat.format(getTotalByType(PurchaseType.CLOTHES))
        );
        System.out.printf(
                "Other - $%s\n",
                this.decimalFormat.format(getTotalByType(PurchaseType.OTHER))
        );
        System.out.printf(
                "Total sum: - $%s\n",
                this.decimalFormat.format(getTotal())
        );
    }

    private void sortCertainType(Scanner sc) {
        String LEGEND = """
                                
                Choose the type of purchase
                1) Food
                2) Clothes
                3) Entertainment
                4) Other
                """;
        System.out.println(LEGEND);
        printSortedPurchasesByType(PurchaseType.findByNumber(Integer.parseInt(sc.nextLine())));
    }

    private void printSortedPurchasesByType(PurchaseType type) {
        if (getTotalByType(type) == 0) {
            System.out.println("\nThe purchase list is empty!");
            return;
        }
        ArrayList<Product> products = this.map.get(type.typeName);
        products.sort(Collections.reverseOrder());
        printPurchasesByType(type);
    }
}
