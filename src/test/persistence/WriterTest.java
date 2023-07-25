package persistence;

import model.Inventory;
import model.Item;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

public class WriterTest {
    private Inventory testInv;

    @BeforeEach
    void runBefore() {
        testInv = new Inventory();
    }

    @Test
    void testInvalidFile() {
        try {
            Writer testWriter = new Writer("./data/invalid|file.json");
            testWriter.open();
            fail("FileNotFoundException Expected");
        } catch (FileNotFoundException e) {
            // pass
        }
    }

    @Test
    void testEmptyInventory() {
        try {
            Writer testWriter = new Writer("./data/TestEmptyInventory.json");
            testWriter.open();
            testWriter.write(testInv);
            testWriter.close();

            Reader testReader = new Reader("./data/TestEmptyInventory.json");
            testInv = testReader.read();
            assertTrue(testInv.getListOfItems().isEmpty());
            assertEquals(0, testInv.getSales());
            assertEquals(0, testInv.getDiscount());
        } catch (IOException e) {
            fail("IOException Not Expected");
        }
    }

    @Test
    void testRegularInventory() {
        try {
            Writer testWriter = new Writer("./data/TestRegularInventory.json");
            testInv.addNewItem(new Item("brita", 2, 5));
            testInv.addNewItem(new Item("electric kettle", 5, 1));
            testWriter.open();
            testWriter.write(testInv);
            testWriter.close();

            Reader testReader = new Reader("./data/TestRegularInventory.json");
            testInv = testReader.read();
            assertEquals(2, testInv.getListOfItems().size());
            assertEquals(0, testInv.getSales());
            assertEquals(0, testInv.getDiscount());
            assertEquals(1, testInv.getListOfItems().get(0).getId());
            assertEquals("brita", testInv.getListOfItems().get(0).getProductName());
            assertEquals(2, testInv.getListOfItems().get(0).getQuantity());
            assertEquals(5, testInv.getListOfItems().get(0).getPrice());
            assertEquals(2, testInv.getListOfItems().get(1).getId());
            assertEquals("electric kettle", testInv.getListOfItems().get(1).getProductName());
            assertEquals(5, testInv.getListOfItems().get(1).getQuantity());
            assertEquals(1, testInv.getListOfItems().get(1).getPrice());
        } catch (IOException e) {
            fail("IOException Not Expected");
        }
    }
}
