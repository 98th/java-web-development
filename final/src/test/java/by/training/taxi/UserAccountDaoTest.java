package by.training.taxi;

import static by.training.taxi.user.Role.DRIVER;
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
    public static void initContext() {
        ApplicationContext.initialize();
    }

    @Before
    public void createTable() throws SQLException {
        String createEnum = "CREATE TYPE user_role " +
                "AS ENUM ('client', 'driver', 'admin');";
        String sql = "CREATE TABLE user_account" +
                "(" +
                "    id bigint auto_increment primary key NOT NULL," +
                "    login character varying NOT NULL," +
                "    password character varying," +
                "    is_locked boolean," +
                "    user_role user_role" +
                ");";
        executeSql(createEnum);
        executeSql(sql);
    }

    @Test
    public void shouldFindAllUsers() throws DAOException {
        UserAccountDao userDao = ApplicationContext.getInstance().getBean(UserAccountDao.class);
        Assert.assertNotNull(userDao);
        UserAccountDto dto1 = UserAccountDto.builder()
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
        long saved1 = userDao.save(dto1);
        long saved2 = userDao.save(dto2);
        Assert.assertEquals(2, userDao.findAll().size());
        boolean isDeleted1 = userDao.delete(saved1);
        Assert.assertTrue(isDeleted1);
        boolean isDeleted2 = userDao.delete(saved2);
        Assert.assertTrue(isDeleted2);
    }

    @Test
    public void shouldSaveAndDeleteUser() throws DAOException {
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
        Assert.assertTrue(userDao.delete(userId));
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
    public void destroy() throws SQLException {
        String dropType = "DROP TYPE user_role";
        String sql = "DROP TABLE user_account";
        executeSql(sql);
        executeSql(dropType);
    }

    @AfterClass
    public static void destroyContext(){
        ApplicationContext.getInstance().destroy();
    }

    private void executeSql(String sql) throws SQLException {
        ConnectionManager connectionManager = ApplicationContext.getInstance().getBean(ConnectionManager.class);
        Assert.assertNotNull(connectionManager);
        try (Connection connection = connectionManager.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            connection.setAutoCommit(true);
            preparedStatement.executeUpdate();
        }
    }
}
