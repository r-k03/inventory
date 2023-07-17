package model;

public class Item {
    private static final int lowQuantity = 3;
    private static int newId = 1;
    private int id;
    private String productName;
    private int quantity;
    private double price;

    public Item(String productName, int quantity, double price) {
        this.id = newId;
        newId++;
        this.productName = productName;
        this.quantity = quantity;
        this.price = price;
    }

    public int getId() {
        return id;
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

    public boolean isLow() {
        return (this.quantity <= lowQuantity);
    }

    public void updateQuantity(int amount) {
        this.quantity += amount;
    }

    public void setPrice(int amount) {
        this.price = amount;
    }
}
