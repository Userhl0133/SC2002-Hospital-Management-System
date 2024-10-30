package HMS.enums;

public enum ServiceType {
    CONSULTATION("Consultation"),
    XRAY("X-ray"),
    BLOOD_TEST("Blood Test");

    private final String displayName;

    ServiceType(String displayName) {
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
