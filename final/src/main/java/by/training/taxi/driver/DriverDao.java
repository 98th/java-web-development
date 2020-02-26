package by.training.taxi.driver;

import by.training.taxi.dao.CRUDDao;
import by.training.taxi.dao.DAOException;
import by.training.taxi.request.RequestDto;
import by.training.taxi.request.RequestStatus;

import java.util.List;
import java.util.Optional;

public interface DriverDao extends CRUDDao<Long, DriverDto> {
    DriverDto getByUserId(Long id) throws DAOException;
    DriverDto getWithInfo(Long id) throws DAOException;
    List<DriverDto> findAll() throws DAOException;
    List<DriverDto> findAllWithInfo() throws DAOException;
   /* boolean acceptRequest(RequestDto requestDto);
    boolean declineRequest(RequestDto requestDto);
    RequestDto getSelectedRequest();
    boolean setStatus(final RequestStatus status);
    RequestStatus getStatus();

    boolean startWaiting();
    int endWaiting();
    boolean leaveWaiting();
    boolean getPayment();
    boolean closeOrder(); */
}
