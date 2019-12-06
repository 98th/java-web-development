package by.training.taxi.driver;

import by.training.taxi.bean.Bean;
import by.training.taxi.dao.ConnectionManager;
import by.training.taxi.dao.DAOException;
import lombok.AllArgsConstructor;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

@Bean
@AllArgsConstructor
public class DriverDaoImpl implements DriverDao {

    private final static AtomicLong COUNTER = new AtomicLong(1);

    private static final String SELECT_ALL_QUERY = "SELECT driver_id, driving_licence_number, expiry_date," +
            " user_account_id FROM driver";
    private static final String SELECT_BY_ID_QUERY = "SELECT driver_id, driving_licence_number, expiry_date," +
            "  user_account_id FROM driver WHERE driver_id = ?";
    private static final String INSERT_QUERY = "INSERT INTO driver (driving_licence_number," +
            " expiry_date, user_account_id) values (?,?,?,?)";
    private static final String UPDATE_QUERY =  "UPDATE driver " +
                                                "SET  driving_licence_number=?, expiry_date=?, user_account_id=?" +
                                                "WHERE driver_id = ?";
    private static final String DELETE_QUERY = "DELETE FROM driver WHERE driver_id = ?";
    private static final String SELECT_BY_USER_ID_QUERY = "SELECT driver_id, driving_licence_number, expiry_date," +
            "  user_account_id FROM driver WHERE user_account_id = ?";

    private ConnectionManager connectionManager;

    @Override
    public DriverDto getByUserId(Long id) throws DAOException {
        List<DriverEntity> result = new ArrayList<>();
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement selectStmt = connection.prepareStatement(SELECT_BY_USER_ID_QUERY)) {
            selectStmt.setLong(1, id);
            ResultSet resultSet = selectStmt.executeQuery();
            while (resultSet.next()) {
                DriverEntity entity = parseResultSet(resultSet);
                result.add(entity);
            }
        }   catch (SQLException e) {
            throw new DAOException("Exception in user dao");
        }
        Optional<DriverDto> output = result.stream().map(this::fromEntity).findFirst();
        if(!output.isPresent()){
            throw new DAOException();
        }
        return output.get();
    }


    @Override
    public Long save(DriverDto userDto) throws DAOException {
        DriverEntity entity = fromDto(userDto);
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement insertStmt = connection.prepareStatement(INSERT_QUERY, Statement.RETURN_GENERATED_KEYS)) {
            int i = 0;
            insertStmt.setString(++i, entity.getDrivingLicenceNum());
            insertStmt.setDate(++i, entity.getExpiryDate());
            insertStmt.setLong(++i, entity.getUserId());
            insertStmt.executeUpdate();
            ResultSet generatedKeys = insertStmt.getGeneratedKeys();
            while (generatedKeys.next()) {
                entity.setId(generatedKeys.getLong(1));
            }

        } catch (SQLException e) {
            throw new DAOException();
        }
        return entity.getId();
    }

    @Override
    public boolean update(DriverDto userDto) throws DAOException {
        DriverEntity entity = fromDto(userDto);
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement updateStmt = connection.prepareStatement(UPDATE_QUERY)){
            int i = 0;
            updateStmt.setString(++i, entity.getDrivingLicenceNum());
            updateStmt.setDate(++i, entity.getExpiryDate());
            updateStmt.setLong(++i, entity.getUserId());
            return updateStmt.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DAOException();
        }
    }

    @Override
    public boolean delete(DriverDto userDto) throws DAOException {
        DriverEntity entity = fromDto(userDto);
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement updateStmt = connection.prepareStatement(DELETE_QUERY)){
            updateStmt.setLong(1, entity.getId());
            return updateStmt.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DAOException();
        }
    }


    @Override
    public List<DriverDto> findAll() throws DAOException {
        List<DriverEntity> result = new ArrayList<>();
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement selectStmt = connection.prepareStatement(SELECT_ALL_QUERY)) {
            ResultSet resultSet = selectStmt.executeQuery();
            while (resultSet.next()) {
                DriverEntity entity = parseResultSet(resultSet);
                result.add(entity);
            }
        }catch (SQLException e) {
            throw new DAOException();
        }
        return result.stream().map(this::fromEntity).collect(Collectors.toList());
    }


    @Override
    public DriverDto getById(Long id) throws DAOException {
        List<DriverEntity> result = new ArrayList<>();
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement selectStmt = connection.prepareStatement(SELECT_BY_ID_QUERY)) {
            selectStmt.setLong(1, id);
            ResultSet resultSet = selectStmt.executeQuery();
            while (resultSet.next()) {
                DriverEntity entity = parseResultSet(resultSet);
                result.add(entity);
            }
        }   catch (SQLException e) {
            throw new DAOException("Exception in user dao");
        }
        Optional<DriverDto> output = result.stream().map(this::fromEntity).findFirst();
        if(!output.isPresent()){
            throw new DAOException();
        }
        return output.get();
    }

    private DriverEntity parseResultSet(ResultSet resultSet) throws SQLException {
        long entityId = resultSet.getLong("driver_id");
        String drivingLicenceNum = resultSet.getString("driving_licence_number");
        Date expiryDate = resultSet.getDate("expiry_date");
        long userId = resultSet.getLong("user_account_id");
        return DriverEntity.builder()
                .id(entityId)
                .drivingLicenceNum(drivingLicenceNum)
                .expiryDate(expiryDate)
                .userId(userId)
                .build();
    }

    private DriverEntity fromDto(DriverDto dto) {
        DriverEntity entity = new DriverEntity();
        entity.setId(dto.getId());
        entity.setDrivingLicenceNum(dto.getDrivingLicenceNum());
        entity.setUserId(dto.getUserId());
        entity.setExpiryDate(dto.getExpiryDate());
        return entity;
    }

    private DriverDto fromEntity(DriverEntity entity) {
        DriverDto dto = new DriverDto();
        dto.setId(entity.getId());
        dto.setDrivingLicenceNum(entity.getDrivingLicenceNum());
        dto.setExpiryDate(entity.getExpiryDate());
        dto.setUserId(entity.getUserId());
        return dto;
    }
}
