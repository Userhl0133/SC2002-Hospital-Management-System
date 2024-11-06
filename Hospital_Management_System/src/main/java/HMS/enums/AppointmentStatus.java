package HMS.enums;

public enum AppointmentStatus {
    ACCEPTED("Accepted"),
    DECLINED("Declined"),
    COMPLETED("Completed");

    private final String displayName;

    AppointmentStatus(String displayName) {
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
