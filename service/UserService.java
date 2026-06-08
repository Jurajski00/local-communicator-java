package project.service;

import project.core.User;
import project.data.UserRepository;
import project.exception.DataAccessException;
import project.exception.EmptyInputException;
import project.exception.UserAlreadyExistsException;
import project.exception.UserNotFoundException;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User login(String username) throws EmptyInputException, UserNotFoundException {
        validateUsername(username);

        return userRepository.getUser(username)
                .orElseThrow(() -> new UserNotFoundException(username));
    }

    public void register(String username) throws EmptyInputException, UserAlreadyExistsException, DataAccessException {
        validateUsername(username);

        if (userRepository.isUserRegistered(username)) {
            throw new UserAlreadyExistsException(username);
        }

        userRepository.addUser(username);
    }

    public void renameUser(User user, String newName)
            throws EmptyInputException, UserAlreadyExistsException, DataAccessException {
        validateUsername(newName);

        if (userRepository.isUserRegistered(newName)) {
            throw new UserAlreadyExistsException(newName);
        }

        userRepository.updateUserName(user, newName);
    }

    public List<User> getAllUsers() {
        return userRepository.getAllUsers();
    }

    public Map<Integer, User> getUsersById() {
        return userRepository.getAllUsers()
                .stream()
                .collect(Collectors.toMap(User::getId, user -> user));
    }

    private void validateUsername(String username) throws EmptyInputException {
        if (username == null || username.trim().isEmpty()) {
            throw new EmptyInputException("Username cannot be empty.");
        }
    }
}