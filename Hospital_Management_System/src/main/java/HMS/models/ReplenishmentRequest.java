package HMS.models;

import HMS.enums.ReplenishmentStatus;

public class ReplenishmentRequest {

    private String pharmacistID;
    private String medicationName;
    private int quantity;
    private int stockLevel;
    private ReplenishmentStatus status;
    private String administratorID;

    // Constructor
    public ReplenishmentRequest(String pharmacistID, String medicationName, int quantity, int stockLevel, ReplenishmentStatus status) {
        this.pharmacistID = pharmacistID;
        this.medicationName = medicationName;
        this.quantity = quantity;
        this.stockLevel = stockLevel;
        this.status = status;
    }

    // Getters and Setters
    public String getPharmacistID() {
        return pharmacistID;
    }

    public void setPharmacistID(String pharmacistID) {
        this.pharmacistID = pharmacistID;
    }

    public String getMedicationName() {
        return medicationName;
    }

    public void setMedicationName(String medicationName) {
        this.medicationName = medicationName;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getStockLevel() {
        return stockLevel;
    }

    public void setStockLevel(int stockLevel) {
        this.stockLevel = stockLevel;
    }

    public ReplenishmentStatus getStatus() {
        return status;
    }

    public void setStatus(ReplenishmentStatus status) {
        this.status = status;
    }

    public String getAdministratorID() {
        return administratorID;
    }

    public void setAdministratorID(String administratorID) {
        this.administratorID = administratorID;
    }

    @Override
    public String toString() {
        return "ReplenishmentRequest{" +
                "pharmacistID='" + pharmacistID + '\'' +
                ", medicationName='" + medicationName + '\'' +
                ", quantity=" + quantity +
                ", stockLevel=" + stockLevel +
                ", status=" + status +
                '}';
    }
}
