package project;

public class Messenger implements Sendable {
    public void sendMessage(User reciepent, Message message) {
        reciepent.recieveMessage(message);
    }
}
