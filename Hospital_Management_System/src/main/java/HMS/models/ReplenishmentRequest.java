package HMS.models;

public class ReplenishmentRequest { 
    private int pharmacistID; 
    private int administratorID; 
    private String medicationName; 
    private int stockLevel; 
    private int quantity;
    private String status; 

    // Constructor
    public ReplenishmentRequest(int pharmacistID, int administratorID, String medicationName, int stockLevel) { 
        this.pharmacistID = pharmacistID; 
        this.administratorID = administratorID; 
        this.medicationName = medicationName; 
        this.stockLevel = stockLevel; 
        this.status = "Pending"; // Default status
    } 

    // Method to submit the replenishment request
    public void submitReplenishmentRequest() { 
        // Submit the replenishment request to the administrator 
        System.out.println("Replenishment request for " + medicationName + " submitted."); 
        this.status = "Submitted"; // Update status to "Submitted"
    } 

    // Method to approve the replenishment request
    public void approveReplenishmentRequest(int requestID) { 
        System.out.println("Replenishment request " + requestID + " approved."); 
        this.status = "Approved"; // Update status to "Approved"
    } 

    // Method to view the status of the replenishment request
    public void viewReplenishmentStatus() { 
        // View the status of the replenishment request 
        System.out.println("Status of replenishment request: " + this.status + ".");
    }

    // Getter for the status
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    // Setters and Getters for other fields
    public int getPharmacistID() { 
        return pharmacistID; 
    }

    public int getQuantity() {
        return quantity;
    }

    public void setPharmacistID(int pharmacistID) { 
        this.pharmacistID = pharmacistID; 
    } 

    public int getAdministratorID() { 
        return administratorID; 
    } 

    public void setAdministratorID(int administratorID) { 
        this.administratorID = administratorID; 
    } 

    public String getMedicationName() { 
        return medicationName; 
    } 

    public void setMedicationName(String medicationName) { 
        this.medicationName = medicationName; 
    } 

    public int getStockLevel() { 
        return stockLevel; 
    } 

    public void setStockLevel(int stockLevel) { 
        this.stockLevel = stockLevel; 
    } 
}
