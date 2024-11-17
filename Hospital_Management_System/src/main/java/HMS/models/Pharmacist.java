package HMS.models;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

import HMS.MainApp;
import HMS.enums.AppointmentStatus;
import HMS.enums.Gender;
import HMS.enums.PrescriptionStatus;
import HMS.enums.ReplenishmentStatus;
import HMS.enums.Role;
import static HMS.models.Appointment.getAppointments;

public class Pharmacist extends User {

    private int age;

    public Pharmacist(String userId, String password, Gender gender, String name, Role role, int age) {
        super(userId, password, gender, name, role);
        this.age = age;
    }
    public int getAge() {
        return age;
    }

    @Override
    public String toString() {
        return String.format("User ID: %s, Name: %s, Gender: %s, Role: %s, Age: %s",
                super.getUserId(), super.getName(), super.getGender(), super.getRole(), age);
    }

    public void viewCompletedAppointmentOutcomeRecords() {
        System.out.println("\n--- All Appointment Outcome Records ---");

        List<Appointment> allAppointments = getAppointments();
        
        if (allAppointments.isEmpty()) {
            System.out.println("No appointments found.\n");
        } else {
            allAppointments.forEach(appointment -> {
                System.out.println("Appointment ID: " + appointment.getAppointmentID());
                System.out.println("Doctor ID: " + appointment.getDoctorID());
                System.out.println("Patient ID: " + appointment.getPatientID());
                if (appointment.getAppointmentOutcomeRecord() != null) {
                    System.out.println(appointment.getAppointmentOutcomeRecord());
                } else {
                    System.out.println("No outcome record available.");
                }
                System.out.println("-----------------------------------");
            });
        }
    
        return;
    }
    

    public void updatePrescriptionStatus() {
        System.out.println("\n--- Update Prescription Status ---");
        Scanner sc = new Scanner(System.in);
    
        List<Appointment> completedAppointments = getAppointments().stream()
                .filter(a -> a.getAppointmentStatus() == AppointmentStatus.COMPLETED)
                .collect(Collectors.toList());
    
        if (completedAppointments.isEmpty()) {
            System.out.println("No completed appointments found.\n");
            return;
        }
    
        System.out.println("Completed appointments available to update the prescription status:");
        completedAppointments.forEach(appointment -> {
            System.out.println("Appointment ID: " + appointment.getAppointmentID());
        });
    
        System.out.print("\nEnter the Appointment ID to update prescription status (or -1 to cancel): ");
        int appointmentIDToUpdate = sc.nextInt();
        sc.nextLine();
    
        if (appointmentIDToUpdate == -1) {
            System.out.println("Action canceled.");
            return;
        }
    
        Appointment selectedAppointment = completedAppointments.stream()
                .filter(a -> a.getAppointmentID() == appointmentIDToUpdate)
                .findFirst()
                .orElse(null);
    
        if (selectedAppointment == null) {
            System.out.println("Invalid Appointment ID. Please try again.");
            return;
        }
    
        AppointmentOutcomeRecord selectedOutcomeRecord = selectedAppointment.getAppointmentOutcomeRecord();
    
        if (selectedOutcomeRecord != null && !selectedOutcomeRecord.getPrescribedMedications().isEmpty()) {
            List<Prescription> prescribedMedications = selectedOutcomeRecord.getPrescribedMedications();
    
            System.out.println("\nList of prescribed medications for Appointment ID " + selectedAppointment.getAppointmentID() + ":");
            prescribedMedications.forEach(prescription -> {
                System.out.println("- " + prescription.getMedication().getMedicationName() + " (Status: " + prescription.getStatus().getDisplayName() + ")");
            });
    
            boolean validChoice = false;
            while (!validChoice) {
                System.out.print("\nDo you want to dispense all pending medications for appointment ID " + selectedAppointment.getAppointmentID() + "? (Y/N): ");
                String dispenseAllChoice = sc.nextLine().trim().toLowerCase();
    
                if (dispenseAllChoice.equals("y")) {
                    validChoice = true;
                    boolean allDispensed = true;
                    for (Prescription prescription : prescribedMedications) {
                        if (prescription.getStatus() == PrescriptionStatus.PENDING) {
                            prescription.setStatus(PrescriptionStatus.DISPENSED);
                            System.out.println("Prescription status updated to DISPENSED for Medication: " +
                                    prescription.getMedication().getMedicationName());
                            prescription.getMedication().dispenseMedication(prescription.getQuantity());
                        } else {
                            allDispensed = false;
                            System.out.println("Prescription " + prescription.getMedication().getMedicationName() +
                                    " is already dispensed or not in PENDING status.");
                        }
                    }
    
                    if (allDispensed) {
                        System.out.println("\nAll medications dispensed successfully.");
                    } else {
                        System.out.println("\nSome medications were already dispensed or not in PENDING status.");
                    }
                } else if (dispenseAllChoice.equals("n")) {
                    validChoice = true;
                    System.out.println("Select the medication to update status:");
                    for (int i = 0; i < prescribedMedications.size(); i++) {
                        Prescription prescription = prescribedMedications.get(i);
                        System.out.println((i + 1) + ". " + prescription.getMedication().getMedicationName() +
                                " (Status: " + prescription.getStatus().getDisplayName() + ")");
                    }
    
                    System.out.print("\nEnter the number to dispense medication (or -1 to cancel): ");
                    int medChoice = sc.nextInt();
                    sc.nextLine();
    
                    if (medChoice == -1) {
                        System.out.println("Action canceled.");
                        return;
                    }
    
                    if (medChoice < 1 || medChoice > prescribedMedications.size()) {
                        System.out.println("Invalid selection. Please try again.");
                        return;
                    }
    
                    Prescription selectedPrescription = prescribedMedications.get(medChoice - 1);
    
                    if (selectedPrescription.getStatus() == PrescriptionStatus.PENDING) {
                        selectedPrescription.setStatus(PrescriptionStatus.DISPENSED);
                        System.out.println("Prescription status updated to DISPENSED for Medication: " +
                                selectedPrescription.getMedication().getMedicationName());
                        selectedPrescription.getMedication().dispenseMedication(selectedPrescription.getQuantity());
                    } else {
                        System.out.println("Prescription status is not 'PENDING', cannot update to DISPENSED.");
                    }
                } else {
                    System.out.println("Invalid input. Please enter 'Y' to dispense all medications or 'N' to select a specific medication.");
                }
            }
        } else {
            System.out.println("No prescribed medications available for this appointment.");
        }
    }

