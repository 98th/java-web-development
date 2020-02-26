package by.training.taxi;

import by.training.taxi.car.CarDao;
import by.training.taxi.contact.ContactDao;
import by.training.taxi.contact.ContactDto;
import by.training.taxi.dao.DAOException;
import by.training.taxi.discount.DiscountDao;
import by.training.taxi.driver.DriverDao;
import by.training.taxi.location.LocationDao;
import by.training.taxi.user.*;
import by.training.taxi.wallet.WalletDao;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static by.training.taxi.user.Role.CLIENT;
import static by.training.taxi.user.Role.DRIVER;

@RunWith(JUnit4.class)
public class UserAccountServiceTest {

    private UserAccountService userService;
    private UserAccountDto expectedUser;

    @Before
    public void init() throws DAOException {
        expectedUser =  UserAccountDto.builder()
                .id(1L)
                .password("s9856eds5fs")
                .login("testUser0")
                .role(DRIVER)
                .isLocked(false)
                .build();
        List<UserAccountDto> userList = new ArrayList<>();
        userList.add(expectedUser);

        UserAccountDao mockUserDao = Mockito.mock(UserAccountDao.class);
        Mockito.when(mockUserDao.save(Mockito.any())).thenReturn(1L);
        Mockito.when(mockUserDao.findAll()).thenReturn(userList);
        Mockito.when(mockUserDao.getByLogin("testUser0")).thenReturn(Optional.of(expectedUser));
        Mockito.when(mockUserDao.getByLogin("unknownUser")).thenThrow(new DAOException());
        Mockito.when(mockUserDao.getById(1L)).thenReturn(expectedUser);
        Mockito.when(mockUserDao.getByLoginAndPassword("login", "pass")).thenReturn(Optional.of(expectedUser));
        Mockito.when(mockUserDao.getByLoginAndPassword("login", "wrongPass")).thenThrow(new DAOException());

        WalletDao mockWalletDao = Mockito.mock(WalletDao.class);
        LocationDao mockLocationDao = Mockito.mock(LocationDao.class);
        DiscountDao mockDiscountDao = Mockito.mock(DiscountDao.class);
        DriverDao mockDriverDao = Mockito.mock(DriverDao.class);
        ContactDao mockContactDao = Mockito.mock(ContactDao.class);
        CarDao mockCarDao = Mockito.mock(CarDao.class);

        userService = new UserAccountServiceImpl(mockUserDao, mockContactDao, mockDriverDao,
                mockCarDao, mockLocationDao, mockWalletDao, mockDiscountDao);
    }

    @Test
    public void shouldTestIfLoginUnique () throws UserServiceException {
        boolean isLoginUnique = userService.isLoginUnique("testUser0");
        Assert.assertFalse(isLoginUnique);
    }

    @Test
    public void shouldRegisterUser () throws UserServiceException {
        UserAccountDto user = new UserAccountDto();
        user.setRole(CLIENT);
        ContactDto contact = new ContactDto();
        user.setContact(contact);
        userService.registerUser(user);
    }

    @Test(expected = UserServiceException.class)
    public void shouldNotFindByLoginAndPassword () throws UserServiceException {
        Optional<UserAccountDto> user = userService.findByLoginAndPassword("login", "wrongPass");
    }

    @Test
    public void shouldFindByLoginAndPassword () throws UserServiceException {
        Optional<UserAccountDto> user = userService.findByLoginAndPassword("login", "pass");
        Assert.assertEquals(1L, (long) user.get().getId());
    }

    @Test
    public void shouldFindAllUsers() throws UserServiceException {
        Assert.assertEquals(1, userService.findAll().size());
    }



}
