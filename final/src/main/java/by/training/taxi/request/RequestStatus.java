package by.training.taxi.request;

import java.util.Arrays;
import java.util.Optional;

public enum RequestStatus {
    OFFERED("offered"),
    RECEIVED("received"),
    ACCEPTED("accepted"),
    RESOLVED("resolved"),
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
