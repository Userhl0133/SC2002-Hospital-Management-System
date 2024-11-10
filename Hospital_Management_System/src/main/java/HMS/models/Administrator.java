package HMS.models;

import java.util.ArrayList;
import java.util.Scanner;

import HMS.MainApp;
import static HMS.MainApp.administrators;
import static HMS.MainApp.appointments;
import static HMS.MainApp.doctors;
import static HMS.MainApp.medications;
import static HMS.MainApp.patients;
import static HMS.MainApp.pharmacists;
import static HMS.MainApp.replenishmentRequests;
import HMS.enums.AppointmentStatus;
import HMS.enums.Gender;
import HMS.enums.ReplenishmentStatus;
import HMS.enums.Role;



public class Administrator extends User {

    private int age;
    // Constructor
    public Administrator(String userId, String password, Gender gender, String name, Role role, int age) {
        super(userId, password, gender, name, role);
        this.age = age;
        replenishmentRequests = new ArrayList<>();
    }

    // Methods
    public void viewStaff() {
        // Implementation for displaying the list of staff members (done)
        for (Administrator administrator : administrators) {
            System.out.println(administrator);
        }
        for (Doctor doctor : doctors) {
            System.out.println(doctor);
        }
        for (Pharmacist pharmacist : pharmacists) {
            System.out.println(pharmacist);
        }
    }
    public void addStaff(Gender gender, String name, Role role, int age) {
        //implementation for adding a staff member (done)
        // Generate a unique userId based on the role
        String userId;
        String password = "password"; // Default password
    
        // Generate userId based on the role and the size of the respective list
        if (role == Role.DOCTOR) {
            userId = "D" + String.format("%03d", doctors.size() + 1); // Example: D001, D002, etc.
            Doctor doctor = new Doctor(userId, password, gender, name, role, age);
            doctors.add(doctor);
            System.out.println("Doctor added with User ID: " + userId);
        } else if (role == Role.PHARMACIST) {
            userId = "P" + String.format("%03d", pharmacists.size() + 1); // Example: P001, P002, etc.
            Pharmacist pharmacist = new Pharmacist(userId, password, gender, name, role, age);
            pharmacists.add(pharmacist);
            System.out.println("Pharmacist added with User ID: " + userId);
        } else {
            System.out.println("Invalid role specified.");
        }
    }

    private void updateStaff(User staff) {
        //implementation for updating a staff member 
        Scanner scanner = new Scanner(System.in);
    
        // Update the staff member's name
        System.out.print("Enter new name: ");
        String newName = scanner.nextLine();
        if (!newName.isEmpty()) {
            staff.setName(newName);
        }
    
        // Prompt the user to update the password
        System.out.print("Do you want to update the password? (yes/no): ");
        String changePassword = scanner.nextLine().toLowerCase();
        
        if (changePassword.equals("yes")) {
            
            System.out.print("Enter new password: ");
            String newPassword = scanner.nextLine();
            staff.changePassword(newPassword);
        }
    
        System.out.println("---- Staff updated successfully! ----");
        System.out.println("Updated Details:");
        System.out.println("User ID: " + staff.getUserId());
        System.out.println("Name: " + staff.getName());
        System.out.println("Role: " + staff.getRole());
    }

    public void removeStaff(String userId) {
        //implementation for removing a staff member (done)
        // Check if the userId belongs to a Doctor
        if (userId.startsWith("D")) {
            if (doctors.removeIf(doctor -> doctor.getUserId().equals(userId))) {
                System.out.println("Doctor with User ID " + userId + " removed successfully.");
                return;
            }
        }

        // Check if the userId belongs to a Pharmacist
        else if (userId.startsWith("P")) {
            if (pharmacists.removeIf(pharmacist -> pharmacist.getUserId().equals(userId))) {
                System.out.println("Pharmacist with User ID " + userId + " removed successfully.");
                return;
            }
        }

        // If not found in any list
        System.out.println("Staff member with User ID " + userId + " not found.");
    }



