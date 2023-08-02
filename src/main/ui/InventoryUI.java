package ui;

import model.Inventory;
import persistence.Reader;
import persistence.Writer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.InvalidPathException;

public class InventoryUI extends JFrame {
    private static final String JSON_LOC = "./data/I||nventory.json";
    private static final int WIDTH = 1000;
    private static final int HEIGHT = 800;
    private Inventory inv;
    private Reader jsonReader;
    private Writer jsonWriter;
    private JDesktopPane desktop;
    private JInternalFrame menuPane;

    public InventoryUI() {
        inv = new Inventory();
        jsonReader = new Reader(JSON_LOC);
        jsonWriter = new Writer(JSON_LOC);

        desktop = new JDesktopPane();
        desktop.addMouseListener(new DesktopFocusAction());
        menuPane = new JInternalFrame("Options", false, false, false, false);
        menuPane.setLayout(new BorderLayout());

        setContentPane(desktop);
        setTitle("Inventory Management System");
        setSize(WIDTH, HEIGHT);

        buttonPanel();

        menuPane.pack();
        menuPane.setVisible(true);
        desktop.add(menuPane);

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
    }

    private void buttonPanel() {
        JPanel buttons = new JPanel();
        buttons.setLayout(new GridLayout(0, 2));
        buttons.add(new JButton("View Items in Inventory"));
        buttons.add(new JButton("new QuitAction()"));
        buttons.add(new JButton("Restock Item"));
        buttons.add(new JButton("Sell Item"));
        buttons.add(new JButton("View Items to Restock"));
        buttons.add(new JButton("Set Discount"));
        buttons.add(new JButton("View Sales"));
        buttons.add(new JButton(new LoadAction()));
        buttons.add(new JButton(new SaveAction()));
        buttons.add(new JButton(new QuitAction()));

        menuPane.add(buttons, BorderLayout.WEST);
    }

    private class LoadAction extends AbstractAction {


        LoadAction() {
            super("Load Save");
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                inv = jsonReader.read();
            } catch (IOException er) {
                String msg = "File Not Accessible: " + JSON_LOC;
                JOptionPane.showMessageDialog(null, msg, "System Error", JOptionPane.INFORMATION_MESSAGE);
            } catch (InvalidPathException er) {
                String msg = "Invalid Path: " + JSON_LOC;
                JOptionPane.showMessageDialog(null, msg, "System Error", JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }

    private class SaveAction extends AbstractAction {


        SaveAction() {
            super("Save");
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                jsonWriter.open();
                jsonWriter.write(inv);
                jsonWriter.close();
            } catch (FileNotFoundException er) {
                String msg = "File Not Found: " + JSON_LOC;
                JOptionPane.showMessageDialog(null, msg, "System Error", JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }

    private class QuitAction extends AbstractAction {


        QuitAction() {
            super("Quit");
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            System.exit(0);
        }
    }

    private class DesktopFocusAction extends MouseAdapter {
        @Override
        public void mouseClicked(MouseEvent e) {
            InventoryUI.this.requestFocusInWindow();
        }
    }



}
