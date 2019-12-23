package by.training.taxi;

import by.training.taxi.car.CarDto;
import by.training.taxi.car.CarService;
import by.training.taxi.car.CarServiceException;
import by.training.taxi.dao.DataSource;
import by.training.taxi.dao.DataSourceImpl;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class CarServiceTest {

    @Before
    public void createTable() throws SQLException {
        ApplicationContext.initialize();
        String createType = "create type requirement_type as enum ('booster', 'pet_friendly', 'baby_seat');";
        String createTable = "CREATE TABLE car_requirement_relation " +
                "(car_id bigint, " +
                "car_requirement requirement_type" +
                ")";
        String sql = "CREATE TABLE user_contact" +
                "(" +
                "    contact_id bigserial NOT NULL," +
                "    first_name character varying NOT NULL," +
                "    last_name character varying," +
                "    email character varying," +
                "    phone character varying," +
                "    user_account_id bigint," +
                "    PRIMARY KEY (contact_id)" +
                ");";
        executeSql(createType);
        executeSql(createTable);
     //   executeSql(sql);
    }

    @After
    public void dropTable() throws SQLException {
        String sql = "DROP TABLE car_requirement_relation";
        executeSql(sql);
        ApplicationContext.getInstance().destroy();
    }

    @Test
    public void shouldRegisterCar() throws CarServiceException {
        CarService carService = ApplicationContext.getInstance().getBean(CarService.class);
        Assert.assertNotNull(carService);
        CarDto car = new CarDto();
        car.setId(1);
        car.setLicencePlateNum("56875421");
        car.setDriverId(1);
        car.setModel("model");
        car.setColor("black");

        boolean registered = carService.save(car);
        Assert.assertTrue(registered);
    }


    private void executeSql(String sql) throws SQLException {
        DataSource dataSource = new DataSourceImpl();
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.executeUpdate();
        }
    }
}
