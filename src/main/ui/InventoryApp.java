package ui;

import model.Inventory;
import model.Item;
import persistence.Reader;
import persistence.Writer;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

// Structure of InventoryApp taken from
// https://github.students.cs.ubc.ca/CPSC210/TellerApp/blob/main/src/main/ca/ubc/cpsc210/bank/ui/TellerApp.java

// Inventory Management Application
public class InventoryApp {
    private static final String JSON_LOC = "./data/Inventory.json";
    private Inventory inv;
    private Scanner input;
    private Writer jsonWriter;
    private Reader jsonReader;

    // EFFECTS: runs the inventory application
    public InventoryApp() throws FileNotFoundException {
        runInventory();
    }

    // MODIFIES: this
    // EFFECTS: handles user input
    private void runInventory() throws FileNotFoundException {
        boolean continueProcess = true;
        String command;

        init();

        while (continueProcess) {
            menuOptions();
            command = input.next();

            if (command.equals("9")) {
                doSave();
                continueProcess = false;
            } else {
                processChoice(command);
            }
        }
        System.out.println("Process Ended. Have a Great Day!");
    }

    // MODIFIES: this
    // EFFECTS: initializes the inventory and scanner
    private void init() {
        inv = new Inventory();
        input = new Scanner(System.in);
        input.useDelimiter("\n");
        jsonWriter = new Writer(JSON_LOC);
        jsonReader = new Reader(JSON_LOC);
    }

    // EFFECTS: presents the options the user can choose from
    private void menuOptions() {
        System.out.println("Choose an Option (Number):");
        System.out.println("1. View Details of all Items in Inventory");
        System.out.println("2. Add New Item to Inventory");
        System.out.println("3. Restock an Existing Item");
        System.out.println("4. Sell an Item");
        System.out.println("5. View Money Made in Sales");
        System.out.println("6. View Products that are Low in Quantity");
        System.out.println("7. Set a Discount Percentage");
        System.out.println("8. Load Inventory From Save");
        System.out.println("9. Quit");
    }

    // EFFECTS: processes the inputs the user gives
    @SuppressWarnings("methodlength")
    private void processChoice(String command) {
        switch (command) {
            case "1":
                doPrint();
                break;
            case "2":
                doNew();
                break;
            case "3":
                doAdd();
                break;
            case "4":
                doSell();
                break;
            case "5":
                returnSales();
                break;
            case "6":
                doViewRestock();
                break;
            case "7":
                doDiscount();
                break;
            case "8":
                doLoad();
                break;
            default:
                System.out.println("Invalid Choice");
        }
    }

    // EFFECTS: returns the details of all items in inventory, if any else indicates otherwise
    @SuppressWarnings("all")
    private void doPrint() {
        if (inv.getListOfItems().isEmpty()) {
            System.out.println("No Items in Inventory");
        } else {
            System.out.println("ID:Name:Quantity:Price");
            for (Item i : inv.getListOfItems()) {
                System.out.println(i.getId() + " : " + i.getProductName() + " : " + i.getQuantity() + " : " + i.getPrice());
            }
        }
    }

    // MODIFIES: this
    // EFFECTS: adds a new item to the list in inventory
    private void doNew() {
        String name = "";
        int amt = -1;
        double price = -1;
        while (name.length() == 0) {
            System.out.println("Enter Product Name: ");
            name = input.next();
            name = name.toLowerCase();
        }
        if (inv.isPresent(name)) {
            System.out.println("Item Already Exists");
        } else {
            while (!(amt >= 0 && price > 0)) {
                System.out.println("Enter Quantity of Products: ");
                amt = input.nextInt();
                System.out.println("Enter Price of the Product: ");
                price = input.nextDouble();
            }
            inv.addNewItem(new Item(name, amt, price));
            System.out.println("Item Successfully Added");
        }
    }

    // EFFECTS: increases the stock of a particular item
    @SuppressWarnings("all")
    private void doAdd() {
        System.out.println("Enter ID of the Product to be Restocked: ");
        int id = input.nextInt();
        if (inv.isPresent(id)) {
            Item itemToAdd = inv.getItemFromId(id);
            int amt = 0;
            while (!(amt > 0)) {
                System.out.println("Enter Quantity of Item: ");
                amt = input.nextInt();
            }
            itemToAdd.updateQuantity(amt);
            System.out.println("Quantity of " + inv.getItemFromId(id).getProductName() + " has been updated to " + inv.getItemFromId(id).getQuantity());
        } else {
            System.out.println("Item Not Found");
        }
    }

    // MODIFIES: this
    // EFFECTS: conducts a sell transaction and adds the money made to sales
    @SuppressWarnings("all")
    private void doSell() {
        System.out.println("Enter ID of the Product to be Sold: ");
        int id = input.nextInt();
        if (inv.isPresent(id)) {
            Item itemToSell = inv.getItemFromId(id);
            int amt = 0;
            while (!(amt > 0 && amt <= itemToSell.getQuantity())) {
                System.out.println("Enter Quantity to Sell: ");
                amt = input.nextInt();
            }
            inv.sellItem(id, amt);
            System.out.println(amt + " " + inv.getItemFromId(id).getProductName() + " has been sold for $" + (inv.getItemFromId(id).getPrice() * amt));
        } else {
            System.out.println("Item Not Found");
        }
    }

    // EFFECTS: returns the money made in sales
    private void returnSales() {
        System.out.printf("Amount Earned in Sales: $%.2f\n", inv.getSales());
    }

    // EFFECTS: returns the items that have to be restocked
    private void doViewRestock() {
        ArrayList<Item> inventoryRestock = inv.itemsToRestock();
        if (inventoryRestock.isEmpty()) {
            System.out.println("No Items to Restock");
        } else {
            System.out.println("items to Restock Are (ID and Name):");
            for (Item i : inventoryRestock) {
                System.out.println(i.getId() + ": " + i.getProductName());
            }
        }
    }


    // MODIFIES: this
    // EFFECTS: sets a discount on items being sold
    private void doDiscount() {
        int discount = -1;
        while (!(0 <= discount && discount <= 100)) {
            System.out.println("Enter Discount to be Set: ");
            discount = input.nextInt();
        }
        inv.setDiscount(discount);
        System.out.println("Discount has been Set");
    }

    // REQUIRES: json file is not blank
    // MODIFIES: this
    // EFFECTS: returns the inventory data stored previously
    private void doLoad() {
        try {
            inv = jsonReader.read();
            System.out.println("Successfully Loaded Inventory Data Stored at: " + JSON_LOC);
        } catch (IOException e) {
            System.out.println("Unable to Read Data from File at: " + JSON_LOC);
        }
    }

    // EFFECTS: saves the current inventory data
    private void doSave() throws FileNotFoundException {
        String save = "";
        while (!(save.equals("y") || save.equals("n"))) {
            System.out.println("Would you Like to Save Inventory Data? (y/n)");
            save = input.next();
        }
        if (save.equals("y")) {
            jsonWriter.open();
            jsonWriter.write(inv);
            jsonWriter.close();
            System.out.println("Data Saved");
        }
    }
}
