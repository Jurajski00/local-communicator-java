package project.core;

import java.time.LocalDateTime;

public class SystemMessage extends Message {
    public SystemMessage(User sender, String content) {
        super(sender, content);
    }

    public SystemMessage(User sender, String content, LocalDateTime createdAt) {
        super(sender, content, createdAt);
    }

    @Override
    public String formatForDisplay() {
        return "SYSTEM [" + getFormattedTime() + "]: " + getContent();
    }

    @Override
    public String getStorageType() {
        return "SYSTEM";
    }
}
