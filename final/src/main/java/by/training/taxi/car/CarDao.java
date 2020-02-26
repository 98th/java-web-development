package by.training.taxi.car;

import by.training.taxi.dao.CRUDDao;
import by.training.taxi.dao.DAOException;

import java.util.List;
import java.util.Set;

public interface CarDao extends CRUDDao<Long, CarDto> {
   List<CarDto> getCarsWithRequirement(String requirement) throws DAOException;
   boolean addRequirementToCar(long id, Set<RequirementType> requirementTypes);
   List<CarDto> getAllFree() throws DAOException;
}
