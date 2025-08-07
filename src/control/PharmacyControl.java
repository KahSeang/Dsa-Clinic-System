package control;

import adt.List;
import entity.Medicine;
import util.FileIO;

public class PharmacyControl {
    private static final String MEDICINE_FILE = "medicines.txt";
    private List<Medicine> medicines;

    public PharmacyControl() {
        medicines = loadMedicines();
    }

    private List<Medicine> loadMedicines() {
        List<Medicine> list = new List<>();
        try {
            List<String> lines = FileIO.readLines(MEDICINE_FILE);
            for (int i = 0; i < lines.size(); i++) {
                list.add(Medicine.fromFileString(lines.get(i)));
            }
        } catch (Exception e) {
            // File may not exist yet
        }
        return list;
    }

    private void saveMedicines() {
        List<String> lines = new List<>();
        for (int i = 0; i < medicines.size(); i++) {
            lines.add(medicines.get(i).toFileString());
        }
        try {
            FileIO.writeLines(MEDICINE_FILE, lines);
        } catch (Exception e) {
            // Handle error
        }
    }

    public boolean addMedicine(String code, String name, int quantity) {
        if (getMedicineByCode(code) != null) return false;
        medicines.add(new Medicine(code, name, quantity));
        saveMedicines();
        return true;
    }

    public void updateMedicine(Medicine m) {
        saveMedicines();
    }

    public boolean deleteMedicine(String code) {
        for (int i = 0; i < medicines.size(); i++) {
            if (medicines.get(i).getCode().equals(code)) {
                medicines.remove(i);
                saveMedicines();
                return true;
            }
        }
        return false;
    }

    public Medicine getMedicineByCode(String code) {
        for (int i = 0; i < medicines.size(); i++) {
            if (medicines.get(i).getCode().equals(code)) return medicines.get(i);
        }
        return null;
    }

    public List<Medicine> getAllMedicines() {
        return medicines;
    }

    public List<Medicine> getMedicinesByName(String name) {
        List<Medicine> result = new List<>();
        for (int i = 0; i < medicines.size(); i++) {
            if (medicines.get(i).getName().equalsIgnoreCase(name)) result.add(medicines.get(i));
        }
        return result;
    }

    public List<Medicine> getMedicinesByQuantityRange(int min, int max) {
        List<Medicine> result = new List<>();
        for (int i = 0; i < medicines.size(); i++) {
            int qty = medicines.get(i).getQuantity();
            if (qty >= min && qty <= max) result.add(medicines.get(i));
        }
        return result;
    }

    public int countMedicines() {
        return medicines.size();
    }
} 