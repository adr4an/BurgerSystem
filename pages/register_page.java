package pages;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;

public class register_page extends JFrame {
	private JTextField emailField;
	private JPasswordField passwordField;

	public register_page() {
		setTitle("Burger Shop");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(620, 340);
		setLocationRelativeTo(null);
		setResizable(false);

		JPanel content = new JPanel(new GridBagLayout());
		content.setBackground(new Color(240, 240, 240));
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.insets = new Insets(8, 8, 8, 8);

		// Title
		JLabel title = new JLabel("Let's create an account for you!");
		title.setFont(new Font("Arial", Font.BOLD, 26));
		title.setForeground(new Color(0, 0, 139));
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.gridwidth = 2;
		gbc.anchor = GridBagConstraints.CENTER;
		gbc.insets = new Insets(24, 0, 24, 0);
		content.add(title, gbc);

		// Email label
		JLabel userLabel = new JLabel("Email");
		userLabel.setFont(new Font("Arial", Font.BOLD, 16));
		gbc.gridwidth = 1;
		gbc.gridx = 0;
		gbc.gridy = 1;
		gbc.anchor = GridBagConstraints.EAST;
		gbc.insets = new Insets(8, 40, 8, 10);
		content.add(userLabel, gbc);

		emailField = new JTextField(20);
		emailField.setFont(new Font("Arial", Font.PLAIN, 14));
		gbc.gridx = 1;
		gbc.gridy = 1;
		gbc.anchor = GridBagConstraints.WEST;
		gbc.insets = new Insets(8, 10, 8, 40);
		content.add(emailField, gbc);

		// Password label
		JLabel passLabel = new JLabel("Password");
		passLabel.setFont(new Font("Arial", Font.BOLD, 16));
		gbc.gridx = 0;
		gbc.gridy = 2;
		gbc.anchor = GridBagConstraints.EAST;
		gbc.insets = new Insets(8, 40, 8, 10);
		content.add(passLabel, gbc);

		passwordField = new JPasswordField(20);
		passwordField.setFont(new Font("Arial", Font.PLAIN, 14));
		gbc.gridx = 1;
		gbc.gridy = 2;
		gbc.anchor = GridBagConstraints.WEST;
		gbc.insets = new Insets(8, 10, 8, 40);
		content.add(passwordField, gbc);

		// Register button
		JButton registerButton = new JButton("Register");
		registerButton.setFont(new Font("Poppins", Font.PLAIN, 14));
		registerButton.setBackground(new Color(220, 230, 240));
		registerButton.setFocusPainted(false);
		gbc.gridx = 1;
		gbc.gridy = 3;
		gbc.anchor = GridBagConstraints.CENTER;
		gbc.insets = new Insets(20, 10, 20, 40);
		content.add(registerButton, gbc);

		// Back to Login link
		JLabel loginLink = new JLabel("<html><a href=''>Back to Login</a></html>");
		loginLink.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		loginLink.setForeground(new Color(0, 102, 204));
		loginLink.setFont(new Font("Arial", Font.PLAIN, 13));
		gbc.gridx = 1;
		gbc.gridy = 4;
		gbc.anchor = GridBagConstraints.CENTER;
		gbc.insets = new Insets(0, 10, 20, 40);
		content.add(loginLink, gbc);

		add(content);

		// Actions
		registerButton.addActionListener(e -> registerUser());
		loginLink.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				dispose();
				SwingUtilities.invokeLater(login_page::new);
			}
		});

		setVisible(true);
	}

	private void registerUser() {
		String email = emailField.getText().trim();
		String password = new String(passwordField.getPassword()).trim();

		if (email.isEmpty() || password.isEmpty()) {
			JOptionPane.showMessageDialog(this, "Please enter both email and password", "Input Error", JOptionPane.WARNING_MESSAGE);
			return;
		}

		// check duplicate email in users.txt
		File f = new File("users.txt");
		if (f.exists()) {
			try (BufferedReader br = new BufferedReader(new FileReader(f))) {
				String line;
				while ((line = br.readLine()) != null) {
					String[] parts = line.split(",");
					if (parts.length >= 1) {
						String existing = parts[0].trim();
						if (existing.equalsIgnoreCase(email)) {
							JOptionPane.showMessageDialog(this, "This email is already registered.", "Duplicate", JOptionPane.WARNING_MESSAGE);
							return;
						}
					}
				}
			} catch (IOException ex) {
				// Show a user-friendly error and log to stderr
				JOptionPane.showMessageDialog(this, "Error reading users file.", "I/O Error", JOptionPane.ERROR_MESSAGE);
				System.err.println("Error reading users.txt: " + ex.getMessage());
			}
		}

		try (FileWriter fw = new FileWriter("users.txt", true);
			 BufferedWriter bw = new BufferedWriter(fw);
			 PrintWriter out = new PrintWriter(bw)) {
			out.println(email + "," + password);
			JOptionPane.showMessageDialog(this, "Registration successful", "Success", JOptionPane.INFORMATION_MESSAGE);
			dispose();
			SwingUtilities.invokeLater(login_page::new);
		} catch (IOException ex) {
			JOptionPane.showMessageDialog(this, "Error saving user.", "I/O Error", JOptionPane.ERROR_MESSAGE);
			System.err.println("Error writing users.txt: " + ex.getMessage());
		}
	}
}
