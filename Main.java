package project;

import java.util.Scanner;
import java.util.List;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        List<User> users = new ArrayList<>();
        users.add(new User("Daniel"));
        users.add(new User("Natalia"));

        String exit;        
        while (true) {
            User currentUser = login(sc, users);
            User chatPartner = selectChat(sc, users, currentUser);

            new ChatSession(currentUser, chatPartner, sc);
            
            System.out.println("Press x to exit");
            exit = sc.nextLine();
            if (exit.equals("x"))
                break;
        } 

        System.out.println();
        sc.close();
    }

    private static User login(Scanner sc, List<User> users) {
        String username;
        while (true) {
            System.out.print("login: ");
            username = sc.nextLine();
            for (User user : users) {
                if (username.equals(user.getName())) {
                    System.out.println("Login successful");
                    return user;
                }
            }
            System.out.println("Wrong username!");
        }
    }

    private static User selectChat(Scanner sc, List<User> users, User currentUser) {
        String chatPartnerUsername;
        while (true) {
            System.out.println("Available chats:");
            for (User user : users) {
                if (!user.equals(currentUser))
                    System.out.println(user.getName());
            }
            System.out.print("Select chat: ");
            chatPartnerUsername = sc.nextLine();
            for (User user : users) {
                if (!user.equals(currentUser) && user.getName().equals(chatPartnerUsername)) {
                    return user;
                }
            }
            System.out.println("Wrong username!");
        }
    }
}