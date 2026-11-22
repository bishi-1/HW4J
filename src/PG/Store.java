package PG;
public class Store {

    private String storeName;
    private Product[] products;
    private int[] soldCount;
    private int productCount;

    public Store(String storeName, int capacity) {
        this.storeName = storeName;
        this.products = new Product[capacity];
        this.soldCount = new int[capacity];
        this.productCount = 0;
    }

    public boolean addProduct(Product product) {
        if (product == null) {
            return false;
        }
        if (productCount == products.length) {
            return false;
        }
        if (findProductIndex(product.getId()) != -1) {
            return false;
        }
        products[productCount] = product;
        productCount++;
        return true;
    }

    public int findProductIndex(int id) {
        int index = -1;
        int i = 0;
        while (i < productCount) {
            if (products[i].getId() == id) {
                index = i;
                break;
            }
            i++;
        }
        return index;
    }

    public boolean sellProduct(int id, int quantity) {
        int index = findProductIndex(id);
        if (index == -1) {
            return false;
        }
        boolean sold = products[index].sell(quantity);
        if (sold) {
            soldCount[index] += quantity;
        }
        return sold;
    }

    public void printAvailableProducts() {
        System.out.println("Available products in " + storeName + ":");
        boolean foundAny = false;
        for (int i = 0; i < productCount; i++) {
            if (products[i].getStock() < 1) {
                continue;
            }
            System.out.println("  " + products[i].getInfo());
            foundAny = true;
        }
        if (!foundAny) {
            System.out.println("  (none)");
        }
    }

    public void printSalesReport() {
        System.out.println("Sales report for " + storeName + ":");
        for (int i = 0; i < productCount; i++) {
            System.out.println("  " + products[i].getName() + ": " + soldCount[i] + " units sold");
        }
    }

    public int getTotalUnitsSold() {
        int total = 0;
        for (int i = 0; i < productCount; i++) {
            total += soldCount[i];
        }
        return total;
    }
}