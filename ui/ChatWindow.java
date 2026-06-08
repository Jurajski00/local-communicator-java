package project.ui;

import project.core.Message;
import project.core.User;
import project.exception.DataAccessException;
import project.exception.EmptyInputException;
import project.service.MessengerService;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class ChatWindow {
    private final User currentUser;
    private final User chatPartner;
    private final MessengerService messengerService;

    private JFrame frame;
    private JTextArea chatArea;
    private JPanel sendPanel;
    private JButton sendButton;
    private JTextField messageField;

    public ChatWindow(User currentUser, User chatPartner, MessengerService messengerService) {
        this.currentUser = currentUser;
        this.chatPartner = chatPartner;
        this.messengerService = messengerService;

        initFrame();
        initPanel();
        createUI();
        loadMessages();
        show();
    }

    private void createUI() {
        JScrollPane scrollPane = new JScrollPane(chatArea);
        frame.add(scrollPane, BorderLayout.CENTER);

        messageField = new JTextField();
        messageField.setPreferredSize(new Dimension(450, 40));
        messageField.addActionListener(event -> sendMessage());
        sendPanel.add(messageField);

        sendButton = new JButton("Send");
        sendButton.setPreferredSize(new Dimension(100, 40));
        sendButton.addActionListener(event -> sendMessage());
        sendPanel.add(sendButton);

        frame.add(sendPanel, BorderLayout.SOUTH);
    }

    private void sendMessage() {
        try {
            messengerService.sendTextMessage(messageField.getText());
            messageField.setText("");
            loadMessages();
        } catch (EmptyInputException exception) {
            JOptionPane.showMessageDialog(frame, exception.getMessage());
        } catch (DataAccessException e) {
            throw new RuntimeException(e);
        }
    }

    private void loadMessages() {
        chatArea.setText("");

        List<Message> messages = messengerService.loadMessages();

        for (Message message : messages) {
            chatArea.append(message.formatForDisplay());
            chatArea.append(System.lineSeparator());
        }

        chatArea.setCaretPosition(chatArea.getDocument().getLength());
    }

    private void initPanel() {
        chatArea = new JTextArea();
        chatArea.setEditable(false);
        chatArea.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 14));

        sendPanel = new JPanel();
        sendPanel.setLayout(new FlowLayout());
    }

    private void initFrame() {
        frame = new JFrame("Chat: " + currentUser.getName() + " → " + chatPartner.getName());
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(650, 800);
        frame.setLocationRelativeTo(null);
        frame.setLayout(new BorderLayout());
    }

    private void show() {
        frame.setVisible(true);
    }
}