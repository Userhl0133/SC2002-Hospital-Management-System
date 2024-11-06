package HMS.models;

import HMS.enums.Gender;
import HMS.enums.Role;

import java.util.Scanner;

public class Pharmacist extends User {
    private int id; private String name; 
    private int i;

    public Pharmacist(String userId, String password, Gender gender, String name, Role role) {
        super(userId, password, gender, name, role);
    }

    public void viewAppointmentOutcomeRecord() {
        // Fetch and display the appointment outcome record 
        System.out.println("Viewing appointment outcome record for pharmacist " + name + "."); 
    } 
    public void updatePrescriptionStatus(int prescriptionId, String status) { 
        // Update the status of a given prescription 
        System.out.println("Updated prescription " + prescriptionId + " to status '" + status + "'."); 
    } 
    public void viewInventory() { 
        // Display current inventory
        System.out.println("Displaying current inventory."); 
    }

    public void showMenu() {
        int choice = 0;
        System.out.println("-----Pharmacist Menu-----");
        System.out.println("1.View Appointment Outcome Record");
        System.out.println("2.Update Prescription Status");
        System.out.println("3.View Medication Inventory");
        System.out.println("4.Submit Replenishment Request");
        System.out.println("5.Logout");
        System.out.print("Please select an option: ");

        Scanner sc = new Scanner(System.in);
        while(choice != 5) {
            choice = sc.nextInt();
            switch(choice) {
                case 1:
                    // View Appointment Outcome Record
                    break;

                case 2:
                    // Update Prescription Status
                    break;

                case 3:
                    // View Available Appointment Slots
                    break;

                case 4:
                    // Submit Replenishment Request
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
