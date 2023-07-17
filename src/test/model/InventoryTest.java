package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class InventoryTest {
    private Inventory testInventory;
    private Item testItem1;
    private Item testItem2;
    private Item testItem3;

    @BeforeEach
    void runBefore() {
        testInventory = new Inventory();
        testItem1 = new Item("pc electric kettle", 1, 40);
        testItem2 = new Item("brita water filter", 6, 8);
        testItem3 = new Item("honeywell turbo fan", 3, 30);
    }

    @Test
    void testConstructor() {
        assertEquals(0, testInventory.getListOfItems().size());
        assertEquals(0, testInventory.getDiscount());
        assertEquals(0, testInventory.getSales());
    }

    @Test
    void testSetDiscount() {
        testInventory.setDiscount(2);
        assertEquals(2, testInventory.getDiscount());

        testInventory.setDiscount(8);
        testInventory.setDiscount(5);
        assertEquals(5, testInventory.getDiscount());
    }

    @Test
    void testAddNewItem() {
        testInventory.addNewItem(testItem1);
        assertEquals(1, testInventory.getListOfItems().size());
        assertEquals(testItem1, testInventory.getListOfItems().get(0));

        testInventory.addNewItem(testItem2);
        testInventory.addNewItem(testItem3);
        assertEquals(3, testInventory.getListOfItems().size());
        assertEquals(testItem2, testInventory.getListOfItems().get(1));
        assertEquals(testItem3, testInventory.getListOfItems().get(2));

    }

    @Test
    void testIsPresent() {
        assertFalse(testInventory.isPresent("pc electric kettle"));

        testInventory.addNewItem(testItem3);
        assertFalse(testInventory.isPresent("brita water filter"));
        assertTrue(testInventory.isPresent("honeywell turbo fan"));

        testInventory.addNewItem(testItem1);
        testInventory.addNewItem(testItem2);
        assertTrue(testInventory.isPresent("brita water filter"));
        assertTrue(testInventory.isPresent("honeywell turbo fan"));
        assertTrue(testInventory.isPresent("pc electric kettle"));
        assertFalse(testInventory.isPresent("razer kraken x"));
    }

    @Test
    void testRestockItem() {
        testInventory.addNewItem(testItem1);
        testInventory.restockItem("pc electric kettle", 2);
        assertEquals(3, testItem1.getQuantity());

        testInventory.addNewItem(testItem2);
        testInventory.addNewItem(testItem3);
        testInventory.restockItem("brita water filter", 1);
        testInventory.restockItem("brita water filter", 3);
        testInventory.restockItem("honeywell turbo fan", 6);
        assertEquals(10, testItem2.getQuantity());
        assertEquals(9, testItem3.getQuantity());
    }

    @Test
    void testItemsToRestock() {
        ArrayList<String> testList = testInventory.itemsToRestock();
        assertEquals(0, testList.size());

        testInventory.addNewItem(testItem2);
        testList = testInventory.itemsToRestock();
        assertEquals(0, testList.size());

        testInventory.addNewItem(testItem1);
        testInventory.addNewItem(testItem3);
        testList = testInventory.itemsToRestock();
        assertEquals(2, testList.size());
        assertTrue(testList.contains("pc electric kettle"));
        assertTrue(testList.contains("honeywell turbo fan"));
    }

    @Test
    void testSellItem() {
        testInventory.addNewItem(testItem1);
        testInventory.sellItem("pc electric kettle", 1);
        assertEquals(0, testItem1.getQuantity());
        assertEquals(40, testInventory.getSales());

        testInventory.addNewItem(testItem2);
        testInventory.addNewItem(testItem3);
        testInventory.sellItem("honeywell turbo fan", 2);
        assertEquals(1, testItem3.getQuantity());
        assertEquals(100, testInventory.getSales());
    }
}
