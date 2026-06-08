package project.core;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Conversation {
    private final ChatKey chatKey;
    private final User currentUser;
    private final User chatPartner;
    private String title;
    private final List<Message> messages;

    public Conversation(User currentUser, User chatPartner) {
        this.currentUser = currentUser;
        this.chatPartner = chatPartner;
        this.chatKey = new ChatKey(currentUser, chatPartner);
        this.title = currentUser.getName() + " & " + chatPartner.getName();
        this.messages = new ArrayList<>();
    }

    public ChatKey getChatKey() {
        return chatKey;
    }

    public User getCurrentUser() {
        return currentUser;
    }

    public User getChatPartner() {
        return chatPartner;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        String trimmedTitle = title.trim();

        if (trimmedTitle.isEmpty()) {
            throw new IllegalArgumentException("Conversation title cannot be empty.");
        }

        this.title = trimmedTitle;
    }

    public void addMessage(Message message) {
        messages.add(message);
    }

    public List<Message> getMessages() {
        return Collections.unmodifiableList(messages);
    }

    public boolean containsUser(User user) {
        return currentUser.equals(user) || chatPartner.equals(user);
    }

    public User getOtherUser(User user) {
        if (currentUser.equals(user)) {
            return chatPartner;
        }

        if (chatPartner.equals(user)) {
            return currentUser;
        }

        throw new IllegalArgumentException("User does not belong to this conversation.");
    }
}
