package entity;

public class User {

    private String username;
    private String password; 
    private String role; // "patient", "doctor", "admin"
    private String linkedId; // Patient or Doctor ID

    public User(String username, String password, String role, String linkedId) {
        this.username = username;
        this.password = password;
        this.role = role;
        this.linkedId = linkedId;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getRole() {
        return role;
    }

    public String getLinkedId() {
        return linkedId;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public void setLinkedId(String linkedId) {
        this.linkedId = linkedId;
    }

    @Override
    public String toString() {
        return String.format("%s (%s) - Linked ID: %s", username, role, linkedId);
    }

    // Convert to a line for file storage (CSV)
    public String toFileString() {
        return String.join(",", username, password, role, linkedId);
    }
    // Parse from a file line
    public static User fromFileString(String line) {
        String[] parts = line.split(",", 4);
        return new User(parts[0], parts[1], parts[2], parts[3]);
    }
}
