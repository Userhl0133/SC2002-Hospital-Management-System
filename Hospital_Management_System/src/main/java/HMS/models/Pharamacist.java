package HMS.models;

public class Pharamacist extends User {

    public void viewAppointmentOutcomeRecord() { // Implementation code here System.out.println("Viewing appointment outcome record for pharmacist " + name + "."); } public void updatePrescriptionStatus(int prescriptionId, String status) { // Implementation code here System.out.println("Updated prescription " + prescriptionId + " to status '" + status + "'."); } public void viewInventory() { // Implementation code here System.out.println("Displaying current inventory."); } public ReplenishmentRequest submitReplenishmentRequest(String medicationName, int stockLevel) { // Create a new replenishment request System.out.println("Submitting replenishment request for " + medicationName + " with stock level " + stockLevel + "."); return new ReplenishmentRequest(this.id, 0, medicationName, stockLevel);
    
}
