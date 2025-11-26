package pages;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import components.cart_item;

public class cart_page {

	public static class CartItem {
		public String name;
		public double price;
		public String imagePath; // full or relative path
		public int qty;

		public CartItem(String name, double price, String imagePath, int qty) {
			this.name = name;
			this.price = price;
			this.imagePath = imagePath;
			this.qty = qty;
		}
	}

	public static final List<CartItem> cart = new ArrayList<>();

	// Keep a reference to the visible page so addItem can refresh the UI
	private static cart_page instance;

	private JPanel panel;
	private JPanel itemsPanel;
	private JLabel subtotalLabel;

	public cart_page() {
		createPanel();
		instance = this; // 
	}

	private void createPanel() {
		panel = new JPanel(new BorderLayout());
		panel.setBackground(new Color(245, 245, 245));

		JLabel title = new JLabel("Your Cart");
		title.setFont(new Font("Segoe UI", Font.BOLD, 20));
		title.setBorder(new EmptyBorder(12,12,12,12));
		panel.add(title, BorderLayout.NORTH);

		itemsPanel = new JPanel();
		itemsPanel.setLayout(new BoxLayout(itemsPanel, BoxLayout.Y_AXIS));
		itemsPanel.setBackground(Color.WHITE);

		JScrollPane scroll = new JScrollPane(itemsPanel,
				JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		scroll.setBorder(BorderFactory.createEmptyBorder(8,8,8,8));
		panel.add(scroll, BorderLayout.CENTER);

		// Bottom area with subtotal and checkout
		JPanel bottom = new JPanel(new BorderLayout());
		bottom.setBorder(new EmptyBorder(10,12,12,12));
		bottom.setBackground(new Color(245, 245, 245));

		subtotalLabel = new JLabel("Subtotal: ₱0.00");
		subtotalLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
		bottom.add(subtotalLabel, BorderLayout.WEST);

		JPanel btns = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		btns.setBackground(new Color(245, 245, 245));
		JButton checkout = new JButton("Checkout");
		checkout.setBackground(new Color(34,139,34));
		checkout.setForeground(Color.WHITE);
		checkout.setFocusPainted(false);
		checkout.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// Open subtotal_page in a modal dialog so user can review totals
				subtotal_page sp = new subtotal_page();
				JDialog dlg = new JDialog((Frame) SwingUtilities.getWindowAncestor(panel), "Order Summary", true);
				dlg.getContentPane().add(sp.getPanel());
				dlg.pack();
				dlg.setLocationRelativeTo(panel);
				dlg.setVisible(true);
			}
		});
		btns.add(checkout);

		bottom.add(btns, BorderLayout.EAST);

		panel.add(bottom, BorderLayout.SOUTH);

		refreshItems();
	}

	// Public API used by other pages to add products to the cart
	public static void addItem(String name, double price, String imagePath) {
		// If same product (by name and price) already in cart, increment qty
		for (CartItem it : cart) {
			if (it.name.equals(name)) {
				it.qty += 1;
				if (instance != null) instance.refreshItems();
				return;
			}
		}

		cart.add(new CartItem(name, price, imagePath, 1));
		if (instance != null) instance.refreshItems();
	}

	// Remove by index
	public static void removeItem(int index) {
		if (index >= 0 && index < cart.size()) {
			cart.remove(index);
			if (instance != null) instance.refreshItems();
		}
	}

	private void refreshItems() {
		itemsPanel.removeAll();

		double subtotal = 0;

		if (cart.isEmpty()) {
			JLabel empty = new JLabel("Your cart is empty.");
			empty.setBorder(new EmptyBorder(12,12,12,12));
			itemsPanel.add(empty);
		} else {
			for (int i = 0; i < cart.size(); i++) {
				CartItem it = cart.get(i);
				subtotal += it.price * it.qty;

				// Use reusable CartItemView component
				cart_item civ = new cart_item(it,
					() -> { cart.remove(it); refreshItems(); },
					() -> { refreshItems(); }
				);
				itemsPanel.add(civ);
			}
		}

		subtotalLabel.setText(String.format("Subtotal: ₱%.2f", subtotal));
		itemsPanel.revalidate();
		itemsPanel.repaint();
	}

	public JPanel getPanel() {
		return panel;
	}

}