package by.training.taxi.user;

import by.training.taxi.bean.Bean;
import by.training.taxi.contact.ContactDao;
import by.training.taxi.dao.DAOException;
import by.training.taxi.dao.Transactional;
import by.training.taxi.role.RoleDao;
import by.training.taxi.wallet.WalletDao;
import lombok.AllArgsConstructor;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Bean
public class UserAccountServiceImpl implements UserAccountService{
    private UserAccountDao userAccountDao;
    private WalletDao walletDao;
    private ContactDao contactDao;

    @Override
    public Optional<UserAccountDto> findByLogin(String login) {
        try {
            return userAccountDao.getByLogin(login);
        } catch (DAOException  e) {
            throw new UserServiceException(e.getMessage());
        }
    }

    @Override
    public Optional<UserAccountDto> findByLoginAndPassword(String login, String password) {
        try {
            return userAccountDao.getByLoginAndPassword(login, password);
        } catch (DAOException  e) {
            throw new UserServiceException(e.getMessage());
        }
    }

    @Override
    @Transactional
    public boolean registerUser(UserAccountDto entity) {
        try {
            Long id = userAccountDao.save(entity);
            return true;
        } catch (DAOException e) {
            return false;
        }
    }

    @Override
    public List<UserAccountDto> getAllUsers() {
        try {
            return userAccountDao.findAll();
        } catch (DAOException e) {
            throw new UserServiceException();
        }
    }
}
