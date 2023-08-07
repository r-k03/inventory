package persistence;

import model.Event;
import model.EventLog;
import model.Item;
import model.Inventory;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.stream.Stream;
import org.json.*;

// class implementation is from:
// https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo/blob/master/src/main/persistence/JsonReader.java
// represents a readers that reloads inventory data stored in a JSON file
public class Reader {
    private String src;

    // EFFECTS: creates a reader to the file at source src
    public Reader(String src) {
        this.src = src;
    }

    // EFFECTS: returns inventory data from the file after reading and logs the event,
    // throws IOException if an error occurs while reading data
    public Inventory read() throws IOException {
        String jsonData = readFile(src);
        JSONObject jsonObject = new JSONObject(jsonData);
        EventLog.getInstance().logEvent(new Event("Inventory Loaded From Save File"));
        return parseInventory(jsonObject);
    }

    // EFFECTS: reads the source file as a string and returns it
    public String readFile(String src) throws IOException {
        StringBuilder jsonString = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(src), StandardCharsets.UTF_8)) {
            stream.forEach(s -> jsonString.append(s));
        }

        return jsonString.toString();
    }

    // EFFECTS: parses inventory from jsonObject and returns it
    private Inventory parseInventory(JSONObject jsonObject) {
        Inventory inv = new Inventory();
        double sales = jsonObject.getDouble("sales");
        int discount = jsonObject.getInt("discount");
        inv.setSales(sales);
        inv.setDiscount(discount);
        addItems(jsonObject, inv);
        return inv;
    }

    // MODIFIES: inv
    // EFFECTS: parses items from jsonObject and adds them to inventory
    private void addItems(JSONObject jsonObject, Inventory inv) {
        JSONArray jsonArray = jsonObject.getJSONArray("listOfItem");
        ArrayList<Item> listOfItems = new ArrayList<>();
        for (Object json : jsonArray) {
            JSONObject nextItem = (JSONObject) json;
            addItem(inv, nextItem);
        }
    }

    // MODIFIES: inv
    // EFFECTS: parses item from jsonObject and adds it to inventory
    private void addItem(Inventory inv, JSONObject jsonObject) {
        String productName = jsonObject.getString("productName");
        int quantity = jsonObject.getInt("quantity");
        double price = jsonObject.getDouble("price");
        inv.addNewItem(new Item(productName, quantity, price));
    }
}
