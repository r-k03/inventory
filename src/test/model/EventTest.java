package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Calendar;
import java.util.Date;

import static java.lang.Math.abs;
import static org.junit.jupiter.api.Assertions.*;

public class EventTest {
    private Event testEvent;
    private Event testEvent1;
    private Event testEvent2;
    private Date testDate;

    @BeforeEach
    void runBefore() {
        testEvent = new Event("Test Event");
        testEvent1 = new Event("Test Event");
        testEvent2 = new Event("Test Event1");
        testDate = Calendar.getInstance().getTime();
    }

    @Test
    void tesConstructor() {
        assertEquals("Test Event", testEvent.getDescription());
        assertTrue(abs(testDate.getTime() - testEvent.getDate().getTime()) <= 20);
    }

    @Test
    void testEquals() {
        assertFalse(testEvent.equals(null));
        assertFalse(testEvent.equals(new Item("a", 1, 1)));
        assertFalse(testEvent.equals(new Event("Not Test Event")));
        assertFalse(testEvent.equals(new Event("Test Event")));
        assertFalse(testEvent.equals(testEvent2));
        assertTrue(testEvent.equals(testEvent));
    }

   @Test
    void testHashCode() {
        assertEquals(testEvent, testEvent1);
        assertEquals(testEvent.hashCode(),testEvent1.hashCode());
    }

    @Test
    void testToString() {
        assertEquals(testDate.toString() + "\n" + "Test Event", testEvent.toString());
    }
}
