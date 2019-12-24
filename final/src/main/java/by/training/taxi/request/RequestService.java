package by.training.taxi.request;

import java.util.List;

public interface RequestService {
    boolean delete(RequestDto requestDto) throws RequestServiceException;
    List<RequestDto> getAllForClient(long id) throws RequestServiceException;
    long registerRequest(RequestDto requestDto) throws  RequestServiceException;
    boolean update(RequestDto requestDto) throws RequestServiceException;
}
