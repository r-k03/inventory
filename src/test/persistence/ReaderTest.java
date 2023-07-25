package persistence;

import model.Inventory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

public class ReaderTest {

    @Test
    void testUnreadableFile() {
        Reader testReader = new Reader("./data/unreadable.json");
        try {
            Inventory testInv = testReader.read();
            fail("IOException Expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testEmptyInventory() {
        Reader testReader = new Reader("./data/TestEmptyInventory2.json");
        try {
            Inventory testInv = testReader.read();
            assertTrue(testInv.getListOfItems().isEmpty());
            assertEquals(0, testInv.getSales());
            assertEquals(0, testInv.getDiscount());
        } catch (IOException e) {
            fail("IOException Not Expected");
        }
    }

    @Test
    void testRegularInventory() {
        Reader testReader = new Reader("./data/TestRegularInventory2.json");
        try {
            Inventory testInv = testReader.read();
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
