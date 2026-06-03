package project.data;

import project.core.Connection;
import project.core.Message;
import project.core.User;

public class Messenger {
    Conversation conversation;

    public Messenger(User currentUser, User chatPartner) {
        Connection connection = new Connection(currentUser, chatPartner);
        String chatKey = connection.getStringKey();
        this.conversation = new Conversation(chatKey);
        
        conversation.loadMessages();
    }

    public void sendMessage(Message message) {
        conversation.storeMessage(message);
    }
}
