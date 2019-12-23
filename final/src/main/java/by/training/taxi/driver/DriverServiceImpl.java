package by.training.taxi.driver;

import by.training.taxi.bean.Bean;
import by.training.taxi.car.CarDao;
import by.training.taxi.contact.ContactDao;
import by.training.taxi.dao.DAOException;
import by.training.taxi.dao.Transactional;
import lombok.AllArgsConstructor;

import java.util.List;

@Bean
@AllArgsConstructor
public class DriverServiceImpl implements DriverService  {
    private DriverDao driverDao;
    private ContactDao contactDao;
    private CarDao carDao;

    @Override
    public boolean updateWithInfo(DriverDto driverDto) throws DriverServiceException {
        try {
            driverDao.update(driverDto);
            contactDao.update(driverDto.getContact());
            carDao.update(driverDto.getCar());
            return true;
        } catch (DAOException e) {
            throw  new DriverServiceException();
        }
    }

    @Override
    public  DriverDto getWithInfo(Long id) throws DriverServiceException {
        try {
            return  driverDao.getWithInfo(id);
        } catch (DAOException e) {
            throw new DriverServiceException(e.getMessage());
        }
    }

    @Override
    public List<DriverDto> findAll() throws DriverServiceException {
        try {
            return driverDao.findAll();
        } catch (DAOException e) {
            throw new DriverServiceException();
        }
    }

    @Override
    public DriverDto getById(Long id) throws DriverServiceException {
        try {
            return driverDao.getById(id);
        } catch (DAOException e) {
            throw new DriverServiceException("exception while getting driver by id");
        }
    }

    @Override
    @Transactional
    public boolean registerDriver(DriverDto driverDto) {
        try {
            driverDao.save(driverDto);
            return true;
        } catch (DAOException e) {
            return false;
        }
    }

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

    @Override
    public List<DriverDto> findAllWithInfo() throws DriverServiceException {
        try {
            return driverDao.findAllWithInfo();
        } catch (DAOException e) {
            throw new DriverServiceException(e.getMessage());
        }
    }
}
