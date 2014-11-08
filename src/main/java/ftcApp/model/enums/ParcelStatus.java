package ftcApp.model.enums;

public enum ParcelStatus {
    REGISTERED_BY_GM(0, "parcelStatus.registeredByGm"),
    REGISTERED_BY_CSM(1, "parcelStatus.registeredByCsm"),
    PUT_UP_FOR_SALE(2, "parcelStatus.putUpForSale");

    private final int value;
    private String label;

    private ParcelStatus(int value, String label) {
        this.value = value;
        this.label = label;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public int getValue() {
        return value;
    }
}
