package HMS.models;

import java.util.ArrayList;
import java.util.List;

import HMS.enums.ReplenishmentStatus;

public class ReplenishmentRequest {

    public static List<ReplenishmentRequest> replenishmentRequests = new ArrayList<>(); // Static list to store requests

    private int pharmacistID;
    private int administratorID;
    private String medicationName;
    private int stockLevel;
    private int quantity;
    private ReplenishmentStatus status;

    // Constructor
    public ReplenishmentRequest(int pharmacistID, int administratorID, String medicationName, int stockLevel, int quantity) {
        this.pharmacistID = pharmacistID;
        this.administratorID = administratorID;
        this.medicationName = medicationName;
        this.stockLevel = stockLevel;
        this.quantity = quantity;
        this.status = ReplenishmentStatus.PENDING;  // Default status
    }

    // Getters and Setters
    public int getPharmacistID() { return pharmacistID; }
    public void setPharmacistID(int pharmacistID) { this.pharmacistID = pharmacistID; }

    public int getAdministratorID() { return administratorID; }
    public void setAdministratorID(int administratorID) { this.administratorID = administratorID; }

    public String getMedicationName() { return medicationName; }
    public void setMedicationName(String medicationName) { this.medicationName = medicationName; }

    public int getStockLevel() { return stockLevel; }
    public void setStockLevel(int stockLevel) { this.stockLevel = stockLevel; }

    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }

    public ReplenishmentStatus getStatus() { return status; }
    public void setStatus(ReplenishmentStatus status) { this.status = status; }

    // Other methods can be added as required
}
