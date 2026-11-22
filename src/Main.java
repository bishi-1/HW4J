import PG.Product;
import PG.Store;
public class Main {

    private static void check(String description, boolean condition) {
        System.out.println((condition ? "[PASS] " : "[FAIL] ") + description);
    }

    public static void main(String[] args) {

        Store store = new Store("Corner Store", 3);

        Product notebook = new Product("Notebook", 12.5, 3);
        Product pen = new Product("Pen", 4.0, 2);
        Product bottle = new Product("Bottle", 30.0, 0);

        System.out.println("--- Step 3: adding three products ---");
        check("add Notebook succeeds", store.addProduct(notebook));
        check("add Pen succeeds", store.addProduct(pen));
        check("add Bottle succeeds", store.addProduct(bottle));

        System.out.println("--- Step 4: adding a fourth product ---");
        Product marker = new Product("Marker", 7.0, 5);
        check("add 4th product fails (store full)", !store.addProduct(marker));

        System.out.println("--- Step 5: sell 2 Notebooks ---");
        check("sell 2 Notebook succeeds", store.sellProduct(notebook.getId(), 2));

        System.out.println("--- Step 6: sell 3 Pens (only 2 in stock) ---");
        check("sell 3 Pen fails", !store.sellProduct(pen.getId(), 3));
        check("Pen stock unchanged (2)", pen.getStock() == 2);

        System.out.println("--- Step 7: sell 2 Pens ---");
        check("sell 2 Pen succeeds", store.sellProduct(pen.getId(), 2));

        System.out.println("--- Step 8: set invalid price -5 on Notebook ---");
        double oldPrice = notebook.getPrice();
        check("setPrice(-5) fails", !notebook.setPrice(-5));
        check("Notebook price unchanged (" + oldPrice + ")", notebook.getPrice() == oldPrice);

        System.out.println("--- Step 9: available products ---");
        store.printAvailableProducts();

        System.out.println("--- Step 10: sales report ---");
        store.printSalesReport();
        System.out.println("Total units sold: " + store.getTotalUnitsSold());

        System.out.println("--- Step 11: ids ---");
        System.out.println("Notebook id: " + notebook.getId());
        System.out.println("Pen id: " + pen.getId());
        System.out.println("Bottle id: " + bottle.getId());
        System.out.println("Marker id (created but not added): " + marker.getId());
        System.out.println("Next id: " + Product.getNextId());

        System.out.println("--- Expected results ---");
        check("Notebook has 1 unit left", notebook.getStock() == 1);
        check("Pen has 0 units left", pen.getStock() == 0);
        check("Only Notebook is available (Bottle and Pen are at 0)",
                notebook.getStock() >= 1 && pen.getStock() == 0 && bottle.getStock() == 0);
        check("total units sold is 4", store.getTotalUnitsSold() == 4);
        check("ids are unique and increasing",
                notebook.getId() == 1000 && pen.getId() == 1001
                        && bottle.getId() == 1002 && marker.getId() == 1003);
        check("next id is 1004", Product.getNextId() == 1004);

        System.out.println("--- Extra edge cases ---");

        Product bad = new Product("   ", -3, -7);
        check("invalid constructor args -> name Unknown", bad.getName().equals("Unknown"));
        check("invalid constructor args -> price 1.0", bad.getPrice() == 1.0);
        check("invalid constructor args -> stock 0", bad.getStock() == 0);

        Product nullName = new Product(null, 5, 1);
        check("null name -> Unknown", nullName.getName().equals("Unknown"));

        check("setName(null) fails", !notebook.setName(null));
        check("setName(\"   \") fails", !notebook.setName("   "));
        check("name unchanged after failed setName", notebook.getName().equals("Notebook"));
        check("setName(\"  Big Notebook  \") succeeds", notebook.setName("  Big Notebook  "));
        check("name is trimmed", notebook.getName().equals("Big Notebook"));

        check("setPrice(0) fails", !notebook.setPrice(0));
        check("setStock(-1) fails", !notebook.setStock(-1));
        check("stock unchanged after failed setStock", notebook.getStock() == 1);

        check("sell(0) fails", !notebook.sell(0));
        check("sell(-2) fails", !notebook.sell(-2));
        check("stock unchanged after invalid sell", notebook.getStock() == 1);

        check("sellProduct with unknown id fails", !store.sellProduct(9999, 1));
        check("findProductIndex of unknown id is -1", store.findProductIndex(9999) == -1);
        check("findProductIndex(Pen) is 1", store.findProductIndex(pen.getId()) == 1);
        check("addProduct(null) fails", !store.addProduct(null));

        Store other = new Store("Second Store", 5);
        check("add product to second store", other.addProduct(bottle));
        check("adding same product (same id) again fails", !other.addProduct(bottle));
        check("failed sale does not change total sold",
                !store.sellProduct(pen.getId(), 1) && store.getTotalUnitsSold() == 4);

        Store empty = new Store("Empty Store", 2);
        empty.printAvailableProducts();
        empty.printSalesReport();
        check("empty store total is 0", empty.getTotalUnitsSold() == 0);
    }
}