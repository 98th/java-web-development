package by.training.taxi.discount;

import by.training.taxi.bean.Bean;
import by.training.taxi.dao.DAOException;
import lombok.AllArgsConstructor;

@Bean
@AllArgsConstructor
public class DiscountServiceImpl implements DiscountService {
    private DiscountDao discountDao;


    @Override
    public boolean update(DiscountDto discount) throws  DiscountServiceException{
        try {
            return discountDao.update(discount);
        } catch (DAOException e) {
            throw  new DiscountServiceException();
        }
    }

    @Override
    public DiscountDto getById(Long id) throws DiscountServiceException {
        try {
            return discountDao.getById(id);
        } catch (DAOException e) {
            throw  new DiscountServiceException(e.getMessage());
        }
    }
}
