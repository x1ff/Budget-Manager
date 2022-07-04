package budget;

import java.io.*;
import java.util.Scanner;

public class MenuLevel1 {

    private int currentActionNumber;
    private Budget budget;

    public MenuLevel1() {
        this.currentActionNumber = -1;
        this.budget = new Budget();
    }

    public void chooseAction(Scanner sc) {
        // Java 15 feature text block
        String LEGEND = """
                                
                Choose your action:
                1) Add income
                2) Add purchase
                3) Show list of purchases
                4) Balance
                5) Save
                6) Load
                7) Analyze (Sort)
                0) Exit
                """;
        System.out.println(LEGEND);
        currentActionNumber = Integer.parseInt(sc.nextLine());
    }

    public int getCurrentActionNumber() {
        return currentActionNumber;
    }

    public void doAction(int actionNumber, Scanner sc) throws IOException {
        switch (actionNumber) {
            case 1:
                addIncome(sc);
                break;
            case 2:
                addPurchase(sc);
                break;
            case 3:
                showListOfPurchases(sc);
                break;
            case 4:
                printBalance();
                break;
            case 5:
                saveToFile();
                break;
            case 6:
                loadFromFile();
                break;
            case 7:
                analyze(sc);
                break;
            case 0:
                System.out.println("Bye!");
            default:
                break;
        }
    }

    /**
     * Method increase balance, like salary
     *
     * @param sc
     */
    private void addIncome(Scanner sc) {
        System.out.println("Enter income:");
        this.budget.setBalance(
                this.budget.getBalance() + Double.parseDouble(sc.nextLine())
        );
        System.out.println("Income was added!");
    }

    private void addPurchase(Scanner sc) {
        final String LEGEND =
                """
                        1) Food
                        2) Clothes
                        3) Entertainment
                        4) Other
                        5) Back
                        """;
        int input = 0;
        while (true) {
            System.out.println("\nChoose the type of purchase");
            System.out.println(LEGEND);
            input = Integer.parseInt(sc.nextLine());
            if (input == 5) {
                return;
            }
            PurchaseType type = PurchaseType.findByNumber(input);
            System.out.println("\nEnter purchase name:");
            String name = sc.nextLine();
            System.out.println("Enter its price:");
            double price = Double.parseDouble(sc.nextLine());
            this.budget.addProduct(name, price, type);
            this.budget.setBalance(this.budget.getBalance() - price);
            System.out.println("Purchase was added!");
        }
    }

    private void showListOfPurchases(Scanner sc) {
        final String LEGEND =
                """
                        1) Food
                        2) Clothes
                        3) Entertainment
                        4) Other
                        5) All
                        6) Back
                        """;

        int input = 0;
        if (this.budget.getMapSize() == 0) {
            System.out.println("\nThe purchase list is empty!");
            return;
        }
        while (input != 6) {
            System.out.println("\nChoose the type of purchases");
            System.out.println(LEGEND);
            input = Integer.parseInt(sc.nextLine());
            if (input < 5) {
                this.budget.printPurchasesByType(PurchaseType.findByNumber(input));
            } else if (input == 5) {
                this.budget.printMap();
            }
        }
    }

    private void printBalance() {
        this.budget.printBalance();
    }

    private void saveToFile() throws IOException {
        this.budget.save();
        System.out.println("Purchases were saved!");
    }

    private void loadFromFile() throws FileNotFoundException {
        this.budget.load();
        System.out.println("\nPurchases were loaded!");
    }

    private void analyze(Scanner sc) {
        budget.analyze(sc);
    }
}
