package ui;

import model.Inventory;
import model.Item;
import persistence.Reader;
import persistence.Writer;

import javax.swing.*;
import javax.swing.event.ListDataListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.InvalidPathException;
import java.util.List;

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

    public InventoryUI() {
        inv = new Inventory();
        jsonReader = new Reader(JSON_LOC);
        jsonWriter = new Writer(JSON_LOC);

        desktop = new JDesktopPane();
        desktop.addMouseListener(new DesktopFocusAction());
        menuPane = new JInternalFrame("Options", false, false, false, false);
        itemPane = new JInternalFrame("Items", false, false, false, false);
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

    private void buttonPanel() {
        JPanel buttons = new JPanel();
        buttons.setLayout(new GridLayout(0, 3));
        buttons.add(new JButton(new NewAction()));
        buttons.add(new JButton("Restock Item"));
        buttons.add(new JButton("Sell Item"));
        buttons.add(new JButton(new ViewRestockAction()));
        buttons.add(new JButton(new DiscountAction()));
        buttons.add(new JButton(new SalesAction()));
        buttons.add(new JButton(new LoadAction()));
        buttons.add(new JButton(new SaveAction()));
        buttons.add(new JButton(new QuitAction()));

        menuPane.add(buttons, BorderLayout.WEST);
    }

    private class NewAction extends AbstractAction {

        NewAction() {
            super("Add New Item");
        }

        // item display addition implemented with reference from: https://stackoverflow.com/a/4262716
        @Override
        public void actionPerformed(ActionEvent e) {
            String name = JOptionPane.showInputDialog(null, "Enter Item Name");
            if (name == null || inv.isPresent(name)) {
                String msg;
                if (name == null) {
                    msg = "No Name Entered";
                } else {
                    msg = "Item With the Same Name Already Exists";
                }
                JOptionPane.showMessageDialog(null, msg, "Input Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            int quantity = Integer.parseInt(JOptionPane.showInputDialog(null, "Enter Quantity in Stock"));
            if (quantity < 0) {
                String msg = "Quantity Cannot be Negative";
                JOptionPane.showMessageDialog(null, msg, "Input Error", JOptionPane.ERROR_MESSAGE);
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
        }
    }

    private class ViewRestockAction extends AbstractAction {

        ViewRestockAction() {
            super("View Items to Restock");
        }

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

    private class DiscountAction extends AbstractAction {

        DiscountAction() {
            super("Set Discount");
        }

        @Override
        public void actionPerformed(ActionEvent e) {
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
        }
    }

    private class SalesAction extends AbstractAction {

        SalesAction() {
            super("View Amount Earned in Sales");
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            String msg = "Amount Earned in Sales is: $" + inv.getSales();
            JOptionPane.showMessageDialog(null, msg, "Sales", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private class LoadAction extends AbstractAction {

        LoadAction() {
            super("Load Save");
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                inv = jsonReader.read();
                listModel.clear();
                for (Item i : inv.getListOfItems()) {
                    listModel.addElement(i);
                }
            } catch (IOException er) {
                String msg = "File Not Accessible: " + JSON_LOC;
                JOptionPane.showMessageDialog(null, msg, "System Error", JOptionPane.ERROR_MESSAGE);
            } catch (InvalidPathException er) {
                String msg = "Invalid Path: " + JSON_LOC;
                JOptionPane.showMessageDialog(null, msg, "System Error", JOptionPane.ERROR_MESSAGE);
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
                JOptionPane.showMessageDialog(null, msg, "System Error", JOptionPane.ERROR_MESSAGE);
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

    private void displayInventory() {
        JPanel itemPanel = new JPanel();
        listModel = new DefaultListModel<>();
        for (Item i : inv.getListOfItems()) {
            listModel.addElement(i);
        }
        JList<Item> itemJList = new JList<>();
        itemJList.setCellRenderer(new MyListRenderer());
        itemJList.setModel(listModel);
//        itemJList.addListSelectionListener(new ListSelectionListener() {
//            @Override
//            public void valueChanged(ListSelectionEvent e) {
//                JList list = (JList) (e.getSource());
//                JOptionPane.showMessageDialog(itemPanel, list.getSelectedValue());
//            }
//        });
        JScrollPane scrollPane = new JScrollPane(itemJList);
        itemPanel.add(scrollPane);

        itemPane.add(itemPanel, BorderLayout.WEST);
    }

    private class DesktopFocusAction extends MouseAdapter {
        @Override
        public void mouseClicked(MouseEvent e) {
            InventoryUI.this.requestFocusInWindow();
        }
    }

    // implementation from https://www.youtube.com/watch?v=cjVB8wT4kA0
    // displays custom labels for Items
    private class MyListRenderer extends DefaultListCellRenderer implements ListCellRenderer<Object> {

        @Override
        public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
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




