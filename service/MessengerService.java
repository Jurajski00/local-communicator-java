package project.service;

import project.core.Conversation;
import project.core.Message;
import project.core.SystemMessage;
import project.core.TextMessage;
import project.core.User;
import project.data.MessageRepository;
import project.exception.DataAccessException;
import project.exception.EmptyInputException;

import java.util.List;
import java.util.Map;

public class MessengerService {
    private final Conversation conversation;
    private final MessageRepository messageRepository;

    public MessengerService(User currentUser, User chatPartner, Map<Integer, User> usersById)
            throws DataAccessException {
        this.conversation = new Conversation(currentUser, chatPartner);
        this.messageRepository = new MessageRepository(
                conversation.getChatKey().getStringKey(),
                usersById
        );

        for (Message message : messageRepository.getMessages()) {
            conversation.addMessage(message);
        }
    }

    public List<Message> loadMessages() {
        return conversation.getMessages();
    }

    public void sendTextMessage(String content) throws EmptyInputException, DataAccessException {
        if (content == null || content.trim().isEmpty()) {
            throw new EmptyInputException("Message cannot be empty.");
        }

        Message message = new TextMessage(conversation.getCurrentUser(), content.trim());
        conversation.addMessage(message);
        messageRepository.storeMessage(message);
    }

    public void sendSystemMessage(String content) throws DataAccessException {
        Message message = new SystemMessage(conversation.getCurrentUser(), content);
        conversation.addMessage(message);
        messageRepository.storeMessage(message);
    }
}