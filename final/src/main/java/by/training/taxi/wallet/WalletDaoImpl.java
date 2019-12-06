package by.training.taxi.wallet;

import by.training.taxi.bean.Bean;
import by.training.taxi.dao.ConnectionManager;
import by.training.taxi.dao.DAOException;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

@Bean
public class WalletDaoImpl implements WalletDao {
    private final static AtomicLong COUNTER = new AtomicLong(1);

    private static final String SELECT_ALL_QUERY = "select wallet_id, amount, user_id from user_wallet";
    private static final String SELECT_BY_ID_QUERY = "select wallet_id, amount, user_id from user_wallet where wallet_id = ?";
    private static final String SELECT_BY_USER_ID = "select wallet_id, amount, user_id from user_wallet where user_id = ?";
    private static final String INSERT_QUERY = "insert into user_wallet (wallet_id, amount, user_id) values (?,?,?)";
    private static final String UPDATE_QUERY = "update user_wallet set amount=?, user_id where wallet_id = ?";
    private static final String DELETE_QUERY = "delete from user_wallet where wallet_id = ?";

    private ConnectionManager connectionManager;

    @Override
    public Long save(WalletDto walletDto) throws DAOException {
        WalletEntity entity = fromDto(walletDto);
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement insertStmt = connection.prepareStatement(INSERT_QUERY, Statement.RETURN_GENERATED_KEYS)) {
            int i = 0;
            insertStmt.setBigDecimal(++i, entity.getAmount());
            insertStmt.setLong(++i, entity.getUserId());
            insertStmt.executeUpdate();
            ResultSet generatedKeys = insertStmt.getGeneratedKeys();
            while (generatedKeys.next()) {
                entity.setId(generatedKeys.getLong(1));
            }

        } catch (SQLException e) { }
        return entity.getId();
    }

    @Override
    public boolean update(WalletDto walletDto) throws DAOException {
        WalletEntity entity = fromDto(walletDto);
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement updateStmt = connection.prepareStatement(UPDATE_QUERY)){
            int i = 0;
            updateStmt.setLong(++i, entity.getId());
            updateStmt.setBigDecimal(++i, entity.getAmount());
            updateStmt.setLong(++i, entity.getUserId());
            return updateStmt.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DAOException();
        }
    }

    @Override
    public boolean delete(WalletDto walletDto) throws DAOException {
        WalletEntity entity = fromDto(walletDto);
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement updateStmt = connection.prepareStatement(DELETE_QUERY)){
            updateStmt.setLong(1, entity.getId());
            return updateStmt.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DAOException();
        }
    }
    @Override
    public WalletDto getById(Long id) throws DAOException {
        List<WalletEntity> result = new ArrayList<>();
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement selectStmt = connection.prepareStatement(SELECT_BY_ID_QUERY)) {
            selectStmt.setLong(1, id);
            ResultSet resultSet = selectStmt.executeQuery();
            while (resultSet.next()) {
                WalletEntity entity = parseResultSet(resultSet);
                result.add(entity);
            }

        } catch (SQLException e) {
            throw new DAOException();
        }
        return result.stream().map(this::fromEntity).findFirst().orElseThrow(() -> new IllegalArgumentException("Entity not found with given id: " + id));
    }

    @Override
    public List<WalletDto> findAll() throws DAOException {
        List<WalletEntity> result = new ArrayList<>();
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement selectStmt = connection.prepareStatement(SELECT_ALL_QUERY)) {
            ResultSet resultSet = selectStmt.executeQuery();
            while (resultSet.next()) {
                WalletEntity entity = parseResultSet(resultSet);
                result.add(entity);
            }
        }catch (SQLException e) {
            throw new DAOException();
        }
        return result.stream().map(this::fromEntity).collect(Collectors.toList());
    }


    private WalletEntity fromDto(WalletDto dto) {
        WalletEntity entity = new WalletEntity();
        entity.setAmount(dto.getAmount());
        entity.setUserId(dto.getUserId());
        return entity;
    }

    private WalletDto fromEntity(WalletEntity entity) {
        WalletDto dto = new WalletDto();
        dto.setAmount(entity.getAmount());
        dto.setUserId(entity.getUserId());
        return dto;
    }

    private WalletEntity parseResultSet(ResultSet resultSet) throws SQLException {
        long entityId = resultSet.getLong("id");
        BigDecimal amount = resultSet.getBigDecimal("amount");
        long userId = resultSet.getLong("userId");
        return WalletEntity.builder()
                .id(entityId)
                .amount(amount)
                .userId(userId).build();
    }
}
