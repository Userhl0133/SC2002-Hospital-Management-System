package HMS.models;

import java.time.LocalDateTime;
import HMS.enums.BloodType;
import HMS.enums.Gender;
import HMS.enums.Role;  
import java.util.List;


public class Doctor extends User{

    // Attributes
    private List<LocalDateTime> availability;

    // Constructor
    public Doctor(List<LocalDateTime> availability) {
        this.availability = availability;
    }

    // Methods
    public void viewPersonalSchedule() {
        // Implementation for viewing the personal schedule
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
        // Implementation for setting availability
        this.availability.add(time);
    }

    public void updateAppointment() {
        // Implementation for updating an appointment
    }

    // Getter for availability (optional, depending on use case)
    public List<LocalDateTime> getAvailability() {
        return availability;
    }

    // Setter for availability (optional, depending on use case)
    public void setAvailability(List<LocalDateTime> availability) {
        this.availability = availability;
    }
}
