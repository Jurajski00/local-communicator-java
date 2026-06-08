package project.service;

import project.core.ChatKey;
import project.core.Message;
import project.core.User;
import project.data.MessageRepository;

public class MessengerService {
    MessageRepository messageRepository;

    public MessengerService(User currentUser, User chatPartner) {
        ChatKey connection = new ChatKey(currentUser, chatPartner);
        String chatKey = connection.getStringKey();
        this.messageRepository = new MessageRepository(chatKey);
    }

    public void loadMessages() { messageRepository.loadMessages(); }

    public void sendMessage(Message message) {
        messageRepository.storeMessage(message);
    }
}
