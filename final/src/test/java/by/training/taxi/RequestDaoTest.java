package by.training.taxi;

import by.training.taxi.dao.ConnectionManager;
import by.training.taxi.dao.DAOException;
import by.training.taxi.request.RequestDao;
import by.training.taxi.request.RequestDto;
import by.training.taxi.request.RequestStatus;
import by.training.taxi.util.LocationUtil;
import org.junit.*;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Date;

@RunWith(JUnit4.class)
public class RequestDaoTest {

    @BeforeClass
    public static void initContext() {
        ApplicationContext.initialize();
    }

    @Before
    public void createTable() throws SQLException {
        String createType = "CREATE TYPE request_status " +
                "  AS ENUM ('offered', 'confirmed', 'declined', 'cancelled');";
        executeSql(createType);
        String createTable = "CREATE TABLE car_request" +
                "(" +
                "    request_id bigint auto_increment primary key," +
                "    client_id bigint NOT NULL," +
                "    driver_id bigint NOT NULL," +
                "    request_date date NOT NULL," +
                "    request_price numeric NOT NULL," +
                "    pick_location character varying NOT NULL," +
                "    drop_location character varying NOT NULL," +
                "    request_status request_status NOT NULL," +
                ");";
        executeSql(createTable);
        String insertRequest = "INSERT INTO car_request (client_id, driver_id, request_date, " +
                " request_price, pick_location, drop_location, request_status) " +
                " VALUES (1, 2, '2013-10-10', 25, 0.11221, 0.2254125, 'offered')";
        executeSql(insertRequest);
        String insertRequest2 = "INSERT INTO car_request (client_id, driver_id, request_date, " +
                " request_price, pick_location, drop_location, request_status) " +
                " VALUES (1, 2, '2013-10-10', 25, 0.11221, 0.2254125, 'offered')";
        executeSql(insertRequest2);
    }

    @Test
    public void shouldSaveAndDeleteWallet() throws DAOException {
        RequestDao requestDao = ApplicationContext.getInstance().getBean(RequestDao.class);
        Assert.assertNotNull(requestDao);
        RequestDto request = RequestDto.builder()
                .clientId(1L)
                .driverId(2L)
                .dropLocation(LocationUtil.getRandomLocation())
                .pickLocation(LocationUtil.getRandomLocation())
                .requestStatus(RequestStatus.DECLINED)
                .requestDate(new Date())
                .price(new BigDecimal("25"))
                .build();
        long savedRequest = requestDao.save(request);
        Assert.assertEquals(3, savedRequest);
        Assert.assertTrue(requestDao.delete(savedRequest));
    }

    @Test
    public void shouldFindRequestById() throws DAOException {
        RequestDao requestDao = ApplicationContext.getInstance().getBean(RequestDao.class);
        Assert.assertNotNull(requestDao);
        RequestDto request = requestDao.getById(1L);
        Assert.assertNotNull(request);
    }

    @Test(expected = DAOException.class)
    public void shouldNotFindRequestById() throws DAOException {
        RequestDao requestDao = ApplicationContext.getInstance().getBean(RequestDao.class);
        Assert.assertNotNull(requestDao);
        requestDao.getById(78L);
    }

    @Test
    public void shouldFindAllRequests() throws DAOException {
        RequestDao requestDao = ApplicationContext.getInstance().getBean(RequestDao.class);
        Assert.assertNotNull(requestDao);
        Assert.assertEquals(2, requestDao.findAll().size());
    }

    @Test
    public void shouldUpdateWallet() throws DAOException {
        RequestDao requestDao = ApplicationContext.getInstance().getBean(RequestDao.class);
        Assert.assertNotNull(requestDao);
        RequestDto request = requestDao.getById(1L);
        request.setClientId(10L);
        Assert.assertTrue(requestDao.update(request));
        Assert.assertEquals(10L, request.getClientId());
    }

    @After
    public void dropTable() throws SQLException {
        String dropTable = "DROP TABLE car_request";
        executeSql(dropTable);
        String dropType = "DROP TYPE request_status";
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
