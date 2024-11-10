package HMS.models;

import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

import HMS.MainApp;  // Access to global variables in MainApp
import static HMS.MainApp.medications;
import HMS.enums.AppointmentStatus;
import HMS.enums.Gender;
import HMS.enums.ReplenishmentStatus;
import HMS.enums.Role; // Added import for ReplenishmentStatus

public class Pharmacist extends User {

    private int age;

    // Constructor
    public Pharmacist(String userId, String password, Gender gender, String name, Role role, int age) {
        super(userId, password, gender, name, role);
        this.age = age;
    }

    // Method to print out details of the pharmacist
    @Override
    public String toString() {
        return String.format("User ID: %s, Name: %s, Gender: %s, Role: %s, Age: %s",
                super.getUserId(), super.getName(), super.getGender(), super.getRole(), age);
    }

    public void viewCompletedAppointmentOutcomeRecords() {
        // Get all appointments with status COMPLETED
        List<Appointment> completedAppointments = MainApp.appointments.stream()
                .filter(a -> a.getAppointmentStatus() == AppointmentStatus.COMPLETED)
                .collect(Collectors.toList());

        if (completedAppointments.isEmpty()) {
            System.out.println("No completed appointments found.");
            return;
        }

        // Display outcome record for each completed appointment
        System.out.println("\n--- Completed Appointment Outcome Records ---");
        for (Appointment appointment : completedAppointments) {
            AppointmentOutcomeRecord outcomeRecord = appointment.getAppointmentOutcomeRecord();
            if (outcomeRecord != null) {
                System.out.println("Appointment ID: " + appointment.getAppointmentID());
                System.out.println("Doctor ID: " + appointment.getDoctorID());
                System.out.println("Patient ID: " + appointment.getPatientID());
                System.out.println("Outcome Record: " + outcomeRecord);
                System.out.println("===============================================");
            } else {
                System.out.println("Appointment ID: " + appointment.getAppointmentID() + " - No outcome record available.");
                System.out.println("===============================================");
            }
        }
    }

    // Update Prescription Status (Placeholder method)
    public void updatePrescriptionStatus() {
        System.out.println("\n--- Update Prescription Status ---");
        // Placeholder implementation where the pharmacist can update the prescription status
        System.out.println("This feature is currently not implemented.");
    }

    // Method to submit replenishment request
    public void submitReplenishmentRequest() {
        Scanner sc = new Scanner(System.in);
    
        // Get the logged-in pharmacist's userId directly from the validated user
        String loggedInPharmacistId = super.getUserId(); // This is a String
    
        // Ask for the pharmacist's ID (String input)
        System.out.print("Enter your Pharmacist ID: ");
        String enteredPharmacistId = sc.nextLine(); // Read as a string
    
        // Validate if the entered ID matches the currently logged-in pharmacist's ID
        if (!enteredPharmacistId.equals(loggedInPharmacistId)) {
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
        int requestID = MainApp.replenishmentRequests.size() + 1; // Generate a new request ID
        ReplenishmentRequest request = new ReplenishmentRequest(
            requestID,
            loggedInPharmacistId,  // Pharmacist ID (now a String)
            medication.getMedicationName(),  // Medication Name
            quantity,  // Requested Quantity
            currentStock,  // Current Stock Level
            ReplenishmentStatus.PENDING // Set the status as PENDING
        );
    
        // Add the request to the global list of replenishment requests
        MainApp.replenishmentRequests.add(request);
    
        // Update the replenishment status of the medication to "PENDING"
        medication.setReplenishmentStatus("PENDING");
    
        System.out.println("Replenishment request submitted for " + medication.getMedicationName() + " by Pharmacist:" + loggedInPharmacistId + " with quantity " + quantity + ".");
    }

    // Show the Pharmacist Menu
    public void showMenu() {
        int choice = 0;
        Scanner sc = new Scanner(System.in);
        while (choice != 5) { // Option 5 will be the logout option
            System.out.println("=============================");
            System.out.println("----- Pharmacist Menu -------");
            System.out.println("=============================");
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
                    viewCompletedAppointmentOutcomeRecords();
                    break;

                case 2:
                    // Update Prescription Status (Placeholder message)
                    updatePrescriptionStatus();
                    break;

                case 3:
                    // View Medication Inventory - Linked to the Medication class
                    Medication.viewInventory(); // Method for viewing inventory
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
