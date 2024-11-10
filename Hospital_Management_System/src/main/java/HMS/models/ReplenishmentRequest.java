package HMS.models;

import HMS.enums.ReplenishmentStatus;

public class ReplenishmentRequest {

    private int requestID; // Added requestID field
    private String pharmacistID;
    private String medicationName;
    private int quantity;
    private int stockLevel;
    private ReplenishmentStatus status;
    private String administratorID;

    // Constructor with requestID
    public ReplenishmentRequest(int requestID, String pharmacistID, String medicationName, int quantity, int stockLevel, ReplenishmentStatus status) {
        this.requestID = requestID;
        this.pharmacistID = pharmacistID;
        this.medicationName = medicationName;
        this.quantity = quantity;
        this.stockLevel = stockLevel;
        this.status = status;
    }

    // Getter for requestID
    public int getRequestID() {
        return requestID;
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

    // Updated toString method to include requestID
    @Override
    public String toString() {
        return  "Request ID: " + this.getRequestID() +
        "\nPharmacist ID: " + this.getPharmacistID() +
        "\nMedication: " + this.getMedicationName() +
        "\nRequested Quantity: " + this.getQuantity() +
        "\nCurrent Stock Level: " + this.getStockLevel() +
        "\nStatus: " + this.getStatus() +'\n';
    }
}
