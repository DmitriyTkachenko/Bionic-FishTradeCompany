package ftcApp.model.enums;

public enum UserRole {
    GENERALMANAGER(0),
    COLDSTOREMANAGER(1),
    ACCOUNTANT(2),
    SECURITYOFFICER(3);

    private final int value;

    private UserRole(int value) {
        this.value = value;
    }
}
