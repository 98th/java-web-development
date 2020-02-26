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
    public RequestDto getById(long id) throws RequestServiceException {
        try {
            return requestDao.getById(id);
        } catch (DAOException e) {
            throw  new RequestServiceException(e.getMessage());
        }
    }

    @Override
    public boolean update(RequestDto requestDto) {
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
    public boolean delete(Long id) throws RequestServiceException {
        try {
            return requestDao.delete(id);
        } catch (DAOException e) {
            throw  new RequestServiceException(e.getMessage());
        }
    }

    @Override
    public List<RequestDto> getAllByClientId(long id) throws RequestServiceException {
        try {
            return requestDao.getAllByClientId(id);
        } catch (DAOException e) {
            throw new RequestServiceException();
        }
    }
    @Override
    public List<RequestDto> getAllByDriverId(long id) throws RequestServiceException {
        try {
            return requestDao.getAllByDriverId(id);
        } catch (DAOException e) {
            throw new RequestServiceException();
        }
    }
}
