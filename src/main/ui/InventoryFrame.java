package ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class InventoryFrame extends JFrame {
    private Container contentPane;
    public InventoryFrame() {
        super("Inventory Management System");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(1000, 800);

        Container contentPane = getContentPane();
        contentPane.add(new OptionsPanel());
    }



}
