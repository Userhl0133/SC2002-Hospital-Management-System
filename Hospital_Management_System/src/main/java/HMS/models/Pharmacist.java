package HMS.models;

public class Pharmacist  {
    private int id; private String name; 
    private int i;

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



}
