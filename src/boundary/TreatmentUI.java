package boundary;

import adt.List;
import control.TreatmentControl;
import entity.Treatment;
import java.util.Scanner;

public class TreatmentUI {
    private TreatmentControl control = new TreatmentControl();
    private Scanner sc = new Scanner(System.in);

    public void start() {
        while (true) {
            System.out.println("1. Add Treatment");
            System.out.println("2. Update Treatment");
            System.out.println("3. Delete Treatment");
            System.out.println("4. Search Treatment");
            System.out.println("5. Reports");
            System.out.println("6. Back");
            System.out.print("Choose: ");
            String choice = sc.nextLine();
            if (choice.equals("1")) {
                addTreatment();
            } else if (choice.equals("2")) {
                updateTreatment();
            } else if (choice.equals("3")) {
                deleteTreatment();
            } else if (choice.equals("4")) {
                searchTreatment();
            } else if (choice.equals("5")) {
                reports();
            } else if (choice.equals("6")) {
                break;
            }
        }
    }

    private void addTreatment() {
        System.out.print("ID: ");
        String id = sc.nextLine();
        System.out.print("Consultation ID: ");
        String consultationId = sc.nextLine();
        System.out.print("Treatment Details: ");
        String details = sc.nextLine();
        System.out.print("Medicine List (semicolon separated): ");
        String medicineList = sc.nextLine();
        boolean success = control.addTreatment(id, consultationId, details, medicineList);
        System.out.println(success ? "Treatment added." : "Invalid data or ID already exists.");
    }

    private void updateTreatment() {
        System.out.print("ID: ");
        String id = sc.nextLine();
        Treatment t = control.getTreatmentById(id);
        if (t == null) {
            System.out.println("Treatment not found.");
            return;
        }
        System.out.print("New Details (blank to skip): ");
        String details = sc.nextLine();
        if (!details.isEmpty()) t.setTreatmentDetails(details);
        System.out.print("New Medicine List (blank to skip): ");
        String medicineList = sc.nextLine();
        if (!medicineList.isEmpty()) t.setMedicineList(medicineList);
        control.updateTreatment(t);
        System.out.println("Treatment updated.");
    }

    private void deleteTreatment() {
        System.out.print("ID: ");
        String id = sc.nextLine();
        boolean success = control.deleteTreatment(id);
        System.out.println(success ? "Treatment deleted." : "Treatment not found.");
    }

    private void searchTreatment() {
        System.out.print("ID: ");
        String id = sc.nextLine();
        Treatment t = control.getTreatmentById(id);
        if (t != null) {
            System.out.println(t);
        } else {
            System.out.println("Treatment not found.");
        }
    }

    private void reports() {
        System.out.println("1. List all treatments");
        System.out.println("2. List treatments by consultation");
        System.out.println("3. List treatments by medicine");
        System.out.println("4. Count treatments");
        System.out.println("5. List treatments with details containing keyword");
        System.out.print("Choose: ");
        String choice = sc.nextLine();
        if (choice.equals("1")) {
            adt.List<Treatment> list = control.getAllTreatments();
            for (int i = 0; i < list.size(); i++) System.out.println(list.get(i));
        } else if (choice.equals("2")) {
            System.out.print("Consultation ID: ");
            String consultationId = sc.nextLine();
            adt.List<Treatment> list = control.getTreatmentsByConsultation(consultationId);
            for (int i = 0; i < list.size(); i++) System.out.println(list.get(i));
        } else if (choice.equals("3")) {
            System.out.print("Medicine: ");
            String medicine = sc.nextLine();
            adt.List<Treatment> list = control.getTreatmentsByMedicine(medicine);
            for (int i = 0; i < list.size(); i++) System.out.println(list.get(i));
        } else if (choice.equals("4")) {
            System.out.println("Total treatments: " + control.countTreatments());
        } else if (choice.equals("5")) {
            System.out.print("Keyword: ");
            String keyword = sc.nextLine();
            adt.List<Treatment> list = control.getAllTreatments();
            for (int i = 0; i < list.size(); i++) {
                if (list.get(i).getTreatmentDetails().contains(keyword))
                    System.out.println(list.get(i));
            }
        }
    }
} 