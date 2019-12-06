package by.training.taxi.driver;

public interface DriverService {
    long save(DriverDto driverDto) throws DriverServiceException;
    DriverDto getByUserId(Long id) throws DriverServiceException;
}
