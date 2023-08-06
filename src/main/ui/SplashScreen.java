package ui;

import javax.swing.*;

// represents a splash screen with only one instance
public class SplashScreen extends JWindow {
    private static SplashScreen splash = new SplashScreen();
    private JWindow window;

    // EFFECTS: constructs a window for the splash screen
    private SplashScreen() {
        JLabel display = new JLabel(new ImageIcon("./data/loading_screen.gif"), SwingConstants.CENTER);
        window = new JWindow();
        window.getContentPane().add(display);
        window.setBounds(500, 150, 700, 300);
        window.setVisible(false);
    }

    // EFFECTS: returns the splash screen
    public static SplashScreen getInstance() {
        return splash;
    }

    // EFFECTS: displays the splash screen for the specified time
    public void displayScreen() {
        window.setVisible(true);
        try {
            Thread.sleep(6750);
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
        window.setVisible(false);
    }







}