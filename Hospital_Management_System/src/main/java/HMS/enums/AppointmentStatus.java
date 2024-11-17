package HMS.enums;

// Enum for Appointment Status
public enum AppointmentStatus {
    PENDING("Pending"),
    CONFIRMED("Confirmed"),
    COMPLETED("Completed"),
    CANCELLED("Cancelled");

    private final String displayName;

// Constructor for AppointmentStatus
    AppointmentStatus(String displayName) {
        this.displayName = displayName;
    }

// Getter for displayName
    public String getDisplayName() {
        return displayName;
    }

// Override toString method
    @Override
    public String toString() {
        return displayName;
    }
}
