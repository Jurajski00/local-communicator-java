package project.ui;

import project.core.Message;
import project.core.User;
import project.service.MessengerService;

import javax.swing.*;
import java.awt.*;

public class ChatWindow {
    private User currentUser;
    private User chatPartner;
    private JFrame frame;
    private JPanel chatPanel;
    private JPanel sendPanel;
    private JButton sendButton;
    private JTextField messageField;
    private MessengerService messengerService;

    public ChatWindow(User currentUser, User chatPartner) {
        this.currentUser = currentUser;
        this.chatPartner = chatPartner;
        messengerService = new MessengerService(currentUser, chatPartner);
        initFrame();
        initPanel();
        frame.add(chatPanel, BorderLayout.CENTER);
        frame.add(sendPanel, BorderLayout.SOUTH);
        createUI();
        show();
    }

//    public static void main(String[] args) {
//        SwingUtilities.invokeLater(() -> {new ChatWindow();});
//    }

    private void createUI() {
        GridBagConstraints gbc = new GridBagConstraints();
        messageField = new JTextField();
        messageField.setPreferredSize(new Dimension(500, 40));
        sendPanel.add(messageField, gbc);
        sendButton = new JButton("Send");
        sendButton.addActionListener(e -> {
            String message = messageField.getText().trim();
            if (message.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "Please enter a message");
                return;
            }
            messengerService.sendMessage(new Message(currentUser, message));
        });
        sendButton.setPreferredSize(new Dimension(100, 40));
        sendPanel.add(sendButton, gbc);
    }

    private void loadMessages() {
        messengerService.loadMessages();
    }

    private void initPanel() {
        chatPanel = new JPanel();
        chatPanel.setLayout(new GridBagLayout());
        sendPanel = new JPanel();
        sendPanel.setLayout(new GridBagLayout());
    }

    private void initFrame() {
        frame = new JFrame("Chat");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 800);
        frame.setLocationRelativeTo(null);
    }

    private void show() {
        frame.setVisible(true);
    }
}
