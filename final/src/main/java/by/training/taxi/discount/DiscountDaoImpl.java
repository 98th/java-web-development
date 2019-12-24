package by.training.taxi.discount;

import by.training.taxi.bean.Bean;
import by.training.taxi.dao.ConnectionManager;
import by.training.taxi.dao.DAOException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Bean
public class DiscountDaoImpl implements DiscountDao {

    private static final String SELECT_ALL_QUERY = "select discount_id, discount_amount from discount";
    private static final String SELECT_BY_ID_QUERY = "select discount_id, discount_amount from discount where discount_id = ?";
    private static final String INSERT_QUERY = "insert into discount (discount_id, discount_amount) values (?,?)";
    private static final String UPDATE_QUERY = "update discount set discount_amount=? where discount_id = ?";
    private static final String DELETE_QUERY = "delete from discount where discount_id = ?";

    private ConnectionManager connectionManager;

    public DiscountDaoImpl(ConnectionManager connectionManager) {
        this.connectionManager = connectionManager;
    }

    @Override
    public Long save(DiscountDto discountDto) throws DAOException {
        DiscountEntity entity = fromDto(discountDto);
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement insertStmt = connection.prepareStatement(INSERT_QUERY, Statement.RETURN_GENERATED_KEYS)) {
            int i = 0;
            insertStmt.setLong(++i, entity.getId());
            insertStmt.setDouble(++i, entity.getAmount());
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
    public boolean update(DiscountDto userDto) throws DAOException {
        DiscountEntity entity = fromDto(userDto);
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement updateStmt = connection.prepareStatement(UPDATE_QUERY)) {
            int i = 0;
            updateStmt.setDouble(++i, entity.getAmount());
            updateStmt.setLong(++i, entity.getId());
            return updateStmt.executeUpdate() > 0;
        } catch (SQLException e) {
            throw  new DAOException(e.getMessage());
        }
    }

    @Override
    public boolean delete(DiscountDto userDto) throws DAOException {
        DiscountEntity entity = fromDto(userDto);
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement updateStmt = connection.prepareStatement(DELETE_QUERY)) {
            updateStmt.setLong(1, entity.getId());
            return updateStmt.executeUpdate() > 0;
        } catch (SQLException e) {
            throw  new DAOException(e.getMessage());
        }
    }

    @Override
    public DiscountDto getById(Long id) throws DAOException {
        List<DiscountEntity> result = new ArrayList<>();
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement selectStmt = connection.prepareStatement(SELECT_BY_ID_QUERY)) {
            selectStmt.setLong(1, id);
            ResultSet resultSet = selectStmt.executeQuery();
            while (resultSet.next()) {
                DiscountEntity entity = parseResultSet(resultSet);
                result.add(entity);
            }
        } catch (SQLException e) {
            throw new DAOException(e.getMessage());
        }
        return result.stream().map(this::fromEntity).findFirst().orElseThrow(() -> new IllegalArgumentException("Entity not found with given id: " + id));
    }

    @Override
    public List<DiscountDto> findAll() throws DAOException {
        List<DiscountEntity> result = new ArrayList<>();
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement selectStmt = connection.prepareStatement(SELECT_ALL_QUERY)) {
            ResultSet resultSet = selectStmt.executeQuery();
            while (resultSet.next()) {
                DiscountEntity entity = parseResultSet(resultSet);
                result.add(entity);
            }
        } catch (SQLException e) {
            throw new DAOException(e.getMessage());
        }
        return result.stream().map(this::fromEntity).collect(Collectors.toList());
    }

    private DiscountEntity parseResultSet(ResultSet resultSet) throws SQLException {
        long entityId = resultSet.getLong("discount_id");
        double amount = resultSet.getDouble("discount_amount");

        return DiscountEntity.builder()
                .id(entityId)
                .amount(amount)
                .build();
    }

    private DiscountEntity fromDto(DiscountDto dto) {
        DiscountEntity entity = new DiscountEntity();
        entity.setId(dto.getId());
        entity.setAmount(dto.getAmount());
        return entity;
    }

    private DiscountDto fromEntity(DiscountEntity entity) {
        DiscountDto dto = new DiscountDto();
        dto.setId(entity.getId());
        dto.setAmount(entity.getAmount());
        return dto;
    }
}
