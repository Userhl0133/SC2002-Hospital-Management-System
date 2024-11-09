package HMS.models;

import java.util.List; 
import HMS.enums.ServiceType;

public class AppointmentOutcomeRecord {
    private int recordID;
    private ServiceType serviceType;
    private List<Medication> prescribedMedications;
    private String consultationNotes;

    // Constructor
    public AppointmentOutcomeRecord(int recordID, ServiceType serviceType, List<Medication> prescribedMedications, String consultationNotes) {
        this.recordID = recordID;
        this.serviceType = serviceType;
        this.prescribedMedications = prescribedMedications;
        this.consultationNotes = consultationNotes;
    }

    // Getter for serviceType
    public ServiceType getServiceType() {
        return serviceType;
    }

    // Setter for serviceType
    public void setServiceType(ServiceType serviceType) {
        this.serviceType = serviceType;
    }

    // Getter for consultationNotes
    public String getConsultationNotes() {
        return consultationNotes;
    }

    // Setter for consultationNotes
    public void setConsultationNotes(String consultationNotes) {
        this.consultationNotes = consultationNotes;
    }

    // Getters and setters for other fields can be added as needed
    public int getRecordID() {
        return recordID;
    }

    public void setRecordID(int recordID) {
        this.recordID = recordID;
    }

    public List<Medication> getPrescribedMedications() {
        return prescribedMedications;
    }

    public void setPrescribedMedications(List<Medication> prescribedMedications) {
        this.prescribedMedications = prescribedMedications;
    }

    @Override
    public String toString() {
        return """
               AppointmentOutcomeRecord: 
               , serviceType=""" + serviceType + "\n" +
                ", prescribedMedications=" + prescribedMedications + "\n" +
                ", consultationNotes='" + consultationNotes;
    }
}
