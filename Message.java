package project;

public class Message {
    private User sender;
    private String content;

    public Message(User sender, String content) {
        this.sender = sender;
        this.content = content;
    }

    public String getContent() {
        return content;
    }

    public User getSender() {
        return sender;
    }
}