package HMS.models;

import java.util.Scanner;

import HMS.MainApp;
import static HMS.MainApp.administrators;
import static HMS.MainApp.doctors;
import static HMS.MainApp.medications;
import static HMS.MainApp.patients;
import static HMS.MainApp.pharmacists;
import static HMS.MainApp.replenishmentRequests;
import HMS.enums.AppointmentStatus;
import HMS.enums.Gender;
import HMS.enums.ReplenishmentStatus;
import HMS.enums.Role;
import static HMS.models.Appointment.getAppointments;

public class Administrator extends User {

    private int age;

    public Administrator(String userId, String password, Gender gender, String name, Role role, int age) {
        super(userId, password, gender, name, role);
        this.age = age;
    }

    public void viewStaff() {
        System.out.println("\n------------------------------ List of Staff -------------------------------");
        for (Administrator administrator : administrators) {
            System.out.println(administrator);
        }
        for (Doctor doctor : doctors) {
            System.out.println(doctor);
        }
        for (Pharmacist pharmacist : pharmacists) {
            System.out.println(pharmacist);
        }
        System.out.println("------------------------------------------------------------------------------");
    }

    public void addStaff(Gender gender, String name, Role role, int age) {
        String userId;
        String password = "password";

        if (role == Role.DOCTOR) {
            userId = "D" + String.format("%03d", doctors.size() + 1); // Example: D001, D002, etc.
            Doctor doctor = new Doctor(userId, password, gender, name, role, age);
            doctors.add(doctor);
            System.out.println("Doctor added with Hospital ID: " + userId);
        } else if (role == Role.PHARMACIST) {
            userId = "P" + String.format("%03d", pharmacists.size() + 1); // Example: P001, P002, etc.
            Pharmacist pharmacist = new Pharmacist(userId, password, gender, name, role, age);
            pharmacists.add(pharmacist);
            System.out.println("Pharmacist added with Hospital ID: " + userId);
        } else {
            System.out.println("Invalid role specified.");
        }
    }

    private void updateStaff(User staff) {
        Scanner scanner = new Scanner(System.in);
        // Update the staff member's name
        System.out.print("Enter new name (-1 to cancel): ");
        String newName = scanner.nextLine();
        if (newName.equals("-1")) {
            System.out.println("Updating staff has cancelled.");
            return;
        }
        if (!newName.isEmpty()) {
            staff.setName(newName);
        }

        boolean validInput = false;
        String changePassword = "";

        while (!validInput) {
            System.out.print("Do you want to update the password? (Y/N): ");
            changePassword = scanner.nextLine().toLowerCase();

            if (changePassword.equals("y") || changePassword.equals("n")) {
                validInput = true;
            } else {
                System.out.println("Invalid input. Please enter 'Y' or 'N' only.");
            }
        }

        if (changePassword.equalsIgnoreCase("Y")) {
            System.out.print("Enter new password: ");
            String newPassword = scanner.nextLine();
            staff.changePassword(newPassword);
        }

        System.out.println("---- Staff updated successfully! ----");
        System.out.println("Updated Details:");
        System.out.println("Hospital ID: " + staff.getUserId());
        System.out.println("Name: " + staff.getName());
        System.out.println("Role: " + staff.getRole());
    }

    public void removeStaff(String userId) {
        if (userId.startsWith("D")) {
            if (doctors.removeIf(doctor -> doctor.getUserId().equals(userId))) {
                System.out.println("Doctor with Hospital ID " + userId + " removed successfully.");
                return;
            }
        } else if (userId.startsWith("P")) {
            if (pharmacists.removeIf(pharmacist -> pharmacist.getUserId().equals(userId))) {
                System.out.println("Pharmacist with Hospital ID " + userId + " removed successfully.");
                return;
            }
        }

        System.out.println("Staff member with Hospital ID " + userId + " not found.");
    }

    public void viewAppointments() {
        if (getAppointments() == null || getAppointments().isEmpty()) {
            System.out.println("\n---- No appointments found ----");
            return;
        }
        System.out.println("\n---- List of Appointments ----");
        for (Appointment appointment : getAppointments()) {
            System.out.println(appointment);

            if (appointment.getAppointmentStatus() == AppointmentStatus.COMPLETED && appointment.getAppointmentOutcomeRecord() != null) {
                System.out.println("-----------------------------------");
            } else {
                System.out.println("Appointment is not completed yet.");
                System.out.println("-----------------------------------");
            }

        }
    }

    public void addMedication(String name, int stockLevel, int lowStockLevel) {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            boolean exists = false;
            for (Medication medication : medications) {
                if (medication.getMedicationName().equalsIgnoreCase(name)) {
                    System.out.println("Medication with name '" + name + "' already exists in the inventory.");
                    System.out.print("Please enter a different medication name: ");
                    name = scanner.nextLine();
                    exists = true;
                    break;
                }
            }
            if (!exists) {
                break;
            }
        }

