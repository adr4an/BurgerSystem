package components;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;

// Simple subtotal summary panel: Subtotal, Tax (Free), Delivery (₱40.00), Total, Checkout
public class subtotal_page {

	private JPanel panel;
	private JLabel subtotalLabel;
	private JLabel taxLabel;
	private JLabel deliveryLabel;
	private JLabel estimatedTimeLabel;
	private JLabel totalLabel;

	private static final double DELIVERY_FEE = 40.0;

	public subtotal_page() {
		createPanel();
		refresh();
	}

	private void createPanel() {
		panel = new JPanel(new BorderLayout());
		panel.setBackground(Color.WHITE);

		JPanel inner = new JPanel();
		inner.setLayout(new BoxLayout(inner, BoxLayout.Y_AXIS));
		inner.setBorder(new EmptyBorder(20, 20, 20, 20));
		inner.setBackground(Color.WHITE);

		subtotalLabel = new JLabel("Subtotal");
		subtotalLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		subtotalLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
		inner.add(createRow("Subtotal", "₱0.00"));

		inner.add(Box.createVerticalStrut(8));

		inner.add(createRow("Estimated Tax", "Free"));

		inner.add(Box.createVerticalStrut(8));

		// Estimated delivery time (default)
		inner.add(createRow("Estimated Delivery Time", "30-45 mins"));

		inner.add(Box.createVerticalStrut(8));

		inner.add(createRow("Shipping Fee:", String.format("₱%.2f", DELIVERY_FEE)));

		inner.add(Box.createVerticalStrut(12));
		inner.add(new JSeparator(SwingConstants.HORIZONTAL));
		inner.add(Box.createVerticalStrut(12));

		inner.add(createRow("Total", "₱0.00", true));

		inner.add(Box.createVerticalStrut(18));

		// Payment method (icons + label)
		inner.add(Box.createVerticalStrut(8));
		inner.add(createPaymentRow());
		inner.add(Box.createVerticalStrut(12));

		JButton checkout = new JButton("Checkout");
		checkout.setBackground(Color.BLACK);
		checkout.setForeground(Color.WHITE);
		checkout.setFocusPainted(false);
		checkout.setPreferredSize(new Dimension(200, 40));
		checkout.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
		checkout.setAlignmentX(Component.CENTER_ALIGNMENT);
		checkout.addActionListener(e -> JOptionPane.showMessageDialog(panel, "Successfully checked out!", "Checkout", JOptionPane.INFORMATION_MESSAGE));
		inner.add(checkout);

		panel.add(inner, BorderLayout.NORTH);
	}

	// helper to create a row; if highlightTotal=true render bolder
	private JPanel createRow(String label, String value) {
		return createRow(label, value, false);
	}

	private JPanel createRow(String label, String value, boolean highlightTotal) {
		JPanel row = new JPanel(new BorderLayout());
		row.setBackground(Color.WHITE);
		row.setMaximumSize(new Dimension(Integer.MAX_VALUE, 36));

		JLabel left = new JLabel(label);
		left.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		left.setBorder(new EmptyBorder(4, 4, 4, 4));

		JLabel right = new JLabel(value);
		right.setFont(new Font("Segoe UI", highlightTotal ? Font.BOLD : Font.PLAIN, highlightTotal ? 16 : 14));
		right.setBorder(new EmptyBorder(4, 4, 4, 4));

		row.add(left, BorderLayout.WEST);
		row.add(right, BorderLayout.EAST);

		// save references for later updates (match specific labels to avoid collisions)
		String lower = label.toLowerCase();
		if (lower.contains("subtotal")) subtotalLabel = right;
		else if (lower.contains("tax")) taxLabel = right;
		else if (lower.contains("delivery time") || lower.contains("estimated delivery time") || lower.contains("time")) estimatedTimeLabel = right;
		else if (lower.contains("shipping") || lower.contains("delivery & handling") || lower.contains("shipping fee")) deliveryLabel = right;
		else if (lower.contains("total")) totalLabel = right;

		return row;
	}

	// Custom row showing payment method icon + label
	private JPanel createPaymentRow() {
		JPanel row = new JPanel(new BorderLayout());
		row.setBackground(Color.WHITE);
		row.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));

		JLabel left = new JLabel("Mode of Payment");
		left.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		left.setBorder(new EmptyBorder(4, 4, 4, 4));

		// Right: icon + label (we only accept cash)
		JPanel right = new JPanel(new FlowLayout(FlowLayout.RIGHT, 8, 6));
		right.setBackground(Color.WHITE);
		JLabel iconLabel = new JLabel();
		try {
			BufferedImage img = ImageIO.read(new File("assets/images/payment/cash.png"));
			if (img != null) {
				Image scaled = img.getScaledInstance(28, 28, Image.SCALE_SMOOTH);
				iconLabel.setIcon(new ImageIcon(scaled));
			}
		} catch (Exception ex) {
			iconLabel.setText("");
		}

		JLabel name = new JLabel("Cash");
		name.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		right.add(iconLabel);
		right.add(name);

		row.add(left, BorderLayout.WEST);
		row.add(right, BorderLayout.EAST);
		return row;
	}

	// Recalculate values from cart_page.cart
	public void refresh() {
		double subtotal = 0;
		for (pages.cart_page.CartItem it : pages.cart_page.cart) {
			subtotal += it.price * it.qty;
		}

		if (subtotalLabel != null) subtotalLabel.setText(String.format("₱%.2f", subtotal));
		if (taxLabel != null) taxLabel.setText("Free");
		if (estimatedTimeLabel != null) estimatedTimeLabel.setText("30-45 mins");
		if (deliveryLabel != null) deliveryLabel.setText(String.format("₱%.2f", DELIVERY_FEE));
		if (totalLabel != null) totalLabel.setText(String.format("₱%.2f", subtotal + DELIVERY_FEE));
	}

	public JPanel getPanel() {
		// refresh before showing
		refresh();
		return panel;
	}
}
