import java.time.LocalDateTime;
import java.util.Date;
import java.util.Scanner;

import HMS.models.Doctor;
import src.enums.BloodType;
import src.enums.Gender;
import src.enums.Role;  

public class Patient extends User{
    
    private LocalDateTime DOB;
    private BloodType bloodType;
    private String contactInfo;

    // Constructor
    public Patient(int userId, String password, Gender gender, String name, Role role, LocalDateTime DOB, BloodType bloodType, String contactInfo) {
        super(userId, password, gender, name, role);  // Passing all required parameters to User
        this.DOB = DOB;
        this.bloodType = bloodType;
        this.contactInfo = contactInfo;
    }

    // Method to update patient details
    public void updateParticular(String name, LocalDateTime DOB, String contactInfo) {
        setName(name);  // using inherited setter from User
        this.DOB = DOB;
        this.contactInfo = contactInfo;
    }

    // Method to view patient details
    public void viewParticular() {
        System.out.println("Patient ID: " + getUserId());  // assuming User has getUserId()
        System.out.println("Name: " + getName());
        System.out.println("DOB: " + DOB);
        System.out.println("Blood Type: " + bloodType);
        System.out.println("Contact Info: " + contactInfo);
    }

    // Placeholder method for viewing appointment details
    public void viewAppointment() {
        // This would contain logic to display appointment details
        System.out.println("Viewing appointment details for patient: " + getName());
    }

    // Getters
    public int getPatientID() {
        return patientID;
    }

    public String getName() {
        return name;
    }

    public LocalDateTime getDOB() {
        return DOB;
    }

    public BloodType getBloodType() {
        return bloodType;
    }

    public String getContactInfo() {
        return contactInfo;
    }

    // Setters
    public void setPatientID(int patientID) {
        this.patientID = patientID;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDOB(LocalDateTime DOB) {
        this.DOB = DOB;
    }

    public void setContactInfo(String contactInfo) {
        this.contactInfo = contactInfo;
    }

    public void setBloodType(BloodType bloodType) {
        this.bloodType = bloodType;
    }

    public void showMenu() {
        int choice = 0;
        System.out.println("-----Patient Menu-----");
        System.out.println("1.View Medical Record");
        System.out.println("2.Update Personal Information");
        System.out.println("3.View Available Appointment Slots");
        System.out.println("4.Schedule an Appointment");
        System.out.println("5.Reschedule an Appointment");
        System.out.println("6.Cancel an Appointment");
        System.out.println("7.View Scheduled Appointments");
        System.out.println("8.View Past Appointment Outcome Records");
        System.out.println("9.Logout");
        System.out.print("Please select an option: ");

        Scanner sc = new Scanner(System.in);
        while(choice != 9) {
            choice = sc.nextInt();
            switch(choice) {
                case 1:
                    // View Medical Record
                    break;

                case 2:
                    // Update Personal Information
                    break;

                case 3:
                    // View Available Appointment Slots
                    break;

                case 4:
                    // Schedule an Appointment
                    break;

                case 5:
                    // Reschedule an Appointment
                    break;

                case 6:
                    // Cancel an Appointment
                    break;

                case 7:
                    // View Scheduled Appointments
                    break;

                case 8:
                    // View Past Appointment Outcome Records
                    break;

                case 9:
                    // Logout
                    break;

                default:
                    System.out.println("Invalid Option");
            }
        }
    }
}
