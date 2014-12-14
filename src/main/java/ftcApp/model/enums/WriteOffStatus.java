package ftcApp.model.enums;

public enum WriteOffStatus {
    NOT_WRITTEN_OFF(0, "writeOffStatus.notWrittenOff"),
    DESIGNATED_FOR_WRITE_OFF(1, "writeOffStatus.designatedForWriteOff"),
    WRITTEN_OFF(2, "writeOffStatus.writtenOff");

    private final int value;
    private String label;

    private WriteOffStatus(int value, String label) {
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
