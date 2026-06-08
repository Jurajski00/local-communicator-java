package project.ui;

import javax.swing.*;
import java.awt.*;

import project.ChatSession;
import project.core.User;
import project.data.UserRepository;

public class LoginWindow {
    private JFrame frame;
    private JPanel panel;
    private JTextField usernameField;
    private JButton loginButton;
    private JButton registerButton;
    private UserRepository userRepository = new UserRepository();

    public LoginWindow() {
        initFrame();
        initPanel();
        createUI();
        frame.add(panel);
        show();
    }

    public void register() {
        String username = usernameField.getText().trim();
        if (username.isEmpty()) {
            JOptionPane.showMessageDialog(frame, "Please enter a username");
            return;
        }
        if (userRepository.isUserRegistered(username)) {
            JOptionPane.showMessageDialog(frame, "User already exists");
            return;
        }
        userRepository.addUser(username);
        JOptionPane.showMessageDialog(frame, "User registered successfully");
    }

    public void login() {
        String username = usernameField.getText().trim();
        if (username.isEmpty()) {
            JOptionPane.showMessageDialog(frame, "Please enter a username");
            return;
        }
        if (!userRepository.isUserRegistered(username)) {
            JOptionPane.showMessageDialog(frame, "User not found");
            return;
        }
        JOptionPane.showMessageDialog(frame, "Welcome " + username);
        new ChatWindow(userRepository.getUser(username), userRepository.getUser("Natalia"));
    }

    private void createUI() {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.insets = new Insets(0, 0, 10, 0);
        panel.add(new JLabel("Username: "), gbc);
        usernameField = new JTextField();
        usernameField.setPreferredSize(new Dimension(200, 40));
        panel.add(usernameField, gbc);
        gbc.gridwidth = 1;
        gbc.gridx = GridBagConstraints.RELATIVE;
        loginButton = new JButton("Login");
        loginButton.setPreferredSize(new Dimension(100, 40));
        loginButton.addActionListener(e -> {
            login();
        });
        panel.add(loginButton, gbc);
        registerButton = new JButton("Register");
        registerButton.setPreferredSize(new Dimension(100, 40));
        registerButton.addActionListener(e -> {
            register();
        });
        panel.add(registerButton, gbc);
    }

    private void initPanel() {
        panel = new JPanel();
        panel.setLayout(new GridBagLayout());
    }

    private void initFrame() {
        frame = new JFrame("Login");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 800);
        frame.setLocationRelativeTo(null);
    }

    private void show() {
        frame.setVisible(true);
    }

//    public static void main(String[] args) {
//        SwingUtilities.invokeLater(() -> {
//            new LoginWindow();
//        });
//    }
}
