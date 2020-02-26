package by.training.taxi.request;

import by.training.taxi.bean.Bean;
import by.training.taxi.dao.ConnectionManager;
import by.training.taxi.dao.DAOException;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j;
import org.omg.CORBA.Request;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Bean
@AllArgsConstructor
@Log4j
public class RequestDaoImpl implements RequestDao {
    private static final String SELECT_ALL_QUERY = "SELECT request_id, client_id, driver_id, request_date," +
            "  pick_location, drop_location, request_price, request_status FROM car_request";
    private static final String SELECT_BY_ID_QUERY = "SELECT request_id, client_id, driver_id, request_date, " +
            " request_price, pick_location, drop_location, request_status FROM car_request where  request_id = ?";
    private static final String INSERT_QUERY = "insert into car_request (client_id, driver_id, request_date, " +
            " pick_location, drop_location, request_price, request_status) values (?,?,?,?,?,?,?::request_status)";
    private static final String UPDATE_QUERY = "update car_request set client_id = ?, driver_id=?, request_date=?, " +
            "pick_location=?, drop_location=?, request_price=?, request_status=?::request_status where request_id = ?";
    private static final String DELETE_QUERY = "delete from car_request where request_id = ?";
    private static final String SELECT_ALL_FOR_CLIENT_QUERY = "SELECT request_id, client_id, driver_id, request_date, " +
            "pick_location, drop_location, request_price, request_status FROM car_request WHERE client_id=?";
    private static final String SELECT_ALL_FOR_DRIVER_QUERY = "SELECT request_id, client_id, driver_id, request_date, " +
            "pick_location, drop_location, request_price, request_status FROM car_request WHERE driver_id=?";

    private ConnectionManager connectionManager;

