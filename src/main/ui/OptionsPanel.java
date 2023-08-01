//package ui;
//
//import javax.swing.*;
//import java.awt.event.ActionEvent;
//import java.awt.event.ActionListener;
//import java.net.http.WebSocket;
//
//public class OptionsPanel extends JPanel {
//    private JButton buttonPrint;
//    private JButton buttonNew;
//    private JButton buttonAdd;
//    private JButton buttonSell;
//    private JButton buttonRestock;
//    private JButton buttonDiscount;
//    private JButton buttonSales;
//    private JButton buttonLoad;
//    private JButton buttonSave;
//    private JButton buttonQuit;
//
//    public OptionsPanel() {
//        add(new JButton("View Items in Inventory"));
//        add(new JButton(new AddItemAction()));
//        add(new JButton("Restock Item"));
//        add(new JButton("Sell Item"));
//        add(new JButton("View Items to Restock"));
//        add(new JButton("Set Discount"));
//        add(new JButton("View Sales"));
//        add(new JButton("Load Inventory"));
//        add(new JButton("Save Inventory"));
//        add(new JButton("Quit"));
//    }
//
//    private class AddItemAction extends AbstractAction {
//
//
//        AddItemAction() {
//            super("Add New Item");
//        }
//
//        @Override
//        public void actionPerformed(ActionEvent e) {
//
//        }
//    }
//
//}
//
//
