package by.training.taxi.validator;

import by.training.taxi.bean.Bean;

import javax.servlet.http.HttpServletRequest;

import static by.training.taxi.ApplicationConstants.*;
import static by.training.taxi.util.ValidatorUtil.isStrEmpty;

@Bean(name=CAR_VALIDATOR)
public class CarValidator implements Validator {
    private static final String LICENCE_PLATE_NUMBER_REGEX = "([0-9]{4})(\\s?)([a-zA-Z]{2}-[1-9]{1})";

    @Override
    public ValidationResult validate(HttpServletRequest req) {
        ValidationResult validationResult = new ValidationResult();

        String licencePlateNum = req.getParameter(PARAM_CAR_LICENCE_PLATE_NUM);
        String carColor = req.getParameter(PARAM_CAR_COLOR);
        String carModel = req.getParameter(PARAM_CAR_MODEL);

        if(isStrEmpty(carModel) || isStrEmpty(carColor) || isStrEmpty(licencePlateNum)) {
            validationResult.addMessage(PARAM_ERROR, "error.empty.fields");
        }

        if (!licencePlateNum.matches(LICENCE_PLATE_NUMBER_REGEX)) {
            validationResult.addMessage(PARAM_ERROR, "error.invalid.licence.plate.num");
        }
        return validationResult;
    }
}
