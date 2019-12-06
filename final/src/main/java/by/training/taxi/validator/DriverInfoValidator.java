package by.training.taxi.validator;

import javax.servlet.http.HttpServletRequest;

import static by.training.taxi.ApplicationConstants.PARAM_CAR_LICENCE_PLATE_NUM;
import static by.training.taxi.ApplicationConstants.PARAM_DRIVER_LICENCE_NUM;

public class DriverInfoValidator {
    private static final String LICENCE_PLATE_NUMBER_REGEX = "([0-9]{4})(\\s?)([a-zA-Z]{2}-[1-9]{1})";
    private static final String DRIVING_LICENCE_NUM_REGEX = "([1-9]{1})([a-zA-Z]{2})(\\s?)([0-9]{6})";


    public ValidationResult validate(HttpServletRequest request) {
        ValidationResult validationResult = new ValidationResult();

        String drivingLicenceNum = request.getParameter(PARAM_DRIVER_LICENCE_NUM);
        String licencePlateNum = request.getParameter(PARAM_CAR_LICENCE_PLATE_NUM);
        String color = request.getParameter("carColor");
        String model = request.getParameter("carModel");

        if (drivingLicenceNum.isEmpty() || licencePlateNum.isEmpty() || model.isEmpty() || color.isEmpty()) {
            validationResult.addMessage("invalid data", "fields cannot be empty");
        }

        if (!licencePlateNum.matches(LICENCE_PLATE_NUMBER_REGEX)) {
            validationResult.addMessage(PARAM_CAR_LICENCE_PLATE_NUM, "Incorrect licence plate number");
        }

        if (!drivingLicenceNum.matches(DRIVING_LICENCE_NUM_REGEX)){
            validationResult.addMessage(PARAM_DRIVER_LICENCE_NUM, "Incorrect driving licence number");
        }

        return validationResult;
    }
}
