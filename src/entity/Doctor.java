package entity;

import adt.List;
import adt.Map;

public class Doctor implements Comparable<Doctor> {

    private String id;
    private String name;
    private String specialization;
    private String phone;
    private String schedule;
    // Timetable: day (e.g., "Monday") -> list of available time slots (e.g.,
    // "09:00", "10:00")
    private Map<String, List<String>> timetable = new Map<>();

    public Doctor(String id, String name, String specialization, String phone, String schedule) {
        this.id = id;
        this.name = name;
        this.specialization = specialization;
        this.phone = phone;
        this.schedule = schedule;
        // Initialize timetable with default slots for each day in schedule
        for (String day : schedule.split(",")) {
            List<String> slots = new List<>();
            slots.add("09:00");
            slots.add("10:00");
            slots.add("11:00");
            slots.add("14:00");
            slots.add("15:00");
            timetable.put(day.trim(), slots);
        }
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getSpecialization() {
        return specialization;
    }

    public String getPhone() {
        return phone;
    }

    public String getSchedule() {
        return schedule;
    }

    public Map<String, List<String>> getTimetable() {
        return timetable;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSpecialization(String specialization) {
        this.specialization = specialization;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setSchedule(String schedule) {
        this.schedule = schedule;
    }

    public void setTimetable(Map<String, List<String>> timetable) {
        this.timetable = timetable;
    }

    // Book a slot: remove from available slots
    public boolean bookSlot(String day, String time) {
        List<String> slots = timetable.get(day);
        if (slots != null) {
            for (int i = 0; i < slots.size(); i++) {
                if (slots.get(i).equals(time)) {
                    slots.remove(i);
                    return true;
                }
            }
        }
        return false;
    }

    // Cancel a slot: add back to available slots (no排序)
    public void cancelSlot(String day, String time) {
        List<String> slots = timetable.get(day);
        if (slots != null) {
            boolean exists = false;
            for (int i = 0; i < slots.size(); i++) {
                if (slots.get(i).equals(time)) {
                    exists = true;
                    break;
                }
            }
            if (!exists) {
                slots.add(time);
            }
        }
    }

    // Convert to a line for file storage (CSV)
    public String toFileString() {
        StringBuilder sb = new StringBuilder();
        sb.append(id).append(",").append(name).append(",").append(specialization).append(",")
          .append(phone).append(",").append(schedule);
        // Timetable not serialized for simplicity (can be enhanced later)
        return sb.toString();
    }
    // Parse from a file line
    public static Doctor fromFileString(String line) {
        String[] parts = line.split(",", 5);
        return new Doctor(parts[0], parts[1], parts[2], parts[3], parts[4]);
    }

    @Override
    public int compareTo(Doctor other) {
        return this.id.compareTo(other.id);
    }

    @Override
    public String toString() {
        return String.format("%s | %s | %s | %s | %s", id, name, specialization, phone, schedule);
    }
}
