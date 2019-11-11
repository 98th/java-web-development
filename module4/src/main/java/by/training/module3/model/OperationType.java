package by.training.module3.model;

public enum  OperationType {
    LOADING("loading"),
    UNLOADING("unloading");
    private String value;


    OperationType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static OperationType getOperationByValue (String value) {
        for (OperationType i : values()) {
            if (i.getValue().equalsIgnoreCase(value)) {
                return i;
            }
        }
        throw new IllegalArgumentException();
    }
}
