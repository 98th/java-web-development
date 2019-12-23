package by.training.taxi.car;



import java.util.List;

public interface CarService {
    boolean save(CarDto carDto) throws CarServiceException;
    CarDto getById(long id) throws CarServiceException;
    CarDto getByDriverId(long driverId) throws CarServiceException;
    CarDto getByUserId(long userId) throws CarServiceException;
    List<CarDto> getCarsWithRequirement(String requirement) throws CarServiceException;
}
