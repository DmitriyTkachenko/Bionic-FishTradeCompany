package ftcApp.model.enums;

public enum OrderStatus {
    PENDING_PREPAYMENT(0),
    PENDING_SHIPPING(1),
    COMPLETED(2);

    private final int value;

    private OrderStatus(int value) {
        this.value = value;
    }
}