    public void viewAppointments() {
        // Implementation for displaying the list of appointments (done but haven't try)include if empty state empty
        // Check if the appointments list is empty
        if (appointments == null || appointments.isEmpty()) {
            System.out.println("\n---- No appointments found ----");
            return;
        }
        System.out.println("\n---- List of Appointments ----");

        // Iterate through each appointment for the patient
        for (Appointment appointment : appointments) {
            System.out.println(appointment);

            // Check if the appointment is completed and has an outcome record
            if (appointment.getAppointmentStatus() == AppointmentStatus.COMPLETED && appointment.getAppointmentOutcomeRecord() != null) {
                AppointmentOutcomeRecord outcome = appointment.getAppointmentOutcomeRecord();

                System.out.println("---- Appointment Outcome Record ----");
                System.out.println("Service Type: " + outcome.getServiceType());
                System.out.println("Consultation Notes: " + outcome.getConsultationNotes());

                // Display prescribed medications if any
                if (outcome.getPrescribedMedications() != null && !outcome.getPrescribedMedications().isEmpty()) {
                    System.out.println("Prescribed Medications: ");
                    for (Medication med : outcome.getPrescribedMedications()) {
                        System.out.println("- " + med.getMedicationName());
                    }
                } else {
                    System.out.println("No medications prescribed.");
                }
                System.out.println("-----------------------------------");
            }
            System.out.println("-----------------------------------");
        }
    }

    public void addMedication(String name, int stockLevel, int lowStockLevel) {
        // Add a New Medication (done)
        int medicationId = medications.size();
        Medication newMedication = new Medication(medicationId, lowStockLevel, stockLevel, name);
        medications.add(newMedication);
        System.out.println("Medication Name: " + name + " added successfully.");
    }

    public void removeMedication(int medicationId) {
        // Remove a Medication (done)
        boolean found = medications.removeIf(med -> med.getMedicationId() == medicationId);
        if (found) {
            System.out.println("Medication removed successfully.");
        } else {
            System.out.println("Medication with ID " + medicationId + " not found.");
        }
    }

    public void updateLowStockLevel(int medicationId, int newStockLevel) {
        // implementation for updating low stock level (done)
        for (Medication medication : medications) {
            if (medication.getMedicationId() == medicationId) {
                medication.updateLowStockLevel(newStockLevel);
                System.out.println("Low stock alert level for " + medication.getMedicationName() + " updated to " + newStockLevel);
                return;
            }
        }
        System.out.println("Medication with ID " + medicationId + " not found.");
    }
    public void viewAllReplenishmentRequests() {
        System.out.println("---- Viewing All Replenishment Requests ----");
        for (ReplenishmentRequest request : replenishmentRequests) {
            System.out.println("Request ID: " + request.getRequestID() +
                    " | Pharmacist ID: " + request.getPharmacistID() +
                    " | Medication: " + request.getMedicationName() +
                    " | Requested Quantity: " + request.getQuantity() +
                    " | Current Stock Level: " + request.getStockLevel() +
                    " | Status: " + request.getStatus());
        }
    }

