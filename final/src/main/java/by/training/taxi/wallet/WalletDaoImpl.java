package by.training.taxi.wallet;

import by.training.taxi.bean.Bean;
import by.training.taxi.dao.ConnectionManager;
import by.training.taxi.dao.DAOException;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Bean
@Log4j
@AllArgsConstructor
public class WalletDaoImpl implements WalletDao {

    private static final String SELECT_ALL_QUERY = "select wallet_id, wallet_amount, user_account_id from user_wallet";
    private static final String SELECT_BY_ID_QUERY = "select wallet_id, wallet_amount, user_account_id from user_wallet " +
                                                     "where wallet_id = ?";
    private static final String SELECT_BY_USER_ID_QUERY = "select wallet_id, wallet_amount, user_account_id from user_wallet " +
            "where user_account_id = ?";
    private static final String INSERT_QUERY = "insert into user_wallet (wallet_amount, user_account_id) values (?,?)";
    private static final String UPDATE_QUERY = "update user_wallet set wallet_amount=?, user_account_id=? where wallet_id = ?";
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
            return entity.getId();
        } catch (SQLException e) {
            throw new DAOException(e.getMessage());
        }
    }

    @Override
    public boolean update(WalletDto walletDto) throws DAOException {
        WalletEntity entity = fromDto(walletDto);
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement updateStmt = connection.prepareStatement(UPDATE_QUERY)){
            int i = 0;
            updateStmt.setBigDecimal(++i, entity.getAmount());
            updateStmt.setLong(++i, entity.getUserId());
            updateStmt.setLong(++i, entity.getId());
            return updateStmt.executeUpdate() > 0;
        } catch (SQLException e) {
            log.error("Failed to update wallet");
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
            log.error("Failed to delete wallet " + id);
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
            log.error("Failed to get wallet by id: " + id);
            throw new DAOException();
        }
        return result.stream().map(this::fromEntity).findFirst()
                .orElseThrow(() -> new DAOException("Entity not found with given id: " + id));
    }

    @Override
    public List<WalletDto> getByUserId(Long id) throws DAOException {
        List<WalletEntity> result = new ArrayList<>();
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement selectStmt = connection.prepareStatement(SELECT_BY_USER_ID_QUERY)) {
            selectStmt.setLong(1, id);
            ResultSet resultSet = selectStmt.executeQuery();
            while (resultSet.next()) {
                WalletEntity entity = parseResultSet(resultSet);
                result.add(entity);
            }
            return result.stream().map(this::fromEntity).collect(Collectors.toList());
        } catch (SQLException e) {
            log.error("Failed to get wallets by user id: " + id);
            throw new DAOException();
        }
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
            log.error("Failed to get all wallets");
            throw new DAOException();
        }
        return result.stream().map(this::fromEntity).collect(Collectors.toList());
    }

    private WalletEntity fromDto(WalletDto dto) {
        WalletEntity entity = new WalletEntity();
        entity.setId(dto.getId());
        entity.setAmount(dto.getAmount());
        entity.setUserId(dto.getUserId());
        return entity;
    }

    private WalletDto fromEntity(WalletEntity entity) {
        WalletDto dto = new WalletDto();
        dto.setId(entity.getId());
        dto.setAmount(entity.getAmount());
        dto.setUserId(entity.getUserId());
        return dto;
    }

    private WalletEntity parseResultSet(ResultSet resultSet) throws SQLException {
        long entityId = resultSet.getLong("wallet_id");
        BigDecimal amount = resultSet.getBigDecimal("wallet_amount");
        long userId = resultSet.getLong("user_account_id");
        return WalletEntity.builder()
                .id(entityId)
                .amount(amount)
                .userId(userId)
                .build();
    }
}
