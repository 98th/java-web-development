package by.training.taxi.user;

import by.training.taxi.dao.DAOException;

public class UserDaoException extends DAOException {
    public UserDaoException() {
    }

    public UserDaoException(String message) {
        super(message);
    }

    public UserDaoException(String message, Throwable cause) {
        super(message, cause);
    }

    public UserDaoException(Throwable cause) {
        super(cause);
    }
}
