package components;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

// Redesigned product card: image top, details bottom, spinner + Buy Now button
public class ProductCard {
    private JPanel panel;
    // selection state — checkbox was removed, use an internal flag
    private boolean selected = false;
    private double price;
    private Runnable onUpdate; // callback to notify when state changes
    private Runnable onBuyNow; // callback to navigate to cart or handle buy action

    // Backwards-compatible constructor (old signature)
    public ProductCard(String name, double price, String imagePath, String imagesBasePath, Runnable onUpdate) {
        this(name, price, imagePath, imagesBasePath, onUpdate, null);
    }

    // New constructor with onBuyNow callback
    public ProductCard(String name, double price, String imagePath, String imagesBasePath, Runnable onUpdate, Runnable onBuyNow) {
        this.price = price;
        this.onUpdate = onUpdate;
        this.onBuyNow = onBuyNow;
        createCard(name, imagePath, imagesBasePath);
    }

    private void createCard(String name, String imagePath, String imagePath2) {
        panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200), 1),
                new EmptyBorder(8, 8, 8, 8)));
        panel.setPreferredSize(new Dimension(220, 360));

        // Top: image area with light beige background
        JPanel imageArea = new JPanel(new GridBagLayout());
        imageArea.setBackground(new Color(245, 242, 240));
        imageArea.setPreferredSize(new Dimension(200, 220));

        JLabel imageLabel = new JLabel();
        imageLabel.setHorizontalAlignment(JLabel.CENTER);
        try {
            ImageIcon icon = new ImageIcon(imagePath2 + imagePath);
            Image img = icon.getImage().getScaledInstance(200, 200, Image.SCALE_SMOOTH);
            imageLabel.setIcon(new ImageIcon(img));
        } catch (Exception e) {
            imageLabel.setText("No Image");
        }
        imageArea.add(imageLabel);

        panel.add(imageArea, BorderLayout.NORTH);

        // Bottom: details area
        JPanel info = new JPanel();
        info.setLayout(new BoxLayout(info, BoxLayout.Y_AXIS));
        info.setBackground(Color.WHITE);
        info.setBorder(new EmptyBorder(10, 6, 6, 6));

        // Row: name
        JLabel nameLabel = new JLabel(name);
        nameLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        nameLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        info.add(nameLabel);

        info.add(Box.createRigidArea(new Dimension(0, 6)));

        // Row: price
        JLabel priceLabel = new JLabel(String.format("Price : %.2f", price));
        priceLabel.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        priceLabel.setForeground(new Color(40, 40, 40));
        priceLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        info.add(priceLabel);

        info.add(Box.createVerticalGlue());

        // Row: buy now button (quantity removed)
        JPanel actionRow = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 0));
        actionRow.setBackground(Color.WHITE);
        actionRow.setAlignmentX(Component.LEFT_ALIGNMENT);

        JButton buyNow = new JButton("Add to cart");
        buyNow.setBackground(new Color(255, 140, 0));
        buyNow.setForeground(Color.WHITE);
        buyNow.setFocusPainted(false);
        buyNow.setBorder(BorderFactory.createEmptyBorder(8, 14, 8, 14));
        buyNow.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        buyNow.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // mark selected when buying
                selected = true;
                if (onUpdate != null) onUpdate.run();
                if (onBuyNow != null) onBuyNow.run();
            }
        });
        actionRow.add(buyNow);

        info.add(actionRow);

        panel.add(info, BorderLayout.CENTER);
    }

    // Calculates total price based on selection and quantity
    public double getTotal() {
        if (selected) {
            int qty = 1; // default quantity when spinner removed
            return price * qty;
        }
        return 0;
    }

    public JPanel getPanel() {
        return panel;
    }

    public boolean isSelected() {
        return selected;
    }
}
