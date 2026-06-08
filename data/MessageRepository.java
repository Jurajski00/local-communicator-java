package project.data;

import project.core.Message;
import project.core.SystemMessage;
import project.core.TextMessage;
import project.core.User;
import project.exception.DataAccessException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MessageRepository {
    private static final Path DATA_DIRECTORY = Path.of("data");
    private static final Path MESSAGES_DIRECTORY = DATA_DIRECTORY.resolve("messages");

    private final String stringKey;
    private final Map<Integer, User> usersById;

    public MessageRepository(String stringKey, Map<Integer, User> usersById) {
        this.stringKey = stringKey;
        this.usersById = usersById;
    }

    public List<Message> getMessages() throws DataAccessException {
        Path filePath = getMessageFilePath();

        try {
            Files.createDirectories(MESSAGES_DIRECTORY);

            if (!Files.exists(filePath)) {
                Files.createFile(filePath);
                return new ArrayList<>();
            }

            List<Message> messages = new ArrayList<>();
            List<String> lines = Files.readAllLines(filePath);

            for (String line : lines) {
                if (!line.isBlank()) {
                    messages.add(parseMessage(line));
                }
            }

            return messages;
        } catch (IOException exception) {
            throw new DataAccessException("Could not load messages.", exception);
        }
    }

    public void storeMessage(Message message) throws DataAccessException {
        try {
            Files.createDirectories(MESSAGES_DIRECTORY);

            String line = message.toFileLine() + System.lineSeparator();

            Files.writeString(
                    getMessageFilePath(),
                    line,
                    StandardOpenOption.CREATE,
                    StandardOpenOption.APPEND
            );
        } catch (IOException exception) {
            throw new DataAccessException("Could not save message.", exception);
        }
    }

    private Message parseMessage(String line) {
        List<String> parts = splitEscaped(line);

        if (parts.size() != 4) {
            throw new IllegalArgumentException("Invalid message line: " + line);
        }

        String type = parts.get(0);
        int senderId = Integer.parseInt(parts.get(1));
        LocalDateTime createdAt = LocalDateTime.parse(parts.get(2));
        String content = parts.get(3);

        User sender = usersById.get(senderId);

        if (sender == null) {
            throw new IllegalArgumentException("Unknown sender id: " + senderId);
        }

        if (type.equals("SYSTEM")) {
            return new SystemMessage(sender, content, createdAt);
        }

        return new TextMessage(sender, content, createdAt);
    }

    private List<String> splitEscaped(String line) {
        List<String> result = new ArrayList<>();
        StringBuilder currentPart = new StringBuilder();
        boolean escaping = false;

        for (char character : line.toCharArray()) {
            if (escaping) {
                if (character == 'n') {
                    currentPart.append('\n');
                } else {
                    currentPart.append(character);
                }

                escaping = false;
            } else if (character == '\\') {
                escaping = true;
            } else if (character == ';') {
                result.add(currentPart.toString());
                currentPart.setLength(0);
            } else {
                currentPart.append(character);
            }
        }

        result.add(currentPart.toString());

        return result;
    }

    private Path getMessageFilePath() {
        return MESSAGES_DIRECTORY.resolve(stringKey + ".txt");
    }
}