package by.training.taxi.validator;

import by.training.taxi.util.ValidatorUtil;

import javax.servlet.http.HttpServletRequest;

import static by.training.taxi.ApplicationConstants.PARAM_DISCOUNT;

public class DiscountValidator implements Validator{

    @Override
    public ValidationResult validate(HttpServletRequest req) {
        ValidationResult validationResult = new ValidationResult();
        String discount = req.getParameter("discountAmount");
        if (ValidatorUtil.isStrEmpty(discount) && !checkDiscount(discount)) {
            validationResult.addMessage(PARAM_DISCOUNT, "error.incorrect_discount");
        }
        return validationResult;
    }

    private boolean checkDiscount(String discount) {
        try {
            double output = Double.parseDouble(discount);
            if (output >= 0 && output < 100) {
                return true;
            } else {
                return false;
            }
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
