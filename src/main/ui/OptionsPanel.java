package ui;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.http.WebSocket;

public class OptionsPanel extends JPanel implements ActionListener {
    private JButton buttonPrint;
    private JButton buttonNew;
    private JButton buttonAdd;
    private JButton buttonSell;
    private JButton buttonRestock;
    private JButton buttonDiscount;
    private JButton buttonLoad;
    private JButton buttonSave;
    private JButton buttonQuit;

    public OptionsPanel() {
        buttonPrint = new JButton("View Items in Inventory");
        buttonNew = new JButton("Add New Item");
        buttonAdd = new JButton("Restock Item");
        buttonSell = new JButton("Sell Item");
        buttonRestock = new JButton("View Items to Restock");
        buttonDiscount = new JButton("Set Discount");
        buttonLoad = new JButton("Load Inventory");
        buttonSave = new JButton("Save Inventory");
        buttonQuit = new JButton("Quit");
        add(buttonPrint);
        add(buttonNew);
        add(buttonAdd);
        add(buttonSell);
        add(buttonRestock);
        add(buttonDiscount);
        add(buttonLoad);
        add(buttonSave);
        add(buttonQuit);
        buttonPrint.addActionListener(this);
        buttonNew.addActionListener(this);
        buttonAdd.addActionListener(this);
        buttonSell.addActionListener(this);
        buttonRestock.addActionListener(this);
        buttonDiscount.addActionListener(this);
        buttonLoad.addActionListener(this);
        buttonSave.addActionListener(this);
        buttonQuit.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        ActionListener actionListener = new OptionsPanel();
        Object choice = e.getSource();
        if (choice.equals(buttonPrint)) {
        } else if (choice.equals(buttonNew)) {
        } else if (choice.equals(buttonAdd)) {
        } else if (choice.equals(buttonSell)) {
        } else if (choice.equals(buttonRestock)) {
        } else if (choice.equals(buttonDiscount)) {
        } else if (choice.equals(buttonLoad)) {
        } else if (choice.equals(buttonSave)) {
        } else if (choice.equals(buttonQuit)) {
        }
    }
}


