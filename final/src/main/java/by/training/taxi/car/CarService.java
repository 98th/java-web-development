package by.training.taxi.car;


import by.training.taxi.dao.DAOException;

public interface CarService {
    long create(CarDto carDto) throws CarServiceException;
    CarDto getById(long id) throws CarServiceException;
    CarDto getByDriverId(long driverId) throws CarServiceException;
    CarDto getByUserId(long userId) throws CarServiceException;
}
