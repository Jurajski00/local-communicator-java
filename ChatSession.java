package project;

import project.core.Message;
import project.core.User;
import project.service.MessengerService;

import java.util.Scanner;

public class ChatSession {
    private User currentUser;
    private User chatPartner;

    public ChatSession(User currentUser, User chatPartner) {
        this.currentUser = currentUser;
        this.chatPartner = chatPartner;
    }

    public void chat(Scanner sc) {
        System.out.println("You are now messaging " + chatPartner.getName());
        System.out.println("Type \"/exit\" to exit");
        MessengerService chat = new MessengerService(currentUser, chatPartner);

        String text;

        while (true) {
            text = sc.nextLine();
            if (text.equals("/exit"))
                break;
            chat.sendMessage(new Message(currentUser, text));
        }
    }
}