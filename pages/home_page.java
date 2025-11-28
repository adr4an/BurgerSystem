package pages;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;

// Reusable home panel that can be embedded in a tabbed pane
public class home_page extends JPanel {

    private JPanel panel;

    // Runnable represnets the action to perform when "See Menu" is clicked
    public home_page(Runnable onSeeMenu) {
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);
        createPanel(onSeeMenu);
    }

    private void createPanel(Runnable onSeeMenu) {
        JPanel content = new JPanel(new BorderLayout());
        content.setBorder(new EmptyBorder(40, 40, 40, 40));
        content.setBackground(Color.WHITE);

        // Left: text (narrow column aligned like the reference)
        JPanel left = new JPanel(new GridBagLayout());
        left.setBackground(Color.WHITE);
            left.setPreferredSize(new Dimension(380, 0));
        GridBagConstraints lg = new GridBagConstraints();
        lg.gridx = 0;
        lg.anchor = GridBagConstraints.NORTHWEST;
        lg.insets = new Insets(0, 0, 0, 0);

        JPanel leftContent = new JPanel();
        leftContent.setBackground(Color.WHITE);
        leftContent.setLayout(new BoxLayout(leftContent, BoxLayout.Y_AXIS));

        JLabel title = new JLabel("<html>Savioury &<br>Delicious</html>");
        title.setFont(new Font("Segoe UI", Font.BOLD, 40));
        title.setForeground(new Color(25, 25, 112));
        title.setAlignmentX(Component.LEFT_ALIGNMENT);
        leftContent.add(title);

        leftContent.add(Box.createVerticalStrut(12));

        JLabel desc = new JLabel("<html><div style='width:320px;'>TASTE THE PERFECT COMBINATION OF CRISPY, JUICY, AND IRRESISTIBLY SAVORY GOODNESS.</div></html>");
        desc.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        desc.setForeground(new Color(100, 100, 100));
        desc.setAlignmentX(Component.LEFT_ALIGNMENT);
        leftContent.add(desc);

        leftContent.add(Box.createVerticalStrut(18));

        JButton seeMenu = new JButton("See Menu");
        seeMenu.setBackground(new Color(255, 179, 71));
        seeMenu.setForeground(Color.WHITE);
        seeMenu.setFocusPainted(false);
        seeMenu.setBorder(BorderFactory.createEmptyBorder(10, 24, 10, 24));
        seeMenu.setFont(new Font("Segoe UI", Font.BOLD, 18));
        seeMenu.setAlignmentX(Component.LEFT_ALIGNMENT);
        seeMenu.addActionListener(e -> {
            if (onSeeMenu != null) onSeeMenu.run();
        });
        leftContent.add(seeMenu);

        left.add(leftContent, lg);
        content.add(left, BorderLayout.WEST);

        // Right: hero image (uses assets/images/store/hero.png by default)
        JPanel right = new JPanel(new GridBagLayout());
        right.setBackground(Color.WHITE);
        JLabel imageLabel = new JLabel();
        imageLabel.setHorizontalAlignment(JLabel.CENTER);
        try {
            BufferedImage src = ImageIO.read(new File("assets/images/store/welcomeBG.png"));
            if (src != null) {
                int w = Math.max(1, (int) Math.round(src.getWidth() * Math.min(800.0/src.getWidth(), 480.0/src.getHeight())));
                int h = Math.max(1, (int) Math.round(src.getHeight() * Math.min(800.0/src.getWidth(), 480.0/src.getHeight())));
                BufferedImage scaled = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
                Graphics2D g2 = scaled.createGraphics();
                g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
                g2.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.drawImage(src, 0, 0, w, h, null);
                g2.dispose();
                imageLabel.setIcon(new ImageIcon(scaled));
            } else {
                imageLabel.setText("No Image");
            }
        } catch (Exception ex) {
            imageLabel.setText("No Image");
        }

        right.add(imageLabel);
        content.add(right, BorderLayout.CENTER);

        add(content, BorderLayout.CENTER);
    }

    public JPanel getPanel(){
        return panel;
    }
}
