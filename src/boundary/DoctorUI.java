package boundary;

import adt.List;
import control.DoctorControl;
import entity.Doctor;
import java.util.Scanner;
import boundary.BarChartUtil;

public class DoctorUI {
    private DoctorControl control = new DoctorControl();
    private Scanner sc = new Scanner(System.in);

    public void start() {
        while (true) {
            System.out.println("1. Add Doctor");
            System.out.println("2. Update Doctor");
            System.out.println("3. Delete Doctor");
            System.out.println("4. Search Doctor");
            System.out.println("5. Reports");
            System.out.println("6. Back");
            System.out.print("Choose: ");
            String choice = sc.nextLine();
            if (choice.equals("1")) {
                addDoctor();
            } else if (choice.equals("2")) {
                updateDoctor();
            } else if (choice.equals("3")) {
                deleteDoctor();
            } else if (choice.equals("4")) {
                searchDoctor();
            } else if (choice.equals("5")) {
                reports();
            } else if (choice.equals("6")) {
                break;
            }
        }
    }

    private void addDoctor() {
        System.out.print("ID: ");
        String id = sc.nextLine();
        System.out.print("Name: ");
        String name = sc.nextLine();
        System.out.print("Specialization: ");
        String specialization = sc.nextLine();
        System.out.print("Phone: ");
        String phone = sc.nextLine();
        System.out.print("Schedule (comma separated days): ");
        String schedule = sc.nextLine();
        boolean success = control.addDoctor(id, name, specialization, phone, schedule);
        System.out.println(success ? "Doctor added." : "Doctor ID already exists.");
    }

    private void updateDoctor() {
        System.out.print("ID: ");
        String id = sc.nextLine();
        Doctor d = control.getDoctorById(id);
        if (d == null) {
            System.out.println("Doctor not found.");
            return;
        }
        System.out.print("New Name (blank to skip): ");
        String name = sc.nextLine();
        if (!name.isEmpty()) d.setName(name);
        System.out.print("New Specialization (blank to skip): ");
        String specialization = sc.nextLine();
        if (!specialization.isEmpty()) d.setSpecialization(specialization);
        System.out.print("New Phone (blank to skip): ");
        String phone = sc.nextLine();
        if (!phone.isEmpty()) d.setPhone(phone);
        System.out.print("New Schedule (blank to skip): ");
        String schedule = sc.nextLine();
        if (!schedule.isEmpty()) d.setSchedule(schedule);
        control.updateDoctor(d);
        System.out.println("Doctor updated.");
    }

    private void deleteDoctor() {
        System.out.print("ID: ");
        String id = sc.nextLine();
        boolean success = control.deleteDoctor(id);
        System.out.println(success ? "Doctor deleted." : "Doctor not found.");
    }

    private void searchDoctor() {
        System.out.print("ID: ");
        String id = sc.nextLine();
        Doctor d = control.getDoctorById(id);
        if (d != null) {
            System.out.println(d);
        } else {
            System.out.println("Doctor not found.");
        }
    }

    private void reports() {
        System.out.println("1. List all doctors");
        System.out.println("2. List doctors by specialization");
        System.out.println("3. List doctors by schedule day");
        System.out.println("4. List doctors by phone");
        System.out.println("5. Count doctors");
        System.out.print("Choose: ");
        String choice = sc.nextLine();
        if (choice.equals("1")) {
            adt.List<Doctor> list = control.getAllDoctors();
            for (int i = 0; i < list.size(); i++) System.out.println(list.get(i));
        } else if (choice.equals("2")) {
            // Bar chart by specialization using only custom ADTs
            adt.List<Doctor> list = control.getAllDoctors();
            adt.List<String> specs = new adt.List<>();
            adt.List<Integer> counts = new adt.List<>();
            for (int i = 0; i < list.size(); i++) {
                String spec = list.get(i).getSpecialization();
                int idx = -1;
                for (int j = 0; j < specs.size(); j++) {
                    if (specs.get(j).equals(spec)) {
                        idx = j;
                        break;
                    }
                }
                if (idx == -1) {
                    specs.add(spec);
                    counts.add(1);
                } else {
                    counts.set(idx, counts.get(idx) + 1);
                }
            }
            String[] specArr = new String[specs.size()];
            int[] countArr = new int[counts.size()];
            for (int i = 0; i < specs.size(); i++) {
                specArr[i] = specs.get(i);
                countArr[i] = counts.get(i);
            }
            BarChartUtil.printBarChart(specArr, countArr, "Doctors by Specialization");
        } else if (choice.equals("3")) {
            System.out.print("Day: ");
            String day = sc.nextLine();
            adt.List<Doctor> list = control.getDoctorsBySchedule(day);
            for (int i = 0; i < list.size(); i++) System.out.println(list.get(i));
        } else if (choice.equals("4")) {
            System.out.print("Phone: ");
            String phone = sc.nextLine();
            adt.List<Doctor> list = control.getDoctorsByPhone(phone);
            for (int i = 0; i < list.size(); i++) System.out.println(list.get(i));
        } else if (choice.equals("5")) {
            System.out.println("Total doctors: " + control.countDoctors());
        }
    }
} 