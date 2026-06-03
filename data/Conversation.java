package project.data;

import project.core.Message;

import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;

public class Conversation {
    private String stringKey;
    private static Map<String, List<Message>> allChatHistory = new HashMap<>();

    Conversation (String stringKey) {
        this.stringKey = stringKey;
        if (!allChatHistory.containsKey(stringKey))
            allChatHistory.put(stringKey, new ArrayList<>());
    }
    
    public void loadMessages() {
        List<Message> thisChatHistory = allChatHistory.get(stringKey);
        for (Message message : thisChatHistory) {
            System.out.println(message.getSender() + ": " + message.getContent());
        }
    }

    public void storeMessage(Message message) {
        allChatHistory.get(stringKey).add(message);
    }
}
