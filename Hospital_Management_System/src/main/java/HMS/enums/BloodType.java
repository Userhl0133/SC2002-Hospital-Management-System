package HMS.enums;

public enum BloodType {
    OPLUS("O+"),
    OMINUS("O-"),
    APLUS("A+"),
    AMINUS("A-"),
    BPLUS("B+"),
    BMINUS("B-"),
    ABPLUS("AB+"),
    ABMINUS("AB-");

    private final String displayName;

    BloodType(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }

    @Override
    public String toString() {
        return displayName;
    }
    
    public static BloodType fromDisplayName(String displayName) {
        for (BloodType type : BloodType.values()) {
            if (type.displayName.equalsIgnoreCase(displayName)) {
                return type;
            }
        }
        throw new IllegalArgumentException("Unknown blood type: " + displayName);
    }
}
