package by.training.taxi.request;

import java.util.Arrays;
import java.util.Optional;

public enum RequestStatus {
    OFFERED("offered"),
    ACCEPTED("accepted"),
    CONFIRMED("confirmed"),
    DECLINED("declined"),
    CANCELLED("cancelled");

    private String value;

    RequestStatus(String value){
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static Optional<RequestStatus> getFromText(String role) {
        return Arrays.stream(values())
                .filter(i -> i.value.equalsIgnoreCase(role))
                .findFirst();
    }
}
