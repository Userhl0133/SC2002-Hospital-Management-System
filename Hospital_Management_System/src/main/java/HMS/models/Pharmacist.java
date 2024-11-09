package HMS.models;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import HMS.enums.Gender;
import HMS.enums.Role;

public class Pharmacist extends User {
    private int age;
    // Map to store Medication objects (Medication ID -> Medication)
    private Map<Integer, Medication> inventory = new HashMap<>();
    // Map to store prescription statuses (Prescription ID -> Status)
    private Map<Integer, String> prescriptionStatus = new HashMap<>();

    public Pharmacist(String userId, String password, Gender gender, String name, Role role, int age) {
        super(userId, password, gender, name, role);
        this.age = age;
        // Initialize with some sample data
        inventory.put(101, new Medication(101, 10, 50, "Paracetamol")); // Medication ID 101 with quantity 50
        inventory.put(102, new Medication(102, 15, 20, "Aspirin")); // Medication ID 102 with quantity 20
        prescriptionStatus.put(1, "pending"); // Prescription ID 1 is pending
        prescriptionStatus.put(2, "dispensed"); // Prescription ID 2 is dispensed
    }

    // Method to view appointment outcome record
    public void viewAppointmentOutcomeRecord() {
        System.out.println("Viewing appointment outcome record for pharmacist " + super.getName() + ".");
        for (Map.Entry<Integer, String> entry : prescriptionStatus.entrySet()) {
            System.out.println("Prescription ID: " + entry.getKey() + " | Status: " + entry.getValue());
        }
    }

    // Method to update prescription status
    public void updatePrescriptionStatus(int prescriptionId, String status) {
        if (prescriptionStatus.containsKey(prescriptionId)) {
            prescriptionStatus.put(prescriptionId, status);
            System.out.println("Updated prescription " + prescriptionId + " to status '" + status + "'.");
        } else {
            System.out.println("Prescription ID " + prescriptionId + " not found.");
        }
    }

    // Method to view current inventory (displaying medication name, stock levels, and replenishment status)
    public void viewInventory() {
        System.out.println("Displaying current inventory:");
        for (Map.Entry<Integer, Medication> entry : inventory.entrySet()) {
            Medication med = entry.getValue();
            System.out.println("Medication ID: " + med.getMedicationId() + " | Name: " + med.getMedicationName() + " | Current Stock Level: " + med.getStockLevel() + " | Low Level Alert: " + med.getLowStockLevel() + " | Replenishment Status: " + med.getReplenishmentStatus());
        }
    }

    public void submitReplenishmentRequest(String pharmacistId, int medicationId, int quantity, Administrator admin) {
        // Validate if the entered pharmacist ID matches the actual pharmacist's userId
        if (!pharmacistId.equals(super.getUserId())) {
            System.out.println("Invalid Pharmacist ID. Replenishment request cannot be processed.");
            return;
        }
    
        Medication medication = inventory.get(medicationId);
        if (medication != null) {
            // Create a replenishment request object
            ReplenishmentRequest request = new ReplenishmentRequest(
                    Integer.parseInt(pharmacistId), 
                    Integer.parseInt(admin.getUserId()), // Administrator ID
                    medication.getMedicationName(),
                    quantity
            );
            // Submit to administrator's list
            admin.addReplenishmentRequest(request);
    
            // Update stock and set replenishment status
            medication.updateStock(medication.getStockLevel() + quantity);
            medication.setReplenishmentStatus("in-progress"); // Set status to "in-progress" when replenishment is requested
            System.out.println("Replenishment request submitted by Pharmacist ID: " + pharmacistId + " for medication ID: " + medicationId);
        } else {
            System.out.println("Medication ID: " + medicationId + " not found.");
        }
    }

    @Override
    public String toString() {
        return String.format("User ID: %s | Name: %s | Gender: %s | Role: %s", super.getUserId(), super.getName(), super.getGender(), super.getRole());
    }

    // Show menu for pharmacist
    @Override
    public void showMenu() {
        int choice = 0;
        Scanner sc = new Scanner(System.in);
        while (choice != 5) { // Menu options for the pharmacist
            System.out.println("=========================");
            System.out.println("-----Pharmacist Menu-----");
            System.out.println("=========================");
            System.out.println("1. View Appointment Outcome Record");
            System.out.println("2. Update Prescription Status");
            System.out.println("3. View Medication Inventory");
            System.out.println("4. Submit Replenishment Request");
            System.out.println("5. Logout");
            System.out.print("Please select an option: ");
            if (sc.hasNextInt()) {
                choice = sc.nextInt();
                switch (choice) {
                    case 1:
                        viewAppointmentOutcomeRecord();
                        break;
                    case 2:
                        // Update Prescription Status
                        System.out.print("Enter Prescription ID: ");
                        if (sc.hasNextInt()) {
                            int prescriptionId = sc.nextInt();
                            sc.nextLine(); // consume newline
                            System.out.print("Enter new status: ");
                            String status = sc.nextLine();
                            updatePrescriptionStatus(prescriptionId, status);
                        } else {
                            System.out.println("Invalid Prescription ID.");
                            sc.next(); // clear invalid input
                        }
                        break;
                    case 3:
                        // View Medication Inventory
                        viewInventory();
                        break;
                    case 4:
                        // Submit Replenishment Request
                        System.out.print("Enter Pharmacist ID: ");
                        String pharmacistId = sc.next(); // Input pharmacist ID as a string
                        System.out.print("Enter Medication ID for Replenishment: ");
                        if (sc.hasNextInt()) {
                            int medicationId = sc.nextInt();
                            System.out.print("Enter quantity: ");
                            if (sc.hasNextInt()) {
                                int quantity = sc.nextInt();
                                submitReplenishmentRequest(pharmacistId, medicationId, quantity);
                            } else {
                                System.out.println("Invalid quantity.");
                                sc.next(); // clear invalid input
                            }
                        } else {
                            System.out.println("Invalid Medication ID.");
                            sc.next(); // clear invalid input
                        }
                        break;
                    case 5:
                        // Logout
                        System.out.println("Logging out...");
                        return; // Exit the method and stop the loop, effectively logging out
                    default:
                        System.out.println("Invalid Option.");
                }
            } else {
                System.out.println("Invalid Option. Please enter a number between 1 and 5.");
                sc.next(); // clear invalid input
            }
        }
        sc.close(); // Close the scanner to free resources
    }
}
