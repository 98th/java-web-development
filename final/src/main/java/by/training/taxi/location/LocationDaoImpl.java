package by.training.taxi.location;

import by.training.taxi.bean.Bean;
import by.training.taxi.dao.ConnectionManager;
import by.training.taxi.dao.DAOException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Bean
public class LocationDaoImpl implements LocationDao {

    private static final String SELECT_ALL_QUERY = "select location_id, latitude, longitude from location";
    private static final String INSERT_QUERY = "insert into location (location_id, latitude, longitude) values (?,?,?)";
    private static final String UPDATE_QUERY = "update location set latitude=?, longitude=? where location_id = ?";
    private static final String DELETE_QUERY = "delete from location where location_id = ?";
    private static final String SELECT_BY_ID_QUERY = "select location_id, latitude, longitude from location where location_id = ?";


    private ConnectionManager connectionManager;

    public LocationDaoImpl(ConnectionManager connectionManager) {
        this.connectionManager = connectionManager;
    }

    @Override
    public List<LocationDto> findAll() throws DAOException {
        List<LocationEntity> result = new ArrayList<>();
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement selectStmt = connection.prepareStatement(SELECT_ALL_QUERY)) {
            ResultSet resultSet = selectStmt.executeQuery();
            while (resultSet.next()) {
                LocationEntity entity = parseResultSet(resultSet);
                result.add(entity);
            }
        }catch (SQLException e) {
            throw new DAOException();
        }
        return result.stream().map(this::fromEntity).collect(Collectors.toList());
    }

    @Override
    public Long save(LocationDto locationDto) throws DAOException {
        LocationEntity entity = fromDto(locationDto);
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement insertStmt = connection.prepareStatement(INSERT_QUERY, Statement.RETURN_GENERATED_KEYS)) {
            int i = 0;
            insertStmt.setLong(++i, entity.getId());
            insertStmt.setDouble(++i, entity.getLatitude());
            insertStmt.setDouble(++i, entity.getLongitude());
            insertStmt.executeUpdate();
            ResultSet generatedKeys = insertStmt.getGeneratedKeys();
            long id = 0;
            if (generatedKeys.next()) {
                id = generatedKeys.getLong(1);
            }
            return id;
        } catch (SQLException e) {
            throw new DAOException(e.getMessage());
        }
    }

    @Override
    public boolean update(LocationDto location) throws DAOException {
        LocationEntity entity = fromDto(location);
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement updateStmt = connection.prepareStatement(UPDATE_QUERY)){
            int i = 0;
            updateStmt.setDouble(++i, entity.getLatitude());
            updateStmt.setDouble(++i, entity.getLongitude());
            updateStmt.setLong(++i, entity.getId());
            return updateStmt.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DAOException();
        }
    }

    @Override
    public boolean delete(LocationDto locationDto) throws DAOException {
        LocationEntity entity = fromDto(locationDto);
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement updateStmt = connection.prepareStatement(DELETE_QUERY)){
            updateStmt.setLong(1, entity.getId());
            return updateStmt.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DAOException();
        }
    }

    @Override
    public LocationDto getById(Long id) throws DAOException {
        List<LocationEntity> result = new ArrayList<>();
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement selectStmt = connection.prepareStatement(SELECT_BY_ID_QUERY)) {
            selectStmt.setLong(1, id);
            ResultSet resultSet = selectStmt.executeQuery();
            while (resultSet.next()) {
                LocationEntity entity = parseResultSet(resultSet);
                result.add(entity);
            }
        } catch (SQLException e) {
            throw new DAOException();
        }
        return result.stream().map(this::fromEntity).findFirst().orElseThrow(() -> new IllegalArgumentException("Entity not found with given id: " + id));
    }

    private LocationEntity parseResultSet(ResultSet resultSet) throws SQLException {
        Long id = resultSet.getLong("location_id");
        double latitude = resultSet.getDouble("latitude");
        double longitude = resultSet.getDouble("longitude");
        return LocationEntity.builder()
                .id(id)
                .latitude(latitude)
                .longitude(longitude)
                .build();
    }

    private LocationEntity fromDto(LocationDto dto) {
        LocationEntity entity = new LocationEntity();
        entity.setId(dto.getId());
        entity.setLatitude(dto.getLatitude());
        entity.setLongitude(dto.getLongitude());
        return entity;
    }

    private LocationDto fromEntity(LocationEntity entity) {
        LocationDto dto = new LocationDto();
        dto.setId(entity.getId());
        dto.setLatitude(entity.getLatitude());
        dto.setLongitude(entity.getLongitude());
        return dto;
    }
}
