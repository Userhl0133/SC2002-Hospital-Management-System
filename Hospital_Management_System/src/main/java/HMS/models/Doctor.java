package HMS.models;

import HMS.enums.*;
import java.util.*;
import java.time.LocalDateTime;

public class Doctor extends User{

    // Attributes
    // availability list contains time slots where the doctor is unavailable, in 1 hour blocks
    private List<LocalDateTime> availability;

    // Constructor
    public Doctor(int userId, String password, Gender gender, String name, Role role, LocalDateTime DOB) {
        super(userId, password, gender, name, role);  // Passing all required parameters to User
        this.availability = new ArrayList<LocalDateTime>();
    }

    // Methods
    public void viewPersonalSchedule() {
        // Implementation for viewing the personal schedule
        for (LocalDateTime date : availability) {
            System.out.println(date);
        }
    }

    public void updatePatientParticular() {
        // Implementation for updating patient particulars
    }

    public void viewUpcomingAppointment() {
        // Implementation for viewing upcoming appointments
    }

    public void updateAppointmentOutcomeRecord() {
        // Implementation for updating appointment outcome records
    }

    public void viewAppointmentOutcomeRecord() {
        // Implementation for viewing appointment outcome records
    }

    public void createAppointmentOutcomeRecord() {
        // Implementation for creating an appointment outcome record
    }

    public void cancelAppointment() {
        // Implementation for canceling an appointment
    }

    public void setAvailability(LocalDateTime time) {
        // If timing exists in availability list, remove the time slot
        if (this.availability.contains(time)) {
            this.availability.remove(time);
        }
        // If timing does not exist, add time slot
        else{
            this.availability.add(time);
        }
    }

    public void updateAppointment() {
        // Implementation for updating an appointment
    }

    public void showMenu() {
        int choice = 0;
        System.out.println("-----Doctor Menu-----");
        System.out.println("1.View Patient Medical Records");
        System.out.println("2.Update Patient Medical Records");
        System.out.println("3.View Personal Schedule");
        System.out.println("4.Set Availability for Appointments");
        System.out.println("5.Accept or Decline Appointment Requests");
        System.out.println("6.View Upcoming Appointments");
        System.out.println("7.Record Appointment Outcome");
        System.out.println("8.Logout");
        System.out.print("Please select an option: ");

        Scanner sc = new Scanner(System.in);
        while (choice != 8) {
            choice = sc.nextInt();
            switch(choice) {
                case 1 :
                    // View Patient Medical Records
                    break;

                case 2 :
                    // Update Patient Medical Records
                    break;

                case 3 :
                    // View Personal Schedule
                    break;

                case 4 :
                    // Set Availability for Appointments
                    break;

                case 5 :
                    // Accept or Decline Appointment Requests
                    break;

                case 6 :
                    // View Upcoming Appointments
                    break;

                case 7 :
                    // Record Appointment Outcome
                    break;

                case 8 :
                    // Logout
                    break;

                default :
                    System.out.println("Invalid Option");
            }
        }
    }
}
