package by.training.taxi.car;

import by.training.taxi.bean.Bean;
import by.training.taxi.dao.ConnectionManager;
import by.training.taxi.dao.DAOException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import static by.training.taxi.car.CarStatus.getCarStatusFromText;

@Bean
public class CarDaoImpl implements CarDao {

    private static final String SELECT_ALL_QUERY = "select car_id, car_model, car_color, car_status, driver_id, licence_plate_number from car";
    private static final String SELECT_BY_ID_QUERY = "select car_id, car_model, car_color, car_status, driver_id, licence_plate_number from car " +
                                                     "where car_id = ?";
    private static final String SELECT_BY_DRIVER_ID_QUERY = "SELECT car_id, car_model, car_color, car_status, driver_id, licence_plate_number " +
                                                             "FROM car " +
                                                              "WHERE driver_id = ?";
    private static final String INSERT_QUERY = "insert into car (car_model, car_color,  driver_id, licence_plate_number ) " +
                                                "values (?,?,?,?)";
    private static final String UPDATE_QUERY = "update сar set car_model=?, car_color=?, car_status=?, driver_id=?, licence_plate_number=?" +
                                                "  where car_id = ?";
    private static final String DELETE_QUERY = "delete from car where car_id = ?";

    private static final String CREATE_REQUIREMENT_QUERY ="INSERT INTO car_requirement_relation (car_id, requirement) \n" +
                                                          "VALUES (?,?)";

    private static final String SELECT_BY_USER_ID_QUERY =  "SELECT car_id, car_model, car_color, car_status, driver_id, licence_plate_number "+
            "FROM car"+
            "WHERE driver_id =("+
            "SELECT driver.driver_id"+
            "FROM driver JOIN user_account ON driver.user_account_id = user_account.id"+
            "WHERE user_account.id = ?"+
            ")";

    private ConnectionManager connectionManager;

    public CarDaoImpl(ConnectionManager connectionManager) {
        this.connectionManager = connectionManager;
    }

    @Override
    public Long save(CarDto userDto) throws DAOException {
        CarEntity entity = fromDto(userDto);
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement insertStmt = connection.prepareStatement(INSERT_QUERY, Statement.RETURN_GENERATED_KEYS)) {
            int i = 0;
            insertStmt.setString(++i, entity.getModel());
            insertStmt.setString(++i, entity.getColor());
            insertStmt.setLong(++i, entity.getDriverId());
            insertStmt.setString(++i, entity.getLicencePlateNum());
            insertStmt.executeUpdate();
            ResultSet generatedKeys = insertStmt.getGeneratedKeys();
            long id = 0;
            while (generatedKeys.next()) {
                id = generatedKeys.getLong(1);
            }
            return id;
        } catch (SQLException e) {
            throw  new DAOException();
        }
    }

    @Override
    public boolean update(CarDto userDto) throws DAOException {
        CarEntity entity = fromDto(userDto);
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement updateStmt = connection.prepareStatement(UPDATE_QUERY)){
            int i = 0;
            updateStmt.setString(++i, entity.getModel());
            updateStmt.setString(++i, entity.getColor());
            updateStmt.setString(++i, entity.getCarStatus().toString());
            updateStmt.setLong(++i, entity.getDriverId());
            updateStmt.setString(++i, entity.getLicencePlateNum());
            return updateStmt.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DAOException();
        }
    }

    @Override
    public boolean delete(CarDto userDto) throws DAOException {
        CarEntity entity = fromDto(userDto);
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement updateStmt = connection.prepareStatement(DELETE_QUERY)){
            updateStmt.setLong(1, entity.getId());
            return updateStmt.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DAOException();
        }
    }



    @Override
    public CarDto getByUserId(long userId) throws DAOException {
        List<CarEntity> result = new ArrayList<>();
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement selectStmt = connection.prepareStatement(SELECT_BY_USER_ID_QUERY)) {
            selectStmt.setLong(1, userId);
            ResultSet resultSet = selectStmt.executeQuery();
            while (resultSet.next()) {
                CarEntity entity = parseResultSet(resultSet);
                result.add(entity);
            }
        } catch (SQLException e) {
            throw new DAOException();
        }
        return result.stream().map(this::fromEntity).findFirst().orElseThrow(() -> new DAOException());
    }

    @Override
    public CarDto getByDriverId(long driverId) throws DAOException {
        List<CarEntity> result = new ArrayList<>();
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement selectStmt = connection.prepareStatement(SELECT_BY_DRIVER_ID_QUERY)) {
            selectStmt.setLong(1, driverId);
            ResultSet resultSet = selectStmt.executeQuery();
            while (resultSet.next()) {
                CarEntity entity = parseResultSet(resultSet);
                result.add(entity);
            }
        } catch (SQLException e) {
            throw new DAOException();
        }
        return result.stream().map(this::fromEntity).findFirst().orElseThrow(() -> new DAOException());
    }

    @Override
    public CarDto getById(Long id) throws DAOException {
        List<CarEntity> result = new ArrayList<>();
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement selectStmt = connection.prepareStatement(SELECT_BY_ID_QUERY)) {
            selectStmt.setLong(1, id);
            ResultSet resultSet = selectStmt.executeQuery();
            while (resultSet.next()) {
                CarEntity entity = parseResultSet(resultSet);
                result.add(entity);
            }
        } catch (SQLException e) {
            throw new DAOException();
        }
        return result.stream().map(this::fromEntity).findFirst().orElseThrow(() -> new DAOException());
    }

    @Override
    public List<CarDto> findAll() throws DAOException {
        List<CarEntity> result = new ArrayList<>();
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement selectStmt = connection.prepareStatement(SELECT_ALL_QUERY)) {
            ResultSet resultSet = selectStmt.executeQuery();
            while (resultSet.next()) {
                CarEntity entity = parseResultSet(resultSet);
                result.add(entity);
            }
        }catch (SQLException e) {
            throw new DAOException();
        }
        return result.stream().map(this::fromEntity).collect(Collectors.toList());
    }


    @Override
    public boolean addRequirementForCar(long id, Set<RequirementType> requirementTypes) {
        return false;
    }

    private CarEntity parseResultSet(ResultSet resultSet) throws SQLException {
        String model = resultSet.getString("car_model");
        String color = resultSet.getString("car_color");
        CarStatus status = getCarStatusFromText(resultSet.getString("car_status")).get();
        Long driverId = resultSet.getLong("driver_id");
        String licenceNum = resultSet.getString("licence_plate_number");
        return CarEntity.builder()
                .model(model)
                .color(color)
                .carStatus(status)
                .driverId(driverId)
                .licencePlateNum(licenceNum)
                .build();
    }

    private CarEntity fromDto(CarDto dto) {
        CarEntity entity = new CarEntity();
        entity.setDriverId(dto.getDriverId());
        entity.setCarStatus(dto.getCarStatus());
        entity.setLicencePlateNum(dto.getLicencePlateNum());
        entity.setModel(dto.getModel());
        entity.setColor(dto.getColor());
        return entity;
    }

    private CarDto fromEntity(CarEntity entity) {
        CarDto dto = new CarDto();
        dto.setLicencePlateNum(entity.getLicencePlateNum());
        dto.setModel(entity.getModel());
        dto.setDriverId(entity.getDriverId());
        dto.setCarStatus(entity.getCarStatus());
        dto.setColor(entity.getColor());
        return dto;
    }
}