/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package boundary;

import adt.List;
import control.UserControl;
import control.PatientControl;
import entity.User;
import util.FileIO;

import java.io.IOException;
import java.util.Scanner;

public class LoginUI {

    private UserControl userControl = new UserControl();
    private PatientControl patientControl = new PatientControl();
    private Scanner sc = new Scanner(System.in);

    // New method for main menu integration
    public User startAndReturnUser() {
        while (true) {
            System.out.println("================================");
            System.out.println("TARUMT CLINIC");
            System.out.println("===============================");
            System.out.println();
            System.out.println("1. Doctor");
            System.out.println("2. Patient");
            System.out.println("3. Admin Staff");
            System.out.println("4. Exit");
            System.out.print("Choose role: ");
            String roleChoice = sc.nextLine();

            if (roleChoice.equals("1")) {
                // Doctor: login only
                while (true) {
                    System.out.println("1. Login");
                    System.out.println("2. Back");
                    System.out.print("Choose: ");
                    String choice = sc.nextLine();
                    if (choice.equals("1")) {
                        User user = loginWithRole("doctor");
                        if (user != null) {
                            return user;
                        }
                    } else if (choice.equals("2")) {
                        break;
                    }
                }
            } else if (roleChoice.equals("2")) {
                // Patient: login or register
                while (true) {
                    System.out.print("Do you register before (y-Yes | n-No | 0-Back):");
                    String choice = sc.nextLine();
                    if (choice.equals("y") || choice.equals("Y")) {
                        //Find back the records
                    } else if (choice.equals("n") || choice.equals("N")) {
                        registerPatientUser();
                    } else if (choice.equals("0")) {
                        break;
                    }
                }
            } else if (roleChoice.equals("3")) {
                // Admin Staff: login only
                while (true) {
                    System.out.println("1. Login");
                    System.out.println("2. Back");
                    System.out.print("Choose: ");
                    String choice = sc.nextLine();
                    if (choice.equals("1")) {
                        User user = loginWithRole("admin");
                        if (user != null) {
                            return user;
                        }
                    } else if (choice.equals("2")) {
                        break;
                    }
                }
            } else if (roleChoice.equals("4")) {
                return null;
            }
        }
    }

    private User loginWithRole(String expectedRole) {
        String username;
        do {
            System.out.print("Username: ");
            username = sc.nextLine().trim();
            if (username.isEmpty()) {
                System.out.println("Username cannot be empty.");
            }
        } while (username.isEmpty());
        String password;
        do {
            System.out.print("Password: ");
            password = sc.nextLine();
            if (password.isEmpty()) {
                System.out.println("Password cannot be empty.");
            }
        } while (password.isEmpty());
        User user = userControl.login(username, password);
        if (user != null && user.getRole().equalsIgnoreCase(expectedRole)) {
            System.out.println("Login successful! Role: " + user.getRole());
            // Force password change if default for privileged roles
            if ((expectedRole.equalsIgnoreCase("doctor") || expectedRole.equalsIgnoreCase("admin"))
                    && password.equals(UserControl.DEFAULT_PASSWORD)) {
                System.out.println("You are using the default password. Please set a new password now.");
                String newPassword;
                do {
                    System.out.print("New Password (min 6 chars): ");
                    newPassword = sc.nextLine();
                    if (newPassword.length() < 6) {
                        System.out.println("Password too short.");
                    }
                    if (newPassword.equals(UserControl.DEFAULT_PASSWORD)) {
                        System.out.println("New password cannot be the default password.");
                    }
                } while (newPassword.length() < 6 || newPassword.equals(UserControl.DEFAULT_PASSWORD));
                if (userControl.updatePassword(username, newPassword)) {
                    System.out.println("Password updated successfully.");
                } else {
                    System.out.println("Failed to update password. Please contact admin.");
                }
            }
            return user;
        } else if (user != null) {
            System.out.println("This account is a '" + user.getRole() + "' account. Please choose the correct role.");
            return null;
        } else {
            System.out.println("Invalid credentials.");
            return null;
        }
    }

    private void registerPatientUser() {
        String username;
        System.out.println("==============================");
        System.out.println("Please Register A New Account!");
        System.out.println("==============================");
        do {
            System.out.print("Username: ");
            username = sc.nextLine().trim();
            if (username.isEmpty()) {
                System.out.println("Username cannot be empty.");
            }
        } while (username.isEmpty());
        String password;
        do {
            System.out.print("Password (min 6 chars): ");
            password = sc.nextLine();
            if (password.length() < 6) {
                System.out.println("Password too short.");
            }
        } while (password.length() < 6);
        String linkedId;
        do {
            System.out.print("Linked Patient ID: ");
            linkedId = sc.nextLine().trim();
            if (linkedId.isEmpty()) {
                System.out.println("Patient ID cannot be empty.");
            }
        } while (linkedId.isEmpty());
        // Validate patient exists before allowing user account creation
        if (patientControl.getPatientById(linkedId) == null) {
            System.out.println("No patient record found with ID '" + linkedId + "'. Please visit the clinic to create a patient record first.");
            return;
        }
        boolean success = userControl.register(username, password, "patient", linkedId);
        if (success) {
            System.out.println("Registration successful! You can now log in as Patient.");
        } else {
            System.out.println("Registration failed. Username may already exist or inputs invalid.");
        }
    }
}
