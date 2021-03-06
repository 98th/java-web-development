package by.training.taxi.contact;

import by.training.taxi.bean.Bean;
import by.training.taxi.dao.ConnectionManager;
import by.training.taxi.dao.DAOException;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Bean
@AllArgsConstructor
@Log4j
public class ContactDaoImpl implements ContactDao {

    private static final String SELECT_ALL_QUERY = "SELECT contact_id, contact_id, first_name, last_name, email, phone, " +
                                                   " user_account_id FROM user_contact";
    private static final String SELECT_BY_ID_QUERY = "SELECT contact_id, first_name, last_name, email, phone,  user_account_id " +
                                                    "FROM user_contact WHERE contact_id = ?";
    private static final String SELECT_BY_USER_ID_QUERY = "SELECT contact_id, first_name, last_name, email, phone,  user_account_id " +
                                                         " FROM user_contact WHERE user_account_id = ?";
    private static final String SELECT_BY_EMAIL_QUERY = "SELECT contact_id, first_name, last_name, email, phone,  " +
                                                        "user_account_id FROM user_contact WHERE email = ?";
    private static final String INSERT_QUERY = "INSERT INTO user_contact (first_name, last_name, email, phone, user_account_id) " +
                                               "values (?,?,?,?,?)";
    private static final String UPDATE_QUERY = "UPDATE user_contact SET email=?, phone=?, first_name=?, last_name=?, " +
                                                "user_account_id=? WHERE contact_id = ?";
    private static final String DELETE_QUERY = "DELETE FROM user_contact WHERE  contact_id = ?";

    private ConnectionManager connectionManager;

    @Override
    public Long save(ContactDto contactDto) throws DAOException {
        ContactEntity entity = fromDto(contactDto);
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement insertStmt = connection.prepareStatement(INSERT_QUERY, Statement.RETURN_GENERATED_KEYS)) {
            int i = 0;
            insertStmt.setString(++i, entity.getFirstName());
            insertStmt.setString(++i, entity.getLastName());
            insertStmt.setString(++i, entity.getEmail());
            insertStmt.setString(++i, entity.getPhone());
            insertStmt.setLong(++i, entity.getUserId());
            insertStmt.executeUpdate();
            try (ResultSet generatedKeys = insertStmt.getGeneratedKeys()) {
                while (generatedKeys.next()) {
                    entity.setId(generatedKeys.getLong(1));
                }
            }
        } catch (SQLException e) {
            log.error("Failed to save an user contact");
            throw new DAOException("Failed to save an user contact");
        }
        return entity.getId();
    }

    @Override
    public boolean update(ContactDto contactDto) throws DAOException {
        ContactEntity entity = fromDto(contactDto);
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement updateStmt = connection.prepareStatement(UPDATE_QUERY)){
            int i = 0;
            updateStmt.setString(++i, entity.getEmail());
            updateStmt.setString(++i, entity.getPhone());
            updateStmt.setString(++i, entity.getFirstName());
            updateStmt.setString(++i, entity.getLastName());
            updateStmt.setLong(++i, entity.getUserId());
            updateStmt.setLong(++i, entity.getId());
            return updateStmt.executeUpdate() > 0;
        } catch (SQLException e) {
            log.error("Failed to update an user contact");
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
            log.error("Failed to delete the contact " + id);
            throw new DAOException();
        }
    }

    @Override
    public ContactDto getById(Long id) throws DAOException {
        List<ContactEntity> result = new ArrayList<>();
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement selectStmt = connection.prepareStatement(SELECT_BY_ID_QUERY)) {
            selectStmt.setLong(1, id);
            ResultSet resultSet = selectStmt.executeQuery();
            while (resultSet.next()) {
                ContactEntity entity = parseResultSet(resultSet);
                result.add(entity);
            }
        } catch (SQLException e) {
            throw new DAOException();
        }
        return result.stream().map(this::fromEntity).findFirst().orElseThrow(() -> new IllegalArgumentException("Entity not found with given id: " + id));
    }

    @Override
    public List<ContactDto> findAll() throws DAOException {
        List<ContactEntity> result = new ArrayList<>();
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement selectStmt = connection.prepareStatement(SELECT_ALL_QUERY)) {
            ResultSet resultSet = selectStmt.executeQuery();
            while (resultSet.next()) {
                ContactEntity entity = parseResultSet(resultSet);
                result.add(entity);
            }
        }catch (SQLException e) {
            throw new DAOException();
        }
        return result.stream().map(this::fromEntity).collect(Collectors.toList());
    }

    @Override
    public ContactDto getByEmail(String email) throws DAOException {
        List<ContactEntity> result = new ArrayList<>();
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement selectStmt = connection.prepareStatement(SELECT_BY_EMAIL_QUERY)) {
            int i = 0;
            selectStmt.setString(++i, email);
            ResultSet resultSet = selectStmt.executeQuery();
            while (resultSet.next()) {
                ContactEntity entity = parseResultSet(resultSet);
                result.add(entity);
            }
        } catch (SQLException e) {
            log.error("Failed to get by email " + email);
            throw new DAOException("Failed to get by email " + email);
        }
        return result.stream().map(this::fromEntity).findFirst()
                .orElseThrow(() -> new DAOException("User not found with the email : " + email));
    }

    @Override
    public ContactDto getByUserId(Long userId) throws DAOException {
        List<ContactEntity> result = new ArrayList<>();
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement selectStmt = connection.prepareStatement(SELECT_BY_USER_ID_QUERY)) {
            selectStmt.setLong(1, userId);
            ResultSet resultSet = selectStmt.executeQuery();
            while (resultSet.next()) {
                ContactEntity entity = parseResultSet(resultSet);
                result.add(entity);
            }
        }   catch (SQLException e) {
            log.error("User not found with the user id: " + userId);
            throw new DAOException("User not found with the user id: " + userId);
        }
        return result.stream().map(this::fromEntity).findFirst()
                .orElseThrow(() -> new DAOException("User not found with the user id: " + userId));
    }

    private ContactEntity parseResultSet(ResultSet resultSet) throws SQLException {
        long contactId = resultSet.getLong("contact_id");
        long userId = resultSet.getLong("user_account_id");
        String email = resultSet.getString("email");
        String lastName = resultSet.getString("last_name");
        String firstName = resultSet.getString("first_name");
        String phone = resultSet.getString("phone");

        return ContactEntity.builder()
                .id(contactId)
                .userId(userId)
                .email(email)
                .lastName(lastName)
                .firstName(firstName)
                .phone(phone)
                .build();
    }

    private ContactEntity fromDto(ContactDto dto) {
        ContactEntity entity = new ContactEntity();
        entity.setId(dto.getId());
        entity.setEmail(dto.getEmail());
        entity.setLastName(dto.getLastName());
        entity.setPhone(dto.getPhone());
        entity.setUserId(dto.getUserId());
        entity.setFirstName(dto.getFirstName());
        return entity;
    }

    private ContactDto fromEntity(ContactEntity entity) {
        ContactDto dto = new ContactDto();
        dto.setId(entity.getId());
        dto.setEmail(entity.getEmail());
        dto.setFirstName(entity.getFirstName());
        dto.setUserId(entity.getUserId());
        dto.setLastName(entity.getLastName());
        dto.setPhone(entity.getPhone());
        return dto;
    }
}


