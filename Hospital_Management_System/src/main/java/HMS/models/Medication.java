package HMS.models;

import java.util.List;

import HMS.MainApp;

public class Medication {

    // Attributes
    private int medicationId;
    private int lowStockLevel;
    private int stockLevel;
    private String medicationName;
    private String replenishmentStatus; // Replenishment status (e.g., "pending", "replenished", "in-progress")

    // Constructor
    public Medication(int medicationId, int lowStockLevel, int stockLevel, String medicationName) {
        this.medicationId = medicationId;
        this.lowStockLevel = lowStockLevel;
        this.stockLevel = stockLevel;
        this.medicationName = medicationName;
        this.replenishmentStatus = "pending"; // Default status is "pending" when a medication is created
    }

    // Methods to update stock and low stock level
    public void updateLowStockLevel(int newLowStockLevel) {
        this.lowStockLevel = newLowStockLevel;
    }

    public void updateStock(int newStockLevel) {
        this.stockLevel = newStockLevel;
    }

    // Getter and Setter for Replenishment Status
    public String getReplenishmentStatus() {
        return replenishmentStatus;
    }

    public void setReplenishmentStatus(String replenishmentStatus) {
        this.replenishmentStatus = replenishmentStatus;
    }

    // Getters and Setters for other attributes
    public int getMedicationId() {
        return medicationId;
    }

    public void setMedicationId(int medicationId) {
        this.medicationId = medicationId;
    }

    public int getLowStockLevel() {
        return lowStockLevel;
    }

    public int getStockLevel() {
        return stockLevel;
    }

    public String getMedicationName() {
        return medicationName;
    }

    public void setMedicationName(String medicationName) {
        this.medicationName = medicationName;
    }

    // Static method to view the inventory of all medications
    public static void viewInventory() {
        System.out.println("\nCurrent Medication Inventory:\n");
        List<Medication> medications = MainApp.medications;  // Access the static list of medications in MainApp

        // Check if medications list is empty
        if (medications.isEmpty()) {
            System.out.println("No medications available in the inventory.");
            return;
        }

        // Iterate over medications and display the details
        for (Medication medication : medications) {
            System.out.println("Medication ID: " + medication.getMedicationId());
            System.out.println("Medication Name: " + medication.getMedicationName());
            System.out.println("Current Stock Level: " + medication.getStockLevel());
            System.out.println("Low Stock Level Alert: " + medication.getLowStockLevel());
            System.out.println("-------------------------------");
        }
    }
}
