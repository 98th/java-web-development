package by.training.taxi;

import by.training.taxi.car.CarDto;
import by.training.taxi.car.CarService;
import by.training.taxi.car.CarServiceException;
import by.training.taxi.dao.ConnectionManager;
import by.training.taxi.dao.DataSource;
import by.training.taxi.dao.DataSourceImpl;
import lombok.extern.log4j.Log4j;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

@RunWith(JUnit4.class)
@Log4j
public class CarServiceTest {

    @Before
    public void createTable() throws SQLException {
        try {
            ApplicationContext.initialize();
        } catch (Exception e) {
            log.warn("Context was already initialized");
        }
        ApplicationContext.initialize();
        String sql = "CREATE TABLE CAR" +
                "(" +
                "    car_id bigserial NOT NULL," +
                "    car_color character varying NOT NULL," +
                "    car_model character varying NOT NULL," +
                "    driver_id bigint NOT NULL," +
                "    licence_plate_number character varying NOT NULL," +
                "    PRIMARY KEY (car_id)" +
                ");";
        executeSql(sql);
    }

    @After
    public void dropTable() throws SQLException {
        String sql = "DROP TABLE car";
        executeSql(sql);
        ApplicationContext.getInstance().destroy();
    }

    @Test
    public void shouldRegisterCar() throws CarServiceException {
        CarService carService = ApplicationContext.getInstance().getBean(CarService.class);
        Assert.assertNotNull(carService);
        CarDto car = CarDto.builder()
                .id(1L)
                .color("black")
                .licencePlateNum("865585")
                .model("someModel")
                .driverId(2L)
                .build();
        boolean registered = carService.save(car);
        Assert.assertTrue(registered);
    }

    @Test
    public void shouldFindByDriverId() throws CarServiceException {
        CarService carService = ApplicationContext.getInstance().getBean(CarService.class);
        Assert.assertNotNull(carService);
        CarDto car = CarDto.builder()
                .id(1L)
                .color("black")
                .licencePlateNum("865585")
                .model("someModel")
                .driverId(2L)
                .build();
        carService.save(car);
        CarDto carDto = carService.getByDriverId(2L);
        Assert.assertEquals(1, carDto.getId());
    }

    private void executeSql(String sql) throws SQLException {
        ConnectionManager connectionManager = ApplicationContext.getInstance().getBean(ConnectionManager.class);
        try (Connection connection = connectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            connection.setAutoCommit(true);
            preparedStatement.executeUpdate();
        }
    }

}
