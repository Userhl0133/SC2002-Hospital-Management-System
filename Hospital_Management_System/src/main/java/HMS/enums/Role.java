package HMS.enums;

// Enum for Role
public enum Role {
    // Different roles in the system
    PATIENT("Patient"),
    DOCTOR("Doctor"),
    PHARMACIST("Pharmacist"),
    ADMINISTRATOR("Administrator");

    private final String displayName;

    Role(String displayName) {
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
