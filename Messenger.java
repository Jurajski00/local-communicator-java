package project;

public class Messenger implements Isendable, Irecieveable {
    Conversation conversation;

    public Messenger(User currentUser, User chatPartner) {
        Connection connection = new Connection(currentUser, chatPartner);
        String chatKey = connection.getStringKey();
        this.conversation = new Conversation(chatKey);
        
        conversation.loadMessages();
    }

    public void recieveMessage() {
    }

    public void sendMessage(Message message) {
        conversation.storeMessage(message);
    }
}
