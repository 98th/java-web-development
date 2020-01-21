package by.training.taxi.car;

import by.training.taxi.bean.Bean;
import by.training.taxi.dao.ConnectionManager;
import by.training.taxi.dao.DAOException;
import lombok.extern.log4j.Log4j;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


@Bean
@Log4j
public class CarDaoImpl implements CarDao {

    private static final String SELECT_ALL_QUERY = "select car_id, car_model, car_color, licence_plate_number from car";
    private static final String SELECT_BY_ID_QUERY = "select car_id, car_model, car_color, licence_plate_number from car " +
                                                     "where car_id = ?";
    private static final String INSERT_QUERY = "insert into car (car_model, car_color, licence_plate_number) " +
                                                "values (?,?,?)";
    private static final String UPDATE_QUERY = "update car set car_model=?, car_color=?, licence_plate_number=?" +
                                                "  where car_id = ?";
    private static final String DELETE_QUERY = "delete from car where car_id = ?";
    private static final String SELECT_BY_REQUIREMENT_QUERY = "SELECT car.car_id,  CR.requirement_type, car_model," +
            " car.car_color, car.licence_plate_number, car.driver_id, car.is_working " +
            "            FROM car_requirement_relation AS CR JOIN car ON car.car_id = CR.car_id " +
            "            WHERE CR.requirement_type = ?::requirement_type  AND car.is_working = true";
    private static final String INSERT_REQUIREMENT_QUERY = "INSERT INTO car_requirement_relation (car_id, requirement_type) " +
            "VALUES (?, ?::requirement_type )";
    private ConnectionManager connectionManager;

    public CarDaoImpl(ConnectionManager connectionManager) {
        this.connectionManager = connectionManager;
    }

    @Override
    public List<CarDto> getCarsWithRequirement(String requirement) throws DAOException {
        List<CarEntity> output = new ArrayList<>();
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_BY_REQUIREMENT_QUERY)) {
            preparedStatement.setString(1, requirement);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    long id = resultSet.getLong("car_id");
                    String model = resultSet.getString("car_model");
                    String color = resultSet.getString("car_color");
                    String licencePlateNum = resultSet.getString("licence_plate_number");
                    CarEntity entity = CarEntity.builder()
                            .id(id)
                            .model(model)
                            .licencePlateNum(licencePlateNum)
                            .color(color)
                            .build();
                    output.add(entity);
                }
            }
        }catch (SQLException e) {
            throw new DAOException();
        }
        return output.stream().map(this::fromEntity).collect(Collectors.toList());
    }

    @Override
    public Long save(CarDto userDto) throws DAOException {
        CarEntity entity = fromDto(userDto);
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement insertStmt = connection.prepareStatement(INSERT_QUERY, Statement.RETURN_GENERATED_KEYS)) {
            int i = 0;
            insertStmt.setString(++i, entity.getModel());
            insertStmt.setString(++i, entity.getColor());
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
            updateStmt.setString(++i, entity.getLicencePlateNum());
            updateStmt.setLong(++i, entity.getId());
            return updateStmt.executeUpdate() > 0;
        } catch (SQLException e) {
            log.error("Failed to update a car");
            throw new DAOException();
        }
    }

    @Override
    public boolean delete(Long id) throws DAOException {
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement updateStmt = connection.prepareStatement(DELETE_QUERY)){
            updateStmt.setLong(1, id);
            return updateStmt.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DAOException();
        }
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
    public boolean addRequirementToCar(long id, Set<RequirementType> requirementTypes) {
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement insertStm = connection.prepareStatement(INSERT_REQUIREMENT_QUERY)) {
            for (RequirementType requirementType : requirementTypes) {
                int i = 0;
                insertStm.setLong(++i, id);
                insertStm.setString(++i, requirementType.getValue());
                insertStm.addBatch();
            }
            return insertStm.executeBatch().length > 0;
        } catch (SQLException e) {
            return false;
        }
    }

    private CarEntity parseResultSet(ResultSet resultSet) throws SQLException {
        long id = resultSet.getLong("car_id");
        String model = resultSet.getString("car_model");
        String color = resultSet.getString("car_color");
        String licenceNum = resultSet.getString("licence_plate_number");
        return CarEntity.builder()
                .id(id)
                .model(model)
                .color(color)
                .licencePlateNum(licenceNum)
                .build();
    }

    private CarEntity fromDto(CarDto dto) {
        CarEntity entity = new CarEntity();
        entity.setId(dto.getId());
        entity.setLicencePlateNum(dto.getLicencePlateNum());
        entity.setModel(dto.getModel());
        entity.setColor(dto.getColor());
        return entity;
    }

    private CarDto fromEntity(CarEntity entity) {
        CarDto dto = new CarDto();
        dto.setId(entity.getId());
        dto.setLicencePlateNum(entity.getLicencePlateNum());
        dto.setModel(entity.getModel());
        dto.setColor(entity.getColor());
        return dto;
    }
}