    public void approveReplenishmentRequests(String adminId) {
        if (replenishmentRequests == null || replenishmentRequests.isEmpty()) {
            System.out.println("---- No replenishment requests available ----");
            return;
        }
    
        System.out.println("\n---- Replenishment Requests ----");
        boolean hasPendingRequests = false;
        for (ReplenishmentRequest request : replenishmentRequests) {
            if (request.getStatus() == ReplenishmentStatus.PENDING) {
                System.out.println("\nRequest ID: " + request.getRequestID() +
                        "| Pharmacist ID: " + request.getPharmacistID() +
                        "| Medication: " + request.getMedicationName() +
                        "| Requested Quantity: " + request.getQuantity() +
                        "| Current Stock Level: " + request.getStockLevel() +
                        "| Status: " + request.getStatus());
                hasPendingRequests = true;
            }
        }
    
        if (!hasPendingRequests) {
            System.out.println("No pending replenishment requests available.");
            return;
        }
    
        // Prompt for pharmacist ID
        Scanner sc = new Scanner(System.in);
        System.out.print("\nEnter Request ID to process the request: ");
        int RequestID = sc.nextInt();
    
        // Find the matching request
        ReplenishmentRequest selectedRequest = findRequestByRequestId(RequestID);
    
        if (selectedRequest == null) {
            System.out.println("No pending request found for the given Request ID.");
            return;
        }
    
        // Confirm with the administrator whether to approve the request
        System.out.print("Do you want to approve this request? (yes/no): ");
        String decision = sc.next().toLowerCase();
    
        if (decision.equals("yes")) {
            // Approve the request
            approveRequest(selectedRequest, adminId);
        } else {
            selectedRequest.setStatus(ReplenishmentStatus.REJECTED);
            System.out.println("Replenishment request for " + selectedRequest.getMedicationName() + " rejected.");
        }
    }
    
    private ReplenishmentRequest findRequestByRequestId(int requestID) {
        for (ReplenishmentRequest request : replenishmentRequests) {
            if (request.getRequestID()==requestID && request.getStatus() == ReplenishmentStatus.PENDING) {
                return request;
            }
        }
        return null;
    }
    
    private void approveRequest(ReplenishmentRequest request, String adminId) {
        // Find the medication in the inventory
        Medication medication = MainApp.medications.stream()
                .filter(med -> med.getMedicationName().equalsIgnoreCase(request.getMedicationName()))
                .findFirst()
                .orElse(null);
    
        if (medication != null) {
            // Update stock level if approved
            medication.updateStock(medication.getStockLevel() + request.getQuantity());
            request.setAdministratorID(adminId);
            request.setStatus(ReplenishmentStatus.APPROVED);
            System.out.println("Replenishment approved for " + request.getMedicationName());
            System.out.println("Stock updated for " + request.getMedicationName() +
                               " to " + medication.getStockLevel());
        } else {
            System.out.println("Medication not found in inventory.");
        }
    }


    @Override
    public String toString() {
        return String.format("User ID: %s, Name: %s, Gender: %s, Role: %s, Age: %s",
                super.getUserId(), super.getName(), super.getGender(), super.getRole(), age);
    }

