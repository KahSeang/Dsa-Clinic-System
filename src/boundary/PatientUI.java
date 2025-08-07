
package boundary;

import adt.List;
import adt.Queue;
import control.PatientControl;
import control.DoctorControl;
import control.ConsultationControl;
import entity.Patient;
import entity.Doctor;
import entity.Consultation;
import util.FileIO;
import boundary.BarChartUtil;

import java.util.Scanner;

public class PatientUI {
    private PatientControl control = new PatientControl();
    private DoctorControl doctorControl = new DoctorControl();
    private ConsultationControl consultationControl = new ConsultationControl();
    private Scanner sc = new Scanner(System.in);

    public void start() {
        while (true) {
            System.out.println("1. Register Patient");
            System.out.println("2. Update Patient");
            System.out.println("3. Delete Patient");
            System.out.println("4. Search Patient");
            System.out.println("5. Reports");
            System.out.println("6. View Patient Queue");
            System.out.println("7. Call Next Patient");
            System.out.println("8. Show Queue Size");
            System.out.println("9. Assign Next Patient to Doctor");
            System.out.println("10. Back");
            System.out.print("Choose: ");
            String choice = sc.nextLine();
            if (choice.equals("1")) {
                registerPatient();
            } else if (choice.equals("2")) {
                updatePatient();
            } else if (choice.equals("3")) {
                deletePatient();
            } else if (choice.equals("4")) {
                searchPatient();
            } else if (choice.equals("5")) {
                reports();
            } else if (choice.equals("6")) {
                viewQueue();
            } else if (choice.equals("7")) {
                callNextPatient();
            } else if (choice.equals("8")) {
                System.out.println("Queue size: " + control.queueSize());
            } else if (choice.equals("9")) {
                assignNextPatientToDoctor();
            } else if (choice.equals("10")) {
                break;
            }
        }
    }

    private void registerPatient() {
        System.out.print("ID: ");
        String id = sc.nextLine();
        System.out.print("Name: ");
        String name = sc.nextLine();
        System.out.print("Gender: ");
        String gender = sc.nextLine();
        System.out.print("Age: ");
        int age = Integer.parseInt(sc.nextLine());
        System.out.print("Phone: ");
        String phone = sc.nextLine();
        System.out.print("Address: ");
        String address = sc.nextLine();
        System.out.print("Allergies (semicolon separated): ");
        String[] allergyArr = sc.nextLine().split(";");
        adt.List<String> allergies = new adt.List<>();
        for (String a : allergyArr) allergies.add(a);
        boolean success = control.registerPatient(id, name, gender, age, phone, address, allergies);
        System.out.println(success ? "Patient registered." : "Patient ID already exists.");
    }

    private void updatePatient() {
        System.out.print("ID: ");
        String id = sc.nextLine();
        Patient p = control.getPatientById(id);
        if (p == null) {
            System.out.println("Patient not found.");
            return;
        }
        System.out.print("New Name (blank to skip): ");
        String name = sc.nextLine();
        if (!name.isEmpty()) p.setName(name);
        System.out.print("New Gender (blank to skip): ");
        String gender = sc.nextLine();
        if (!gender.isEmpty()) p.setGender(gender);
        System.out.print("New Age (blank to skip): ");
        String ageStr = sc.nextLine();
        if (!ageStr.isEmpty()) p.setAge(Integer.parseInt(ageStr));
        System.out.print("New Phone (blank to skip): ");
        String phone = sc.nextLine();
        if (!phone.isEmpty()) p.setPhone(phone);
        System.out.print("New Address (blank to skip): ");
        String address = sc.nextLine();
        if (!address.isEmpty()) p.setAddress(address);
        System.out.print("New Allergies (semicolon separated, blank to skip): ");
        String allergiesStr = sc.nextLine();
        if (!allergiesStr.isEmpty()) {
            adt.List<String> allergies = new adt.List<>();
            for (String a : allergiesStr.split(";")) allergies.add(a);
            p.setAllergies(allergies);
        }
        control.updatePatient(p);
        System.out.println("Patient updated.");
    }

