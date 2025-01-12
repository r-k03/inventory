package model;

import org.json.JSONObject;
import persistence.Writable;

// represents an item having a unique id, product name, its quantity, as well as the price for one unit of the item
// method implementation of toJson taken from:
// https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo/blob/master/src/main/model/Thingy.java
public class Item implements Writable {
    private static final int lowQuantity = 3;
    private int id;
    private String productName;
    private int quantity;
    private double price;
    private EventLog log = EventLog.getInstance();

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
    // EFFECTS: updates the item's quantity by adding the parameter to it and adds the event to the log
    public void updateQuantity(int amount) {
        this.quantity += amount;
        String eventString = "Item " + getProductName() + "'s quantity has been";
        String appendString = (amount >= 0) ? " increased by: " + amount : " decreased by: " + amount;
        eventString += appendString;
        log.logEvent(new Event(eventString));
    }

    // REQUIRES: amount > 0
    // MODIFIES: this
    // EFFECTS: sets the price of the item
    public void setPrice(int amount) {
        this.price = amount;
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("id", id);
        json.put("productName", productName);
        json.put("quantity", quantity);
        json.put("price", price);
        return json;
    }
}
