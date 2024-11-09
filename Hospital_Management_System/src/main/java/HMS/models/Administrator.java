package HMS.models;

import java.util.*;

import HMS.MainApp;
import HMS.enums.*;
import static HMS.MainApp.*;
import java.util.Scanner;
import static HMS.MainApp.administrators;
import static HMS.MainApp.doctors;
import static HMS.MainApp.pharmacists;
import HMS.enums.Gender;
import HMS.enums.Role;

public class Administrator extends User {

    private int age;

    // Constructor
    public Administrator(String userId, String password, Gender gender, String name, Role role, int age) {
        super(userId, password, gender, name, role);
        this.age = age;
    }

    // Methods
    public void viewStaff() {
        // Implementation for displaying the list of staff members
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

     public void addStaff(String userId, String password, Gender gender, String name, Role role, int age) {
        // Implementation for adding a staff member (but change to auto running number, not input)
        if (role == Role.DOCTOR) {
            Doctor doctor = new Doctor(userId, password, gender, name, role, age);
            doctors.add(doctor);
        }
        else if (role == Role.ADMINISTRATOR) {
            Administrator administrator = new Administrator(userId, password, gender, name, role, age);
            administrators.add(administrator);
        }
        else if (role == Role.PHARMACIST) {
            Pharmacist pharmacist = new Pharmacist(userId, password, gender, name, role, age);
            pharmacists.add(pharmacist);
        }
    }

    private void updateStaff(User staff) {
        //implementation for updating staff (not working)
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter new name: ");
        staff.setName(scanner.nextLine());

        System.out.print("Enter new password: ");
        staff.changePassword(scanner.nextLine());

        System.out.println("Staff updated successfully.");
    }

    public void removeStaff(String userId) {
        // Implementation for removing a staff member (not working)
        if (doctors.removeIf(d -> d.getUserId().equals(userId)) ||
            pharmacists.removeIf(p -> p.getUserId().equals(userId))) {
            System.out.println("Staff removed successfully.");
        } else {
            System.out.println("Staff not found.");
        }
    }


    public void viewAppointments() {
        // Implementation for displaying the list of appointments
        System.out.println("\nList of Appointments:");

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
                    System.out.println("Prescribed Medications:");
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

    public void viewInventory() {
        for (Medication medication : medications) {
            System.out.println("Medication: " + medication.getMedicationName()
                    + ", Stock Level: " + medication.getStockLevel()
                    + ", Low Stock Level: " + medication.getLowStockLevel());
        }
    }

    public void addMedication(int medicationId, String name, int stockLevel, int lowStockLevel) {
        // Add a New Medication
        Medication newMedication = new Medication(medicationId, lowStockLevel, stockLevel, name);
        medications.add(newMedication);
        System.out.println("Medication " + name + " added successfully.");
    }

    public void removeMedication(int medicationId) {
        // Remove a Medication
        boolean found = medications.removeIf(med -> med.getMedicationId() == medicationId);
        if (found) {
            System.out.println("Medication removed successfully.");
        } else {
            System.out.println("Medication with ID " + medicationId + " not found.");
        }
    }

    public void updateMedicationStock(String medicationName, int newStockLevel) {
        for (Medication medication : medications) {
            if (medication.getMedicationName().equalsIgnoreCase(medicationName)) {
                medication.updateStock(medication.getLowStockLevel() + newStockLevel);
                System.out.println("Updated stock for " + medicationName);
                return;
            }
        }
        System.out.println("Medication not found.");
    }

    public void updateLowStockLevel(int medicationId, int newStockLevel) {
        // implementation for updating low stock level
        for (Medication medication : medications) {
            if (medication.getMedicationId() == medicationId) {
                medication.updateLowStockLevel(medication.getLowStockLevel() + newStockLevel);
                System.out.println("Low stock alert level for " + medication.getMedicationName() + " updated to " + newLowStockLevel);
                return;
            }
        }
        System.out.println("Medication with ID " + medicationId + " not found.");
    }


    public void ApproveReplenishmentRequests(int adminId) {
        System.out.println("\n--- Replenishment Requests ---");

        for (ReplenishmentRequest request : ReplenishmentRequest.replenishmentRequests) {
            if (request.getStatus().equalsIgnoreCase("Pending")) {
                System.out.println("Pharmacist ID: " + request.getPharmacistID() +
                        ", Medication: " + request.getMedicationName() +
                        ", Requested Quantity: " + request.getStockLevel() +
                        ", Status: " + request.getStatus());
            }
        }

        Scanner sc = new Scanner(System.in);
        System.out.print("Enter Pharmacist ID to approve: ");
        int pharmacistId = sc.nextInt();

        for (ReplenishmentRequest request : ReplenishmentRequest.replenishmentRequests) {
            if (request.getPharmacistID() == pharmacistId && request.getStatus().equals("Pending")) {
                Medication medication = MainApp.medications.stream()
                        .filter(med -> med.getMedicationName().equalsIgnoreCase(request.getMedicationName()))
                        .findFirst()
                        .orElse(null);

                if (medication != null) {
                    medication.updateStock(medication.getStockLevel() + request.getStockLevel());
                    request.approveReplenishmentRequest(adminId);
                    System.out.println("Replenishment approved for " + request.getMedicationName());
                } else {
                    System.out.println("Medication not found in inventory.");
                }
                return;
            }
        }
        System.out.println("No pending request found for the given Pharmacist ID.");
    }


    @Override
    public String toString() {
        return String.format("User ID: %s, Name: %s, Gender: %s, Role: %s",
                super.getUserId(), super.getName(), super.getGender(), super.getRole());
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
            System.out.println("9.Update Medication stock");
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

                    System.out.print("Enter User ID: ");
                    String userId = scanner.nextLine();

                    System.out.print("Enter Password: ");
                    String password = scanner.nextLine();

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

                    System.out.print("Enter Role (Administrator/Doctor/Pharmacist): ");
                    Role role = null;
                    while (role == null) {
                        try {
                            role = Role.valueOf(scanner.nextLine().toUpperCase());
                        } catch (IllegalArgumentException e) {
                            System.out.print("Invalid role. Please enter Administrator, Doctor, or Pharmacist: ");
                        }
                    }

                    System.out.print("Enter Age: ");
                    int age = scanner.nextInt();

                    addStaff(userId, password, gender, name, role, age);
                    break;

                case 3:
                    // Update Staff
                    
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
                    viewInventory();
                    break;

                case 7:
                    // Add Medication
                    System.out.print("Enter Medication ID: ");
                    int medId = sc.nextInt();
                    sc.nextLine();
                    System.out.print("Enter Medication Name: ");
                    String medName = sc.nextLine();
                    System.out.print("Enter Stock Level: ");
                    int stockLevel = sc.nextInt();
                    System.out.print("Enter Low Stock Level: ");
                    int lowStockLevel = sc.nextInt();
                    addMedication(medId, medName, stockLevel, lowStockLevel);
                    break;

                case 8:
                    // Remove Medication
                    System.out.print("Enter Medication ID to remove: ");
                    int removeMedId = sc.nextInt();
                    removeMedication(removeMedId);
                    break;

                case 9:
                    // Update Medication stock
                    System.out.print("Enter Medication Name to update stock: ");
                    String updateMedName = sc.nextLine();
                    System.out.print("Enter new stock level: ");
                    int newStockLevel = sc.nextInt();
                    updateMedicationStock(updateMedName, newStockLevel);
                    break;

                case 10:
                    // Approve Replenishment Request
                    System.out.print("Enter Administrator ID: ");
                    int adminId = sc.nextInt();
                    ApproveReplenishmentRequests(adminId);
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
