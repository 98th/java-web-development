package by.training.taxi.discount;

public interface DiscountService {
    DiscountDto getById(Long id) throws DiscountServiceException ;
    boolean update (DiscountDto discountDto) throws  DiscountServiceException;
}
