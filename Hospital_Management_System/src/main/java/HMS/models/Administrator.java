package HMS.models;

import java.io.*;
import java.util.*;
import HMS.enums.*;


public class Administrator extends User{

    // Constructor
    public Administrator(int userId, String password, Gender gender, String name, Role role) {
        super(userId, password, gender, name, role);
    }

    // Methods
    public void addStaff(int userId, String password, Gender gender, String name, Role role) {
        // Implementation for adding a staff member

    }

    public void updateStaff() {
        // Implementation for updating a staff member's details

    }

    public void removeStaff() {
        // Implementation for removing a staff member
    }

    public void displayStaff() {
        // Implementation for displaying the list of staff members
    }

    public void viewAppointments() {
        // Implementation for viewing all appointments
    }

    public void viewInventory() {
        // Implementation for viewing inventory details
    }

    public void updateMedicationStock(Medication medication, int quantity) {
        // Implementation for updating medication stock levels

    }

    public void updateLowStockLevel() {
        // Implementation for updating low stock level threshold
    }

    public void approveReplenishmentRequest() {
        // Implementation for approving a replenishment request
    }

    public void showMenu() {
        int choice = 0;
        System.out.println("-----Administrator Menu-----");
        System.out.println("1.View Staff");
        System.out.println("2.Add Staff");
        System.out.println("3.Update Staff");
        System.out.println("4.Remove Staff");
        System.out.println("5.View Appointments Details");
        System.out.println("6.View Medication Inventory");
        System.out.println("7.Update Medication stock");
        System.out.println("8.Approve Replenishment Request");
        System.out.println("9.Logout");
        System.out.print("Please select an option: ");

        Scanner sc = new Scanner(System.in);
        while (choice != 9) {
            choice = sc.nextInt();
            switch(choice) {
                case 1 :
                    // View Staff
                    break;

                case 2 :
                    // Add Staff
                    break;

                case 3 :
                    // Update Staff
                    break;

                case 4 :
                    // Remove Staff
                    break;

                case 5 :
                    // View Appointments Details
                    break;

                case 6 :
                    // View Medication Inventory
                    break;

                case 7 :
                    // Update Medication stock
                    break;

                case 8 :
                    // Approve Replenishment Request
                    break;

                case 9 :
                    // Logout
                    break;

                default :
                    System.out.println("Invalid Option");
            }
        }
    }
}
