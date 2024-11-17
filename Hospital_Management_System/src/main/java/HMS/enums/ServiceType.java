package HMS.enums;

// Enum for Service Type
public enum ServiceType {
    // Different types of medical services
    CONSULTATION("Consultation"),
    XRAY("X-ray"),
    BLOOD_TEST("Blood Test");

    private final String displayName;

// Constructor for ServiceType
    ServiceType(String displayName) {
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
