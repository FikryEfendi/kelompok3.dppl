import java.nio.file.*;
import java.util.*;
import java.io.*;

public class DataStore {
    private Path usersFile = Paths.get("users.json");
    private Path appsFile = Paths.get("applications.json");

    public DataStore() {
        try {
            if (!Files.exists(usersFile)) Files.write(usersFile, "[]".getBytes());
            if (!Files.exists(appsFile)) Files.write(appsFile, "[]".getBytes());
        } catch (Exception e) { e.printStackTrace(); }
    }

    public List<User> loadUsers() {
        try {
            String s = new String(Files.readAllBytes(usersFile));
            return User.listFromJson(s);
        } catch (Exception e) { e.printStackTrace(); return new ArrayList<>(); }
    }

    public void saveUsers(List<User> users) {
        try {
            Files.write(usersFile, User.listToJson(users).getBytes());
        } catch (Exception e) { e.printStackTrace(); }
    }

    public List<Application> loadApplications() {
        try {
            String s = new String(Files.readAllBytes(appsFile));
            return Application.listFromJson(s);
        } catch (Exception e) { e.printStackTrace(); return new ArrayList<>(); }
    }

    public void saveApplications(List<Application> apps) {
        try {
            Files.write(appsFile, Application.listToJson(apps).getBytes());
        } catch (Exception e) { e.printStackTrace(); }
    }
}