package by.training.taxi.user;

import by.training.taxi.bean.Bean;
import by.training.taxi.contact.ContactDto;
import by.training.taxi.dao.ConnectionManager;
import by.training.taxi.dao.DAOException;
import by.training.taxi.discount.DiscountDto;
import by.training.taxi.driver.DriverDto;
import by.training.taxi.role.Role;
import lombok.extern.log4j.Log4j;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static by.training.taxi.role.Role.getRoleFromText;

@Bean
@Log4j
public class UserAccountDaoImpl implements UserAccountDao {

    private static final String SELECT_ALL_QUERY = "select id, login, password, user_role, is_locked from user_account";
    private static final String SELECT_BY_ID_QUERY = "select id, login, password, user_role, is_locked from user_account where id = ?";
    private static final String SELECT_BY_LOGIN_QUERY = "select id, login, password, user_role, is_locked from user_account where login = ?";
    private static final String SELECT_BY_LOGIN_PASS_QUERY = "select id, login, password, user_role, is_locked from user_account where login = ? and password = ?";
    private static final String INSERT_QUERY = "insert into user_account (login, password, user_role, is_locked) values (?,?,?,?)";
    private static final String UPDATE_QUERY = "update user_account set login=?, password=?, user_role=?::user_role, is_locked = ? where id = ?";
    private static final String DELETE_QUERY = "delete from user_account where id = ?";
    private static final String SELECT_ALL_WITH_CONTACT_QUERY = "SELECT UA.id, UA.login, UC.first_name, UC.last_name, UC.email, " +
            "UC.phone, UA.user_role, UA.is_locked, D.discount_amount " +
            "FROM user_account AS UA LEFT JOIN user_contact AS UC ON UA.id = UC.user_account_id " +
            "JOIN discount AS D on D.discount_id = UA.id " +
            "WHERE UA.user_role = 'client' OR UA.user_role = 'admin'";

    private ConnectionManager connectionManager;

    public UserAccountDaoImpl(ConnectionManager connectionManager) {
        this.connectionManager = connectionManager;
    }

    @Override
    public Long save(UserAccountDto userDto) throws DAOException {
        UserAccountEntity entity = fromDto(userDto);
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement insertStmt = connection.prepareStatement(INSERT_QUERY, Statement.RETURN_GENERATED_KEYS)) {
            int i = 0;
            insertStmt.setString(++i, entity.getLogin());
            insertStmt.setString(++i, entity.getPassword());
            insertStmt.setString(++i, entity.getRole().toString());
            insertStmt.setBoolean(++i, entity.isLocked());
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
    public boolean update(UserAccountDto userDto) throws DAOException {
        UserAccountEntity entity = fromDto(userDto);
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement updateStmt = connection.prepareStatement(UPDATE_QUERY)){
            int i = 0;
            updateStmt.setString(++i, entity.getLogin());
            updateStmt.setString(++i, entity.getPassword());
            updateStmt.setString(++i, entity.getRole().getValue());
            updateStmt.setBoolean(++i, entity.isLocked());
            updateStmt.setLong(++i, entity.getId());
            return updateStmt.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DAOException();
        }
    }

