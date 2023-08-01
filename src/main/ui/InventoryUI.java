package ui;

import model.Inventory;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class InventoryUI extends JFrame {
    private static final int WIDTH = 1000;
    private static final int HEIGHT = 800;
    private Inventory inv;
    private JDesktopPane desktop;
    private JInternalFrame menuPane;

    public InventoryUI() {
        inv = new Inventory();

        desktop = new JDesktopPane();
        desktop.addMouseListener(new DesktopFocusAction());
        menuPane = new JInternalFrame("Options", true, false, false, false);
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
        buttons.setLayout(new GridLayout(2,5));
        add(new JButton("View Items in Inventory"));
        add(new JButton("new QuitAction()"));
        add(new JButton("Restock Item"));
        add(new JButton("Sell Item"));
        add(new JButton("View Items to Restock"));
        add(new JButton("Set Discount"));
        add(new JButton("View Sales"));
        add(new JButton("Load Inventory"));
        add(new JButton("Save Inventory"));
        add(new JButton(new QuitAction()));

        menuPane.add(buttons, BorderLayout.WEST);
    }

    private class QuitAction extends AbstractAction {


        QuitAction() {
            super("Quit");
        }

        @Override
        public void actionPerformed(ActionEvent e) {

        }
    }

    private class DesktopFocusAction extends MouseAdapter {
        @Override
        public void mouseClicked(MouseEvent e) {
            InventoryUI.this.requestFocusInWindow();
        }
    }



}
