package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ItemTest {
    private Item testItem;

    @BeforeEach
    void runBefore() {
        testItem = new Item("PC Electric Kettle", 1, 40);
    }

    @Test
    void testConstructor() {
        assertEquals(3, testItem.getId());
        assertEquals("PC Electric Kettle", testItem.getProductName());
        assertEquals(1, testItem.getQuantity());
        assertEquals(40, testItem.getPrice());
    }

    @Test
    void testIsLow() {
        assertTrue(testItem.isLow());

        testItem.updateQuantity(1);
        assertTrue(testItem.isLow());

        testItem.updateQuantity(1);
        assertTrue(testItem.isLow());

        testItem.updateQuantity(1);
        assertFalse(testItem.isLow());
    }

    @Test
    void testUpdateQuantity() {
        testItem.updateQuantity(1);
        assertEquals(2, testItem.getQuantity());

        testItem.updateQuantity(1);
        testItem.updateQuantity(3);
        assertEquals(6, testItem.getQuantity());

        testItem.updateQuantity(-6);
        assertEquals(0, testItem.getQuantity());
    }
}