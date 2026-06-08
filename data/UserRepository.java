package project.data;

import project.core.User;
import project.exception.DataAccessException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class UserRepository {
    private static final Path DATA_DIRECTORY = Path.of("data");
    private static final Path USERS_FILE = DATA_DIRECTORY.resolve("users.txt");

    private final List<User> users;

    public UserRepository() throws DataAccessException {
        this.users = new ArrayList<>();
        loadUsers();
        createDefaultUsersWhenFileIsEmpty();
    }

    public List<User> getAllUsers() {
        return Collections.unmodifiableList(users);
    }

    public boolean isUserRegistered(String name) {
        return users.stream()
                .anyMatch(user -> user.getName().equalsIgnoreCase(name.trim()));
    }

    public void addUser(String name) throws DataAccessException {
        User user = new User(name.trim());
        users.add(user);
        saveUsers();
    }

    public void updateUserName(User user, String newName) throws DataAccessException {
        user.setName(newName);
        saveUsers();
    }

    public Optional<User> getUser(String name) {
        return users.stream()
                .filter(user -> user.getName().equalsIgnoreCase(name.trim()))
                .findFirst();
    }

    private void loadUsers() throws DataAccessException {
        try {
            Files.createDirectories(DATA_DIRECTORY);

            if (!Files.exists(USERS_FILE)) {
                Files.createFile(USERS_FILE);
                return;
            }

            List<String> lines = Files.readAllLines(USERS_FILE);

            for (String line : lines) {
                if (!line.isBlank()) {
                    users.add(User.fromFileLine(line));
                }
            }
        } catch (IOException exception) {
            throw new DataAccessException("Could not load users.", exception);
        }
    }

    private void saveUsers() throws DataAccessException {
        try {
            Files.createDirectories(DATA_DIRECTORY);

            List<String> lines = users.stream()
                    .map(User::toFileLine)
                    .toList();

            Files.write(USERS_FILE, lines);
        } catch (IOException exception) {
            throw new DataAccessException("Could not save users.", exception);
        }
    }

    private void createDefaultUsersWhenFileIsEmpty() throws DataAccessException {
        if (!users.isEmpty()) {
            return;
        }

        users.add(new User("Daniel"));
        users.add(new User("Natalia"));
        saveUsers();
    }
}