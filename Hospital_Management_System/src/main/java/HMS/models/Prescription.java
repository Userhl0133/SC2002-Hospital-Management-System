package HMS.models;

import HMS.enums.PrescriptionStatus;

public class Prescription {
    private Medication medication;
    private PrescriptionStatus status;

    public Prescription(Medication medication, PrescriptionStatus status) {
        this.medication = medication;
        this.status = status;
    }


    public Medication getMedication() {
        return medication;
    }

    public void setMedication(Medication medication) {
        this.medication = medication;
    }

    public PrescriptionStatus getStatus() {
        return status;
    }

    public void setStatus(PrescriptionStatus status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Prescription{" +
                "Medication=" + medication.getMedicationName() +
                ", Status='" + status +
                '}';
    }
}
