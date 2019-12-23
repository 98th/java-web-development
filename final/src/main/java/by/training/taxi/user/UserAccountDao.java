package by.training.taxi.user;

import by.training.taxi.dao.CRUDDao;
import by.training.taxi.dao.DAOException;

import java.util.List;
import java.util.Optional;

public interface UserAccountDao extends CRUDDao<Long, UserAccountDto> {
    Optional<UserAccountDto> getByLogin(String login) throws DAOException;
    Optional<UserAccountDto> getByLoginAndPassword(String login, String password) throws DAOException;
    List<UserAccountDto> getAllWithContact() throws DAOException;
}
