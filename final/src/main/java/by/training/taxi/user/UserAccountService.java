package by.training.taxi.user;

import java.util.List;
import java.util.Optional;

public interface UserAccountService {

    boolean registerUser(UserAccountDto userDto);

    Optional<UserAccountDto> findByLogin(String login);

    Optional<UserAccountDto> findByLoginAndPassword(String login, String password);

    List<UserAccountDto> getAllUsers();
}