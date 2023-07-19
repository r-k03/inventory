package model;

import java.util.ArrayList;
import java.util.List;

// represents an inventory containing a list of items in stock, the current discount, and total sales ($)
public class Inventory {
    private final List<Item> listOfItems;
    private int discount;
    private double sales;

    public Inventory() {
        this.listOfItems = new ArrayList<>();
        this.discount = 0;
        this.sales = 0;
    }

    public List<Item> getListOfItems() {
        return listOfItems;
    }

    public int getDiscount() {
        return this.discount;
    }

    public double getSales() {
        return this.sales;
    }

    public void setDiscount(int amount) {
        this.discount = amount;
    }

    // REQUIRES: an item with the given id is present in listOfItems
    // EFFECTS: returns the item with matching id
    public Item getItemFromId(int id) {
        for (Item i : listOfItems) {
            if (id == i.getId()) {
                return i;
            }
        }
        return null;
    }

    // REQUIRES: the item is not present in listOfItems
    // MODIFIES: this
    // EFFECTS: adds a new item to the list of items
    public void addNewItem(Item i) {
        i.setId(listOfItems.size() + 1);
        this.listOfItems.add(i);
    }

    // EFFECTS: indicates whether an item with the same id is present in the list
    public boolean isPresent(int id) {
        for (Item loopItem : listOfItems) {
            if (loopItem.getId() == id) {
                return true;
            }
        }
        return false;
    }

    // EFFECTS: indicates whether an item with the same name is present in the list
    public boolean isPresent(String name) {
        for (Item loopItem : listOfItems) {
            if (loopItem.getProductName().equals(name)) {
                return true;
            }
        }
        return false;
    }

    // REQUIRES: an item with the given id is present in listOfItems
    // EFFECTS: updates the item with the same id by increasing its quantity
    public void restockItem(int id, int amount) {
        for (Item loopItem : listOfItems) {
            if (loopItem.getId() == id) {
                loopItem.updateQuantity(amount);
            }
        }
    }

    // EFFECTS: returns a list of items that have to be restocked
    public ArrayList<Item> itemsToRestock() {
        ArrayList<Item> restockList = new ArrayList<>();
        for (Item loopItem : listOfItems) {
            if (loopItem.isLow()) {
                restockList.add(loopItem);
            }
        }
        return restockList;
    }

    // REQUIRES: an item with matching ids is present in listOfItems && amount <= quantity of specified item
    // MODIFIES: this
    // EFFECTS: sells the given quantity of the item and adds the price to sales
    public void sellItem(int id, int amount) {
        for (Item i : listOfItems) {
            if (i.getId() == id) {
                i.updateQuantity(-amount);
                if (discount != 0) {
                    this.sales += amount * i.getPrice() * (1 - this.discount / 100.0);
                } else {
                    this.sales += amount * i.getPrice();
                }
            }
        }
    }
}