    @Override
    public Long save(RequestDto requestDto) throws DAOException {
        RequestEntity entity = fromDto(requestDto);
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement insertStmt = connection.prepareStatement(INSERT_QUERY, Statement.RETURN_GENERATED_KEYS)) {
            int i = 0;
            insertStmt.setLong(++i, entity.getClientId());
            insertStmt.setLong(++i, entity.getDriverId());
            insertStmt.setTimestamp(++i, new Timestamp(entity.getRequestDate().getTime()));
            insertStmt.setString(++i, entity.getPickLocation());
            insertStmt.setString(++i, entity.getDropLocation());
            insertStmt.setBigDecimal(++i, entity.getPrice());
            insertStmt.setString(++i, entity.getRequestStatus().getValue());
            insertStmt.executeUpdate();
            try (ResultSet generatedKeys = insertStmt.getGeneratedKeys()) {
                while (generatedKeys.next()) {
                    entity.setId(generatedKeys.getLong(1));
                }
            }
        } catch (SQLException e) {
            log.error("Failed to save the request");
            throw new DAOException(e.getMessage());
        }
        return entity.getId();
    }

    @Override
    public List<RequestDto> getAllByClientId (long id) throws  DAOException{
        List<RequestEntity> result = new ArrayList<>();
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement selectStmt = connection.prepareStatement(SELECT_ALL_FOR_CLIENT_QUERY)) {
            selectStmt.setLong(1, id);
            try (ResultSet resultSet = selectStmt.executeQuery()) {
                while (resultSet.next()) {
                    RequestEntity entity = parseResultSet(resultSet);
                    result.add(entity);
                }
            }
        } catch (SQLException e) {
            log.error("Failed to find all requests");
            throw new DAOException();
        }
        return result.stream().map(this::fromEntity).collect(Collectors.toList());
    }

    @Override
    public List<RequestDto> getAllByDriverId(long id) throws  DAOException {
        List<RequestEntity> result = new ArrayList<>();
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement selectStmt = connection.prepareStatement(SELECT_ALL_FOR_DRIVER_QUERY)) {
            selectStmt.setLong(1, id);
            try (ResultSet resultSet = selectStmt.executeQuery()) {
                while (resultSet.next()) {
                    RequestEntity entity = parseResultSet(resultSet);
                    result.add(entity);
                }
            }
        } catch (SQLException e) {
            log.error("Failed to find all requests");
            throw new DAOException();
        }
        return result.stream().map(this::fromEntity).collect(Collectors.toList());
    }

    @Override
    public boolean update(RequestDto requestDto) throws DAOException {
        RequestEntity entity = fromDto(requestDto);
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement updateStmt = connection.prepareStatement(UPDATE_QUERY)) {
            int i = 0;
            updateStmt.setLong(++i, entity.getClientId());
            updateStmt.setLong(++i, entity.getDriverId());
            updateStmt.setTimestamp(++i, new Timestamp(entity.getRequestDate().getTime()));
            updateStmt.setString(++i, entity.getPickLocation());
            updateStmt.setString(++i, entity.getDropLocation());
            updateStmt.setBigDecimal(++i, entity.getPrice());
            updateStmt.setString(++i, entity.getRequestStatus().getValue());
            updateStmt.setLong(++i, entity.getId());
            return updateStmt.executeUpdate() > 0;
        } catch (SQLException e) {
            log.error("Failed to save the request");
            throw new DAOException(e.getMessage());
        }
    }

    @Override
    public boolean delete(Long id) throws DAOException {
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement updateStmt = connection.prepareStatement(DELETE_QUERY)){
            updateStmt.setLong(1, id);
            return updateStmt.executeUpdate() > 0;
        } catch (SQLException e) {
            log.error("Failed to delete request " + id);
            throw new DAOException();
        }
    }

    @Override
    public RequestDto getById(Long id) throws DAOException {
        List<RequestEntity> result = new ArrayList<>();
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement selectStmt = connection.prepareStatement(SELECT_BY_ID_QUERY)) {
            selectStmt.setLong(1, id);
            try (ResultSet resultSet = selectStmt.executeQuery()) {
                while (resultSet.next()) {
                    RequestEntity entity = parseResultSet(resultSet);
                    result.add(entity);
                }
            }
        } catch (SQLException e) {
            log.error("Failed to get the request by id");
            throw new DAOException("Failed to get the request by id");
        }
        return result.stream().map(this::fromEntity).findFirst()
                .orElseThrow(() -> new DAOException("Entity not found with given id: " + id));
    }

    @Override
    public List<RequestDto> findAll() throws DAOException {
        List<RequestEntity> result = new ArrayList<>();
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement selectStmt = connection.prepareStatement(SELECT_ALL_QUERY)) {
            try (ResultSet resultSet = selectStmt.executeQuery()) {
                while (resultSet.next()) {
                    RequestEntity entity = parseResultSet(resultSet);
                    result.add(entity);
                }
            }
        } catch (SQLException e) {
            log.error("Failed to find all requests");
            throw new DAOException();
        }
        return result.stream().map(this::fromEntity).collect(Collectors.toList());
    }


    private RequestEntity parseResultSet(ResultSet resultSet) throws SQLException {
        long id = resultSet.getLong("request_id");
        long clientId = resultSet.getLong("client_id");
        long driverId = resultSet.getLong("driver_id");
        Date requestDate = resultSet.getTimestamp("request_date");
        String pickLocation = resultSet.getString("pick_location");
        String dropLocation = resultSet.getString("drop_location");
        BigDecimal price = resultSet.getBigDecimal("request_price");
        RequestStatus status = RequestStatus.getFromText(resultSet.getString("request_status")).orElse(null);
        return RequestEntity.builder()
                .id(id)
                .clientId(clientId)
                .driverId(driverId)
                .requestDate(requestDate)
                .pickLocation(pickLocation)
                .dropLocation(dropLocation)
                .price(price)
                .requestStatus(status)
                .build();
    }

    private RequestEntity fromDto(RequestDto dto) {
        RequestEntity entity = new RequestEntity();
        entity.setId(dto.getId());
        entity.setDropLocation(dto.getDropLocation());
        entity.setPickLocation(dto.getPickLocation());
        entity.setDriverId(dto.getDriverId());
        entity.setClientId(dto.getClientId());
        entity.setRequestDate(dto.getRequestDate());
        entity.setPrice(dto.getPrice());
        entity.setRequestStatus(dto.getRequestStatus());
        return entity;
    }

    private RequestDto fromEntity(RequestEntity entity) {
        RequestDto dto = new RequestDto();
        dto.setId(entity.getId());
        dto.setPrice(entity.getPrice());
        dto.setDriverId(entity.getDriverId());
        dto.setClientId(entity.getClientId());
        dto.setDropLocation(entity.getDropLocation());
        dto.setPickLocation(entity.getPickLocation());
        dto.setRequestDate(entity.getRequestDate());
        dto.setRequestStatus(entity.getRequestStatus());
        dto.setPrice(entity.getPrice());
        return dto;
    }
}
