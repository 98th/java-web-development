package by.training.taxi.feedback;

import by.training.taxi.bean.Bean;
import by.training.taxi.dao.ConnectionManager;
import by.training.taxi.dao.DAOException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

@Bean
public class FeedbackDaoImpl implements FeedbackDao {

    private final static AtomicLong COUNTER = new AtomicLong(1);

    private static final String SELECT_ALL_QUERY = "SELECT feedback_id, email, first_name, last_name, user_id FROM feedback";
    private static final String SELECT_BY_ID_QUERY = "SELECT icontact_id, email, first_name, last_name, user_id FROM feedback WHERE id = ?";
    private static final String SELECT_BY_REQUEST_ID_QUERY = "SELECT  feedback_id, email, first_name, last_name, user_id  FROM feedback WHERE request_is = ?";
    private static final String SELECT_BY_EMAIL_QUERY = "SELECT id, first_name, last_name, email, phone FROM feedback WHERE email = ?";
    private static final String INSERT_QUERY = "INSERT INTO feedback (email, phone, first_name, last_name, user_id) values (?,?,?,?,?)";
    private static final String UPDATE_QUERY = "UPDATE feedback SET email=?, phone=?, first_name=?, last_name=? user_id=? WHERE id = ?";
    private static final String DELETE_QUERY = "DELETE FROM feedback WHERE id = ?";

    private ConnectionManager connectionManager;


    @Override
    public Long save(FeedbackDto feedbackDto) throws DAOException {
        FeedbackEntity entity = fromDto(feedbackDto);
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement insertStmt = connection.prepareStatement(INSERT_QUERY, Statement.RETURN_GENERATED_KEYS)) {
            int i = 0;
            insertStmt.setLong(++i, COUNTER.getAndIncrement());
            insertStmt.setString(++i, entity.getDriverMessage());
            insertStmt.setString(++i, entity.getUserMessage());
            insertStmt.setByte(++i, entity.getUserRating());
            insertStmt.setByte(++i, entity.getDriverRating());
            insertStmt.setLong(++i, entity.getRequestId());
            insertStmt.setLong(++i, entity.getId());
            insertStmt.executeUpdate();
            ResultSet generatedKeys = insertStmt.getGeneratedKeys();
            while (generatedKeys.next()) {
                entity.setId(generatedKeys.getLong(1));
            }

        } catch (SQLException e) { }
        return entity.getId();
    }

    @Override
    public boolean update(FeedbackDto feedbackDto) throws DAOException {
        FeedbackEntity entity = fromDto(feedbackDto);
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement updateStmt = connection.prepareStatement(UPDATE_QUERY)){
            int i = 0;
            updateStmt.setString(++i, entity.getUserMessage());
            updateStmt.setString(++i, entity.getDriverMessage());
            updateStmt.setLong(++i, entity.getId());
            updateStmt.setLong(++i, entity.getRequestId());
            updateStmt.setByte(++i, entity.getUserRating());
            updateStmt.setByte(++i, entity.getDriverRating());
            return updateStmt.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DAOException();
        }
    }

    @Override
    public boolean delete(FeedbackDto feedbackDto) throws DAOException {
        FeedbackEntity entity = fromDto(feedbackDto);
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement updateStmt = connection.prepareStatement(DELETE_QUERY)){
            updateStmt.setLong(1, entity.getId());
            return updateStmt.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DAOException();
        }
    }

    @Override
    public FeedbackDto getById(Long id) throws DAOException {
        List<FeedbackEntity> result = new ArrayList<>();
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement selectStmt = connection.prepareStatement(SELECT_BY_ID_QUERY)) {
            selectStmt.setLong(1, id);
            ResultSet resultSet = selectStmt.executeQuery();
            while (resultSet.next()) {
                FeedbackEntity entity = parseResultSet(resultSet);
                result.add(entity);
            }

        } catch (SQLException e) {
            throw new DAOException();
        }
        return result.stream().map(this::fromEntity).findFirst().orElseThrow(() -> new IllegalArgumentException("Entity not found with given id: " + id));
    }

    @Override
    public List<FeedbackDto> findAll() throws DAOException {
        List<FeedbackEntity> result = new ArrayList<>();
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement selectStmt = connection.prepareStatement(SELECT_BY_REQUEST_ID_QUERY)) {
            ResultSet resultSet = selectStmt.executeQuery();
            while (resultSet.next()) {
                FeedbackEntity entity = parseResultSet(resultSet);
                result.add(entity);
            }
        }catch (SQLException e) {
            throw new DAOException();
        }
        return result.stream().map(this::fromEntity).collect(Collectors.toList());
    }

    @Override
    public Optional<FeedbackDto> getByRequestId(Long requestId) throws DAOException {
        List<FeedbackEntity> result = new ArrayList<>();
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement selectStmt = connection.prepareStatement(SELECT_BY_EMAIL_QUERY)) {
            int i = 0;
            selectStmt.setLong(++i, requestId);
            ResultSet resultSet = selectStmt.executeQuery();
            while (resultSet.next()) {
                FeedbackEntity entity = parseResultSet(resultSet);
                result.add(entity);
            }
        } catch (SQLException e) {
            throw new DAOException("Exception in user dao");
        }

        return result.stream().map(this::fromEntity).findFirst();
    }

    private FeedbackEntity parseResultSet(ResultSet resultSet) throws SQLException {
        long entityId = resultSet.getLong("id");
        long requestId = resultSet.getLong("requestId");
        byte driverRating = resultSet.getByte("driverRating");
        byte userRating = resultSet.getByte("userRating");
        String driverMessage = resultSet.getString("driverMessage");
        String userMessage = resultSet.getString("userMessage");

        return FeedbackEntity.builder()
                .id(entityId)
                .requestId(requestId)
                .driverRating(driverRating)
                .userRating(userRating)
                .driverMessage(driverMessage)
                .driverMessage(driverMessage)
                .userMessage(userMessage)
                .build();
    }

    private FeedbackEntity fromDto(FeedbackDto dto) {
        FeedbackEntity entity = new FeedbackEntity();
        entity.setId(dto.getId());
        entity.setRequestId(dto.getRequestId());
        entity.setDriverMessage(dto.getDriverMessage());
        entity.setDriverRating(dto.getDriverRating());
        entity.setUserRating(dto.getUserRating());
        entity.setUserMessage(dto.getUserMessage());
        return entity;
    }

    private FeedbackDto fromEntity(FeedbackEntity entity) {
        FeedbackDto dto = new FeedbackDto();
        dto.setId(entity.getId());
        dto.setDriverMessage(entity.getDriverMessage());
        dto.setUserMessage(entity.getUserMessage());
        dto.setUserRating(entity.getUserRating());
        dto.setRequestId(entity.getRequestId());
        dto.setDriverRating(entity.getDriverRating());
        return dto;
    }
}
