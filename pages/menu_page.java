package pages;

import javax.swing.*;
import java.awt.*;

public class menu_page extends JFrame {
    private burger_page burgerPage;
    private cart_page cartPage;
    private contact_page contactPage;

    public menu_page() {
        setTitle("Burger Ordering System - Menu");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 600);
        setLocationRelativeTo(null);
        setResizable(false);

        // Create tabbed pane
        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.setFont(new Font("Arial", Font.BOLD, 14));

        // Create burger, cart and contact pages
        burgerPage = new burger_page();
        cartPage = new cart_page();
        contactPage = new contact_page();

        // Add tabs: Home first, then Burgers and Cart
        // Home tab will switch to Burgers when user clicks "See Menu"
        home_page homePanel = new home_page(() -> {
            // switch to Burgers tab (will be at index 1)
            if (tabbedPane != null) tabbedPane.setSelectedIndex(1);
        });
        tabbedPane.addTab("Home", homePanel);
        tabbedPane.addTab("Burgers", burgerPage.getPanel());
        tabbedPane.addTab("Cart", cartPage.getPanel());
        tabbedPane.addTab("Contact", contactPage.getPanel());

        add(tabbedPane);
        setVisible(true);
    }
}