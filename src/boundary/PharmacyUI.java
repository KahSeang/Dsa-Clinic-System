package boundary;

import adt.List;
import control.PharmacyControl;
import entity.Medicine;
import java.util.Scanner;
import boundary.BarChartUtil;

public class PharmacyUI {
    private PharmacyControl control = new PharmacyControl();
    private Scanner sc = new Scanner(System.in);

    public void start() {
        while (true) {
            System.out.println("1. Add Medicine");
            System.out.println("2. Update Medicine");
            System.out.println("3. Delete Medicine");
            System.out.println("4. Search Medicine");
            System.out.println("5. Reports");
            System.out.println("6. Back");
            System.out.print("Choose: ");
            String choice = sc.nextLine();
            if (choice.equals("1")) {
                addMedicine();
            } else if (choice.equals("2")) {
                updateMedicine();
            } else if (choice.equals("3")) {
                deleteMedicine();
            } else if (choice.equals("4")) {
                searchMedicine();
            } else if (choice.equals("5")) {
                reports();
            } else if (choice.equals("6")) {
                break;
            }
        }
    }

    private void addMedicine() {
        System.out.print("Code: ");
        String code = sc.nextLine();
        System.out.print("Name: ");
        String name = sc.nextLine();
        System.out.print("Quantity: ");
        int quantity = Integer.parseInt(sc.nextLine());
        boolean success = control.addMedicine(code, name, quantity);
        System.out.println(success ? "Medicine added." : "Medicine code already exists.");
    }

    private void updateMedicine() {
        System.out.print("Code: ");
        String code = sc.nextLine();
        Medicine m = control.getMedicineByCode(code);
        if (m == null) {
            System.out.println("Medicine not found.");
            return;
        }
        System.out.print("New Name (blank to skip): ");
        String name = sc.nextLine();
        if (!name.isEmpty()) m.setQuantity(Integer.parseInt(name));
        System.out.print("New Quantity (blank to skip): ");
        String qtyStr = sc.nextLine();
        if (!qtyStr.isEmpty()) m.setQuantity(Integer.parseInt(qtyStr));
        control.updateMedicine(m);
        System.out.println("Medicine updated.");
    }

    private void deleteMedicine() {
        System.out.print("Code: ");
        String code = sc.nextLine();
        boolean success = control.deleteMedicine(code);
        System.out.println(success ? "Medicine deleted." : "Medicine not found.");
    }

    private void searchMedicine() {
        System.out.print("Code: ");
        String code = sc.nextLine();
        Medicine m = control.getMedicineByCode(code);
        if (m != null) {
            System.out.println(m);
        } else {
            System.out.println("Medicine not found.");
        }
    }

    private void reports() {
        System.out.println("1. List all medicines");
        System.out.println("2. List medicines by name");
        System.out.println("3. List medicines by quantity range");
        System.out.println("4. Count medicines");
        System.out.println("5. List medicines with low stock (<10)");
        System.out.print("Choose: ");
        String choice = sc.nextLine();
        if (choice.equals("1")) {
            adt.List<Medicine> list = control.getAllMedicines();
            for (int i = 0; i < list.size(); i++) System.out.println(list.get(i));
        } else if (choice.equals("2")) {
            System.out.print("Name: ");
            String name = sc.nextLine();
            adt.List<Medicine> list = control.getMedicinesByName(name);
            for (int i = 0; i < list.size(); i++) System.out.println(list.get(i));
        } else if (choice.equals("3")) {
            System.out.print("Min: ");
            int min = Integer.parseInt(sc.nextLine());
            System.out.print("Max: ");
            int max = Integer.parseInt(sc.nextLine());
            adt.List<Medicine> list = control.getMedicinesByQuantityRange(min, max);
            for (int i = 0; i < list.size(); i++) System.out.println(list.get(i));
        } else if (choice.equals("4")) {
            System.out.println("Total medicines: " + control.countMedicines());
        } else if (choice.equals("5")) {
            // Bar chart for stock levels
            adt.List<Medicine> list = control.getAllMedicines();
            String[] names = new String[list.size()];
            int[] stocks = new int[list.size()];
            for (int i = 0; i < list.size(); i++) {
                names[i] = list.get(i).getName();
                stocks[i] = list.get(i).getQuantity();
            }
            BarChartUtil.printBarChart(names, stocks, "Medicine Stock Levels");
        }
    }
} 