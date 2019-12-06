package by.training.taxi.feedback;

import by.training.taxi.dao.CRUDDao;
import by.training.taxi.dao.DAOException;

import java.util.Optional;

public interface FeedbackDao extends CRUDDao<Long, FeedbackDto> {
    Optional<FeedbackDto> getByRequestId(Long requestId)  throws DAOException;
}
