package pages;
import javax.swing.*;

import java.awt.*;
import java.awt.event.*;
import java.io.*;

public class login_page extends JFrame {
    private JTextField emailField;
    private JPasswordField passwordField;
    private JButton loginButton;

    public login_page() {
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
        JLabel title = new JLabel("Welcome back, you've been missed!");
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
        passLabel.setFont(new Font("Poppins", Font.BOLD, 16));
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.EAST;
        gbc.insets = new Insets(8, 40, 8, 10);
        content.add(passLabel, gbc);

        passwordField = new JPasswordField(20);
        passwordField.setFont(new Font("Poppins", Font.PLAIN, 14));
        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(8, 10, 8, 40);
        content.add(passwordField, gbc);

        // Login button
        loginButton = new JButton("Login");
        loginButton.setFont(new Font("Poppins", Font.PLAIN, 14));
        loginButton.setBackground(new Color(220, 230, 240));
        loginButton.setFocusPainted(false);
        gbc.gridx = 1;
        gbc.gridy = 3;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(20, 10, 8, 40);
        content.add(loginButton, gbc);

        // Register Now link below
        JLabel registerLink = new JLabel("<html><a href=''>Register Now</a></html>");
        registerLink.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        registerLink.setForeground(new Color(0, 102, 204));
        registerLink.setFont(new Font("Arial", Font.PLAIN, 13));
        gbc.gridx = 1;
        gbc.gridy = 4;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(0, 10, 20, 40);
        content.add(registerLink, gbc);

        add(content);

        // Action: validate inputs and show messages (file-backed auth)
        ActionListener doLogin = e -> {
            String email = emailField.getText().trim();
            String pass = new String(passwordField.getPassword()).trim();

            if (email.isEmpty() || pass.isEmpty()) {
                JOptionPane.showMessageDialog(this,
                        "Please enter both email and password",
                        "Input Error",
                        JOptionPane.WARNING_MESSAGE);
                return;
            }

            if (authenticate(email, pass)) {
                JOptionPane.showMessageDialog(this, "Login successful", "Success", JOptionPane.INFORMATION_MESSAGE);
                dispose();
                SwingUtilities.invokeLater(menu_page::new);
            } else {
                JOptionPane.showMessageDialog(this, "Invalid email or password", "Login Failed", JOptionPane.ERROR_MESSAGE);
            }
        };

        loginButton.addActionListener(doLogin);

        // Register link action
        registerLink.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                dispose();
                SwingUtilities.invokeLater(register_page::new);
            }
        });

        setVisible(true);
    }

        // Authenticate against users.txt (format: username,password per line).
        // Returns true if credentials match a line in the file or the admin fallback.
        private boolean authenticate(String username, String password) {
            // keep admin fallback
            if ("admin".equals(username) && "admin".equals(password)) return true;

            File f = new File("users.txt");
            // Check if the users file exists
            if (!f.exists()) return false;

            try (BufferedReader br = new BufferedReader(new FileReader(f))) {
                String line;
                while ((line = br.readLine()) != null) {
                    String[] parts = line.split(",");
                    if (parts.length >= 2) {
                        String u = parts[0].trim();
                        String p = parts[1].trim();
                        if (u.equals(username) && p.equals(password)) return true;
                    }
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }

            return false;
        }
}
