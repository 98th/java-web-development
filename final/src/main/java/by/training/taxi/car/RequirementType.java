package by.training.taxi.car;

import java.util.Arrays;
import java.util.Optional;

public enum RequirementType {
    PET_FRIENDLY("pet_friendly"),
    BOOSTER("booster"),
    BABY_SEAT("baby_seat");

    private String value;

    RequirementType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static Optional<RequirementType> getRequirementFromText(String role) {
        return Arrays.stream(values())
                .filter(i -> i.value.equalsIgnoreCase(role))
                .findFirst();
    }
}
