package ftcApp.model.enums;

public enum OrderStatus {
    PENDINGPREPAYMENT(0),
    PENDINGSHIPPING(1),
    COMPLETED(2);

    private final int value;

    private OrderStatus(int value) {
        this.value = value;
    }
}
