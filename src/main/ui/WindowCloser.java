package ui;

import model.Event;
import model.EventLog;

import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.event.WindowStateListener;

// represents a window listener
public class WindowCloser implements WindowListener {

    // EFFECTS: nothing
    @Override
    public void windowOpened(WindowEvent e) {

    }

    // EFFECTS: prints the event log and exits the application
    @Override
    public void windowClosing(WindowEvent e) {
        for (Event next : EventLog.getInstance()) {
            System.out.println(next.toString());
        }
        System.exit(0);
    }

    // EFFECTS: nothing
    @Override
    public void windowClosed(WindowEvent e) {

    }

    // EFFECTS: nothing
    @Override
    public void windowIconified(WindowEvent e) {

    }

    // EFFECTS: nothing
    @Override
    public void windowDeiconified(WindowEvent e) {

    }

    // EFFECTS: nothing
    @Override
    public void windowActivated(WindowEvent e) {

    }

    // EFFECTS: nothing
    @Override
    public void windowDeactivated(WindowEvent e) {

    }
}
