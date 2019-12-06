package by.training.taxi.car;

import by.training.taxi.dao.CRUDDao;
import by.training.taxi.dao.DAOException;

import java.util.List;
import java.util.Set;

public interface CarDao extends CRUDDao<Long, CarDto> {
    CarDto getByDriverId(long driverId) throws DAOException;
    CarDto getByUserId(long userId) throws DAOException;
   // List<CarDto> getCarsByRequirement();
    boolean addRequirementForCar(long id, Set<RequirementType> requirementTypes);
}
