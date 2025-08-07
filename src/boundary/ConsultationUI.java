package boundary;

import adt.List;
import control.ConsultationControl;
import entity.Consultation;
import java.util.Scanner;

public class ConsultationUI {
    private ConsultationControl control = new ConsultationControl();
    private Scanner sc = new Scanner(System.in);

    public void start() {
        while (true) {
            System.out.println("1. Add Consultation");
            System.out.println("2. Update Consultation");
            System.out.println("3. Delete Consultation");
            System.out.println("4. Search Consultation");
            System.out.println("5. Reports");
            System.out.println("6. Back");
            System.out.print("Choose: ");
            String choice = sc.nextLine();
            if (choice.equals("1")) {
                addConsultation();
            } else if (choice.equals("2")) {
                updateConsultation();
            } else if (choice.equals("3")) {
                deleteConsultation();
            } else if (choice.equals("4")) {
                searchConsultation();
            } else if (choice.equals("5")) {
                reports();
            } else if (choice.equals("6")) {
                break;
            }
        }
    }

    private void addConsultation() {
        System.out.print("ID: ");
        String id = sc.nextLine();
        System.out.print("Patient ID: ");
        String patientId = sc.nextLine();
        System.out.print("Doctor ID: ");
        String doctorId = sc.nextLine();
        System.out.print("Date: ");
        String date = sc.nextLine();
        System.out.print("Diagnosis: ");
        String diagnosis = sc.nextLine();
        System.out.print("Notes: ");
        String notes = sc.nextLine();
        boolean success = control.addConsultation(id, patientId, doctorId, date, diagnosis, notes);
        System.out.println(success ? "Consultation added." : "Consultation ID already exists.");
    }

    private void updateConsultation() {
        System.out.print("ID: ");
        String id = sc.nextLine();
        Consultation c = control.getConsultationById(id);
        if (c == null) {
            System.out.println("Consultation not found.");
            return;
        }
        System.out.print("New Diagnosis (blank to skip): ");
        String diagnosis = sc.nextLine();
        if (!diagnosis.isEmpty()) c.setDiagnosis(diagnosis);
        System.out.print("New Notes (blank to skip): ");
        String notes = sc.nextLine();
        if (!notes.isEmpty()) c.setNotes(notes);
        control.updateConsultation(c);
        System.out.println("Consultation updated.");
    }

    private void deleteConsultation() {
        System.out.print("ID: ");
        String id = sc.nextLine();
        boolean success = control.deleteConsultation(id);
        System.out.println(success ? "Consultation deleted." : "Consultation not found.");
    }

    private void searchConsultation() {
        System.out.print("ID: ");
        String id = sc.nextLine();
        Consultation c = control.getConsultationById(id);
        if (c != null) {
            System.out.println(c);
        } else {
            System.out.println("Consultation not found.");
        }
    }

    private void reports() {
        System.out.println("1. List all consultations");
        System.out.println("2. List consultations by patient");
        System.out.println("3. List consultations by doctor");
        System.out.println("4. List consultations by date");
        System.out.println("5. Count consultations");
        System.out.print("Choose: ");
        String choice = sc.nextLine();
        if (choice.equals("1")) {
            adt.List<Consultation> list = control.getAllConsultations();
            for (int i = 0; i < list.size(); i++) System.out.println(list.get(i));
        } else if (choice.equals("2")) {
            System.out.print("Patient ID: ");
            String patientId = sc.nextLine();
            adt.List<Consultation> list = control.getConsultationsByPatient(patientId);
            for (int i = 0; i < list.size(); i++) System.out.println(list.get(i));
        } else if (choice.equals("3")) {
            System.out.print("Doctor ID: ");
            String doctorId = sc.nextLine();
            adt.List<Consultation> list = control.getConsultationsByDoctor(doctorId);
            for (int i = 0; i < list.size(); i++) System.out.println(list.get(i));
        } else if (choice.equals("4")) {
            System.out.print("Date: ");
            String date = sc.nextLine();
            adt.List<Consultation> list = control.getConsultationsByDate(date);
            for (int i = 0; i < list.size(); i++) System.out.println(list.get(i));
        } else if (choice.equals("5")) {
            System.out.println("Total consultations: " + control.countConsultations());
        }
    }
} 