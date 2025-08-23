package boundary;

import control.UserControl;
import control.DoctorControl;
import java.util.Scanner;

public class UserManagementUI {
    private final UserControl userControl = new UserControl();
    private final DoctorControl doctorControl = new DoctorControl();
    private final Scanner sc = new Scanner(System.in);

    public void start() {
        while (true) {
            System.out.println("User Account Management (Admin Only):");
            System.out.println("1. Register Doctor (default password)");
            System.out.println("2. Register Admin Staff (default password)");
            System.out.println("3. Reset User Password to Default");
            System.out.println("4. Back");
            System.out.print("Choose: ");
            String choice = sc.nextLine();
            if (choice.equals("1")) {
                registerWithDefault("doctor");
            } else if (choice.equals("2")) {
                registerWithDefault("admin");
            } else if (choice.equals("3")) {
                resetPasswordToDefault();
            } else if (choice.equals("4")) {
                break;
            }
        }
    }

    private void registerWithDefault(String role) {
        System.out.print("Username: ");
        String username = sc.nextLine();
        String linkedId;
        do {
            System.out.print((role.equals("doctor") ? "Linked Doctor ID" : "Linked Staff ID") + ": ");
            linkedId = sc.nextLine().trim();
            if (linkedId.isEmpty()) System.out.println("Linked ID cannot be empty.");
        } while (linkedId.isEmpty());
        if (role.equals("doctor") && doctorControl.getDoctorById(linkedId) == null) {
            System.out.println("No doctor record found with ID '" + linkedId + "'. Please create the doctor first in Doctor Management.");
            return;
        }
        boolean success = userControl.registerWithDefaultPassword(username, role, linkedId);
        if (success) {
            System.out.println("Registered '" + role + "' with default password: " + UserControl.DEFAULT_PASSWORD);
        } else {
            System.out.println("Registration failed. Ensure username unique and valid role.");
        }
    }

    private void resetPasswordToDefault() {
        System.out.print("Username to reset: ");
        String username = sc.nextLine().trim();
        if (username.isEmpty()) {
            System.out.println("Username cannot be empty.");
            return;
        }
        boolean ok = userControl.updatePassword(username, UserControl.DEFAULT_PASSWORD);
        if (ok) {
            System.out.println("Password reset to default for user '" + username + "'.");
        } else {
            System.out.println("Failed to reset password. User may not exist.");
        }
    }
}


