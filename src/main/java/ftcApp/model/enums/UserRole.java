package ftcApp.model.enums;

public enum UserRole {
    CUSTOMER(0),
    GENERAL_MANAGER(1),
    COLD_STORE_MANAGER(2),
    ACCOUNTANT(3),
    SECURITY_OFFICER(4),
    BLOCKED(5);

    private final int value;

    private UserRole(int value) {
        this.value = value;
    }
}
