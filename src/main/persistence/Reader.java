package persistence;

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

    public Reader(String src) {
        this.src = src;
    }

    public Inventory read() throws IOException {
        String jsonData = readFile(src);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseInventory(jsonObject);
    }

    public String readFile(String src) throws IOException {
        StringBuilder jsonString = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(src), StandardCharsets.UTF_8)) {
            stream.forEach(s -> jsonString.append(s));
        }

        return jsonString.toString();
    }

    private Inventory parseInventory(JSONObject jsonObject) {
        Inventory inv = new Inventory();
        double sales = jsonObject.getDouble("sales");
        int discount = jsonObject.getInt("discount");
        inv.setSales(sales);
        inv.setDiscount(discount);
        JSONArray jsonArray = jsonObject.getJSONArray("listOfItem");
        ArrayList<Item> listOfItems = new ArrayList<>();
        for (Object json : jsonArray) {
            JSONObject nextItem = (JSONObject) json;
            addItem(inv, nextItem);
        }
        return inv;
    }

    private void addItem(Inventory inv, JSONObject nextItem) {
        String productName = nextItem.getString("productName");
        int quantity = nextItem.getInt("quantity");
        double price = nextItem.getDouble("price");
        inv.addNewItem(new Item(productName, quantity, price));
    }
}
