package entity;

public class Medicine implements Comparable<Medicine> {

    private String code;
    private String name;
    private int quantity;

    public Medicine(String code, String name, int quantity) {
        this.code = code;
        this.name = name;
        this.quantity = quantity;
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public int compareTo(Medicine other) {
        return this.code.compareTo(other.code);
    }

    public String toString() {
        return String.format("Code: %s | Name: %s | Quantity: %d", code, name, quantity);
    }

    // Convert to a line for file storage (CSV)
    public String toFileString() {
        return String.join(",", code, name, String.valueOf(quantity));
    }
    // Parse from a file line
    public static Medicine fromFileString(String line) {
        String[] parts = line.split(",", 3);
        return new Medicine(parts[0], parts[1], Integer.parseInt(parts[2]));
    }
}
