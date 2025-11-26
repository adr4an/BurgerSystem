package components;

import pages.cart_page.CartItem;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

// Reusable cart row component showing image, name, price, qty spinner and remove button
public class cart_item extends JPanel {
    private JSpinner qtySpinner;

    public cart_item(CartItem item, Runnable onRemove, Runnable onChange) {
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);
        setBorder(BorderFactory.createMatteBorder(0,0,1,0,new Color(230,230,230)));
        setMaximumSize(new Dimension(Integer.MAX_VALUE, 100));

        // image
        JLabel imgLabel = new JLabel();
        imgLabel.setPreferredSize(new Dimension(80,80));
        try {
            ImageIcon ic = new ImageIcon(item.imagePath);
            Image img = ic.getImage().getScaledInstance(80,80, Image.SCALE_SMOOTH);
            imgLabel.setIcon(new ImageIcon(img));
        } catch (Exception ex) {
            imgLabel.setText("No Image");
        }
        add(imgLabel, BorderLayout.WEST);

        // center: name + price
        JPanel center = new JPanel();
        center.setLayout(new BoxLayout(center, BoxLayout.Y_AXIS));
        center.setBackground(Color.WHITE);
        JLabel name = new JLabel(item.name);
        name.setFont(new Font("Segoe UI", Font.BOLD, 14));
        JLabel price = new JLabel(String.format("₱%.2f", item.price));
        price.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        center.add(name);
        center.add(Box.createVerticalStrut(6));
        center.add(price);
        add(center, BorderLayout.CENTER);

        // right: qty spinner + remove
        JPanel right = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        right.setBackground(Color.WHITE);

        qtySpinner = new JSpinner(new SpinnerNumberModel(item.qty, 1, 99, 1));
        qtySpinner.setPreferredSize(new Dimension(60, 24));
        qtySpinner.addChangeListener(ev -> {
            try {
                item.qty = (Integer) qtySpinner.getValue();
            } catch (Exception ex) {
                item.qty = 1;
                qtySpinner.setValue(1);
            }
            if (onChange != null) onChange.run();
        });
        right.add(qtySpinner);

        JButton remove = new JButton("Remove");
        remove.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (onRemove != null) onRemove.run();
            }
        });
        right.add(remove);

        add(right, BorderLayout.EAST);
    }
}
