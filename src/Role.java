public enum Role {
    PATIENT("Patient"),
    DOCTOR("Doctor"),
    PHARMACIST("Pharmacist"),
    ADMINISTRATOR("Administrator");

    private final String displayName;

    Role(String displayName) {
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
