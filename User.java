package project;

public class User {
    private static int nextId = 1;
    private final int id;
    private String name;

    public User(String name) {
        this.name = name;
        this.id = nextId;
        nextId++;
    }

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }
}