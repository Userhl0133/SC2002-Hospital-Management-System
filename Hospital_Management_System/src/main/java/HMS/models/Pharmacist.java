package HMS.models;

import java.util.Scanner;

import static HMS.MainApp.medications;
import HMS.enums.Gender;
import HMS.enums.Role;

public class Pharmacist extends User {

    private int age;

    // Constructor
    public Pharmacist(String userId, String password, Gender gender, String name, Role role, int age) {
        super(userId, password, gender, name, role);
        this.age = age;
    }

    // View Appointment Outcome Record (Placeholder)
    public void viewAppointmentOutcomeRecord() {
      
    }

    // Update Prescription Status (Placeholder method)
    public void updatePrescriptionStatus() {
        System.out.println("\n--- Update Prescription Status ---");
        // Placeholder implementation where the pharmacist can update the prescription status
        System.out.println("This feature is currently not implemented.");
    }

    // View Medication Inventory - Displaying Medication ID
    public void viewInventory() {
        System.out.println("\nCurrent Medication Inventory");
        // Iterate over medications and display the details
        for (Medication medication : medications) {
            System.out.println("Medication ID: " + medication.getMedicationId());
            System.out.println("Medication Name: " + medication.getMedicationName());
            System.out.println("Current Stock Level: " + medication.getStockLevel());
            System.out.println("Low Stock Level Alert: " + medication.getLowStockLevel());
            System.out.println("Replenishment Status: " + medication.getReplenishmentStatus());
            System.out.println("-------------------------------");
        }
    }

    // Submit Replenishment Request
    public void submitReplenishmentRequest() {
        Scanner sc = new Scanner(System.in);

        // Ask for the pharmacist ID
        System.out.print("Enter your Pharmacist ID: ");
        int enteredPharmacistId = sc.nextInt();

        // Validate if the entered ID matches the currently logged-in pharmacist's ID
        int pharmacistId = Integer.parseInt(super.getUserId()); // Assuming User ID is the same as pharmacist ID
        if (enteredPharmacistId != pharmacistId) {
            System.out.println("The entered Pharmacist ID does not match the logged-in Pharmacist ID.");
            return; // Exit if the ID does not match
        }

        // Ask for the medication ID
        System.out.print("Enter Medication ID to request replenishment: ");
        int medicationId = sc.nextInt();
        sc.nextLine();  // Clear the buffer

        // Find the medication by its ID
        Medication medication = medications.stream()
                .filter(med -> med.getMedicationId() == medicationId)
                .findFirst()
                .orElse(null);

        if (medication == null) {
            System.out.println("Medication with ID " + medicationId + " not found in inventory.");
            return; // Exit if medication is not found
        }

        // Ask for the replenishment quantity
        System.out.print("Enter quantity to request: ");
        int quantity = sc.nextInt();

        // Get current stock level of the medication
        int currentStock = medication.getStockLevel();
        System.out.println("Current stock level for " + medication.getMedicationName() + ": " + currentStock);

        // Create a new ReplenishmentRequest
        int administratorId = 1; // Placeholder: ideally, fetch this dynamically from the admin system
        ReplenishmentRequest request = new ReplenishmentRequest(pharmacistId, enteredPharmacistId, medication.getMedicationName(), currentStock, quantity);

        // Add the request to the replenishment request list
        ReplenishmentRequest.replenishmentRequests.add(request);

        // Update the replenishment status of the medication to "PENDING"
        medication.setReplenishmentStatus("pending");

        System.out.println("Replenishment request submitted for " + medication.getMedicationName() + " with quantity " + quantity + ".");
    }

    // Show the Pharmacist Menu
    public void showMenu() {
        int choice = 0;
        Scanner sc = new Scanner(System.in);
        while (choice != 5) { // Option 5 will be the logout option
            System.out.println("============================");
            System.out.println("----- Pharmacist Menu -------");
            System.out.println("============================");
            System.out.println("1. View Appointment Outcome Record");
            System.out.println("2. Update Prescription Status");
            System.out.println("3. View Medication Inventory");
            System.out.println("4. Submit Replenishment Request");
            System.out.println("5. Logout");
            System.out.print("Please select an option: ");
            choice = sc.nextInt();
            sc.nextLine(); // Clear the buffer after nextInt()
            switch (choice) {
                case 1:
                    // View Appointment Outcome Record
                    viewAppointmentOutcomeRecord();
                    break;

                case 2:
                    // Update Prescription Status (Placeholder message)
                    updatePrescriptionStatus();
                    break;

                case 3:
                    // View Medication Inventory - Linked to the Medication class
                    viewInventory(); // Method for viewing inventory
                    break;

                case 4:
                    // Submit Replenishment Request
                    submitReplenishmentRequest();
                    break;

                case 5:
                    // Logout
                    System.out.println("Logging out...");
                    break;

                default:
                    System.out.println("Invalid Option, please try again.");
            }
        }
    }
}
