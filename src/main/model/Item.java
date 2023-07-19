package model;

public class Item {
    private static final int lowQuantity = 3;
    private int id;
    private String productName;
    private int quantity;
    private double price;

    // EFFECTS: item is assigned a name, quantity and price
    public Item(String productName, int quantity, double price) {
        this.productName = productName;
        this.quantity = quantity;
        this.price = price;
    }

    public int getId() {
        return id;
    }

    // REQUIRES: values is a unique int not assigned to another item
    // MODIFIES: this
    // EFFECTS: sets the id of the items as the parameter
    public void setId(int value) {
        this.id = value;
    }

    public String getProductName() {
        return productName;
    }

    public int getQuantity() {
        return quantity;
    }

    public double getPrice() {
        return price;
    }

    // EFFECTS: indicates whether an items quantity is below an established threshold
    public boolean isLow() {
        return (this.quantity <= lowQuantity);
    }

    // REQUIRES: if negative, !(abs(amount) > this.quantity)
    // MODIFIES: this
    // EFFECTS: updates the item's quantity by adding the parameter to it
    public void updateQuantity(int amount) {
        this.quantity += amount;
    }

    // REQUIRES: amount > 0
    // MODIFIES: this
    // EFFECTS: sets the price of the item
    public void setPrice(int amount) {
        this.price = amount;
    }
}
