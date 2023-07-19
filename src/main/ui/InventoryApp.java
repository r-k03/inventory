package ui;

import model.Inventory;
import model.Item;

import java.util.ArrayList;
import java.util.Scanner;

// Structure of InventoryApp taken from
// https://github.students.cs.ubc.ca/CPSC210/TellerApp/blob/main/src/main/ca/ubc/cpsc210/bank/ui/TellerApp.java

public class InventoryApp {
    private Inventory inv;
    private Scanner input;

    public InventoryApp() {
        runInventory();
    }

    private void runInventory() {
        boolean continueProcess = true;
        String command;

        init();

        while (continueProcess) {
            menuOptions();
            command = input.next();

            if (command.equals("7")) {
                continueProcess = false;
            } else {
                processChoice(command);
            }
        }
        System.out.println("Process Ended. Have a Great Day!");
    }

    private void init() {
        inv = new Inventory();
        input = new Scanner(System.in);
        input.useDelimiter("\n");
    }

    private void menuOptions() {
        System.out.println("Choose an Option (Number):");
        System.out.println("1. Add New Item to Inventory");
        System.out.println("2. Restock an Existing Item");
        System.out.println("3. Sell an Item");
        System.out.println("4. View Money Made in Sales");
        System.out.println("5. View Products that are Low in Quantity");
        System.out.println("6. Set a Discount Percentage");
        System.out.println("7. Quit");
    }

    private void processChoice(String command) {
        switch (command) {
            case "1":
                doNew();
                break;

            case "2":
                doAdd();
                break;

            case "3":
                doSell();
                break;

            case "4":
                returnSales();
                break;

            case "5":
                doViewRestock();
                break;

            case "6":
                doDiscount();
                break;

            default:
                System.out.println("Invalid Choice");
        }
    }

    private void doNew() {
        String name;
        int amt = -1;
        double price = -1;
        System.out.println("Enter Product Name: ");
        name = input.next();
        name = name.toLowerCase();
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
        }
    }

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
        } else {
            System.out.println("Item Not Found");
        }
    }

    private void doSell() {
        System.out.println("Enter ID of the Product to be Restocked: ");
        int id = input.nextInt();
        if (inv.isPresent(id)) {
            Item itemToSell = inv.getItemFromId(id);
            int amt = 0;
            while (!(amt > 0 && amt <= itemToSell.getQuantity())) {
                System.out.println("Enter Quantity to Sell: ");
                amt = input.nextInt();
            }
            inv.sellItem(id, amt);
        } else {
            System.out.println("Item Not Found");
        }
    }

    private void returnSales() {
        System.out.printf("Amount Earned in Sales: $%.2f\n", inv.getSales());
    }

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

    private void doDiscount() {
        int discount = -1;
        while (!(0 <= discount && discount <= 100)) {
            System.out.println("Enter Discount to be Set: ");
            discount = input.nextInt();
        }
        inv.setDiscount(discount);
    }
}
