package project.core;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Conversation {
    private final ChatKey chatKey;
    private final User currentUser;
    private final User chatPartner;
    private final List<Message> messages;

    public Conversation(User currentUser, User chatPartner) {
        this.currentUser = currentUser;
        this.chatPartner = chatPartner;
        this.chatKey = new ChatKey(currentUser, chatPartner);
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

    public void addMessage(Message message) {
        messages.add(message);
    }

    public List<Message> getMessages() {
        return Collections.unmodifiableList(messages);
    }
}
