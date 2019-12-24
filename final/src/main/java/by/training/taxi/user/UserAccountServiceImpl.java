package by.training.taxi.user;

import by.training.taxi.bean.Bean;
import by.training.taxi.car.CarDao;
import by.training.taxi.car.CarDto;
import by.training.taxi.contact.ContactDao;
import by.training.taxi.contact.ContactDto;
import by.training.taxi.dao.DAOException;
import by.training.taxi.dao.TransactionManager;
import by.training.taxi.dao.Transactional;
import by.training.taxi.discount.DiscountDao;
import by.training.taxi.discount.DiscountDto;
import by.training.taxi.driver.DriverDao;
import by.training.taxi.driver.DriverDto;
import by.training.taxi.location.LocationDao;
import by.training.taxi.location.LocationDto;
import by.training.taxi.util.LocationUtil;
import by.training.taxi.wallet.WalletDao;
import by.training.taxi.wallet.WalletDto;
import by.training.taxi.wallet.WalletService;
import by.training.taxi.wallet.WalletServiceException;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j;
import org.h2.engine.User;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import static by.training.taxi.role.Role.DRIVER;

@AllArgsConstructor
@Bean
@Log4j
public class UserAccountServiceImpl implements UserAccountService{
    private TransactionManager transactionManager;

    private UserAccountDao userAccountDao;
    private WalletDao walletDao;
    private ContactDao contactDao;
    private DriverDao driverDao;
    private CarDao carDao;
    private LocationDao locationDao;
    private DiscountDao discountDao;

    public boolean fill(long id, BigDecimal amount) throws WalletServiceException {
        try {
            WalletDto wallet = walletDao.getById(id);
            BigDecimal sum = wallet.getAmount().add(amount);
            wallet.setAmount(sum);
            return  walletDao.update(wallet);
        } catch (DAOException e) {
            throw new WalletServiceException(e.getMessage());
        }
    }

    @Override
    public boolean isLoginUnique(String login)  throws UserServiceException {
        try {
            return !userAccountDao.getByLogin(login).isPresent();
        } catch (DAOException e) {
            throw new UserServiceException(e.getMessage());
        }
    }

    @Override
    public UserAccountDto findById(Long id) throws UserServiceException {
        try {
            return userAccountDao.getById(id);
        } catch (DAOException e) {
            log.error("cannot find user with id " + id);
            throw new UserServiceException(e.getMessage());
        }
    }

    @Override
    public Optional<UserAccountDto> findByLogin(String login) throws UserServiceException {
        try {
            return userAccountDao.getByLogin(login);
        } catch (DAOException  e) {
            throw new UserServiceException(e.getMessage());
        }
    }

    @Override
    public boolean update(UserAccountDto userAccountDto) throws UserServiceException {
        try {
            return userAccountDao.update(userAccountDto);
        } catch (DAOException e) {
            log.error("User updating exception");
            throw new UserServiceException(e.getMessage());
        }
    }


    @Override
    @Transactional
    public void transfer(long userId, long driverId, BigDecimal amount) throws  UserServiceException {
        try {
            transactionManager.beginTransaction();
            WalletDto userWallet = walletDao.getById(userId);
            WalletDto driverWallet = walletDao.getById(driverId);
            BigDecimal sumFrom = userWallet.getAmount();
            BigDecimal sumTo = driverWallet.getAmount();
            userWallet.setAmount(sumFrom.subtract(amount));
            driverWallet.setAmount(sumTo.add(amount));
            walletDao.update(userWallet);
            walletDao.update(driverWallet);
            transactionManager.commitTransaction();
        } catch (SQLException | DAOException e) {
            try {
                transactionManager.rollbackTransaction();
            } catch (SQLException k) {
                throw new UserServiceException();
            }
        }
    }

    @Override
    public Optional<UserAccountDto> findByLoginAndPassword(String login, String password)  throws UserServiceException {
        try {
            return userAccountDao.getByLoginAndPassword(login, password);
        } catch (DAOException  e) {
            throw new UserServiceException(e.getMessage());
        }
    }

    @Override
    @Transactional
    public void fullUpdate(UserAccountDto user) throws  UserServiceException {
        try {
            transactionManager.beginTransaction();
            userAccountDao.update(user);
            ContactDto contact = user.getContact();
            contactDao.update(contact);
            WalletDto wallet = user.getWallet();
            walletDao.update(wallet);
            LocationDto location = user.getLocation();
            locationDao.update(location);
            if (DRIVER == user.getRole()) {
                DriverDto driver = user.getDriver();
                driver.setLocation(location);
                driverDao.update(driver);
                CarDto car = driver.getCar();
                carDao.update(car);
            }
            transactionManager.commitTransaction();
        } catch (SQLException | DAOException e) {
            try {
                transactionManager.rollbackTransaction();
            } catch (SQLException ex) {
                throw new UserServiceException();
            }
        }
    }


    @Override
    @Transactional
    public long registerUser(UserAccountDto user) throws UserServiceException, SQLException {
        try {
            transactionManager.beginTransaction();
            Long id = userAccountDao.save(user);
            ContactDto contact = user.getContact();
            contact.setUserId(id);
            contactDao.save(contact);
            user.setContact(contact);
            WalletDto wallet = WalletDto.builder()
                    .id(id)
                    .amount(new BigDecimal("0"))
                    .build();
            walletDao.save(wallet);
            user.setWallet(wallet);
            DiscountDto discount = DiscountDto.builder()
                    .id(id)
                    .amount(0)
                    .build();
            LocationDto location = LocationDto.builder()
                    .id(id)
                    .latitude(LocationUtil.generateLatitude())
                    .longitude(LocationUtil.generateLongitude())
                    .build();
            locationDao.save(location);
            user.setLocation(location);
            discountDao.save(discount);
            user.setDiscount(discount);
            user.setId(id);
            if (DRIVER == user.getRole()) {
                DriverDto driver = user.getDriver();
                driver.setLocation(location);
                driver.setUserId(id);
                Long driverId = driverDao.save(driver);
                CarDto car = driver.getCar();
                car.setDriverId(driverId);
                carDao.save(car);
                driver.setCar(car);
                user.setDriver(driver);
                transactionManager.commitTransaction();
            }
            return id;
        } catch (DAOException  e) {
            transactionManager.rollbackTransaction();
            log.error("Failed to register user");
            throw new UserServiceException();
        }
    }

    @Override
    public List<UserAccountDto> findAll()  throws UserServiceException {
        try {
            return userAccountDao.findAll();
        } catch (DAOException e) {
            throw new UserServiceException();
        }
    }

    @Override
    public List<UserAccountDto> getAllWithContact() throws UserServiceException {
        try {
            return userAccountDao.getAllWithContact();
        } catch (DAOException e) {
            throw new UserServiceException(e.getMessage());
        }
    }
}
