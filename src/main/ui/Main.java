package ui;

import java.io.FileNotFoundException;

public class Main {
    public static void main(String[] args) {
        try {
            new InventoryApp();
        } catch (FileNotFoundException e) {
            System.out.println("Unable to Find File at Destination");
        }
    }
}