    private void deletePatient() {
        System.out.print("ID: ");
        String id = sc.nextLine();
        boolean success = control.deletePatient(id);
        System.out.println(success ? "Patient deleted." : "Patient not found.");
    }

    private void searchPatient() {
        System.out.print("ID: ");
        String id = sc.nextLine();
        Patient p = control.getPatientById(id);
        if (p != null) {
            System.out.println(p);
        } else {
            System.out.println("Patient not found.");
        }
    }

    private void reports() {
        System.out.println("1. List all patients");
        System.out.println("2. List patients by gender");
        System.out.println("3. List patients by age range");
        System.out.println("4. List patients with allergies");
        System.out.println("5. Count patients");
        System.out.print("Choose: ");
        String choice = sc.nextLine();
        if (choice.equals("1")) {
            adt.List<Patient> list = control.getAllPatients();
            for (int i = 0; i < list.size(); i++) System.out.println(list.get(i));
        } else if (choice.equals("2")) {
            // Bar chart by gender
            String[] genders = {"Male", "Female", "Other"};
            int[] counts = new int[genders.length];
            adt.List<Patient> list = control.getAllPatients();
            for (int i = 0; i < list.size(); i++) {
                String g = list.get(i).getGender().trim().toLowerCase();
                if (g.equals("male")) counts[0]++;
                else if (g.equals("female")) counts[1]++;
                else counts[2]++;
            }
            BarChartUtil.printBarChart(genders, counts, "Patients by Gender");
        } else if (choice.equals("3")) {
            System.out.print("Min age: ");
            int min = Integer.parseInt(sc.nextLine());
            System.out.print("Max age: ");
            int max = Integer.parseInt(sc.nextLine());
            adt.List<Patient> list = control.getPatientsByAgeRange(min, max);
            for (int i = 0; i < list.size(); i++) System.out.println(list.get(i));
        } else if (choice.equals("4")) {
            adt.List<Patient> list = control.getPatientsWithAllergies();
            for (int i = 0; i < list.size(); i++) System.out.println(list.get(i));
        } else if (choice.equals("5")) {
            System.out.println("Total patients: " + control.getAllPatients().size());
        }
    }

    private void viewQueue() {
        System.out.println("Next patient in queue: ");
        Patient p = control.peekQueue();
        if (p != null) {
            System.out.println(p);
        } else {
            System.out.println("Queue is empty.");
        }
    }

    private void callNextPatient() {
        Patient p = control.dequeuePatient();
        if (p != null) {
            System.out.println("Calling patient: " + p.getName() + " (ID: " + p.getId() + ")");
        } else {
            System.out.println("Queue is empty.");
        }
    }

    private void assignNextPatientToDoctor() {
        Patient patient = control.dequeuePatient();
        if (patient == null) {
            System.out.println("No patients in queue.");
            return;
        }
        // Find available doctor (first doctor for simplicity)
        adt.List<Doctor> doctors = doctorControl.getAllDoctors();
        Doctor availableDoctor = null;
        for (int i = 0; i < doctors.size(); i++) {
            availableDoctor = doctors.get(i);
            break; // For now, just pick the first doctor
        }
        if (availableDoctor == null) {
            System.out.println("No available doctors.");
            control.enqueuePatient(patient); // Put patient back in queue
            return;
        }
        // Create consultation record
        String consId = "CONS" + System.currentTimeMillis();
        Consultation consultation = new Consultation(consId, patient.getId(), availableDoctor.getId(), "today", "", "");
        consultationControl.addConsultation(consultation.getId(), consultation.getPatientId(), consultation.getDoctorId(), consultation.getDate(), consultation.getDiagnosis(), consultation.getNotes());
        System.out.println("Assigned patient " + patient.getName() + " (ID: " + patient.getId() + ") to Doctor " + availableDoctor.getName() + " (ID: " + availableDoctor.getId() + ")");
        System.out.println("Consultation record created: " + consId);
    }
}
