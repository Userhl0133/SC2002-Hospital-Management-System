package HMS.models;

import HMS.MainApp;
import HMS.enums.*;
import java.util.*;
import static HMS.MainApp.*;
import static HMS.MainApp.pharmacists;

public class Pharmacist extends User {

    private int age;

    // Constructor
    public Pharmacist(String userId, String password, Gender gender, String name, Role role, int age) {
        super(userId, password, gender, name, role);
        this.age = age;
    }

    // Methods
    public void viewInventory() {
        // Implementation for displaying the list of medications
        for (Medication medication : medications) {
            System.out.println("Medication: " + medication.getMedicationName() + ", Stock Level: " + medication.getStockLevel() + ", Low Stock Level: " + medication.getLowStockLevel());
        }
    }

    public void updateMedicationStock(String medicationName, int newStockLevel) {
        for (Medication medication : medications) {
            if (medication.getMedicationName().equalsIgnoreCase(medicationName)) {
                medication.updateStock(medication.getLowStockLevel() + newStockLevel);
                System.out.println("Updated stock for " + medicationName);
                return;
            }
        }
        System.out.println("Medication not found.");
    }

    public void approveReplenishmentRequests(int pharmacistId) {
        System.out.println("\n--- Replenishment Requests ---");
        
        for (ReplenishmentRequest request : ReplenishmentRequest.replenishmentRequests) {
            if (request.getStatus().equalsIgnoreCase("Pending")) {
                System.out.println("Administrator ID: " + request.getAdministratorID() + ", Medication: " + request.getMedicationName() + ", Requested Quantity: " + request.getStockLevel() + ", Status: " + request.getStatus());
            }
        }

        Scanner sc = new Scanner(System.in);
        System.out.print("Enter Administrator ID to approve: ");
        int adminId = sc.nextInt();

        for (ReplenishmentRequest request : ReplenishmentRequest.replenishmentRequests) {
            if (request.getAdministratorID() == adminId && request.getStatus().equals("Pending")) {
                Medication medication = medications.stream()
                        .filter(med -> med.getMedicationName().equalsIgnoreCase(request.getMedicationName()))
                        .findFirst()
                        .orElse(null);

                if (medication != null) {
                    medication.updateStock(medication.getStockLevel() + request.getStockLevel());
                    request.approveReplenishmentRequest(adminId);
                    System.out.println("Replenishment approved for " + request.getMedicationName());
                } else {
                    System.out.println("Medication not found in inventory.");
                }
                return;
            }
        }
        System.out.println("No pending request found for the given Administrator ID.");
    }

    @Override
    public String toString() {
        return String.format("User ID: %s, Name: %s, Gender: %s, Role: %s", super.getUserId(), super.getName(), super.getGender(), super.getRole());
    }

    public void showMenu() {
        int choice = 0;
        Scanner sc = new Scanner(System.in);
        while (choice != 7) {
            System.out.println("============================");
            System.out.println("-----Pharmacist Menu--------");
            System.out.println("============================");
            System.out.println("1.View Medication Inventory");
            System.out.println("2.Update Medication stock");
            System.out.println("3.Approve Replenishment Request");
            System.out.println("4.Logout");
            System.out.print("Please select an option: ");
            choice = sc.nextInt();
            sc.nextLine();
            switch (choice) {
                case 1:
                    // View Medication Inventory
                    System.out.println("Medication Inventory:");
                    viewInventory();
                    break;

                case 2:
                    // Update Medication stock
                    System.out.print("Enter Medication Name to update stock: ");
                    String updateMedName = sc.nextLine();
                    System.out.print("Enter new stock level: ");
                    int newStockLevel = sc.nextInt();
                    updateMedicationStock(updateMedName, newStockLevel);
                    break;

                case 3:
                    // Approve Replenishment Request
                    System.out.print("Enter Pharmacist ID to approve replenishment request: ");
                    int pharmacistId = sc.nextInt();
                    approveReplenishmentRequests(pharmacistId);
                    break;

                case 4:
                    // Logout
                    System.out.println("Logging out...");
                    break;

                default:
                    System.out.println("Invalid Option");
            }
        }
    }
}
