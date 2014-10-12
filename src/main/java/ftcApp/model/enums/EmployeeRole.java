package ftcApp.model.enums;

public enum EmployeeRole {
    GENERAL_MANAGER(0),
    COLD_STORE_MANAGER(1),
    ACCOUNTANT(2),
    SECURITY_OFFICER(3);

    private final int value;

    private EmployeeRole(int value) {
        this.value = value;
    }
}
