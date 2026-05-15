package project;

import java.util.List;
import java.util.ArrayList;

public class User {
    private static int nextId = 1;
    private final int id;
    private String name;
    private List<Message> messages = new ArrayList<>();

    public User(String name) {
        this.name = name;
        this.id = nextId;
        nextId++;
    }

    public void recieveMessage(Message message) {
        messages.add(message);
    }

    public void getMessages() {
        for(Message message : messages) {
            System.out.println(message.getContent());
        }
    }

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }
}