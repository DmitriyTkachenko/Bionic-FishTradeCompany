package ftcApp.model.enums;

public enum ParcelStatus {
    REGISTEREDBYGM(0),
    REGISTEREDBYCSM(1),
    PUTUPFORSALE(2);

    private final int value;

    private ParcelStatus(int value) {
        this.value = value;
    }
}
