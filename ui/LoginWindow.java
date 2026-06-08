package project.ui;

import project.core.User;
import project.data.UserRepository;
import project.exception.DataAccessException;
import project.exception.EmptyInputException;
import project.exception.UserAlreadyExistsException;
import project.exception.UserNotFoundException;
import project.service.MessengerService;
import project.service.UserService;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class LoginWindow {
    private final UserRepository userRepository;
    private final UserService userService;

    private JFrame frame;
    private JPanel panel;
    private JTextField usernameField;
    private JButton loginButton;
    private JButton registerButton;
    private JButton renameButton;

    public LoginWindow() throws DataAccessException {
        this.userRepository = new UserRepository();
        this.userService = new UserService(userRepository);

        initFrame();
        initPanel();
        createUI();

        frame.add(panel);
        show();
    }

    public void register() {
        try {
            userService.register(usernameField.getText());
            JOptionPane.showMessageDialog(frame, "User registered successfully.");
        } catch (EmptyInputException | UserAlreadyExistsException exception) {
            JOptionPane.showMessageDialog(frame, exception.getMessage());
        } catch (DataAccessException e) {
            throw new RuntimeException(e);
        }
    }

    public void login() {
        try {
            User currentUser = userService.login(usernameField.getText());
            User chatPartner = selectChatPartner(currentUser);

            if (chatPartner == null) {
                return;
            }

            MessengerService messengerService = new MessengerService(
                    currentUser,
                    chatPartner,
                    userService.getUsersById()
            );

            boolean isNewConversation = messengerService.loadMessages().isEmpty();
            if (isNewConversation) {
                messengerService.sendSystemMessage(
                        currentUser.getName() + " started a chat with " + chatPartner.getName() + "."
                );
            }

            new ChatWindow(currentUser, chatPartner, messengerService);
        } catch (EmptyInputException | UserNotFoundException exception) {
            JOptionPane.showMessageDialog(frame, exception.getMessage());
        } catch (DataAccessException e) {
            throw new RuntimeException(e);
        }
    }

    private User selectChatPartner(User currentUser) {
        List<User> availableUsers = userService.getAllUsers()
                .stream()
                .filter(user -> !user.equals(currentUser))
                .toList();

        if (availableUsers.isEmpty()) {
            JOptionPane.showMessageDialog(frame, "No other users available.");
            return null;
        }

        return (User) JOptionPane.showInputDialog(
                frame,
                "Select chat partner:",
                "Chats",
                JOptionPane.QUESTION_MESSAGE,
                null,
                availableUsers.toArray(),
                availableUsers.getFirst()
        );
    }

    private void renameCurrentUser() {
        try {
            User currentUser = userService.login(usernameField.getText());

            String newName = JOptionPane.showInputDialog(
                    frame,
                    "Enter new username:",
                    currentUser.getName()
            );

            if (newName == null) {
                return;
            }

            userService.renameUser(currentUser, newName);
            usernameField.setText(newName.trim());
            JOptionPane.showMessageDialog(frame, "Username changed successfully.");
        } catch (EmptyInputException | UserNotFoundException | UserAlreadyExistsException exception) {
            JOptionPane.showMessageDialog(frame, exception.getMessage());
        } catch (DataAccessException e) {
            throw new RuntimeException(e);
        }
    }

    private void createUI() {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.insets = new Insets(0, 0, 10, 0);

        panel.add(new JLabel("Username:"), gbc);

        usernameField = new JTextField();
        usernameField.setPreferredSize(new Dimension(220, 40));
        panel.add(usernameField, gbc);

        JPanel buttonPanel = new JPanel(new FlowLayout());

        loginButton = new JButton("Login");
        loginButton.setPreferredSize(new Dimension(100, 40));
        loginButton.addActionListener(event -> login());
        buttonPanel.add(loginButton);

        registerButton = new JButton("Register");
        registerButton.setPreferredSize(new Dimension(100, 40));
        registerButton.addActionListener(event -> register());
        buttonPanel.add(registerButton);

        renameButton = new JButton("Rename");
        renameButton.setPreferredSize(new Dimension(100, 40));
        renameButton.addActionListener(event -> renameCurrentUser());
        buttonPanel.add(renameButton);

        panel.add(buttonPanel, gbc);
    }

    private void initPanel() {
        panel = new JPanel();
        panel.setLayout(new GridBagLayout());
    }

    private void initFrame() {
        frame = new JFrame("Local Messenger - Login");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 800);
        frame.setLocationRelativeTo(null);
    }

    private void show() {
        frame.setVisible(true);
    }
}