package by.training.taxi;

import static by.training.taxi.role.Role.DRIVER;
import by.training.taxi.dao.*;
import by.training.taxi.user.UserAccountDao;
import by.training.taxi.user.UserAccountDto;
import org.junit.*;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

@RunWith(JUnit4.class)
public class UserAccountDaoTest {

    @BeforeClass
    public static void contextInitialize() {
        ApplicationContext.initialize();
    }

    @Before
    public void createTable() throws SQLException {
        String sql = "CREATE TABLE user_account" +
                "(" +
                "    id bigserial NOT NULL," +
                "    login character varying NOT NULL," +
                "    password character varying," +
                "    is_locked boolean," +
                "    user_role character varying," +
                "    PRIMARY KEY (id)" +
                ");";
        executeSql(sql);
    }


    @Test
    public void shouldSaveUser() throws DAOException {
        UserAccountDao userDao = ApplicationContext.getInstance().getBean(UserAccountDao.class);
        Assert.assertNotNull(userDao);
        UserAccountDto dto = UserAccountDto.builder()
                .id(1L)
                .password("s9856eds5fs")
                .login("testUser0")
                .role(DRIVER)
                .isLocked(false)
                .build();
        long userId  = userDao.save(dto);
        Assert.assertEquals(1, userId);
        boolean isUserPresent = userDao.getByLogin("testUser0").isPresent();
        Assert.assertTrue(isUserPresent);
    }


    @Test
    public void shouldSaveAndDeleteUser() throws DAOException {
        UserAccountDao userDao = ApplicationContext.getInstance().getBean(UserAccountDao.class);
        Assert.assertNotNull(userDao);
        UserAccountDto dto = UserAccountDto.builder()
                .id(1L)
                .password("s9856eds5fs")
                .login("testUser2")
                .role(DRIVER)
                .isLocked(false)
                .build();
        long userId  = userDao.save(dto);
        Assert.assertEquals(1, userId);
        Assert.assertTrue( userDao.delete(dto));
    }

    @Test
    public void shouldFindAllUsers() throws DAOException {
        UserAccountDao userDao = ApplicationContext.getInstance().getBean(UserAccountDao.class);
        Assert.assertNotNull(userDao);
        UserAccountDto dto = UserAccountDto.builder()
                .id(1L)
                .password("s9856eds5fs")
                .login("testUser1")
                .role(DRIVER)
                .isLocked(false)
                .build();
        UserAccountDto dto2 = UserAccountDto.builder()
                .id(2L)
                .password("s9856eds5fs")
                .login("testUser2")
                .role(DRIVER)
                .isLocked(true)
                .build();
        userDao.save(dto);
        userDao.save(dto2);
        Assert.assertEquals(2, userDao.findAll().size());
        boolean isDeleted2 = userDao.delete(dto2);
        Assert.assertTrue(isDeleted2);
    }

    @Test
    public void shouldFindUserByLogin() throws DAOException {
        UserAccountDao userDao = ApplicationContext.getInstance().getBean(UserAccountDao.class);
        Assert.assertNotNull(userDao);
        UserAccountDto dto = UserAccountDto.builder()
                .id(1L)
                .password("s9856eds5fs")
                .login("testUser2")
                .role(DRIVER)
                .isLocked(false)
                .build();
        long userId  = userDao.save(dto);
        Assert.assertEquals(1, userId);
        boolean isUserPresent = userDao.getByLogin("testUser2").isPresent();
        Assert.assertTrue(isUserPresent);

    }


    @After
    public void dropTable() throws SQLException {
        String sql = "DROP TABLE user_account";
        executeSql(sql);
        ApplicationContext.getInstance().destroy();
    }


    private void executeSql(String sql) throws  SQLException {
        ConnectionManager connectionManager = ApplicationContext.getInstance().getBean(ConnectionManager.class);
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            connection.setAutoCommit(true);
            preparedStatement.executeUpdate();
        }
    }

}
