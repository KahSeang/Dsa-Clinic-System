package control;

import adt.List;
import entity.Treatment;
import util.FileIO;

public class TreatmentControl {
    private static final String TREATMENT_FILE = "treatments.txt";
    private List<Treatment> treatments;
    private final ConsultationControl consultationControl = new ConsultationControl();

    public TreatmentControl() {
        treatments = loadTreatments();
    }

    private List<Treatment> loadTreatments() {
        List<Treatment> list = new List<>();
        try {
            List<String> lines = FileIO.readLines(TREATMENT_FILE);
            for (int i = 0; i < lines.size(); i++) {
                list.add(Treatment.fromFileString(lines.get(i)));
            }
        } catch (Exception e) {
            // File  not exist gok
        }
        return list;
    }

    private void saveTreatments() {
        List<String> lines = new List<>();
        for (int i = 0; i < treatments.size(); i++) {
            lines.add(treatments.get(i).toFileString());
        }
        try {
            FileIO.writeLines(TREATMENT_FILE, lines);
        } catch (Exception e) {
            // Handle error
        }
    }

    public boolean addTreatment(String id, String consultationId, String treatmentDetails, String medicineList) {
        if (!isNonEmpty(id) || getTreatmentById(id) != null) return false;
        if (!isNonEmpty(consultationId) || consultationControl.getConsultationById(consultationId) == null) return false;
        if (!isNonEmpty(treatmentDetails)) return false;
        treatments.add(new Treatment(id, consultationId, treatmentDetails, medicineList));
        saveTreatments();
        return true;
    }

    public void updateTreatment(Treatment t) {
        saveTreatments();
    }

    public boolean deleteTreatment(String id) {
        for (int i = 0; i < treatments.size(); i++) {
            if (treatments.get(i).getId().equals(id)) {
                treatments.remove(i);
                saveTreatments();
                return true;
            }
        }
        return false;
    }

    public Treatment getTreatmentById(String id) {
        for (int i = 0; i < treatments.size(); i++) {
            if (treatments.get(i).getId().equals(id)) return treatments.get(i);
        }
        return null;
    }

    public List<Treatment> getAllTreatments() {
        return treatments;
    }

    public List<Treatment> getTreatmentsByConsultation(String consultationId) {
        List<Treatment> result = new List<>();
        for (int i = 0; i < treatments.size(); i++) {
            if (treatments.get(i).getConsultationId().equals(consultationId)) result.add(treatments.get(i));
        }
        return result;
    }

    public List<Treatment> getTreatmentsByMedicine(String medicine) {
        List<Treatment> result = new List<>();
        for (int i = 0; i < treatments.size(); i++) {
            if (treatments.get(i).getMedicineList().contains(medicine)) result.add(treatments.get(i));
        }
        return result;
    }

    public int countTreatments() {
        return treatments.size();
    }

    private boolean isNonEmpty(String s) { return s != null && !s.trim().isEmpty(); }
} 