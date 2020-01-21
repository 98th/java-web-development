package by.training.taxi.discount;

import by.training.taxi.bean.Bean;
import by.training.taxi.command.Command;
import by.training.taxi.command.CommandException;
import by.training.taxi.user.UserAccountDto;
import by.training.taxi.user.UserAccountService;
import by.training.taxi.user.UserServiceException;
import by.training.taxi.util.RequestUtil;
import by.training.taxi.validator.DiscountValidator;
import by.training.taxi.validator.ValidationResult;
import by.training.taxi.validator.Validator;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static by.training.taxi.ApplicationConstants.GET_USER_LIST_VIEW;
import static by.training.taxi.ApplicationConstants.POST_ASSIGN_DISCOUNT;

@Bean(name=POST_ASSIGN_DISCOUNT)
@AllArgsConstructor
@Log4j
public class AssignDiscountCommand implements Command {
    private DiscountService discountService;
    private DiscountValidator discountValidator;

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        try {
            ValidationResult validationResult = discountValidator.validate(request);
            if (validationResult.isValid()) {
                Long id = Long.parseLong(request.getParameter("userId"));
                double discount = Double.parseDouble(request.getParameter("discountAmount"));
                DiscountDto discountDto = discountService.getById(id);
                discountDto.setAmount(discount);
                discountService.update(discountDto);
                log.info("discount " + discount + " was assigned to the user " + id);
            }
            RequestUtil.sendRedirectToCommand(request, response, GET_USER_LIST_VIEW);
        } catch (DiscountServiceException e) {
            log.error("Failed to assign a discount to the user");
            throw new CommandException();
        }
    }
}
