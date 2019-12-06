package by.training.taxi.car;

import by.training.taxi.bean.Bean;
import by.training.taxi.dao.DAOException;
import by.training.taxi.user.ServiceException;
import lombok.AllArgsConstructor;

@Bean
@AllArgsConstructor
public class CarServiceImpl implements CarService {
    private CarDao carDao;

    @Override
    public long create(CarDto carDto) throws  CarServiceException{
        try {
            return carDao.save(carDto);
        } catch (DAOException e) {
            throw new CarServiceException("Exception while creating a car");
        }
    }

    @Override
    public CarDto getById(long id) throws CarServiceException{
        try {
            return carDao.getById(id);
        } catch (DAOException e) {
            throw new CarServiceException();
        }
    }


    @Override
    public CarDto getByUserId(long id) throws CarServiceException{
        try {
            return carDao.getByUserId(id);
        } catch (DAOException e) {
            throw new CarServiceException();
        }
    }

    @Override
    public CarDto getByDriverId(long id) throws CarServiceException{
        try {
            return carDao.getByDriverId(id);
        } catch (DAOException e) {
            throw new CarServiceException();
        }
    }
}
