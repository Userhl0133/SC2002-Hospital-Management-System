package HMS.models;

import HMS.enums.Gender;
import HMS.enums.Role;

import java.util.Scanner;

public class Pharmacist extends User {
    private int age;

    public Pharmacist(String userId, String password, Gender gender, String name, Role role, int age) {
        super(userId, password, gender, name, role);
        this.age = age;
    }

    public void viewAppointmentOutcomeRecord() {
        // Fetch and display the appointment outcome record 
        System.out.println("Viewing appointment outcome record for pharmacist " + super.getName() + ".");
    } 

    public void updatePrescriptionStatus(int prescriptionId, String status) { 
        // Update the status of a given prescription 
        System.out.println("Updated prescription " + prescriptionId + " to status '" + status + "'."); 
    } 

    public void viewInventory() { 
        // Display current inventory
        System.out.println("Displaying current inventory."); 
    }

    public void submitReplenishmentRequest(int medicationId, int quantity) {
        // Submit a request to replenish a given medication
        System.out.println("Replenishment request submitted for medication ID " + medicationId + " with quantity " + quantity + ".");
    }

    @Override
    public String toString() {
        return String.format("User ID: %s, Name: %s, Gender: %s, Role: %s",
                super.getUserId(), super.getName(), super.getGender(), super.getRole());
    }

    public void showMenu() {
        int choice = 0;
        Scanner sc = new Scanner(System.in);
        while(choice != 5) {
            System.out.println("-----Pharmacist Menu-----");
            System.out.println("1. View Appointment Outcome Record");
            System.out.println("2. Update Prescription Status");
            System.out.println("3. View Medication Inventory");
            System.out.println("4. Submit Replenishment Request");
            System.out.println("5. Logout");
            System.out.print("Please select an option: ");
            choice = sc.nextInt();
            switch(choice) {
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
