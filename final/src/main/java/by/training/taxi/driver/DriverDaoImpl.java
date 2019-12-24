package by.training.taxi.driver;

import by.training.taxi.bean.Bean;
import by.training.taxi.car.CarDao;
import by.training.taxi.car.CarDto;
import by.training.taxi.car.CarEntity;
import by.training.taxi.contact.ContactDto;
import by.training.taxi.dao.ConnectionManager;
import by.training.taxi.dao.DAOException;
import by.training.taxi.location.LocationDto;
import by.training.taxi.user.UserAccountDto;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Bean
@AllArgsConstructor
@Log4j
public class DriverDaoImpl implements DriverDao {
    private static final String SELECT_ALL_QUERY = "SELECT driver_id, driving_licence_number, " +
            " user_account_id FROM driver";
    private static final String SELECT_BY_ID_QUERY = "SELECT driver_id, driving_licence_number,  user_account_id FROM driver WHERE driver_id = ?";
    private static final String INSERT_QUERY = "INSERT INTO driver (driving_licence_number," +
            " user_account_id) values (?,?,?)";
    private static final String UPDATE_QUERY =  "UPDATE driver " +
                                                "SET  driving_licence_number=?,  user_account_id=?," +
                                                "WHERE driver_id = ?";
    private static final String DELETE_QUERY = "DELETE FROM driver WHERE driver_id = ?";
    private static final String SELECT_BY_USER_ID_QUERY = "SELECT driver_id, driving_licence_number," +
            " user_account_id FROM driver WHERE user_account_id = ?";

    private static final String SELECT_WITH_INFO = "SELECT  D.driver_id, D.user_account_id, C.first_name, C.last_name, " +
            "C.phone, car.car_color, car.car_model, car.licence_plate_number, UA.is_locked, " +
            "L.longitude, L.latitude " +
            "FROM driver AS D " +
            "JOIN user_contact AS C ON C.user_account_id = D.user_account_id " +
            "JOIN car ON car.driver_id = D.driver_id "  +
            "JOIN location AS L ON L.location_id = D.user_account_id " +
            "JOIN user_account AS UA ON UA.id = D.user_account_id " +
            "WHERE D.driver_id = ?";

    private static final String SELECT_ALL_WITH_INFO = "SELECT  D.driver_id, D.user_account_id, C.first_name, C.last_name, C.phone, " +
            "car.car_color, car.car_model, car.licence_plate_number, D.driving_licence_number, D.user_account_id, UA.is_locked " +
            "FROM driver AS D " +
            "JOIN user_contact AS C ON C.user_account_id = D.user_account_id " +
            "JOIN car ON car.driver_id = D.driver_id " +
            "JOIN user_account AS UA ON UA.id = D.user_account_id";

    private ConnectionManager connectionManager;



    @Override
    public DriverDto getWithInfo(Long id) throws DAOException {
        List<DriverDto> result = new ArrayList<>();
        try (Connection connection = connectionManager.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(SELECT_WITH_INFO)) {
            preparedStatement.setLong(1, id);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    String firstName = resultSet.getString("first_name");
                    String lastName = resultSet.getString("last_name");
                    String phone = resultSet.getString("phone");
                    ContactDto contact = ContactDto.builder()
                            .firstName(firstName)
                            .lastName(lastName)
                            .phone(phone)
                            .build();
                    String model = resultSet.getString("car_model");
                    String color = resultSet.getString("car_color");
                    String licencePlateNum = resultSet.getString("licence_plate_number");
                    CarDto car = CarDto.builder()
                            .color(color)
                            .licencePlateNum(licencePlateNum)
                            .model(model)
                            .build();
                    LocationDto locationDto;
                    Double longitude = resultSet.getDouble("longitude");
                    Double latitude = resultSet.getDouble("latitude");
                    locationDto = LocationDto.builder()
                            .id(id)
                            .latitude(latitude)
                            .longitude(longitude)
                            .build();
                    long userAccountId = resultSet.getLong("user_account_id");
                    DriverDto driver = DriverDto.builder()
                            .id(id)
                            .car(car)
                            .contact(contact)
                            .location(locationDto)
                            .userId(userAccountId)
                            .build();
                    result.add(driver);
                }
            }
        } catch (SQLException e) {
            log.error("Failed to find a driver with info");
            throw new DAOException();
        }
        Optional<DriverDto> output = result.stream()
                .findFirst();
        if (!output.isPresent()) {
            log.error("Failed to find a driver with info");
            throw new DAOException();
        }
        return output.get();
    }


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

    @Override
    public List<DriverDto> findAllWithInfo() throws DAOException {
        List<DriverDto> drivers = new ArrayList<>();
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_WITH_INFO)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            DriverDto driver;
            while (resultSet.next()) {
                long id = resultSet.getLong("driver_id");
                String firstName = resultSet.getString("first_name");
                String lastName = resultSet.getString("last_name");
                String phone = resultSet.getString("phone");
                ContactDto contact = ContactDto.builder()
                        .firstName(firstName)
                        .lastName(lastName)
                        .phone(phone)
                        .build();
                String model = resultSet.getString("car_model");
                String color = resultSet.getString("car_color");
                String licencePlateNum = resultSet.getString("licence_plate_number");
                CarDto car = CarDto.builder()
                        .color(color)
                        .licencePlateNum(licencePlateNum)
                        .model(model)
                        .build();
                String drivingLicence = resultSet.getString("driving_licence_number");
                long userId = resultSet.getLong("user_account_id");
                boolean isLocked = resultSet.getBoolean("is_locked");
                UserAccountDto user = UserAccountDto.builder().isLocked(isLocked).build();
                driver = DriverDto.builder()
                        .id(id)
                        .car(car)
                        .contact(contact)
                        .drivingLicenceNum(drivingLicence)
                        .userId(userId)
                        .userAccount(user)
                        .build();
                drivers.add(driver);
            }
            return drivers;
        } catch (SQLException e) {
            throw new DAOException();
        }
    }

    private DriverEntity parseResultSet(ResultSet resultSet) throws SQLException {
        long entityId = resultSet.getLong("driver_id");
        String drivingLicenceNum = resultSet.getString("driving_licence_number");
        long userId = resultSet.getLong("user_account_id");
        return DriverEntity.builder()
                .id(entityId)
                .drivingLicenceNum(drivingLicenceNum)
                .userId(userId)
                .build();
    }

    private DriverEntity fromDto(DriverDto dto) {
        DriverEntity entity = new DriverEntity();
        entity.setId(dto.getId());
        entity.setDrivingLicenceNum(dto.getDrivingLicenceNum());
        entity.setUserId(dto.getUserId());
        return entity;
    }

    private DriverDto fromEntity(DriverEntity entity) {
        DriverDto dto = new DriverDto();
        dto.setId(entity.getId());
        dto.setDrivingLicenceNum(entity.getDrivingLicenceNum());
        dto.setUserId(entity.getUserId());
        return dto;
    }
}
