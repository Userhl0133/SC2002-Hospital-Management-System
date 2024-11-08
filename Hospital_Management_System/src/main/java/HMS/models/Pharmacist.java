package HMS.models;

import HMS.enums.Gender;
import HMS.enums.Role;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Pharmacist extends User {
    private int age;

    // Map to store inventory (Medication ID -> Quantity)
    private Map<Integer, Integer> inventory = new HashMap<>();
    
    // Map to store prescription statuses (Prescription ID -> Status)
    private Map<Integer, String> prescriptionStatus = new HashMap<>();

    public Pharmacist(String userId, String password, Gender gender, String name, Role role, int age) {
        super(userId, password, gender, name, role);
        this.age = age;

        // Initialize with some sample data
        inventory.put(101, 50); // Medication ID 101 with quantity 50
        inventory.put(102, 20); // Medication ID 102 with quantity 20
        prescriptionStatus.put(1, "pending"); // Prescription ID 1 is pending
        prescriptionStatus.put(2, "dispensed"); // Prescription ID 2 is dispensed
    }

    // Method to view appointment outcome record
    public void viewAppointmentOutcomeRecord() {
        System.out.println("Viewing appointment outcome record for pharmacist " + super.getName() + ".");
        for (Map.Entry<Integer, String> entry : prescriptionStatus.entrySet()) {
            System.out.println("Prescription ID: " + entry.getKey() + ", Status: " + entry.getValue());
        }
    }

    // Method to update prescription status
    public void updatePrescriptionStatus(int prescriptionId, String status) {
        // Check if the prescription ID exists and update the status
        if (prescriptionStatus.containsKey(prescriptionId)) {
            prescriptionStatus.put(prescriptionId, status);
            System.out.println("Updated prescription " + prescriptionId + " to status '" + status + "'.");
        } else {
            System.out.println("Prescription ID " + prescriptionId + " not found.");
        }
    }

    // Method to view current inventory
    public void viewInventory() {
        System.out.println("Displaying current inventory:");
        for (Map.Entry<Integer, Integer> entry : inventory.entrySet()) {
            System.out.println("Medication ID: " + entry.getKey() + ", Quantity: " + entry.getValue());
        }
    }

    // Method to submit a replenishment request
    public void submitReplenishmentRequest(int medicationId, int quantity) {
        if (inventory.containsKey(medicationId)) {
            // Update the inventory by adding the requested quantity
            int currentQuantity = inventory.get(medicationId);
            inventory.put(medicationId, currentQuantity + quantity);
            System.out.println("Replenishment request submitted for medication ID " + medicationId + ". New quantity: " + (currentQuantity + quantity));
        } else {
            System.out.println("Medication ID " + medicationId + " not found.");
        }
    }

    // Method to update inventory directly (set new quantity)
    public void updateInventory(int medicationId, int newQuantity) {
        if (inventory.containsKey(medicationId)) {
            inventory.put(medicationId, newQuantity);
            System.out.println("Updated inventory for medication ID " + medicationId + " to quantity " + newQuantity + ".");
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
        while (choice != 6) { // Increased menu size to handle new options
            System.out.println("-----Pharmacist Menu-----");
            System.out.println("1. View Appointment Outcome Record");
            System.out.println("2. Update Prescription Status");
            System.out.println("3. View Medication Inventory");
            System.out.println("4. Submit Replenishment Request");
            System.out.println("5. Update Medication Inventory");
            System.out.println("6. Logout");
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
                    // Update Medication Inventory
                    System.out.print("Enter Medication ID: ");
                    int medId = sc.nextInt();
                    System.out.print("Enter new inventory quantity: ");
                    int newQty = sc.nextInt();
                    updateInventory(medId, newQty);
                    break;
                case 6:
                    // Logout
                    System.out.println("Logging out");
                    break;
                default:
                    System.out.println("Invalid Option");
            }
        }
    }
}
