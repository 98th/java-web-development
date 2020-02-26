package by.training.taxi.validator;

import by.training.taxi.bean.Bean;

import javax.servlet.http.HttpServletRequest;

import static by.training.taxi.ApplicationConstants.*;
import static by.training.taxi.ApplicationConstants.PARAM_DRIVER_LICENCE_NUM;

import static by.training.taxi.util.ValidatorUtil.isStrEmpty;


@Bean(name=DRIVER_VALIDATOR)
public class DriverValidator implements Validator {
    private static final String DRIVING_LICENCE_NUM_REGEX = "([1-9]{1})([a-zA-Z]{2})(\\s?)([0-9]{6})";
    private static final String LICENCE_PLATE_NUMBER_REGEX = "([0-9]{4})(\\s?)([a-zA-Z]{2}-[1-9]{1})";


    @Override
    public ValidationResult validate(HttpServletRequest request) {
        ValidationResult validationResult = new ValidationResult();

        String drivingLicenceNum = request.getParameter(PARAM_DRIVER_LICENCE_NUM);
        String licencePlateNum = request.getParameter(PARAM_CAR_LICENCE_PLATE_NUM);

        if (isStrEmpty(licencePlateNum) || !licencePlateNum.matches(LICENCE_PLATE_NUMBER_REGEX)) {
            validationResult.addMessage(PARAM_ERROR, "error.invalid.licence.plate.num");
        }

        if (isStrEmpty(drivingLicenceNum) || !drivingLicenceNum.matches(DRIVING_LICENCE_NUM_REGEX)){
            validationResult.addMessage(PARAM_DRIVER_LICENCE_NUM, "");
        }

        return validationResult;
    }


}
