package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Calendar;
import java.util.Date;

import static java.lang.Math.abs;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class EventTest {
    private Event testEvent;
    private Date testDate;

    @BeforeEach
    void runBefore() {
        testEvent = new Event("Test Event");
        testDate = Calendar.getInstance().getTime();
    }

    @Test
    void tesConstructor() {
        assertEquals("Test Event", testEvent.getDescription());
        assertTrue(abs(testDate.getTime() - testEvent.getDate().getTime()) <= 20);
    }

    @Test
    void testToString() {
        assertEquals(testDate.toString() + "\n" + "Test Event", testEvent.toString());
    }
}
