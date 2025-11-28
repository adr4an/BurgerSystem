package pages;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;

public class contact_page extends JPanel {

	public contact_page() {
		setLayout(new BorderLayout());
		setBackground(Color.WHITE);
		createUI();
	}

	private void createUI() {
		JPanel content = new JPanel(new BorderLayout());
		content.setBorder(new EmptyBorder(20, 20, 20, 20));
		content.setBackground(Color.WHITE);

		// Left: form
		JPanel left = new JPanel();
		left.setBackground(Color.WHITE);
		left.setLayout(new BoxLayout(left, BoxLayout.Y_AXIS));
		left.setPreferredSize(new Dimension(520, 0));

		JLabel header = new JLabel("Contact us");
		header.setFont(new Font("Segoe UI", Font.BOLD, 32));
		header.setForeground(new Color(34, 79, 140));
		header.setAlignmentX(Component.LEFT_ALIGNMENT);
		left.add(header);

		left.add(Box.createVerticalStrut(18));

		left.add(createLabeledField("Name"));
		left.add(Box.createVerticalStrut(10));
		left.add(createLabeledField("Email"));
		left.add(Box.createVerticalStrut(10));
		left.add(createTextAreaField("Message", 6));
		left.add(Box.createVerticalStrut(18));

		JButton send = new JButton("Send Message");
		send.setBackground(new Color(8, 174, 201));
		send.setForeground(Color.WHITE);
		send.setFocusPainted(false);
		send.setBorder(BorderFactory.createEmptyBorder(12, 24, 12, 24));
		send.setFont(new Font("Segoe UI", Font.BOLD, 14));
		send.setAlignmentX(Component.LEFT_ALIGNMENT);
		send.addActionListener(e -> JOptionPane.showMessageDialog(this, "Message sent (demo)."));
		left.add(send);

		content.add(left, BorderLayout.WEST);

		// Right: illustration
		JPanel right = new JPanel(new GridBagLayout());
		right.setBackground(Color.WHITE);
		JLabel imgLabel = new JLabel();
		try {
			BufferedImage img = ImageIO.read(new File("assets/images/store/contactService.png"));
			if (img != null) {
				int maxW = 420, maxH = 360;
				double scale = Math.min((double) maxW / img.getWidth(), (double) maxH / img.getHeight());
				int w = Math.max(1, (int) Math.round(img.getWidth() * scale));
				int h = Math.max(1, (int) Math.round(img.getHeight() * scale));
				Image scaled = img.getScaledInstance(w, h, Image.SCALE_SMOOTH);
				imgLabel.setIcon(new ImageIcon(scaled));
			}
		} catch (Exception ex) {
			imgLabel.setText("No Image");
		}
		right.add(imgLabel);
		content.add(right, BorderLayout.CENTER);

		add(content, BorderLayout.CENTER);
	}

	private JComponent createLabeledField(String label) {
		JPanel p = new JPanel();
		p.setLayout(new BorderLayout());
		p.setBackground(Color.WHITE);
		JLabel l = new JLabel(label);
		l.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		JTextField tf = new JTextField(20);
		tf.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		tf.setBorder(BorderFactory.createLineBorder(new Color(220, 230, 235), 1));
		p.add(l, BorderLayout.NORTH);
		p.add(tf, BorderLayout.CENTER);
		p.setAlignmentX(Component.LEFT_ALIGNMENT);
		return p;
	}

	private JComponent createTextAreaField(String label, int rows) {
		JPanel p = new JPanel();
		p.setLayout(new BorderLayout());
		p.setBackground(Color.WHITE);
		JLabel l = new JLabel(label);
		l.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		JTextArea ta = new JTextArea(rows, 1);
		ta.setLineWrap(true);
		ta.setWrapStyleWord(true);
		ta.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		ta.setBorder(BorderFactory.createLineBorder(new Color(220, 230, 235), 1));
		JScrollPane sp = new JScrollPane(ta);
		sp.setPreferredSize(new Dimension(420, rows * 24));
		p.add(l, BorderLayout.NORTH);
		p.add(sp, BorderLayout.CENTER);
		p.setAlignmentX(Component.LEFT_ALIGNMENT);
		return p;
	}

	public JPanel getPanel() {
		return this;
	}
}
