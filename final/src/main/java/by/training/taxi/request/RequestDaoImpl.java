package by.training.taxi.request;

import by.training.taxi.bean.Bean;
import by.training.taxi.dao.DAOException;

import java.util.List;

@Bean
public class RequestDaoImpl implements RequestDao {
    @Override
    public Long save(RequestDto requestDto) throws DAOException {
        return null;
    }

    @Override
    public boolean update(RequestDto requestDto) throws DAOException {
        return false;
    }

    @Override
    public boolean delete(RequestDto requestDto) throws DAOException {
        return false;
    }

    @Override
    public RequestDto getById(Long id) throws DAOException {
        return null;
    }

    @Override
    public List<RequestDto> findAll() throws DAOException {
        return null;
    }
}