    public void showMenu() {
        int choice = 0;
        Scanner sc = new Scanner(System.in);
        while (choice != 11) {
            System.out.println("============================");
            System.out.println("-----Administrator Menu-----");
            System.out.println("============================");
            System.out.println("1.View Staff");
            System.out.println("2.Add Staff");
            System.out.println("3.Update Staff");
            System.out.println("4.Remove Staff");
            System.out.println("5.View Appointments Details");
            System.out.println("6.View Medication Inventory");
            System.out.println("7.Add Medication");
            System.out.println("8.Remove Medication");
            System.out.println("9.Update Medication Low Stock Level Alert");
            System.out.println("10.Approve Replenishment Request");
            System.out.println("11.Logout");
            System.out.print("Please select an option: ");
            choice = sc.nextInt();
            sc.nextLine();
            switch (choice) {
                case 1:
                    // View Staff
                    System.out.println("List of Staff:");
                    viewStaff();
                    break;

                case 2:
                    // Add Staff
                    Scanner scanner = new Scanner(System.in);

                    System.out.print("Enter Name: ");
                    String name = scanner.nextLine();

                    System.out.print("Enter Gender (Male/Female): ");
                    Gender gender = null;
                    while (gender == null) {
                        try {
                            gender = Gender.valueOf(scanner.nextLine().toUpperCase());
                        } catch (IllegalArgumentException e) {
                            System.out.print("Invalid gender. Please enter Male or Female: ");
                        }
                    }

                    System.out.print("Enter Role (Doctor/Pharmacist): ");
                    Role role = null;
                    while (role == null) {
                        try {
                            role = Role.valueOf(scanner.nextLine().toUpperCase());
                        } catch (IllegalArgumentException e) {
                            System.out.print("Invalid role. Please enter Doctor, or Pharmacist: ");
                        }
                    }

                    System.out.print("Enter Age: ");
                    int age = scanner.nextInt();

                    addStaff(gender, name, role, age);
                    break;

                    case 3:
                    // Update Staff
                    System.out.print("Enter the User ID of the staff member to update: ");
                    String userIdToUpdate = sc.nextLine();
                
                    // Use the findUserById method to search for the staff member
                    User staffToUpdate = User.findUserById(userIdToUpdate, patients, doctors, pharmacists, administrators);
                
                    if (staffToUpdate != null) {
                        System.out.println("\nStaff member found:");
                        System.out.println("Name: " + staffToUpdate.getName() + ", Role: " + staffToUpdate.getRole());
                
                        // Call updateStaff method to change details
                        updateStaff(staffToUpdate);
                    } else {
                        System.out.println("Staff member with User ID " + userIdToUpdate + " not found.");
                    }                    
                    
                    break;

                case 4:
                    // Remove Staff
                    System.out.print("Enter User ID to remove: ");
                    String removeUserId = sc.nextLine();
                    removeStaff(removeUserId);
                    break;

                case 5:
                    // View Appointments Details
                    viewAppointments();
                    break;

                case 6:
                    // View Medication Inventory
                    Medication.viewInventory();
                    break;

                case 7:
                    // Add Medication
                    System.out.print("Enter Medication Name: ");
                    String medName = sc.nextLine();
                    System.out.print("Enter Stock Level: ");
                    int stockLevel = sc.nextInt();
                    System.out.print("Enter Low Stock Level Alert: ");
                    int lowStockLevel = sc.nextInt();
                    addMedication(medName, stockLevel, lowStockLevel);
                    break;

                case 8:
                    // Remove Medication
                    System.out.print("Enter Medication ID to remove: ");
                    int removeMedId = sc.nextInt();
                    removeMedication(removeMedId);
                    break;

                case 9:
                    // Update Medication Low Stock Level Alert
                    System.out.print("Enter Medication ID to update low stock level alert: ");
                    int medicationID = sc.nextInt();
                    System.out.print("Enter new stock level alert: ");
                    int newStockLevelalert = sc.nextInt();
                    updateLowStockLevel(medicationID, newStockLevelalert); 
                    break;

                case 10:
                    // Check if there are any pending replenishment requests
                    if (replenishmentRequests == null || replenishmentRequests.isEmpty()) {
                        System.out.println("No replenishment requests available. Returning to the main menu...");
                        break;
                    }
                    // Display the list of replenishment requests
                    viewAllReplenishmentRequests();
                    // Ask if the user wants to view the pending requests
                    System.out.print("Do you want to view the pending replenishment requests? (yes/no): ");
                    String viewDecision = sc.nextLine().toLowerCase();

                    if (viewDecision.equals("yes")) {
                        String loggedInadminId = super.getUserId();

                        // Prompt for administrator ID to continue with the approval process
                        System.out.print("Enter Administrator ID to proceed: ");
                        String adminId = sc.nextLine();
                        // Validate if the entered ID matches the currently logged-in Administrator's ID
                        if (!adminId.equals(loggedInadminId)) {
                            System.out.println("The entered Administrator ID does not match the logged-in Administrator ID.");
                            break; // Exit if the ID does not match
                        }
                        // Call the method to approve replenishment requests
                        approveReplenishmentRequests(adminId);
                    }else {
                        System.out.println("Returning to the main menu...");
                    }
                    break;

                case 11:
                    // Logout
                    System.out.println("Logging out");
                    break;

                default:
                    System.out.println("Invalid Option");
            }
        }
    }
}
