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
        String createEnum = "CREATE TYPE IF NOT EXISTS  user_role " +
                "AS ENUM ('client', 'driver', 'admin');";
        String sql = "CREATE TABLE user_account" +
                "(" +
                "    id bigint auto_increment primary key NOT NULL," +
                "    login character varying NOT NULL," +
                "    password character varying," +
                "    is_locked boolean," +
                "    user_role user_role," +
                "    avatar binary" +
                ");";
        executeSql(createEnum);
        executeSql(sql);
        String insertUser = "INSERT INTO user_account (login, password, is_locked, user_role) " +
                "                VALUES ('testUser0', '123456', false, 'driver')";
        executeSql(insertUser);
        String insertUser2 = "INSERT INTO user_account (login, password, is_locked, user_role) " +
                "                VALUES ('testUser1', '654321', true, 'admin')";
        executeSql(insertUser2);
    }

    @Test
    public void shouldFindAllUsers() throws DAOException {
        UserAccountDao userDao = ApplicationContext.getInstance().getBean(UserAccountDao.class);
        Assert.assertNotNull(userDao);
        Assert.assertEquals(2, userDao.findAll().size());
    }

    @Test
    public void shouldSaveAndDeleteUser() throws DAOException {
        UserAccountDao userDao = ApplicationContext.getInstance().getBean(UserAccountDao.class);
        Assert.assertNotNull(userDao);
        UserAccountDto dto = UserAccountDto.builder()
                .password("s9856eds5fs")
                .login("testUser0")
                .role(DRIVER)
                .isLocked(false)
                .build();
        long userId  = userDao.save(dto);
        Assert.assertEquals(3, userId);
        boolean isUserPresent = userDao.getByLogin("testUser0").isPresent();
        Assert.assertTrue(isUserPresent);
        Assert.assertTrue(userDao.delete(userId));
    }

    @Test
    public void shouldFindUserByLogin() throws DAOException {
        UserAccountDao userDao = ApplicationContext.getInstance().getBean(UserAccountDao.class);
        Assert.assertNotNull(userDao);
        boolean isUserPresent = userDao.getByLogin("testUser0").isPresent();
        Assert.assertTrue(isUserPresent);
    }

    @Test
    public void shouldNotFindUserByLogin() throws DAOException {
        UserAccountDao userDao = ApplicationContext.getInstance().getBean(UserAccountDao.class);
        Assert.assertNotNull(userDao);
        boolean isUserPresent = userDao.getByLogin("testUser2").isPresent();
        Assert.assertFalse(isUserPresent);
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
