package HMS.models;

import java.time.LocalDateTime;
import HMS.enums.BloodType;
import HMS.enums.Gender;
import HMS.enums.Role;  

public class Medication {

    // Attributes
    private int medicationId;
    private int lowStockLevel;
    private int stockLevel;
    private String medicationName;

    // Constructor
    public Medication(int medicationId, int lowStockLevel, int stockLevel, String medicationName) {
        this.medicationId = medicationId;
        this.lowStockLevel = lowStockLevel;
        this.stockLevel = stockLevel;
        this.medicationName = medicationName;
    }

    // Methods
    public void updateLowStockLevel(int newLowStockLevel) {
        // Update the low stock level with the provided value
        this.lowStockLevel = newLowStockLevel;
    }

    public void updateStock(int newStockLevel) {
        // Update the stock level with the provided value
        this.stockLevel = newStockLevel;
    }

    // Getters and Setters (optional, depending on needs)
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
}
