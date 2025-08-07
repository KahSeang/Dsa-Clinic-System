package control;

import adt.List;
import entity.Doctor;
import util.FileIO;

public class DoctorControl {
    private static final String DOCTOR_FILE = "doctors.txt";
    private List<Doctor> doctors;

    public DoctorControl() {
        doctors = loadDoctors();
    }

    private List<Doctor> loadDoctors() {
        List<Doctor> list = new List<>();
        try {
            List<String> lines = FileIO.readLines(DOCTOR_FILE);
            for (int i = 0; i < lines.size(); i++) {
                list.add(Doctor.fromFileString(lines.get(i)));
            }
        } catch (Exception e) {
            // File may not exist yet
        }
        return list;
    }

    private void saveDoctors() {
        List<String> lines = new List<>();
        for (int i = 0; i < doctors.size(); i++) {
            lines.add(doctors.get(i).toFileString());
        }
        try {
            FileIO.writeLines(DOCTOR_FILE, lines);
        } catch (Exception e) {
            // Handle error
        }
    }

    public boolean addDoctor(String id, String name, String specialization, String phone, String schedule) {
        if (getDoctorById(id) != null) return false;
        doctors.add(new Doctor(id, name, specialization, phone, schedule));
        saveDoctors();
        return true;
    }

    public void updateDoctor(Doctor d) {
        saveDoctors();
    }

    public boolean deleteDoctor(String id) {
        for (int i = 0; i < doctors.size(); i++) {
            if (doctors.get(i).getId().equals(id)) {
                doctors.remove(i);
                saveDoctors();
                return true;
            }
        }
        return false;
    }

    public Doctor getDoctorById(String id) {
        for (int i = 0; i < doctors.size(); i++) {
            if (doctors.get(i).getId().equals(id)) return doctors.get(i);
        }
        return null;
    }

    public List<Doctor> getAllDoctors() {
        return doctors;
    }

    public List<Doctor> getDoctorsBySpecialization(String specialization) {
        List<Doctor> result = new List<>();
        for (int i = 0; i < doctors.size(); i++) {
            if (doctors.get(i).getSpecialization().equalsIgnoreCase(specialization)) result.add(doctors.get(i));
        }
        return result;
    }

    public List<Doctor> getDoctorsBySchedule(String day) {
        List<Doctor> result = new List<>();
        for (int i = 0; i < doctors.size(); i++) {
            if (doctors.get(i).getSchedule().contains(day)) result.add(doctors.get(i));
        }
        return result;
    }

    public int countDoctors() {
        return doctors.size();
    }

    public List<Doctor> getDoctorsByPhone(String phone) {
        List<Doctor> result = new List<>();
        for (int i = 0; i < doctors.size(); i++) {
            if (doctors.get(i).getPhone().equals(phone)) result.add(doctors.get(i));
        }
        return result;
    }
} 