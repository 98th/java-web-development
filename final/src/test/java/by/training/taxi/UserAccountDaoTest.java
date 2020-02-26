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
        String createEnum = "CREATE TYPE IF NOT EXISTS user_role " +
                "AS ENUM ('client', 'driver', 'admin');";
        executeSql(createEnum);
        String sql = "CREATE TABLE IF NOT EXISTS user_account" +
                "(" +
                "    id bigint auto_increment primary key NOT NULL," +
                "    login character varying NOT NULL," +
                "    password character varying," +
                "    is_locked boolean," +
                "    user_role user_role," +
                "    avatar binary" +
                ");";

        executeSql(sql);
        String createContact = "CREATE TABLE IF NOT EXISTS user_contact " +
                "(" +
                "    contact_id bigint auto_increment primary key," +
                "    first_name character varying," +
                "    last_name character varying," +
                "    email character varying," +
                "    phone character varying," +
                "    user_account_id bigint" +
                ");";
        executeSql(createContact);
        String insertContacts = "INSERT INTO user_contact (first_name, last_name, phone, email, user_account_id) " +
                "                VALUES ('Ivan', 'Olegov', '375299638521', 'olegOleg@gmail.com', 1), " +
                "                    ('Oleg', 'Ivanov', '375299638521', 'olegOleg@gmail.com', 2) ";
        executeSql(insertContacts);
        String discount = "CREATE TABLE IF NOT EXISTS discount" +
                "(" +
                "    discount_id bigint auto_increment primary key," +
                "    discount_amount bigint" +
                ");";
        executeSql(discount);
        String insertDiscounts = "INSERT INTO discount (discount_id, discount_amount) " +
                "                VALUES (1, 5), " +
                "                    (2, 10) ";

        executeSql(insertDiscounts);

        String insertUsers = "INSERT INTO user_account (login, password, is_locked, user_role) " +
                "                VALUES ('testUser1', '123456', false, 'driver'), " +
                "                        ('testUser2', '654321', true, 'admin')";

        executeSql(insertUsers);

    }

    @Test
    public void shouldFindAllUsers() throws DAOException {
        UserAccountDao userDao = ApplicationContext.getInstance().getBean(UserAccountDao.class);
        Assert.assertNotNull(userDao);
        Assert.assertEquals(2, userDao.findAll().size());
    }

    @Test
    public void shouldGetAllWithContact() throws DAOException {
        UserAccountDao userDao = ApplicationContext.getInstance().getBean(UserAccountDao.class);
        Assert.assertNotNull(userDao);
        Assert.assertEquals(1, userDao.getAllWithContact().size());
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
    public void shouldUpdateUser() throws DAOException {
        UserAccountDao userDao = ApplicationContext.getInstance().getBean(UserAccountDao.class);
        Assert.assertNotNull(userDao);
        UserAccountDto user = userDao.getById(1L);
        Assert.assertTrue(!userDao.getById(1L).getIsLocked());
        user.setLocked(true);
        Assert.assertTrue(userDao.update(user));
        Assert.assertTrue(userDao.getById(1L).getIsLocked());

    }

    @Test
    public void shouldFindUserByLogin() throws DAOException {
        UserAccountDao userDao = ApplicationContext.getInstance().getBean(UserAccountDao.class);
        Assert.assertNotNull(userDao);
        boolean isUserPresent = userDao.getByLogin("testUser1").isPresent();
        Assert.assertTrue(isUserPresent);
    }

    @Test
    public void shouldNotFindUserByLogin() throws DAOException {
        UserAccountDao userDao = ApplicationContext.getInstance().getBean(UserAccountDao.class);
        Assert.assertNotNull(userDao);
        boolean isUserPresent = userDao.getByLogin("testUser5").isPresent();
        Assert.assertFalse(isUserPresent);
    }

    @After
    public void destroy() throws SQLException {
        String dropType = "DROP TYPE user_role";
        String dropUsers = "DROP TABLE user_account";
        String dropContact = "DROP TABLE user_contact";
        String dropDiscount = "DROP TABLE discount";
        executeSql(dropContact);
        executeSql(dropDiscount);
        executeSql(dropUsers);
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
