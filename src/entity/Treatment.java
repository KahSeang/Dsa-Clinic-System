package entity;

public class Treatment implements Comparable<Treatment> {

    private String id;
    private String consultationId;
    private String treatmentDetails;
    private String medicineList;

    public Treatment(String id, String consultationId, String treatmentDetails, String medicineList) {
        this.id = id;
        this.consultationId = consultationId;
        this.treatmentDetails = treatmentDetails;
        this.medicineList = medicineList;
    }

    public String getId() {
        return id;
    }

    public String getConsultationId() {
        return consultationId;
    }

    public String getTreatmentDetails() {
        return treatmentDetails;
    }

    public String getMedicineList() {
        return medicineList;
    }

    public void setTreatmentDetails(String treatmentDetails) {
        this.treatmentDetails = treatmentDetails;
    }

    public void setMedicineList(String medicineList) {
        this.medicineList = medicineList;
    }

    // Convert to a line for file storage (CSV)
    public String toFileString() {
        return String.join(",", id, consultationId, treatmentDetails, medicineList);
    }
    // Parse from a file line
    public static Treatment fromFileString(String line) {
        String[] parts = line.split(",", 4);
        return new Treatment(parts[0], parts[1], parts[2], parts[3]);
    }

    @Override
    public int compareTo(Treatment other) {
        return this.id.compareTo(other.id);
    }

    @Override
    public String toString() {
        return String.format("%s | %s | %s | %s", id, consultationId, treatmentDetails, medicineList);
    }
}
