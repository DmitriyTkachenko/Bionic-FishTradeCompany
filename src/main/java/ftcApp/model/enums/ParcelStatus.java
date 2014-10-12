package ftcApp.model.enums;

public enum ParcelStatus {
    REGISTERED_BY_GM(0),
    REGISTERED_BY_CSM(1),
    PUT_UP_FOR_SALE(2);

    private final int value;

    private ParcelStatus(int value) {
        this.value = value;
    }
}
