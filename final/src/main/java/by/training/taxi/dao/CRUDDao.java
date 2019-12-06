package by.training.taxi.dao;

import java.util.List;

public interface CRUDDao <KEY, ENTITY> {
    KEY save(ENTITY entity) throws DAOException;

    boolean update(ENTITY entity) throws DAOException;

    boolean delete(ENTITY entity) throws DAOException;

    ENTITY getById(KEY id) throws DAOException;

    List<ENTITY> findAll() throws DAOException;
}
