/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package boundary;

import adt.List;
import control.UserControl;
import entity.User;
import util.FileIO;

import java.io.IOException;
import java.util.Scanner;

public class LoginUI {
    private UserControl userControl = new UserControl();
    private Scanner sc = new Scanner(System.in);

    // New method for main menu integration
    public User startAndReturnUser() {
        while (true) {
            System.out.println("1. Login");
            System.out.println("2. Register");
            System.out.println("3. Exit");
            System.out.print("Choose: ");
            String choice = sc.nextLine();
            if (choice.equals("1")) {
                User user = login();
                if (user != null) return user;
            } else if (choice.equals("2")) {
                register();
            } else if (choice.equals("3")) {
                return null;
            }
        }
    }

    private User login() {
        System.out.print("Username: ");
        String username = sc.nextLine();
        System.out.print("Password: ");
        String password = sc.nextLine();
        User user = userControl.login(username, password);
        if (user != null) {
            System.out.println("Login successful! Role: " + user.getRole());
            return user;
        } else {
            System.out.println("Invalid credentials.");
            return null;
        }
    }

    private void register() {
        System.out.print("Username: ");
        String username = sc.nextLine();
        System.out.print("Password: ");
        String password = sc.nextLine();
        System.out.print("Role (patient/doctor/admin): ");
        String role = sc.nextLine();
        System.out.print("Linked ID: ");
        String linkedId = sc.nextLine();
        boolean success = userControl.register(username, password, role, linkedId);
        if (success) {
            System.out.println("Registration successful!");
        } else {
            System.out.println("Username already exists.");
        }
    }
}
