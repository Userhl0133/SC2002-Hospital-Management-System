package HMS.models;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import HMS.enums.Gender;
import HMS.enums.Role;

public class Pharmacist extends User {
    private int age;

    // Example data structures to represent inventory and appointment records
    private Map<Integer, String> prescriptionStatus = new HashMap<>();
    private Map<Integer, Integer> inventory = new HashMap<>();

    public Pharmacist(String userId, String password, Gender gender, String name, Role role, int age) {
        super(userId, password, gender, name, role);
        this.age = age;
        // Initialize with some data for demonstration
        prescriptionStatus.put(1, "pending");
        prescriptionStatus.put(2, "dispensed");
        inventory.put(101, 50);  // Medication ID 101 with quantity 50
        inventory.put(102, 20);  // Medication ID 102 with quantity 20
    }

    public void viewAppointmentOutcomeRecord() {
        // Fetch and display the appointment outcome record
        System.out.println("Viewing appointment outcome record for pharmacist " + super.getName() + ".");
        for (Map.Entry<Integer, String> entry : prescriptionStatus.entrySet()) {
            System.out.println("Prescription ID: " + entry.getKey() + ", Status: " + entry.getValue());
        }
    }

    public void updatePrescriptionStatus(int prescriptionId, String status) {
        // Update the status of a given prescription
        if (prescriptionStatus.containsKey(prescriptionId)) {
            prescriptionStatus.put(prescriptionId, status);
            System.out.println("Updated prescription " + prescriptionId + " to status '" + status + "'.");
        } else {
            System.out.println("Prescription ID " + prescriptionId + " not found.");
        }
    }

    public void viewInventory() {
        // Display current inventory
        System.out.println("Displaying current inventory:");
        for (Map.Entry<Integer, Integer> entry : inventory.entrySet()) {
            System.out.println("Medication ID: " + entry.getKey() + ", Quantity: " + entry.getValue());
        }
    }

    public void submitReplenishmentRequest(int medicationId, int quantity) {
        // Submit a request to replenish a given medication
        if (inventory.containsKey(medicationId)) {
            int currentQuantity = inventory.get(medicationId);
            inventory.put(medicationId, currentQuantity + quantity);
            System.out.println("Replenishment request submitted for medication ID " + medicationId + " with quantity " + quantity + ".");
        } else {
            System.out.println("Medication ID " + medicationId + " not found.");
        }
    }

    @Override
    public String toString() {
        return String.format("User ID: %s, Name: %s, Gender: %s, Role: %s",
                super.getUserId(), super.getName(), super.getGender(), super.getRole());
    }

    public void showMenu() {
        int choice = 0;
        Scanner sc = new Scanner(System.in);
        while (choice != 5) {
            System.out.println("-----Pharmacist Menu-----");
            System.out.println("1. View Appointment Outcome Record");
            System.out.println("2. Update Prescription Status");
            System.out.println("3. View Medication Inventory");
            System.out.println("4. Submit Replenishment Request");
            System.out.println("5. Logout");
            System.out.print("Please select an option: ");
            choice = sc.nextInt();
            switch (choice) {
                case 1:
                    viewAppointmentOutcomeRecord();
                    break;

                case 2:
                    // Update Prescription Status
                    System.out.print("Enter Prescription ID: ");
                    int prescriptionId = sc.nextInt();
                    sc.nextLine(); // consume newline
                    System.out.print("Enter new status: ");
                    String status = sc.nextLine();
                    updatePrescriptionStatus(prescriptionId, status);
                    break;

                case 3:
                    viewInventory();
                    break;

                case 4:
                    // Submit Replenishment Request
                    System.out.print("Enter Medication ID: ");
                    int medicationId = sc.nextInt();
                    System.out.print("Enter quantity: ");
                    int quantity = sc.nextInt();
                    submitReplenishmentRequest(medicationId, quantity);
                    break;

                case 5:
                    // Logout
                    System.out.println("Logging out");
                    break;

                default:
                    System.out.println("Invalid Option");
            }
        }
    }
}
