
package HMS.models;

import java.util.ArrayList;
import java.util.List;

import HMS.enums.ServiceType;
import static HMS.models.Appointment.getAppointments;

public class AppointmentOutcomeRecord {
    private int recordID;  // Unique ID for the record
    private ServiceType serviceType;  // Type of the medical service provided
    private String diagnosis;
    private String treatmentPlan;
    private List<Prescription> prescribedMedications;  // Medications prescribed during the appointment
    private String consultationNotes;  // Notes from the consultation

    // Constructor to initialize all fields
    public AppointmentOutcomeRecord(int recordID, ServiceType serviceType, String diagnosis, String treatmentPlan, List<Prescription> prescribedMedications, String consultationNotes) {
        this.recordID = recordID;
        this.serviceType = serviceType;
        this.diagnosis = diagnosis;
        this.treatmentPlan = treatmentPlan;
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

    // Getter for diagnosis
    public String getDiagnosis() {
        return diagnosis;
    }

    // Setter for diagnosis
    public void setDiagnosis(String diagnosis) {
        this.diagnosis = diagnosis;
    }

    // Getter for treatment plan
    public String getTreatmentPlan() {
        return treatmentPlan;
    }

    // Setter for treatment plan
    public void setTreatmentPlan(String treatmentPlan) {
        this.treatmentPlan = treatmentPlan;
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

    public static ArrayList<AppointmentOutcomeRecord> getAppointmentOutcomeRecords() {
        ArrayList<AppointmentOutcomeRecord> result = new ArrayList<>();
        for(Appointment appointment : getAppointments()){
            result.add(appointment.getAppointmentOutcomeRecord());
        }
        return result;
    }

    // Override toString() to provide a meaningful representation of AppointmentOutcomeRecord
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("\nService Type: ").append(serviceType).append("\n");
        builder.append("Consultation Notes: ").append(consultationNotes).append("\n");
        builder.append("Diagnosis: ").append(diagnosis).append("\n");
        builder.append("Treatment plan: ").append(treatmentPlan).append("\n");
        builder.append("Prescribed Medications:\n");

        // Loop through medications and print without stock level
        for (Prescription prescription : prescribedMedications) {
            builder.append(" - ").append(prescription.getMedication().getMedicationName()) // Assuming the Medication class has `getMedicationName()`
                   .append(" (Status: ").append(prescription.getStatus()).append(")")
                    .append("\n     - Quantity: ").append(prescription.getQuantity())
                    .append("\n     - Frequency: ").append(prescription.getFrequency()).append("\n"); // Display status of the prescription
        }
        return builder.toString();
    }
}
