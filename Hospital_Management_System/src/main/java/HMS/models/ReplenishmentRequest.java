package HMS.models;

public class ReplenishmentRequest { 
  private int pharmacistID; 
  private int administratorID; 
  private String medicationName; 
  private int stockLevel; 
  private String status; 

  public ReplenishmentRequest(int pharmacistID, int administratorID, String medicationName, int stockLevel) { 
    this.pharmacistID = pharmacistID; 
    this.administratorID = administratorID; 
    this.medicationName = medicationName; 
    this.stockLevel = stockLevel; this.status = "Pending";
  } 
  public void submitReplenishmentRequest() { 
    // Submit the replenishment request to the administrator 
    System.out.println("Replenishment request for " + medicationName + " submitted."); 
    this.status = "Submitted"; 
  } 
  public void approveReplenishmentRequest(int requestID) { 
    // Approve the replenishment request System.out.println("Replenishment request " + requestID + " approved."); 
    this.status = "Approved"; 
  } 
  public void viewReplenishmentStatus() { 
    // View the status of the replenishment request 
    System.out.println("Status of replenishment request: " + this.status + "."); 
  } 
  // Getters and Setters 
  public int getPharmacistID() { 
  return pharmacistID; 
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