    public void submitReplenishmentRequest() {
        Scanner sc = new Scanner(System.in);
        System.out.println("\n--- Available Medications ---");
        for (Medication medication : MainApp.medications) {
            System.out.println("Medication ID: " + medication.getMedicationId() + " | " + medication.getMedicationName());
        }

        System.out.print("Enter Medication ID to request replenishment: ");
        int medicationId = sc.nextInt();
        sc.nextLine();

        Medication medication = MainApp.medications.stream()
                .filter(med -> med.getMedicationId() == medicationId)
                .findFirst()
                .orElse(null);

        if (medication == null) {
            System.out.println("Medication with ID " + medicationId + " not found in inventory.");
            return;
        }

        System.out.print("Enter quantity to request: ");
        int quantity = sc.nextInt();

        int currentStock = medication.getStockLevel();
        System.out.println("Current stock level for " + medication.getMedicationName() + ": " + currentStock);

        int requestID = MainApp.replenishmentRequests.size() + 1;

        String pharmacistId = this.getUserId();

        ReplenishmentRequest request = new ReplenishmentRequest(
            requestID,
            pharmacistId,
            medication.getMedicationName(),
            quantity,
            currentStock,
            ReplenishmentStatus.PENDING
        );

        MainApp.replenishmentRequests.add(request);

        medication.setReplenishmentStatus("PENDING");

        System.out.println("Replenishment request submitted for " + medication.getMedicationName() + 
                        " by Pharmacist: " + pharmacistId + " with quantity " + quantity + ".");
    }


    public void showMenu() {
        int choice = 0;
        Scanner sc = new Scanner(System.in);
        boolean lowStockNotificationDisplayed = false;

        // Show notification for pending medications that need to be dispensed
        for (Appointment appointment : getAppointments()) {
            if (appointment.getAppointmentStatus() == AppointmentStatus.COMPLETED) {
                AppointmentOutcomeRecord outcomeRecord = appointment.getAppointmentOutcomeRecord();
                
                if (outcomeRecord != null && !outcomeRecord.getPrescribedMedications().isEmpty()) {
                    List<Prescription> prescribedMedications = outcomeRecord.getPrescribedMedications();
                    
                    prescribedMedications.forEach(prescription -> {
                        if (prescription.getStatus() == PrescriptionStatus.PENDING) {
                            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
                            System.out.println("\n---- Notification System ----");
                            System.out.println("Pending Medication to be Dispensed");
                            System.out.println("Appointment ID: " + appointment.getAppointmentID());
                            System.out.println("Patient: " + new Patient().getPatientById(appointment.getPatientID()).getName());
                            System.out.println("Medication: " + prescription.getMedication().getMedicationName());
                            System.out.println("Appointment Date & Time: " + appointment.getAppointmentDateTime().format(formatter));
                        }
                    });
                }
            }
        }

        // Now show Low Stock Alert only if low stock is detected
        for (Medication medication : MainApp.medications) {
            if (medication.getStockLevel() <= medication.getLowStockLevel()) {
                if (!lowStockNotificationDisplayed) {
                    System.out.println("\n---- Low Stock Medication Level Alert ----");
                    lowStockNotificationDisplayed = true; // Set flag to prevent multiple alerts
                }
                System.out.println("Medication " + medication.getMedicationName() + " is running low.");
                System.out.println("Current stock level: " + medication.getStockLevel() +
                        " (Low stock alert: " + medication.getLowStockLevel() + ")");
                System.out.println("Please consider submitting a replenishment request.");
            }
        }

        // If no low stock medications, do not print the "Low Stock Medication Level Alert" header
        if (lowStockNotificationDisplayed) {
            // Add any other logic here if needed, but nothing will print if no low stock is detected
        }

        while (choice != 5) {
            System.out.println();
            System.out.println("=============================");
            System.out.println("----- Pharmacist Menu -------");
            System.out.println("=============================");
            System.out.println("1. View Appointment Outcome Record");
            System.out.println("2. Update Prescription Status");
            System.out.println("3. View Medication Inventory");
            System.out.println("4. Submit Replenishment Request");
            System.out.println("5. Logout");
            System.out.print("Please select an option: ");
            choice = sc.nextInt();
            sc.nextLine();
            switch (choice) {
                case 1:
                    viewCompletedAppointmentOutcomeRecords();
                    break;

                case 2:
                    updatePrescriptionStatus();
                    break;

                case 3:
                    Medication.viewInventory();
                    break;

                case 4:
                    submitReplenishmentRequest();
                    break;

                case 5:
                    // Logout
                    System.out.println("Logging out... Returning to main login page...");
                    break;

                default:
                    System.out.println("Invalid Option, please try again.");
            }
        }
    }
}
