package project.ui;

import javax.swing.*;
import java.awt.*;

public class LoginWindow {
    private JFrame frame;
    private JPanel panel;
    private JTextField usernameField;
    private JButton loginButton;

    public LoginWindow() {
        initFrame();
        initPanel();
        createUI();
        frame.add(panel, BorderLayout.CENTER);
        show();
    }

    private void login() {
        String username = usernameField.getText().trim();
        if (username.isEmpty()) {
            JOptionPane.showMessageDialog(frame, "Please enter a username");
            return;
        }
        JOptionPane.showMessageDialog(frame, "Welcome " + username);
    }

    private void createUI() {
        GridBagConstraints gbc =  new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.insets = new Insets(0, 0, 10, 1);
        panel.add(new JLabel("Username: "), gbc);
        usernameField = new JTextField();
        usernameField.setPreferredSize(new Dimension(200, 40));
        panel.add(usernameField, gbc);
        loginButton = new JButton("Login");
        loginButton.setPreferredSize(new Dimension(100, 40));
        loginButton.addActionListener(e -> {login();});
        panel.add(loginButton, gbc);
    }

    private void initPanel() {
        panel = new JPanel();
        panel.setLayout(new GridBagLayout());
    }

    private void initFrame() {
        frame = new JFrame("Login");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 800);
        frame.setLayout(new BorderLayout());
        frame.setLocationRelativeTo(null);
    }

    private void show() {
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {new LoginWindow();});
    }
}
