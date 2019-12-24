package by.training.taxi.role;

import java.util.Arrays;
import java.util.Optional;

public enum Role {
    ADMIN("admin"), CLIENT("client"), DRIVER("driver"), GUEST("guest");
    private String value;
    Role(String value){
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static Optional<Role> getRoleFromText(String role) {
        return Arrays.stream(values())
                .filter(i -> i.value.equalsIgnoreCase(role))
                .findFirst();
    }
}
