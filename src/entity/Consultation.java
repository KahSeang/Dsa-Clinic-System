package entity;

public class Consultation implements Comparable<Consultation> {

    private String id;
    private String patientId;
    private String doctorId;
    private String date;
    private String diagnosis;
    private String notes;

    public Consultation(String id, String patientId, String doctorId, String date, String diagnosis, String notes) {
        this.id = id;
        this.patientId = patientId;
        this.doctorId = doctorId;
        this.date = date;
        this.diagnosis = diagnosis;
        this.notes = notes;
    }

    public String getId() {
        return id;
    }

    public String getPatientId() {
        return patientId;
    }

    public String getDoctorId() {
        return doctorId;
    }

    public String getDate() {
        return date;
    }

    public String getDiagnosis() {
        return diagnosis;
    }

    public String getNotes() {
        return notes;
    }

    public void setDiagnosis(String diagnosis) {
        this.diagnosis = diagnosis;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    @Override
    public int compareTo(Consultation other) {
        return this.id.compareTo(other.id);
    }

    @Override
    public String toString() {
        return String.format("%s | %s | %s | %s | %s | %s", id, patientId, doctorId, date, diagnosis, notes);
    }

    // Convert to a line for file storage (CSV)
    public String toFileString() {
        return String.join(",", id, patientId, doctorId, date, diagnosis, notes);
    }
    // Parse from a file line
    public static Consultation fromFileString(String line) {
        String[] parts = line.split(",", 6);
        return new Consultation(parts[0], parts[1], parts[2], parts[3], parts[4], parts[5]);
    }
}
