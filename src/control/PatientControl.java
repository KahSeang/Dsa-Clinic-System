
package control;

import adt.List;
import adt.Queue;
import entity.Patient;
import util.FileIO;

public class PatientControl {
    private static final String PATIENT_FILE = "patients.txt";
    private List<Patient> patients;
    private Queue<Patient> patientQueue;

    public PatientControl() {
        patients = loadPatients();
        patientQueue = new Queue<>();
        // Optionally, load queue from file or reconstruct from patients
    }

    private List<Patient> loadPatients() {
        List<Patient> list = new List<>();
        try {
            List<String> lines = FileIO.readLines(PATIENT_FILE);
            for (int i = 0; i < lines.size(); i++) {
                list.add(Patient.fromFileString(lines.get(i)));
            }
        } catch (Exception e) {
            // File may not exist yet
        }
        return list;
    }

    private void savePatients() {
        List<String> lines = new List<>();
        for (int i = 0; i < patients.size(); i++) {
            lines.add(patients.get(i).toFileString());
        }
        try {
            FileIO.writeLines(PATIENT_FILE, lines);
        } catch (Exception e) {
            // Handle error
        }
    }

    public boolean registerPatient(String id, String name, String gender, int age, String phone, String address, List<String> allergies) {
        if (getPatientById(id) != null) return false;
        Patient p = new Patient(id, name, gender, age, phone, address);
        p.setAllergies(allergies);
        patients.add(p);
        patientQueue.enqueue(p); // Add to queue upon registration
        savePatients();
        return true;
    }

    public void updatePatient(Patient p) {
        savePatients();
    }

    public boolean deletePatient(String id) {
        for (int i = 0; i < patients.size(); i++) {
            if (patients.get(i).getId().equals(id)) {
                patients.remove(i);
                // Optionally, remove from queue if present
                savePatients();
                return true;
            }
        }
        return false;
    }

    public Patient getPatientById(String id) {
        for (int i = 0; i < patients.size(); i++) {
            if (patients.get(i).getId().equals(id)) return patients.get(i);
        }
        return null;
    }

    public List<Patient> getAllPatients() {
        return patients;
    }

    public List<Patient> getPatientsByGender(String gender) {
        List<Patient> result = new List<>();
        for (int i = 0; i < patients.size(); i++) {
            if (patients.get(i).getGender().equalsIgnoreCase(gender)) result.add(patients.get(i));
        }
        return result;
    }

    public List<Patient> getPatientsByAgeRange(int min, int max) {
        List<Patient> result = new List<>();
        for (int i = 0; i < patients.size(); i++) {
            int age = patients.get(i).getAge();
            if (age >= min && age <= max) result.add(patients.get(i));
        }
        return result;
    }

    public List<Patient> getPatientsWithAllergies() {
        List<Patient> result = new List<>();
        for (int i = 0; i < patients.size(); i++) {
            if (patients.get(i).getAllergies() != null && patients.get(i).getAllergies().size() > 0) result.add(patients.get(i));
        }
        return result;
    }

    // Queue management
    public void enqueuePatient(Patient p) {
        patientQueue.enqueue(p);
    }

    public Patient dequeuePatient() {
        return patientQueue.dequeue();
    }

    public Patient peekQueue() {
        return patientQueue.peek();
    }

    public int queueSize() {
        return patientQueue.size();
    }

    public boolean isQueueEmpty() {
        return patientQueue.isEmpty();
    }
}
