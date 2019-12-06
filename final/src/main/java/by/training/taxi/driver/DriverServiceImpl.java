package by.training.taxi.driver;

import by.training.taxi.bean.Bean;
import by.training.taxi.dao.DAOException;
import lombok.AllArgsConstructor;

@Bean
@AllArgsConstructor
public class DriverServiceImpl implements DriverService  {
    private DriverDao driverDao;

    @Override
    public DriverDto getByUserId (Long id) throws DriverServiceException {
        try{
          return driverDao.getByUserId(id);
        } catch (DAOException e) {
            throw new DriverServiceException("exception while getting driver by user id");
        }
    }

    @Override
    public long save(DriverDto driverDto) throws DriverServiceException {
        try {
            return driverDao.save(driverDto);
        } catch (DAOException e) {
            throw new DriverServiceException("exception while saving a driver");
        }
    }
}
