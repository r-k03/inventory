package ui;

import model.Inventory;
import model.Item;
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
import java.util.List;

// represents the inventory applications GUI
public class InventoryUI extends JFrame {
    private static final String JSON_LOC = "./data/Inventory.json";
    private static final int WIDTH = 1000;
    private static final int HEIGHT = 800;
    private Inventory inv;
    private Reader jsonReader;
    private Writer jsonWriter;
    private JDesktopPane desktop;
    private JInternalFrame menuPane;
    private JInternalFrame itemPane;
    private DefaultListModel<Item> listModel;
    private JList<Item> itemJList;

    // EFFECTS: displays the splash screen, sets up the items and buttons panel
    public InventoryUI() {
        //SplashScreen.getInstance().displayScreen();

        inv = new Inventory();
        jsonReader = new Reader(JSON_LOC);
        jsonWriter = new Writer(JSON_LOC);

        desktop = new JDesktopPane();
        desktop.addMouseListener(new DesktopFocusAction());
        desktop.setLayout(new FlowLayout());
        menuPane = new JInternalFrame("Options", false, false, false, false);
        itemPane = new JInternalFrame("Items", false, false, false, false);
//        menuPane.setLocation(20, 80);
//        itemPane.setLocation(650, 50);
        menuPane.setLayout(new BorderLayout());

        setContentPane(desktop);
        setTitle("Inventory Management System");
        setSize(WIDTH, HEIGHT);

        buttonPanel();

        menuPane.pack();
        menuPane.setVisible(true);
        desktop.add(menuPane);

        displayInventory();

        itemPane.pack();
        itemPane.setVisible(true);
        desktop.add(itemPane);

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
    }

    // MODIFIES: this
    // EFFECTS: adds buttons to the buttons panel
    private void buttonPanel() {
        JPanel buttons = new JPanel();
        buttons.setLayout(new GridLayout(0, 3));
        buttons.add(new JButton(new NewAction()));
        buttons.add(new JButton(new RestockAction()));
        buttons.add(new JButton(new SellAction()));
        buttons.add(new JButton(new ViewRestockAction()));
        buttons.add(new JButton(new DiscountAction()));
        buttons.add(new JButton(new SalesAction()));
        buttons.add(new JButton(new LoadAction()));
        buttons.add(new JButton(new SaveAction()));
        buttons.add(new JButton(new QuitAction()));

        menuPane.add(buttons, BorderLayout.WEST);
    }

    // represents an add item button
    private class NewAction extends AbstractAction {

        NewAction() {
            super("Add New Item");
        }

