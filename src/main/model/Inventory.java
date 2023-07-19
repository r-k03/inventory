package model;

import java.util.ArrayList;
import java.util.List;

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

    public void addNewItem(Item i) {
        i.setId(listOfItems.size() + 1);
        this.listOfItems.add(i);
    }

    public boolean isPresent(String name) {
        for (Item loopItem : listOfItems) {
            if (loopItem.getProductName().equals(name)) {
                return true;
            }
        }
        return false;
    }

    public void restockItem(int id, int amount) {
        for (Item loopItem : listOfItems) {
            if (loopItem.getId() == id) {
                loopItem.updateQuantity(amount);
            }
        }
    }

    public ArrayList<Item> itemsToRestock() {
        ArrayList<Item> restockList = new ArrayList<>();
        for (Item loopItem : listOfItems) {
            if (loopItem.isLow()) {
                restockList.add(loopItem);
            }
        }
        return restockList;
    }

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
