package project.data;

import project.core.User;

import java.util.ArrayList;
import java.util.List;

public class UserRepository {
    private List<User> users = new ArrayList<>();

    public UserRepository() {
        users.add(new User("Daniel"));
        users.add(new User("Natalia"));
    }

    public boolean isUserRegistered(String name) {
        return users.stream().anyMatch(user -> user.getName().equals(name));
    }

    public void addUser(String name) {
        if (users.stream().noneMatch(user -> user.getName().equals(name))) {
            users.add(new User(name));
        }
    }

    public User getUser(String name) {
        return users.stream().filter(user -> user.getName().equals(name)).findFirst().orElse(null);
    }
}
