package HMS.models;

public class Pharamacist extends User {
    private int id; private String name; 
    public Pharmacist(int id, String name) {
        this.id = id; this.name = name; 
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
    public ReplenishmentRequest submitReplenishmentRequest(String medicationName, int stockLevel) { 
        // Create a new replenishment request 
        System.out.println("Submitting replenishment request for " + medicationName + " with stock level " + stockLevel + "."); 
        return new ReplenishmentRequest(this.id, 0, medicationName, stockLevel);
    }



}
