package HMS.models;

import HMS.enums.PrescriptionStatus;

public class Prescription {
    private Medication medication;
    private PrescriptionStatus status;
    private Integer quantity;
    private String frequency;

    public Prescription(Medication medication, PrescriptionStatus status, int quantity, String frequency) {
        this.medication = medication;
        this.status = status;
        this.quantity = quantity;
        this.frequency = frequency;
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

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public String getFrequency() {
        return frequency;
    }

    public void setFrequency(String frequency) {
        this.frequency = frequency;
    }

    @Override
    public String toString() {
        return "Prescription{" +
                "Medication=" + medication.getMedicationName() +
                ", Status='" + status +
                '}';
    }
}
