package pages;

import javax.swing.*;
import java.awt.*;

public class menu_page extends JFrame {
    private burger_page burgerPage;
    private cart_page cartPage;

    public menu_page() {
        setTitle("Burger Ordering System - Menu");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1400, 750);
        setLocationRelativeTo(null);
        setResizable(false);

        // Create tabbed pane
        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.setFont(new Font("Arial", Font.BOLD, 14));

        // Create burger and cart pages
        burgerPage = new burger_page();
        cartPage = new cart_page();

        // Add tabs
        tabbedPane.addTab("Burgers", burgerPage.getPanel());
        tabbedPane.addTab("Cart", cartPage.getPanel());

        add(tabbedPane);
        setVisible(true);
    }
}