package by.training.taxi;

import by.training.taxi.car.CarDao;
import by.training.taxi.car.CarDto;
import by.training.taxi.car.RequirementType;
import by.training.taxi.dao.ConnectionManager;
import by.training.taxi.dao.DAOException;
import org.junit.*;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

import static by.training.taxi.car.RequirementType.BABY_SEAT;
import static by.training.taxi.car.RequirementType.BOOSTER;

@RunWith(JUnit4.class)
public class CarDaoTest {

    @BeforeClass
    public static void initContext() {
        ApplicationContext.initialize();
    }

    @Before
    public void createTable() throws SQLException {
        String createEnum = "CREATE TYPE IF NOT EXISTS requirement_type " +
                "AS ENUM ('pet_friendly', 'booster', 'baby_seat');";
        String sql = "CREATE TABLE IF NOT EXISTS car" +
                "(" +
                "    car_id  bigint auto_increment primary key NOT NULL," +
                "    car_color character varying NOT NULL," +
                "    car_model character varying NOT NULL," +
                "    licence_plate_number character varying NOT NULL" +
                ");";
        String createCarRequirementRel = "CREATE TABLE IF NOT EXISTS car_requirement_relation " +
                "(" +
                "    car_id  integer NOT NULL," +
                "    requirement_type requirement_type," +
                ");";
        executeSql(createEnum);
        executeSql(sql);
        executeSql(createCarRequirementRel);
    }

    @Test
    public void shouldUpdateCar() throws DAOException {
        CarDao carDao = ApplicationContext.getInstance().getBean(CarDao.class);
        Assert.assertNotNull(carDao);
        CarDto car = getCar();
        long carId = carDao.save(car);
        Assert.assertEquals(1L, carId);
        car.setModel("someModel2");
        Assert.assertTrue(carDao.update(car));
        Assert.assertEquals("someModel2", car.getModel());
    }

    @Test
    public void shouldAddRequirement() throws DAOException {
        CarDao carDao = ApplicationContext.getInstance().getBean(CarDao.class);
        Assert.assertNotNull(carDao);
        CarDto car = getCar();
        long carId = carDao.save(car);
        Assert.assertEquals(1L, carId);
        Set<RequirementType> requirement = new HashSet<>();
        requirement.add(BOOSTER);
        requirement.add(BABY_SEAT);
        Assert.assertTrue(carDao.addRequirementToCar(1, requirement));
    }

    @Test
    public void shouldFindAllCars() throws DAOException {
        CarDao carDao = ApplicationContext.getInstance().getBean(CarDao.class);
        Assert.assertNotNull(carDao);
        CarDto car = getCar();
        CarDto car2 = CarDto.builder()
                .id(2L)
                .color("black")
                .licencePlateNum("7857587")
                .model("someModel2")
                .build();
        carDao.save(car);
        carDao.save(car2);
        Assert.assertEquals(2, carDao.findAll().size());
    }

    @Test
    public void shouldSaveAndDeleteCar() throws DAOException {
        CarDao carDao = ApplicationContext.getInstance().getBean(CarDao.class);
        Assert.assertNotNull(carDao);
        CarDto car = getCar();
        long carId = carDao.save(car);
        Assert.assertEquals(1L, carId);
        Assert.assertTrue(carDao.delete(carId));
    }

    @After
    public void destroy() throws SQLException {
        String dropType = "DROP TYPE requirement_type";
        String dropCar = "DROP TABLE car";
        String dropCarRequirementRel = "DROP TABLE car_requirement_relation";
        executeSql(dropCarRequirementRel);
        executeSql(dropCar);
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

    private CarDto getCar() {
        return CarDto.builder()
                .id(1L)
                .color("black")
                .licencePlateNum("865585")
                .model("someModel")
                .build();
    }
}
