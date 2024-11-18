package HMS.enums;

// Enum for Prescription Status
public enum PrescriptionStatus {
    PENDING("Pending"),
    DISPENSED("Dispensed");

    private final String displayName;

    PrescriptionStatus(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }

    @Override
    public String toString() {
        return displayName;
    }
}
