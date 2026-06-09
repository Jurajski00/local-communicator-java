package project.core;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public abstract class Message implements MessageFormatter {
    private final User sender;
    private String content;
    private final LocalDateTime createdAt;

    protected Message(User sender, String content) {
        this(sender, content, LocalDateTime.now());
    }

    protected Message(User sender, String content, LocalDateTime createdAt) {
        this.sender = sender;
        this.content = content;
        this.createdAt = createdAt;
    }

    public String getContent() {
        return content;
    }

    public String getSenderName() {
        return sender.getName();
    }

    protected String getFormattedTime() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        return createdAt.format(formatter);
    }

    public abstract String getStorageType();

    public String toFileLine() {
        return getStorageType()
                + ";"
                + sender.getId()
                + ";"
                + createdAt
                + ";"
                + escape(content);
    }

    protected static String escape(String value) {
        return value
                .replace("\\", "\\\\")
                .replace(";", "\\;")
                .replace("\n", "\\n");
    }
}