        // MODIFIES: this
        // EFFECTS: adds a new item to the list of items, shows an error dialog box otherwise
        @Override
        public void actionPerformed(ActionEvent e) {
            String name = JOptionPane.showInputDialog(null, "Enter Item Name");
            if (name == null || name.equals("") || inv.isPresent(name)) {
                String msg = (!inv.isPresent(name)) ? "No Name Entered" : "Item With the Same Name Already Exists";
                JOptionPane.showMessageDialog(null, msg, "Input Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            try {
                int quantity = Integer.parseInt(JOptionPane.showInputDialog(null, "Enter Quantity in Stock"));
                if (quantity < 0) {
                    negativeQuantity();
                    return;
                }
                double price = Double.parseDouble(JOptionPane.showInputDialog(null, "Enter Item Price"));
                if (price <= 0.0) {
                    String msg = "Price Cannot be Zero or Negative";
                    JOptionPane.showMessageDialog(null, msg, "Input Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                inv.addNewItem(new Item(name, quantity, price));
                listModel.addElement(new Item(name, quantity, price));
            } catch (NumberFormatException ex) {
                numberFormatMessage();
            }
        }
    }

    // EFFECTS: displays an error dialog box
    private static void numberFormatMessage() {
        String msg = "No Input Given/Input is a String";
        JOptionPane.showMessageDialog(null, msg, "Input Error", JOptionPane.ERROR_MESSAGE);
    }

    // represents a restock button
    private class RestockAction extends AbstractAction {

        RestockAction() {
            super("Restock Selected Item");
        }

        // EFFECTS: updates the quantity of the selected item, shows an error dialog box otherwise
        @Override
        public void actionPerformed(ActionEvent e) {
            Item selected = itemJList.getSelectedValue();
            if (selected == null) {
                JOptionPane.showMessageDialog(null, "No Item Selected", "Input Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            try {
                int quantity = Integer.parseInt(JOptionPane.showInputDialog(null, "Enter Quantity to Stock"));
                if (quantity < 0) {
                    negativeQuantity();
                    return;
                }
                selected.updateQuantity(quantity);
            } catch (NumberFormatException ex) {
                numberFormatMessage();
            }
        }
    }

    // EFFECTS: shows an error dialog box
    private static void negativeQuantity() {
        String msg = "Negative Amount Given";
        JOptionPane.showMessageDialog(null, msg, "Input Error", JOptionPane.ERROR_MESSAGE);
    }

    // represents a sell button
    private class SellAction extends AbstractAction {

        SellAction() {
            super("Sell Item");
        }

        // EFFECTS: sells the item selected, and adds the amount to sales
        @Override
        public void actionPerformed(ActionEvent e) {
            Item selected = itemJList.getSelectedValue();
            if (selected == null) {
                JOptionPane.showMessageDialog(null, "No Item Selected", "Input Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            try {
                int quantity = Integer.parseInt(JOptionPane.showInputDialog(null, "Enter Quantity to Sell"));
                if (quantity < 0) {
                    negativeQuantity();
                    return;
                }
                if (quantity > selected.getQuantity()) {
                    String msg = "Insufficient Quantity in Stock";
                    JOptionPane.showMessageDialog(null, msg, "Input Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                inv.sellItem(selected.getId(), quantity);
            } catch (NumberFormatException ex) {
                numberFormatMessage();
            }
        }
    }

    // represents a view items to restock button
    private class ViewRestockAction extends AbstractAction {

        ViewRestockAction() {
            super("View Items to Restock");
        }

        // EFFECTS: returns a list of items low in quantity, display a message if no items low
        @Override
        public void actionPerformed(ActionEvent e) {
            List<Item> listRestock = inv.itemsToRestock();
            if (listRestock.isEmpty()) {
                String msg = "No Items Low in Quantity";
                JOptionPane.showMessageDialog(null, msg, "Items to Restock", JOptionPane.INFORMATION_MESSAGE);
            } else {
                DefaultListModel<Item> restockModel = new DefaultListModel<>();
                for (Item i : listRestock) {
                    restockModel.addElement(i);
                }
                JList<Item> restockJlist = new JList<>();
                restockJlist.setCellRenderer(new MyListRenderer());
                restockJlist.setModel(restockModel);
                JScrollPane restockScroll = new JScrollPane(restockJlist);
                JOptionPane.showMessageDialog(null, restockScroll, "Items to Restock", JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }

    // represents a set discount button
    private class DiscountAction extends AbstractAction {

        DiscountAction() {
            super("Set Discount");
        }

        // EFFECTS: modifies the discount amount, shows an error dialog box otherwise
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                int discount = Integer.parseInt(JOptionPane.showInputDialog(null, "Enter Discount as a Percentage"));
                if (discount < 0) {
                    String msg = "Discount Cannot be Negative";
                    JOptionPane.showMessageDialog(null, msg, "Input Error", JOptionPane.ERROR_MESSAGE);
                    return;
                } else if (discount > 100) {
                    String msg = "Discount Cannot be Greater than 100%";
                    JOptionPane.showMessageDialog(null, msg, "Input Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                inv.setDiscount(discount);
            } catch (NumberFormatException ex) {
                numberFormatMessage();
            }
        }
    }

    // represents a sales button
    private class SalesAction extends AbstractAction {

        SalesAction() {
            super("View Amount Earned in Sales");
        }

        // EFFECTS: displays the sales made so far
        @Override
        public void actionPerformed(ActionEvent e) {
            String msg = "Amount Earned in Sales is: $" + inv.getSales();
            JOptionPane.showMessageDialog(null, msg, "Sales", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    // represents a load button
    private class LoadAction extends AbstractAction {

        LoadAction() {
            super("Load Save");
        }

        // MODIFIES: this
        // EFFECTS: loads the inventory from a save, throws an error if it cannot be loaded
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                inv = jsonReader.read();
                listModel.clear();
                for (Item i : inv.getListOfItems()) {
                    listModel.addElement(i);
                }
            } catch (IOException ex) {
                String msg = "File Not Accessible: " + JSON_LOC;
                JOptionPane.showMessageDialog(null, msg, "System Error", JOptionPane.ERROR_MESSAGE);
            } catch (InvalidPathException ex) {
                String msg = "Invalid Path: " + JSON_LOC;
                JOptionPane.showMessageDialog(null, msg, "System Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    // represents a save button
    private class SaveAction extends AbstractAction {

        SaveAction() {
            super("Save");
        }

        // EFFECTS: saves the inventory to a save file
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                jsonWriter.open();
                jsonWriter.write(inv);
                jsonWriter.close();
            } catch (FileNotFoundException ex) {
                String msg = "File Not Found: " + JSON_LOC;
                JOptionPane.showMessageDialog(null, msg, "System Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    // represents a quit button
    private class QuitAction extends AbstractAction {

        QuitAction() {
            super("Quit");
        }

        // EFFECTS: closes the application
        @Override
        public void actionPerformed(ActionEvent e) {
            System.exit(0);
        }
    }

    // MODIFIES: this
    // EFFECTS: displays the items currently in inventory
    private void displayInventory() {
        JPanel itemPanel = new JPanel();
        listModel = new DefaultListModel<>();
        for (Item i : inv.getListOfItems()) {
            listModel.addElement(i);
        }
        itemJList = new JList<>();
        itemJList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        itemJList.setCellRenderer(new MyListRenderer());
        itemJList.setModel(listModel);

        JScrollPane scrollPane = new JScrollPane(itemJList);
        scrollPane.setPreferredSize(new Dimension(250, 300));
        itemPanel.add(scrollPane);

        itemPane.add(itemPanel, BorderLayout.WEST);
    }

    // represents a mouse listener
    private class DesktopFocusAction extends MouseAdapter {

        // EFFECTS: sets a mouse click listener for the UI
        @Override
        public void mouseClicked(MouseEvent e) {
            InventoryUI.this.requestFocusInWindow();
        }
    }

    // represents a cell renderer for JLists
    private class MyListRenderer extends DefaultListCellRenderer implements ListCellRenderer<Object> {

        // EFFECTS: sets custom labels for list items
        @Override
        public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected,
                                                      boolean cellHasFocus) {
            Item item = (Item) value;
            setText(item.getProductName());

            if (isSelected) {
                setBackground(list.getSelectionBackground());
                setForeground(list.getSelectionForeground());
            } else {
                setBackground(list.getBackground());
                setForeground(list.getForeground());

            }

            setEnabled(true);
            setFont(list.getFont());

            return this;
        }
    }
}




