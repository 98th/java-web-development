package by.training.taxi;

import by.training.taxi.dao.ConnectionManager;
import by.training.taxi.dao.DAOException;
import by.training.taxi.driver.DriverDao;
import by.training.taxi.driver.DriverDto;
import org.junit.*;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

@RunWith(JUnit4.class)
public class DriverDaoTest {

    @BeforeClass
    public static void initContext() {
        ApplicationContext.initialize();
    }

    @Before
    public void createTable() throws SQLException {
        String sql = "CREATE TABLE driver" +
                "(" +
                "    driver_id bigint auto_increment primary key," +
                "    driving_licence_number character varying NOT NULL," +
                "    user_account_id bigint NOT NULL," +
                "    car_id bigint NOT NULL," +
                "    is_working boolean NOT NULL," +
                ");";
        executeSql(sql);
        String insertDriver = "INSERT INTO driver (driving_licence_number, user_account_id, car_id, is_working) " +
                                " VALUES ('5AA 000031', 1, 2, true)";
        executeSql(insertDriver);
        String insertDriver2 = "INSERT INTO driver (driving_licence_number, user_account_id, car_id, is_working) " +
                " VALUES ('9DA 785453', 8, 5, false)";
        executeSql(insertDriver2);
    }

    @Test
    public void shouldSaveAndDeleteDriver() throws DAOException {
        DriverDao driverDao = ApplicationContext.getInstance().getBean(DriverDao.class);
        Assert.assertNotNull(driverDao);
        DriverDto driver = DriverDto.builder()
                .drivingLicenceNum("4WR 895659")
                .userId(5)
                .carId(4)
                .isWorking(false)
                .build();
        long savedDriver = driverDao.save(driver);
        Assert.assertEquals(3, savedDriver);
        Assert.assertTrue(driverDao.delete(savedDriver));
    }

    @Test
    public void shouldFindDriverById() throws DAOException {
        DriverDao driverDao = ApplicationContext.getInstance().getBean(DriverDao.class);
        Assert.assertNotNull(driverDao);
        DriverDto driver = driverDao.getById(1L);
        Assert.assertNotNull(driver);
        Assert.assertEquals(true, driver.isWorking());
    }

    @Test(expected = DAOException.class)
    public void shouldNotFindDriverById() throws DAOException {
        DriverDao driverDao = ApplicationContext.getInstance().getBean(DriverDao.class);
        Assert.assertNotNull(driverDao);
        driverDao.getById(78L);
    }

    @Test
    public void shouldFindAllDrivers() throws DAOException {
        DriverDao driverDao = ApplicationContext.getInstance().getBean(DriverDao.class);
        Assert.assertNotNull(driverDao);
        Assert.assertEquals(2, driverDao.findAll().size());
    }

    @Test
    public void shouldUpdateDriver() throws DAOException {
        DriverDao driverDao = ApplicationContext.getInstance().getBean(DriverDao.class);
        Assert.assertNotNull(driverDao);
        DriverDto driver = driverDao.getById(1L);
        driver.setWorking(false);
        Assert.assertTrue(driverDao.update(driver));
        Assert.assertEquals(false, driver.isWorking());
    }

    @Test
    public void shouldGetByUserId() throws DAOException {
        DriverDao driverDao = ApplicationContext.getInstance().getBean(DriverDao.class);
        Assert.assertNotNull(driverDao);
        Assert.assertNotNull(driverDao.getByUserId(8L));
    }

    @Test(expected = DAOException.class)
    public void shouldNotGetByUserId() throws DAOException {
        DriverDao driverDao = ApplicationContext.getInstance().getBean(DriverDao.class);
        Assert.assertNotNull(driverDao);
        Assert.assertNotNull(driverDao.getByUserId(859L));
    }

    @After
    public void dropTable() throws SQLException {
        String sql = "DROP TABLE driver";
        executeSql(sql);
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
