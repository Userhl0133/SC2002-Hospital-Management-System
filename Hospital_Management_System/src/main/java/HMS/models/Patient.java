import java.time.LocalDateTime;
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
}
