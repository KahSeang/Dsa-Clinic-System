
package boundary;

import entity.User;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        LoginUI loginUI = new LoginUI();
        User user = loginUI.startAndReturnUser();
        if (user == null) return;
        Scanner sc = new Scanner(System.in);
        while (true) {
            if (user.getRole().equals("admin")) {
                System.out.println("Admin Menu:");
                System.out.println("1. Patient Management");
                System.out.println("2. Doctor Management");
                System.out.println("3. Consultation Management");
                System.out.println("4. Treatment Management");
                System.out.println("5. Pharmacy Management");
                System.out.println("6. Logout");
                System.out.print("Choose: ");
                String choice = sc.nextLine();
                if (choice.equals("1")) new PatientUI().start();
                else if (choice.equals("2")) new DoctorUI().start();
                else if (choice.equals("3")) new ConsultationUI().start();
                else if (choice.equals("4")) new TreatmentUI().start();
                else if (choice.equals("5")) new PharmacyUI().start();
                else if (choice.equals("6")) break;
            } else if (user.getRole().equals("doctor")) {
                System.out.println("Doctor Menu:");
                System.out.println("1. Consultation Management");
                System.out.println("2. Treatment Management");
                System.out.println("3. Logout");
                System.out.print("Choose: ");
                String choice = sc.nextLine();
                if (choice.equals("1")) new ConsultationUI().start();
                else if (choice.equals("2")) new TreatmentUI().start();
                else if (choice.equals("3")) break;
            } else if (user.getRole().equals("patient")) {
                System.out.println("Patient Menu:");
                System.out.println("1. View My Consultations");
                System.out.println("2. View My Treatments");
                System.out.println("3. Logout");
                System.out.print("Choose: ");
                String choice = sc.nextLine();
                if (choice.equals("1")) new ConsultationUI().start();
                else if (choice.equals("2")) new TreatmentUI().start();
                else if (choice.equals("3")) break;
            } else {
                System.out.println("Unknown role. Exiting.");
                break;
            }
        }
    }
}
