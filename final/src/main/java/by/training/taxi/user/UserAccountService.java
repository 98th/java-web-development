package by.training.taxi.user;

import by.training.taxi.wallet.WalletServiceException;
import org.h2.engine.User;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface UserAccountService {

    boolean update(UserAccountDto userAccountDto) throws UserServiceException;

    UserAccountDto findById(Long id) throws UserServiceException;

    long  registerUser(UserAccountDto userDto) throws UserServiceException;

    Optional<UserAccountDto>  findByLoginAndPassword(String login, String password) throws UserServiceException;

    List<UserAccountDto> findAll() throws UserServiceException;

    boolean isLoginUnique(String login) throws UserServiceException;

    void fullUpdate(UserAccountDto userAccountDto) throws UserServiceException;

    void transfer(long userId, long driverId, BigDecimal amount) throws  UserServiceException;

    List<UserAccountDto> getAllWithContact() throws UserServiceException;

    boolean fill(long id, BigDecimal amount) throws WalletServiceException;
}