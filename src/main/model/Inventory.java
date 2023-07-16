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
        this.listOfItems.add(i);
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