    @Override
    public boolean delete(UserAccountDto userDto) throws DAOException {
        UserAccountEntity entity = fromDto(userDto);
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement updateStmt = connection.prepareStatement(DELETE_QUERY)){
            updateStmt.setLong(1, entity.getId());
            return updateStmt.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DAOException();
        }
    }

    @Override
    public UserAccountDto getById(Long id) throws DAOException {
        List<UserAccountEntity> result = new ArrayList<>();
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement selectStmt = connection.prepareStatement(SELECT_BY_ID_QUERY)) {
            selectStmt.setLong(1, id);
            ResultSet resultSet = selectStmt.executeQuery();
            while (resultSet.next()) {
                UserAccountEntity entity = parseResultSet(resultSet);
                result.add(entity);
            }

        } catch (SQLException e) {
            throw new DAOException();
        }
        return result.stream().map(this::fromEntity).findFirst().orElseThrow(() -> new IllegalArgumentException("Entity not found with given id: " + id));
    }

    @Override
    public List<UserAccountDto> findAll() throws DAOException {
        List<UserAccountEntity> result = new ArrayList<>();
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement selectStmt = connection.prepareStatement(SELECT_ALL_QUERY)) {
                ResultSet resultSet = selectStmt.executeQuery();
                while (resultSet.next()) {
                    UserAccountEntity entity = parseResultSet(resultSet);
                    result.add(entity);
                }
        }catch (SQLException e) {
            throw new DAOException();
        }
        return result.stream().map(this::fromEntity).collect(Collectors.toList());
    }

    @Override
    public List<UserAccountDto> getAllWithContact() throws DAOException {
        List<UserAccountDto> result = new ArrayList<>();
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_WITH_CONTACT_QUERY)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Long id = resultSet.getLong("id");
                String login = resultSet.getString("login");
                String userRole = resultSet.getString("user_role");
                boolean isLocked = resultSet.getBoolean("is_locked");
                String firstName = resultSet.getString("first_name");
                String lastName = resultSet.getString("last_name");
                String phone = resultSet.getString("phone");
                String email = resultSet.getString("email");
                ContactDto contact = ContactDto.builder()
                        .firstName(firstName)
                        .lastName(lastName)
                        .email(email)
                        .phone(phone)
                        .build();
                Double amount = resultSet.getDouble("discount_amount");
                DiscountDto discount = DiscountDto.builder().amount(amount).build();
                UserAccountDto user = UserAccountDto.builder()
                        .id(id)
                        .login(login)
                        .role(Role.getRoleFromText(userRole).get())
                        .isLocked(isLocked)
                        .discount(discount)
                        .contact(contact)
                        .build();
                result.add(user);
            }
            return result;
        } catch(SQLException e) {
            log.error("Failed to get all users with contacts");
            throw new DAOException(e.getMessage());
        }
    }

    @Override
    public Optional<UserAccountDto> getByLoginAndPassword(String login, String password) throws DAOException {
        List<UserAccountEntity> result = new ArrayList<>();
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement selectStmt = connection.prepareStatement(SELECT_BY_LOGIN_PASS_QUERY)) {
            int i = 0;
            selectStmt.setString(++i, login);
            selectStmt.setString(++i, password);
            ResultSet resultSet = selectStmt.executeQuery();
            while (resultSet.next()) {
                UserAccountEntity entity = parseResultSet(resultSet);
                result.add(entity);
            }
        } catch (SQLException e) {
            throw new DAOException("Exception in user dao");
        }

        return result.stream().map(this::fromEntity).findFirst();
    }

    @Override
    public Optional<UserAccountDto> getByLogin(String login) throws DAOException {
        List<UserAccountEntity> result = new ArrayList<>();
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement selectStmt = connection.prepareStatement(SELECT_BY_LOGIN_QUERY)) {
            selectStmt.setString(1, login);
            ResultSet resultSet = selectStmt.executeQuery();
            while (resultSet.next()) {
                UserAccountEntity entity = parseResultSet(resultSet);
                result.add(entity);
            }
        }   catch (SQLException e) {
            throw new DAOException("Exception in user dao");
        }

        return result.stream().map(this::fromEntity).findFirst();
    }

    private UserAccountEntity parseResultSet(ResultSet resultSet) throws SQLException {
        Long id = resultSet.getLong("id");
        String login = resultSet.getString("login");
        String password = resultSet.getString("password");
        Role role = getRoleFromText(resultSet.getString("user_role")).get();
        boolean isLocked = resultSet.getBoolean("is_locked");
        return UserAccountEntity.builder()
                .id(id)
                .login(login)
                .password(password)
                .role(role)
                .isLocked(isLocked)
                .build();
    }

    private UserAccountEntity fromDto(UserAccountDto dto) {
        UserAccountEntity entity = new UserAccountEntity();
        entity.setId(dto.getId());
        entity.setLogin(dto.getLogin());
        entity.setPassword(dto.getPassword());
        entity.setRole(dto.getRole());
        entity.setLocked(dto.getIsLocked());
        return entity;
    }

    private UserAccountDto fromEntity(UserAccountEntity entity) {
        UserAccountDto dto = new UserAccountDto();
        dto.setId(entity.getId());
        dto.setLogin(entity.getLogin());
        dto.setPassword(entity.getPassword());
        dto.setRole(entity.getRole());
        dto.setLocked(entity.isLocked());
        return dto;
    }
}
