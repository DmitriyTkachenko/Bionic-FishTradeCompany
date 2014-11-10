package ftcApp.model.enums;

public enum UserRole {
    CUSTOMER(0, "userRole.customer"),
    GENERAL_MANAGER(1, "userRole.generalManager"),
    COLD_STORE_MANAGER(2, "userRole.coldStoreManager"),
    ACCOUNTANT(3, "userRole.accountant"),
    SECURITY_OFFICER(4, "userRole.securityOfficer"),
    BLOCKED(5, "userRole.blocked");

    private final int value;
    private String label;

    private UserRole(int value, String label) {
        this.value = value;
        this.label = label;
    }

    public int getValue() {
        return value;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }
}
