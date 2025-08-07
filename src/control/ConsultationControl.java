package control;

import adt.List;
import entity.Consultation;
import util.FileIO;

public class ConsultationControl {
    private static final String CONSULTATION_FILE = "consultations.txt";
    private List<Consultation> consultations;

    public ConsultationControl() {
        consultations = loadConsultations();
    }

    private List<Consultation> loadConsultations() {
        List<Consultation> list = new List<>();
        try {
            List<String> lines = FileIO.readLines(CONSULTATION_FILE);
            for (int i = 0; i < lines.size(); i++) {
                list.add(Consultation.fromFileString(lines.get(i)));
            }
        } catch (Exception e) {
            // File may not exist yet
        }
        return list;
    }

    private void saveConsultations() {
        List<String> lines = new List<>();
        for (int i = 0; i < consultations.size(); i++) {
            lines.add(consultations.get(i).toFileString());
        }
        try {
            FileIO.writeLines(CONSULTATION_FILE, lines);
        } catch (Exception e) {
            // Handle error
        }
    }

    public boolean addConsultation(String id, String patientId, String doctorId, String date, String diagnosis, String notes) {
        if (getConsultationById(id) != null) return false;
        consultations.add(new Consultation(id, patientId, doctorId, date, diagnosis, notes));
        saveConsultations();
        return true;
    }

    public void updateConsultation(Consultation c) {
        saveConsultations();
    }

    public boolean deleteConsultation(String id) {
        for (int i = 0; i < consultations.size(); i++) {
            if (consultations.get(i).getId().equals(id)) {
                consultations.remove(i);
                saveConsultations();
                return true;
            }
        }
        return false;
    }

    public Consultation getConsultationById(String id) {
        for (int i = 0; i < consultations.size(); i++) {
            if (consultations.get(i).getId().equals(id)) return consultations.get(i);
        }
        return null;
    }

    public List<Consultation> getAllConsultations() {
        return consultations;
    }

    public List<Consultation> getConsultationsByPatient(String patientId) {
        List<Consultation> result = new List<>();
        for (int i = 0; i < consultations.size(); i++) {
            if (consultations.get(i).getPatientId().equals(patientId)) result.add(consultations.get(i));
        }
        return result;
    }

    public List<Consultation> getConsultationsByDoctor(String doctorId) {
        List<Consultation> result = new List<>();
        for (int i = 0; i < consultations.size(); i++) {
            if (consultations.get(i).getDoctorId().equals(doctorId)) result.add(consultations.get(i));
        }
        return result;
    }

    public List<Consultation> getConsultationsByDate(String date) {
        List<Consultation> result = new List<>();
        for (int i = 0; i < consultations.size(); i++) {
            if (consultations.get(i).getDate().equals(date)) result.add(consultations.get(i));
        }
        return result;
    }

    public int countConsultations() {
        return consultations.size();
    }
} 