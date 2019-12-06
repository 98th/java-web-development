package by.training.taxi.car;


import java.util.Arrays;
import java.util.Optional;

public enum CarStatus {
    FREE("free"),
    BUSY("busy");

    private String value;

    CarStatus(String value) {
        this.value = value;
    }

    public static Optional<CarStatus> getCarStatusFromText(String role) {
        return Arrays.stream(values())
                .filter(i -> i.value.equalsIgnoreCase(role))
                .findFirst();
    }
}
