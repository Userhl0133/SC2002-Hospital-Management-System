package HMS.models;

import java.time.LocalDateTime;
import HMS.enums.BloodType;
import HMS.enums.Gender;
import HMS.enums.Role;

public class Patient extends User {

    private LocalDateTime DOB;
    private BloodType bloodType;
    private String contactInfo;

    // Constructor
    public Patient(String userId, String password, Gender gender, String name, Role role, LocalDateTime DOB, BloodType bloodType, String contactInfo) {
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
}
