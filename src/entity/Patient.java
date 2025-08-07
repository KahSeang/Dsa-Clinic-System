package entity;

import adt.List;

public class Patient implements Comparable<Patient> {

    private String id;
    private String name;
    private String gender;
    private int age;
    private String phone;
    private String address;
    // List of allergies (e.g., "Penicillin", "Aspirin")
    private List<String> allergies = new List<>();

    public Patient(String id, String name, String gender, int age, String phone, String address) {
        this.id = id;
        this.name = name;
        this.gender = gender;
        this.age = age;
        this.phone = phone;
        this.address = address;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getGender() {
        return gender;
    }

    public int getAge() {
        return age;
    }

    public String getPhone() {
        return phone;
    }

    public String getAddress() {
        return address;
    }

    public List<String> getAllergies() {
        return allergies;
    }

    public void setAllergies(List<String> allergies) {
        this.allergies = allergies;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public int compareTo(Patient other) {
        return this.id.compareTo(other.id);
    }

    @Override
    public String toString() {
        return String.format("%s | %s | %s | %d | %s | %s | Allergies: %s", id, name, gender, age, phone, address, allergies);
    }

    // Convert to a line for file storage (CSV)
    public String toFileString() {
        StringBuilder sb = new StringBuilder();
        sb.append(id).append(",").append(name).append(",").append(gender).append(",")
          .append(age).append(",").append(phone).append(",").append(address).append(",");
        for (int i = 0; i < allergies.size(); i++) {
            sb.append(allergies.get(i));
            if (i < allergies.size() - 1) sb.append(";");
        }
        return sb.toString();
    }
    // Parse from a file line
    public static Patient fromFileString(String line) {
        String[] parts = line.split(",", 7);
        Patient p = new Patient(parts[0], parts[1], parts[2], Integer.parseInt(parts[3]), parts[4], parts[5]);
        if (parts.length > 6 && !parts[6].isEmpty()) {
            String[] allergyArr = parts[6].split(";");
            List<String> allergies = new List<>();
            for (String a : allergyArr) allergies.add(a);
            p.setAllergies(allergies);
        }
        return p;
    }
}
