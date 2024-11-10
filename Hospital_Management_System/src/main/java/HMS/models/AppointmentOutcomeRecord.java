package HMS.models;

import java.util.List;

import HMS.enums.PrescriptionStatus;
import HMS.enums.ServiceType;

public class AppointmentOutcomeRecord {
    private int recordID;  // Unique ID for the record
    private ServiceType serviceType;  // Type of the medical service provided
    private List<Prescription> prescribedMedications;  // Medications prescribed during the appointment
    private String consultationNotes;  // Notes from the consultation

    // Constructor to initialize all fields
    public AppointmentOutcomeRecord(int recordID, ServiceType serviceType, List<Prescription> prescribedMedications, String consultationNotes) {
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

    // Getter for recordID
    public int getRecordID() {
        return recordID;
    }

    // Setter for recordID
    public void setRecordID(int recordID) {
        this.recordID = recordID;
    }

    // Getter for prescribedMedications
    public List<Prescription> getPrescribedMedications() {
        return prescribedMedications;
    }

    // Setter for prescribedMedications
    public void setPrescribedMedications(List<Prescription> prescribedMedications) {
        if (prescribedMedications != null) {
            this.prescribedMedications = prescribedMedications;
        } else {
            // Handle null scenario, e.g., throw an exception or log a warning
            System.out.println("Error: prescribedMedications list cannot be null.");
        }
    }

    // Override toString() to provide a meaningful representation of AppointmentOutcomeRecord
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Appointment Outcome Record: \n");
        sb.append("Record ID: ").append(recordID).append("\n");
        sb.append("Service Type: ").append(serviceType).append("\n");
        sb.append("Consultation Notes: ").append(consultationNotes).append("\n");

        if (prescribedMedications != null && !prescribedMedications.isEmpty()) {
            sb.append("Prescribed Medications: \n");
            for (Prescription prescription : prescribedMedications) {
                sb.append(" - ").append(prescription.getMedication().getMedicationName()).append(" (Stock Level: ").append(prescription.getMedication().getStockLevel()).append(")\n");
            }
        } else {
            sb.append("No medications prescribed.\n");
        }

        return sb.toString();
    }
}
