package budget;

public enum PurchaseType {
    FOOD(1),
    CLOTHES(2),
    ENTERTAINMENT(3),
    OTHER(4);

    int number;
    String typeName;

    PurchaseType(int number) {
        this.number = number;
        switch (number) {
            case 1 -> this.typeName = "Food";
            case 2 -> this.typeName = "Clothes";
            case 3 -> this.typeName = "Entertainment";
            case 4 -> this.typeName = "Other";
            default -> {
            }
        }
    }

    public static PurchaseType findByNumber(int number) {
        PurchaseType type = null;
        switch (number) {
            case 1 -> type = PurchaseType.FOOD;
            case 2 -> type = PurchaseType.CLOTHES;
            case 3 -> type = PurchaseType.ENTERTAINMENT;
            case 4 -> type = PurchaseType.OTHER;
            default -> {
            }
        }
        return type;
    }

    public static PurchaseType findByName(String name) {
        PurchaseType type = null;
        switch (name) {
            case "Food" -> type = PurchaseType.FOOD;
            case "Clothes" -> type = PurchaseType.CLOTHES;
            case "Entertainment" -> type = PurchaseType.ENTERTAINMENT;
            case "Other" -> type = PurchaseType.OTHER;
            default -> {
            }
        }
        return type;
    }

    public int getNumber() {
        return number;
    }

    public String getTypeName() {
        return typeName;
    }
}
