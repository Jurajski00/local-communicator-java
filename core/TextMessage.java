package project.core;

import java.time.LocalDateTime;

public class TextMessage extends Message {
    public TextMessage(User sender, String content) {
        super(sender, content);
    }

    public TextMessage(User sender, String content, LocalDateTime createdAt) {
        super(sender, content, createdAt);
    }

    @Override
    public String formatForDisplay() {
        return "[" + getFormattedTime() + "] " + getSenderName() + ": " + getContent();
    }

    @Override
    public String getStorageType() {
        return "TEXT";
    }
}