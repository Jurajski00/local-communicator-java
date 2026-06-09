# Local Messenger

A simple desktop chat application written in Java. Two users can open a conversation and exchange messages — everything is stored locally in plain text files, no server or internet connection needed.

## How to run

Requires Java 17 or newer.

Compile and run from the project root:

```
javac -d out src/**/*.java
java -cp out project.Main
```

Or just open the project in IntelliJ and run `Main.java`.

## How to use

When you launch the app, you'll see a login screen with three buttons.

**Register** — create a new account by typing a username and clicking Register. Two default users (Daniel and Natalia) are created automatically on first run so you can test it straight away.

**Login** — type your username and click Login. You'll then be asked to pick someone to chat with from the list of registered users. Once you select a partner, the chat window opens.

**Rename** — type your current username, click Rename, and enter the new name in the popup.

Inside the chat window, type a message in the text field at the bottom and press Enter or click Send. Previous messages load automatically when you open a conversation.

## Data storage

User data is saved in `data/users.txt`. Each conversation gets its own file under `data/messages/`, named after the two user IDs involved (e.g. `1&2.txt`). The app creates all of this on first run — you don't need to set anything up manually.

## Project structure

```
src/
  project/
    core/        — domain classes (User, Message, Conversation, ...)
    data/        — file reading and writing (UserRepository, MessageRepository)
    service/     — business logic (UserService, MessengerService)
    ui/          — Swing windows (LoginWindow, ChatWindow)
    exception/   — custom exceptions
    Main.java
```

## Notes

- Usernames are case-insensitive, so "daniel" and "Daniel" are treated as the same user.
- Messages cannot be deleted or edited after sending.
- The app is single-machine only — there's no networking of any kind.