package ftcApp.model.enums;

public enum OrderStatus {
    PENDING_PREPAYMENT(0, "orderStatus.pendingPrepayment"),
    PENDING_SHIPMENT(1, "orderStatus.pendingShipment"),
    SHIPPED(2, "orderStatus.shipped"),
    COMPLETED(3, "orderStatus.completed");

    private final int value;
    private String label;

    private OrderStatus(int value, String label) {
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
