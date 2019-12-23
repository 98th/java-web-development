package by.training.taxi.car;

import by.training.taxi.bean.Bean;
import by.training.taxi.dao.DAOException;
import by.training.taxi.driver.DriverServiceException;
import lombok.AllArgsConstructor;

import java.util.List;

@Bean
@AllArgsConstructor
public class CarServiceImpl implements CarService {
    private CarDao carDao;


    @Override
    public List<CarDto> getCarsWithRequirement(String requirementId) throws CarServiceException {
        try {
            return carDao.getCarsWithRequirement(requirementId);
        } catch (DAOException e) {throw  new CarServiceException(e.getMessage());}
    }

    @Override
    public boolean save(CarDto carDto) {
        try {
            Long id = carDao.save(carDto);
            return true;
        } catch (DAOException e) {
            return false;
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
