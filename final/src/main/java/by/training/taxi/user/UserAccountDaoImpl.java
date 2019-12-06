package by.training.taxi.user;

import by.training.taxi.bean.Bean;
import by.training.taxi.dao.ConnectionManager;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import by.training.taxi.dao.DAOException;
import by.training.taxi.role.Role;

import static by.training.taxi.role.Role.getRoleFromText;

@Bean
public class UserAccountDaoImpl implements UserAccountDao {

    private static final String SELECT_ALL_QUERY = "select id, login, password, user_role from user_account";
    private static final String SELECT_BY_ID_QUERY = "select id, login, password, user_role from user_account where id = ?";
    private static final String SELECT_BY_LOGIN_QUERY = "select id, login, password, user_role from user_account where login = ?";
    private static final String SELECT_BY_LOGIN_PASS_QUERY = "select id, login, password, user_role from user_account where login = ? and password = ?";
    private static final String INSERT_QUERY = "insert into user_account (login, password, user_role) values (?,?,?)";
    private static final String UPDATE_QUERY = "update user_account set login=?, password=? user_role=? where id = ?";
    private static final String DELETE_QUERY = "delete from user_account where id = ?";

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
            insertStmt.executeUpdate();
            ResultSet generatedKeys = insertStmt.getGeneratedKeys();
            long id = 0;
            if (generatedKeys.next()) {
                id = generatedKeys.getLong(1);
            }
            return id;

        } catch (SQLException e) { }
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
            updateStmt.setString(++i, entity.getRole().toString());
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
        return UserAccountEntity.builder()
                .id(id)
                .login(login)
                .password(password)
                .role(role)
                .build();
    }

    private UserAccountEntity fromDto(UserAccountDto dto) {
        UserAccountEntity entity = new UserAccountEntity();
        entity.setId(dto.getId());
        entity.setLogin(dto.getLogin());
        entity.setPassword(dto.getPassword());
        entity.setRole(dto.getRole());
        return entity;
    }

    private UserAccountDto fromEntity(UserAccountEntity entity) {
        UserAccountDto dto = new UserAccountDto();
        dto.setId(entity.getId());
        dto.setLogin(entity.getLogin());
        dto.setPassword(entity.getPassword());
        dto.setRole(entity.getRole());
        return dto;
    }
}
