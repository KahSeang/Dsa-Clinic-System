package boundary;

import control.UserControl;
import entity.User;
import java.util.Scanner;

public class AccountSettingsUI {
    private final UserControl userControl = new UserControl();
    private final Scanner sc = new Scanner(System.in);

    public void start(User currentUser) {
        while (true) {
            System.out.println("Account Settings:");
            System.out.println("1. Change Password");
            System.out.println("2. Back");
            System.out.print("Choose: ");
            String choice = sc.nextLine();
            if (choice.equals("1")) {
                changePassword(currentUser);
            } else if (choice.equals("2")) {
                break;
            }
        }
    }

    private void changePassword(User currentUser) {
        System.out.print("Current Password: ");
        String current = sc.nextLine();
        if (userControl.login(currentUser.getUsername(), current) == null) {
            System.out.println("Current password incorrect.");
            return;
        }
        String newPassword;
        do {
            System.out.print("New Password (min 6 chars): ");
            newPassword = sc.nextLine();
            if (newPassword.length() < 6) System.out.println("Password too short.");
            if (newPassword.equals(UserControl.DEFAULT_PASSWORD)) System.out.println("New password cannot be the default password.");
        } while (newPassword.length() < 6 || newPassword.equals(UserControl.DEFAULT_PASSWORD));
        if (userControl.updatePassword(currentUser.getUsername(), newPassword)) {
            System.out.println("Password updated successfully.");
        } else {
            System.out.println("Failed to update password.");
        }
    }
}



