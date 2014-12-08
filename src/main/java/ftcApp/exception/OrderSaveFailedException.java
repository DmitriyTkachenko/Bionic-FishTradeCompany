package ftcApp.exception;

public class OrderSaveFailedException extends Exception {
    public OrderSaveFailedException() {
    }

    public OrderSaveFailedException(String message) {
        super(message);
    }
}
