package by.training.taxi.request;

import by.training.taxi.bean.Bean;
import by.training.taxi.dao.DAOException;
import lombok.AllArgsConstructor;

import java.util.List;

@Bean
@AllArgsConstructor
public class RequestServiceImpl implements RequestService {
    private RequestDao requestDao;

    @Override
    public boolean update(RequestDto requestDto) throws RequestServiceException {
        try {
            return requestDao.update(requestDto);
        } catch (DAOException e) {
            return false;
        }
    }

    @Override
    public long registerRequest(RequestDto requestDto) throws RequestServiceException {
        try {
            return requestDao.save(requestDto);
        } catch (DAOException e) {
            throw  new RequestServiceException(e.getMessage());
        }
    }

    @Override
    public boolean delete(RequestDto requestDto) throws RequestServiceException {
        try {
            return requestDao.delete(requestDto);
        } catch (DAOException e) {
            throw  new RequestServiceException(e.getMessage());
        }
    }

    @Override
    public List<RequestDto> getAllForClient(long id) throws RequestServiceException {
        try {
            return requestDao.getAllForClient(id);
        } catch (DAOException e) {
            throw new RequestServiceException();
        }
    }
}
