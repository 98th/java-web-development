package by.training.taxi.driver;

import java.util.List;

public interface DriverService {
    long save(DriverDto driverDto) throws DriverServiceException;
    DriverDto getByUserId(Long id) throws DriverServiceException;
    boolean registerDriver(DriverDto driverDto);
    DriverDto getById(Long id) throws DriverServiceException;
    DriverDto getWithInfo(Long id) throws DriverServiceException;
    List<DriverDto> findAll() throws DriverServiceException;
    List<DriverDto> findAllWithInfo()  throws DriverServiceException;
    boolean updateWithInfo(DriverDto driverDto)  throws DriverServiceException;

}
