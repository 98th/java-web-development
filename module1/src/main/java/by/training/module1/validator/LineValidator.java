package by.training.module1.validator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LineValidator {

    private final Pattern militaryAircraftPattern = Pattern.compile("type:military\\|model:[A-Z]{1}\\d{3}\\|passengers:\\d+\\|" +
            "fuelConsumption:\\d+\\|loadCapacity:\\d+(.\\d+)?\\|speed:\\d+\\|missileNumber:\\d+");
    private final Pattern medicalAircraftPattern = Pattern.compile("type:medical\\|model:[A-Z]{1}\\d{3}\\|passengers:\\d+\\|" +
            "fuelConsumption:\\d+\\|loadCapacity:\\d+(.\\d+)?\\|speed:\\d+\\|resuscitationEquipment:(true|false)\\|firstAidKits:\\d+");

    public ValidationResult validateLine(String str) {
        ValidationResult validationResult = new ValidationResult();
        Pattern pattern;
        if (str.matches("type:military.*")) {
            pattern = militaryAircraftPattern;
        } else if (str.matches("type:medical.*")) {
            pattern = medicalAircraftPattern;
        } else {
            validationResult.addMessage("data","incorrect data");
            return validationResult;
        }
        Matcher m = pattern.matcher(str);

        if (m.matches()) {
            return validationResult;
        } else {
            validationResult.addMessage("data","invalid data");
            return validationResult;
        }
    }
}
