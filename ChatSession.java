package project;

import java.util.Currency;
import java.util.Scanner;

public class ChatSession {
    private User currentUser;
    private User chatPartner;

    public ChatSession(User currentUser, User chatPartner, Scanner sc) {
        this.currentUser = currentUser;
        this.chatPartner = chatPartner;
        chat(sc);
    }

    public void chat(Scanner sc) {
        System.out.println("You are now messaging " + chatPartner.getName());
        currentUser.getMessages();

        Messenger chat = new Messenger();
        String text;

        while (true) {
            text = sc.nextLine();
            if (text.equals("/exit"))
                break;
            chat.sendMessage(chatPartner, new Message(currentUser, text));
        }
    }
}