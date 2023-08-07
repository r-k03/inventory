package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class EventLogTest {
    private Event testEvent1;
    private Event testEvent2;
    private Event testEvent3;

    @BeforeEach
    void runBefore() {
        testEvent1 = new Event("1");
        testEvent2 = new Event("2");
        testEvent3 = new Event("3");
        EventLog log = EventLog.getInstance();
        log.logEvent(testEvent1);
        log.logEvent(testEvent2);
        log.logEvent(testEvent3);
    }

    @Test
    void testLogEvent() {
        List<Event> eventList = new ArrayList<>();
        EventLog log = EventLog.getInstance();
        for (Event e : log) {
            eventList.add(e);
        }
        assertEquals(3, eventList.size());
        assertTrue(eventList.contains(testEvent1));
        assertTrue(eventList.contains(testEvent2));
        assertTrue(eventList.contains(testEvent3));
    }

    @Test
    void testClear() {
        EventLog log = EventLog.getInstance();
        log.clear();
        Iterator<Event> itr = log.iterator();
        assertTrue(itr.hasNext());
        assertEquals("Event log cleared.", itr.next().getDescription());
        assertFalse(itr.hasNext());
    }
}