package HMS.models;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Scanner;

import HMS.enums.AppointmentStatus;
import HMS.enums.BloodType;
import HMS.enums.Gender;
import HMS.enums.Role;

public class Patient extends User {

    private LocalDateTime DOB;
    private BloodType bloodType;
    private String contactInfo;
    private ArrayList<Appointment> appointments;

    // Constructor
    public Patient(String userId, String password, Gender gender, String name, Role role, LocalDateTime DOB, BloodType bloodType, String contactInfo) {
        super(userId, password, gender, name, role);  // Passing all required parameters to User
        this.DOB = DOB;
        this.bloodType = bloodType;
        this.contactInfo = contactInfo;
        this.appointments = null;
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
    public void viewUpcomingAppointment() {
        // This would contain logic to display appointment details
        System.out.println("Viewing appointment details for patient: " + getName());
    }

    public void scheduleAppointment() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Scheduling appointment for patient: " + getName());

        // Print doctor name
        String doctorName = sc.nextLine();

        // Date and Time
        System.out.print("Enter Appointment Date and Time (yyyy-MM-ddTHH:mm): ");
        String dateTimeInput = sc.nextLine();
        LocalDateTime appointmentDateTime = LocalDateTime.parse(dateTimeInput);

        Appointment newAppointment = new Appointment(this.getUserId(), "", appointmentDateTime, AppointmentStatus.PENDING, null);
        
        if (appointments == null) {
            appointments = new ArrayList<>();
        }
        appointments.add(newAppointment);

        System.out.println("Appointment scheduled with Dr. " + doctorName + " on " + appointmentDateTime);
    }

    // Getters
    public String getPatientID() {
        return getUserId();
    }

    public String getName() {
        return super.getName();
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

    public Role getRole() {
        return super.getRole();
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

    @Override
    public String toString() {
        return "Patient{"
                + "userId='" + getUserId() + '\''
                + ", name='" + getName() + '\''
                + ", gender=" + getGender()
                + ", role=" + getRole()
                + ", DOB=" + DOB
                + ", bloodType=" + bloodType
                + ", contactInfo='" + contactInfo + '\''
                + '}';
    }

    public void showMenu() {
        int choice = 0;
        Scanner sc = new Scanner(System.in);
        while(choice != 9) {
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
                    scheduleAppointment();
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
                    System.out.println("Logging out");
                    break;

                default:
                    System.out.println("Invalid Option");
            }
        }
    }
}
