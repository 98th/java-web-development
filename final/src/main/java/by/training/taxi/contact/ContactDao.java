package by.training.taxi.contact;

import by.training.taxi.dao.CRUDDao;
import by.training.taxi.dao.DAOException;

import java.util.Optional;

public interface ContactDao extends CRUDDao<Long, ContactDto> {
    ContactDto getByUserId(Long id) throws DAOException;
    ContactDto getByEmail(String email) throws DAOException;
}
