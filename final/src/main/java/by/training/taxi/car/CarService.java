package by.training.taxi.car;



import java.util.List;

public interface CarService {
    boolean save(CarDto carDto) throws CarServiceException;
    CarDto getById(long id) throws CarServiceException;
    List<CarDto> getCarsWithRequirement(String requirement) throws CarServiceException;
    List<CarDto> getAllFree() throws CarServiceException;
}
