package by.training.taxi.request;

import by.training.taxi.dao.CRUDDao;
import by.training.taxi.dao.DAOException;

import java.util.List;

public interface RequestDao extends CRUDDao<Long, RequestDto> {
    List<RequestDto> getAllForClient (long id) throws DAOException;
}
