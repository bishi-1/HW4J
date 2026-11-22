package PG;
import java.util.Locale;

public class Product {

    private int id;
    private String name;
    private double price;
    private int stock;
    private static int nextId = 1000;

    public Product(String name, double price, int stock) {
        this.id = nextId;
        nextId++;

        this.name = "Unknown";
        this.price = 1.0;
        this.stock = 0;

        setName(name);
        setPrice(price);
        setStock(stock);
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public int getStock() {
        return stock;
    }

    public boolean setName(String name) {
        if (name == null) {
            return false;
        }
        String trimmed = name.trim();
        if (trimmed.isEmpty()) {
            return false;
        }
        this.name = trimmed;
        return true;
    }

    public boolean setPrice(double price) {
        if (price > 0) {
            this.price = price;
            return true;
        }
        return false;
    }

    public boolean setStock(int stock) {
        if (stock >= 0) {
            this.stock = stock;
            return true;
        }
        return false;
    }

    public boolean sell(int quantity) {
        if (quantity <= 0 || quantity > stock) {
            return false;
        }
        stock -= quantity;
        return true;
    }

    public String getInfo() {
        return String.format(Locale.US, "ID: %d | Name: %s | Price: %.2f | Stock: %d",
                id, name, price, stock);
    }

    public static int getNextId() {
        return nextId;
    }
}