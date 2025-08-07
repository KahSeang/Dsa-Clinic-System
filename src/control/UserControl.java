package control;

import adt.List;
import entity.User;
import util.FileIO;

public class UserControl {
    private static final String USER_FILE = "users.txt";
    private List<User> users;

    public UserControl() {
        users = loadUsers();
    }

    private List<User> loadUsers() {
        List<User> list = new List<>();
        try {
            List<String> lines = FileIO.readLines(USER_FILE);
            for (int i = 0; i < lines.size(); i++) {
                list.add(User.fromFileString(lines.get(i)));
            }
        } catch (Exception e) {
            // File may not exist yet
        }
        return list;
    }

    private void saveUsers() {
        List<String> lines = new List<>();
        for (int i = 0; i < users.size(); i++) {
            lines.add(users.get(i).toFileString());
        }
        try {
            FileIO.writeLines(USER_FILE, lines);
        } catch (Exception e) {
            // Handle error
        }
    }

    public User login(String username, String password) {
        for (int i = 0; i < users.size(); i++) {
            User u = users.get(i);
            if (u.getUsername().equals(username) && u.getPassword().equals(password)) {
                return u;
            }
        }
        return null;
    }

    public boolean register(String username, String password, String role, String linkedId) {
        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getUsername().equals(username)) {
                return false;
            }
        }
        users.add(new User(username, password, role, linkedId));
        saveUsers();
        return true;
    }
} 