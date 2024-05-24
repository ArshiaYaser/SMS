package sms;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class Admin extends Person {

    public Admin(String id, String username, String name, String password) {
        super(id, username, name, password);
    }
 // Getters
    public String getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    // Setters
    public void setId(String id) {
        this.id = id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    protected void saveToFile(String filename) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename, true))) {
            writer.write(id + "," + username + "," + name + "," + password);
            writer.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
