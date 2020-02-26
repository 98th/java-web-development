package by.training.taxi.driver;

import by.training.taxi.dao.CRUDDao;
import by.training.taxi.dao.DAOException;
import by.training.taxi.request.RequestDto;
import by.training.taxi.request.RequestStatus;

import java.util.List;
import java.util.Optional;

public interface DriverDao extends CRUDDao<Long, DriverDto> {
    DriverDto getByUserId(Long id) throws DAOException;
    DriverDto getWithInfo(Long id) throws DAOException;
    List<DriverDto> findAll() throws DAOException;
    List<DriverDto> findAllWithInfo() throws DAOException;
    DriverDto getByCarId(Long id) throws DAOException;
}
