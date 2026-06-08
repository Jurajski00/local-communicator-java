package project.core;

import java.util.Objects;

public class User {
    private static int nextId = 1;

    private final int id;
    private String name;

    public User(String name) {
        this.name = name;
        this.id = nextId;
        nextId++;
    }

    public User(int id, String name) {
        this.id = id;
        this.name = name;

        if (id >= nextId)
            nextId = id + 1;
    }

    public void setName(String name) {
        String trimmedName = name.trim();

        if (trimmedName.isEmpty()) {
            throw new IllegalArgumentException("Name cannot be empty");
        }

        this.name = trimmedName;
    }

    public String toFileLine() {
        return id + ";" + name;
    }

    public static User fromFileLine(String line) {
        String[] parts = line.split(";", 2);

        if (parts.length != 2) {
            throw new IllegalArgumentException("Invalid file line format" + line);
        }

        int id = Integer.parseInt(parts[0]);
        String name = parts[1];

        return new User(id, name);
    }

    @Override
    public String toString() {
        return name;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof User user)) {
            return false;
        }
        return id == user.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public int getId() { return id; }

    public String getName() { return name; }
}