package by.training.taxi.request;

import java.util.List;

public interface RequestService {
    boolean delete(Long id) throws RequestServiceException;
    List<RequestDto> getAllByClientId(long id) throws RequestServiceException;
    long registerRequest(RequestDto requestDto) throws  RequestServiceException;
    boolean update(RequestDto requestDto) throws RequestServiceException;
    RequestDto getById(long id) throws RequestServiceException;
    List<RequestDto> getAllByDriverId(long id) throws RequestServiceException;
}
