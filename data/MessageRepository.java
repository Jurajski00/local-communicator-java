package project.data;

import project.core.Message;

import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;

public class MessageRepository {
    private String stringKey;
    private static Map<String, List<Message>> allChatHistory = new HashMap<>();

    public MessageRepository(String stringKey) {
        this.stringKey = stringKey;
        if (!allChatHistory.containsKey(stringKey))
            allChatHistory.put(stringKey, new ArrayList<>());
    }
    
    public List<Message> getMessages() {
        return allChatHistory.getOrDefault(stringKey, new ArrayList<>());
    }

    public void storeMessage(Message message) {
        allChatHistory.get(stringKey).add(message);
    }
}
