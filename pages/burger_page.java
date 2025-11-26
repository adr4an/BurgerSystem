package pages;

import components.ProductCard;
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class burger_page {
    private JPanel panel;
    private List<ProductCard> productCards = new ArrayList<>();

    public burger_page() {
        createPanel(); // 
    }

    private void createPanel() {
        panel = new JPanel(new BorderLayout());
        panel.setBackground(new Color(240, 240, 240));

        // Main content area with scroll
        JPanel contentPanel = new JPanel();
        contentPanel.setBackground(new Color(240, 240, 240));
        contentPanel.setLayout(new GridLayout(3, 4, 10, 10));
        contentPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Burger products with prices
        String[] burgerNames = {
            "Grand Angus", "Bacon Burger", "BBQ Veggie", "Grilled Chicken Rs.500",
            "Chicken Veggie", "Big Mac", "Big Fish", "Double Stack",
            "Hawaiian", "Double BBQ", "Hash & Cheese", "Grilled Chicken Rs.500"
        };

        double[] burgerPrices = { 49,49,49,49,49,49,49,49,49,49,49,49 };

        String[] burgerImages = {
            "burger1.png", "burger2.png", "burger3.png", "burger4.png",
            "burger5.png", "burger6.png", "burger7.png", "burger8.png",
            "burger9.png", "burger10.png", "burger1.png", "burger2.png"
        };

        for (int i = 0; i < burgerNames.length; i++) {
            ProductCard card = createProductCard(burgerNames[i], burgerPrices[i], burgerImages[i]);
            productCards.add(card);
            contentPanel.add(card.getPanel());
        }
        // put content in a scroll pane
        JScrollPane scroll = new JScrollPane(contentPanel,
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scroll.getVerticalScrollBar().setUnitIncrement(16);
        scroll.setBorder(null);

        panel.add(scroll, BorderLayout.CENTER);
    }

    // returns a new ProductCard instance
    private ProductCard createProductCard(String name, double price, String imagePath) {
        // pass onUpdate to refresh subtotal and onBuyNow to add item to static cart
        return new ProductCard(name, price, imagePath, "assets/images/burgers/",
                () -> this.updateSubtotal(),
                () -> pages.cart_page.addItem(name, price, "assets/images/burgers/" + imagePath));
    }

    public void updateSubtotal() {
        double total = 0;
        for (ProductCard card : productCards) {
            total += card.getTotal();
        }
        // currently we don't show a persistent subtotal panel; log for debugging
        System.out.println("Subtotal: " + total);
    }

    public JPanel getPanel() {
        return panel;
    }

}