        int medicationId = medications.size();
        Medication newMedication = new Medication(medicationId, lowStockLevel, stockLevel, name);
        medications.add(newMedication);
        System.out.println("Medication Name: " + name + " added successfully.");
    }

    public void removeMedication() {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.print("\nEnter Medication ID to remove (-1 to cancel): ");
            int medicationId = scanner.nextInt();
            if (medicationId == -1) {
                System.out.println("Removing medication has cancelled.");
                return;
            }

            boolean found = medications.removeIf(med -> med.getMedicationId() == medicationId);
            if (found) {
                System.out.println("Medication with ID " + medicationId + " removed successfully.");
                break;
            } else {
                System.out.println("Medication with ID " + medicationId + " not found. Please try again.");
            }
        }
    }

    public void updateLowStockLevel() {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.print("\nEnter Medication ID to update low stock level (-1 to cancel): ");
            if (scanner.hasNextInt()) {
                int medicationId = scanner.nextInt();
                if (medicationId == -1) {
                    System.out.println("Updating the low stock level has cancelled.");
                    return;
                }

                boolean found = false;
                for (Medication medication : medications) {
                    if (medication.getMedicationId() == medicationId) {
                        int newStockLevel;
                        while (true) {
                            System.out.print("Enter new low stock level (positive integer): ");
                            if (scanner.hasNextInt()) {
                                newStockLevel = scanner.nextInt();
                                if (newStockLevel > 0) {
                                    break;
                                } else {
                                    System.out.println("Invalid input. Low stock level must be a positive integer. Please try again.");
                                }
                            } else {
                                System.out.println("Invalid input. Please enter a positive integer.");
                                scanner.next(); // Clear invalid input
                            }
                        }
                        medication.updateLowStockLevel(newStockLevel);
                        System.out.println("Low stock alert level for " + medication.getMedicationName() + " updated to " + newStockLevel);
                        found = true;
                        break;
                    }
                }
                if (found) {
                    break;
                } else {
                    System.out.println("Medication with ID " + medicationId + " not found. Please try again.");
                }
            } else {
                System.out.println("Invalid input. Please enter a valid Medication ID.");
                scanner.next(); // Clear invalid input
            }
        }
    }

    public void viewAllReplenishmentRequests() {
        if (replenishmentRequests == null || replenishmentRequests.isEmpty()) {
            System.out.println("\n---- No Replenishment Requests Available ---- ");
            return;
        }
        System.out.println("\n---- Viewing All Replenishment Requests ----");
        for (ReplenishmentRequest request : replenishmentRequests) {
            System.out.print(request);
        }
    }
    
    public void approveReplenishmentRequests() {
        if (replenishmentRequests == null || replenishmentRequests.isEmpty()) {
            System.out.println("\n---- No Replenishment Requests Available ---- ");
            return;
        }
    
        System.out.println("\n---- Replenishment Requests (Pending) ----");
        boolean hasPendingRequests = false;
        for (ReplenishmentRequest request : replenishmentRequests) {
            if (request.getStatus() == ReplenishmentStatus.PENDING) {
                System.out.println(request);
                hasPendingRequests = true;
            }
        }
    
        if (!hasPendingRequests) {
            System.out.println("---- No Pending Replenishment Requests Available ---- ");
            return;
        }
    
        Scanner sc = new Scanner(System.in);
    
        while (true) {
            try {
                System.out.print("\nEnter Request ID to process the request (-1 to cancel): ");
                String input = sc.next();
    
                // Check if input is -1 for canceling
                if (input.equals("-1")) {
                    System.out.println("Approving replenishment requests has cancelled.");
                    return;
                }
    
                // Parse input to integer
                int RequestID = Integer.parseInt(input);
    
                // Find the request by ID
                ReplenishmentRequest selectedRequest = findRequestByRequestId(RequestID);
    
                if (selectedRequest == null) {
                    System.out.println("No pending request found for the given Request ID.");
                } else {
                    String decision;
                    while (true) {
                        System.out.print("Do you want to approve this request? (Y/N): ");
                        decision = sc.next().toLowerCase();
                        if (decision.equalsIgnoreCase("Y")) {
                            approveRequest(selectedRequest);
                            System.out.println("Replenishment request approved.");
                            return;
                        } else if (decision.equalsIgnoreCase("N")) {
                            selectedRequest.setStatus(ReplenishmentStatus.REJECTED);
                            System.out.println("Replenishment request for " + selectedRequest.getMedicationName() + " rejected.");
                            return;
                        } else {
                            System.out.println("Invalid input. Please enter 'Y' or 'N'.");
                        }
                    }
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid Request ID or -1 to cancel.");
            }
        }
    }
    
    

    private ReplenishmentRequest findRequestByRequestId(int requestID) {
        for (ReplenishmentRequest request : replenishmentRequests) {
            if (request.getRequestID() == requestID && request.getStatus() == ReplenishmentStatus.PENDING) {
                return request;
            }
        }
        return null;
    }

    private void approveRequest(ReplenishmentRequest request) {
        Medication medication = MainApp.medications.stream()
                .filter(med -> med.getMedicationName().equalsIgnoreCase(request.getMedicationName()))
                .findFirst()
                .orElse(null);

        if (medication != null) {
            // Update stock level if approved
            medication.updateStock(medication.getStockLevel() + request.getQuantity());
            request.setStatus(ReplenishmentStatus.APPROVED);
            System.out.println("Replenishment approved for " + request.getMedicationName() + "Stock updated to " + medication.getStockLevel());
        } else {
            System.out.println("---- Medication not found in inventory ---- ");
        }
    }

    public int getAge() {
        return age;
    }

    @Override
    public String toString() {
        return String.format("User ID: %s, Name: %s, Gender: %s, Role: %s, Age: %s",
                super.getUserId(), super.getName(), super.getGender(), super.getRole(), age);
    }

    public void showNotifications(){
        boolean hasPendingRequests = replenishmentRequests.stream()
                .anyMatch(request -> request.getStatus() == ReplenishmentStatus.PENDING);

        System.out.println("\n---- Notifications ----");
        if (hasPendingRequests) {
            for (ReplenishmentRequest request : replenishmentRequests) {
                if (request.getStatus() == ReplenishmentStatus.PENDING) {
                    System.out.println("Request ID no.: " + request.getRequestID() + ", Medication: " + request.getMedicationName() + ", Requested by: " + request.getPharmacistID());
                }
            }
        }
        else{
            System.out.println("No new notifications");
        }
    }

    public void showMenu() {
        int choice = 0;
        Scanner sc = new Scanner(System.in);

        showNotifications();

        while (choice != 12) {
            System.out.println();
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
            System.out.println("10.View All Replenishment Request"); // change
            System.out.println("11.Approve Replenishment Request"); // change
            System.out.println("12.Logout");
            System.out.print("Please select an option: ");
            try {
                choice = sc.nextInt();
            } 
            catch (Exception e) {
            }
            sc.nextLine();

            switch (choice) {
                case 1:
                    viewStaff();
                    break;

                case 2:
                    Scanner scanner = new Scanner(System.in);
                    boolean isCancelled = false; // Flag to check if operation is cancelled

                    System.out.print("Enter Name (-1 to cancel): ");
                    String name = scanner.nextLine();
                    if (name.equals("-1")) {
                        System.out.println("Adding staff has cancelled.");
                        isCancelled = true;
                        break;
                    }

                    System.out.print("Enter Gender (Male/Female) (-1 to cancel): ");
                    Gender gender = null;
                    String genderInput = scanner.nextLine().toUpperCase();
                    if (genderInput.equals("-1")) {
                        System.out.println("Adding staff has cancelled.");
                        isCancelled = true;
                        break;
                    }
                    while (gender == null && !isCancelled) {
                        try {
                            gender = Gender.valueOf(genderInput);
                        } catch (IllegalArgumentException e) {
                            System.out.print("Invalid gender. Please enter Male or Female: ");
                            genderInput = scanner.nextLine().toUpperCase();
                            if (genderInput.equals("-1")) {
                                System.out.println("Adding staff has cancelled.");
                                isCancelled = true;
                                break;
                            }
                        }
                    }
                    if (isCancelled) {
                        break;
                    }

                    System.out.print("Enter Role (Doctor/Pharmacist) (-1 to cancel): ");
                    Role role = null;
                    String roleInput = scanner.nextLine().toUpperCase();
                    if (roleInput.equals("-1")) {
                        System.out.println("Adding staff has cancelled.");
                        isCancelled = true;
                        break;
                    }
                    while (role == null && !isCancelled) {
                        try {
                            role = Role.valueOf(roleInput);
                        } catch (IllegalArgumentException e) {
                            System.out.print("Invalid role. Please enter Doctor or Pharmacist: ");
                            roleInput = scanner.nextLine().toUpperCase();
                            if (roleInput.equals("-1")) {
                                System.out.println("Adding staff has cancelled.");
                                isCancelled = true;
                                break;
                            }
                        }
                    }
                    if (isCancelled) {
                        break;
                    }

                    System.out.print("Enter Age (-1 to cancel): ");
                    int inputAge = -1;

                    while (!isCancelled) {
                        try {
                            String input = scanner.nextLine(); // Read input as a string
                            inputAge = Integer.parseInt(input); // Convert to integer

                            if (inputAge == -1) {
                                System.out.println("Adding staff has cancelled.");
                                isCancelled = true;
                                break;
                            }

                            if (inputAge <= 0) {
                                System.out.println("Age must be a positive number. Please try again.");
                            } else {
                                break; // Valid positive age entered
                            }
                        } catch (NumberFormatException e) {
                            System.out.println("Invalid input. Please enter a valid number for age.");
                        }
                    }
                    if (isCancelled) {
                        break;
                    }

                    // If not cancelled, proceed to add the staff and view the list
                    addStaff(gender, name, role, inputAge);
                    viewStaff();
                    break;

                case 3:
                    viewStaff();
                    String userIdToUpdate = "";
                    while (true) {
                        System.out.print("\nEnter the Hospital ID of the staff member to update (-1 to cancel): ");
                        userIdToUpdate = sc.nextLine();
                        if (userIdToUpdate.equals("-1")) {
                            System.out.println("Viewing staff has cancelled.");
                            break;
                        }
                        User staffToUpdate = User.findUserById(userIdToUpdate, patients, doctors, pharmacists, administrators);

                        if (staffToUpdate != null) {
                            System.out.println("\nStaff member found:");
                            System.out.println("Name: " + staffToUpdate.getName() + ", Role: " + staffToUpdate.getRole());
                            updateStaff(staffToUpdate);
                            break;
                        } else {
                            System.out.println("Staff member with Hospital ID " + userIdToUpdate + " not found. Please try again.");
                        }
                    }

                    viewStaff();
                    break;

                case 4:
                    viewStaff();
                    System.out.print("\nEnter Hospital ID to remove (-1 to cancel): ");
                    String removeUserId = sc.nextLine();
                    if (removeUserId.equals("-1")) {
                        System.out.println("Removing staff has cancelled.");
                        break;
                    }
                    removeStaff(removeUserId);
                    viewStaff();
                    break;

                case 5:
                    viewAppointments();
                    break;

                case 6:
                    Medication.viewInventory();
                    break;

                case 7:
                    boolean canceled = false;

                    // Prompt for Medication Name
                    System.out.print("\nEnter Medication Name (-1 to cancel): ");
                    String medName = sc.nextLine().trim();
                    if (medName.equals("-1")) {
                        System.out.println("Adding Medication has cancelled.");
                        break; // Exit case 7
                    }

                    // Prompt and validate Stock Level
                    int stockLevel = -1;
                    while (true) {
                        System.out.print("Enter Stock Level (positive integer) (-1 to cancel): ");
                        String input = sc.nextLine().trim();

                        // Handle cancellation
                        if (input.equals("-1")) {
                            System.out.println("Adding Medication has cancelled.");
                            canceled = true;
                            break; // Exit stock level prompt
                        }

                        // Try to parse the input as an integer
                        try {
                            stockLevel = Integer.parseInt(input);
                            if (stockLevel > 0) {
                                break; // Valid stock level entered
                            } else {
                                System.out.println("Invalid input. Stock level must be a positive integer. Please try again.");
                            }
                        } catch (NumberFormatException e) {
                            System.out.println("Invalid input. Please enter a positive integer.");
                        }
                    }

                    // Exit case 7 if cancelled during stock level entry
                    if (canceled) {
                        break;
                    }

                    // Prompt and validate Low Stock Level Alert
                    int lowStockLevel = -1;
                    while (true) {
                        System.out.print("Enter Low Stock Level Alert (positive integer) (-1 to cancel): ");
                        String input = sc.nextLine().trim();

                        // Handle cancellation
                        if (input.equals("-1")) {
                            System.out.println("Adding Medication has cancelled.");
                            canceled = true;
                            break; // Exit low stock level prompt
                        }

                        // Try to parse the input as an integer
                        try {
                            lowStockLevel = Integer.parseInt(input);
                            if (lowStockLevel > 0) {
                                break; // Valid low stock level entered
                            } else {
                                System.out.println("Invalid input. Low stock level alert must be a positive integer. Please try again.");
                            }
                        } catch (NumberFormatException e) {
                            System.out.println("Invalid input. Please enter a positive integer.");
                        }
                    }

                    // Exit case 7 if cancelled during low stock level entry
                    if (canceled) {
                        break;
                    }

                    // Add medication and display the inventory if not cancelled
                    addMedication(medName, stockLevel, lowStockLevel);
                    Medication.viewInventory();
                    break;

                case 8:
                    Medication.viewInventory();
                    removeMedication();
                    Medication.viewInventory();
                    break;

                case 9:
                    Medication.viewInventory();
                    updateLowStockLevel();
                    Medication.viewInventory();
                    break;

                case 10:
                    viewAllReplenishmentRequests();
                    break;
                case 11:
                    approveReplenishmentRequests();
                    break;

                case 12:
                    System.out.println("Logging out... Returning to main login page...");
                    break;

                default:
                    System.out.println("Invalid Option");
            }
        }
    }
}
