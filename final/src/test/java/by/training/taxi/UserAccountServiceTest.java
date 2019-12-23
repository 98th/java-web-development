package by.training.taxi;

import static by.training.taxi.role.Role.DRIVER;
import by.training.taxi.dao.*;
import by.training.taxi.user.UserAccountDto;
import by.training.taxi.user.UserAccountService;
import by.training.taxi.user.UserServiceException;
import org.junit.*;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.Mockito;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

@RunWith(JUnit4.class)
public class UserAccountServiceTest {



    @Before
    public void createTable() throws SQLException {
        ApplicationContext.initialize();

        String sql = "CREATE TABLE user_account" +
                "(" +
                "    id bigserial NOT NULL," +
                "    login character varying NOT NULL," +
                "    password character varying," +
                "    avatar bytea," +
                "    user_role character varying," +
                "    PRIMARY KEY (id)" +
                ");";
        executeSql(sql);
    }

    @After
    public void dropTable() throws SQLException {
        String sql = "DROP TABLE user_account";
        executeSql(sql);
        ApplicationContext.getInstance().destroy();
    }

    @Test
    public void shouldRegisterUser() throws UserServiceException {
        UserAccountService userService = ApplicationContext.getInstance().getBean(UserAccountService.class);
        Assert.assertNotNull(userService);
        UserAccountDto    dto = new UserAccountDto();
        dto.setLogin("testLogin");
        dto.setPassword("testPass");
        dto.setRole(DRIVER);
        boolean registered = userService.registerUser(dto);
        Assert.assertTrue(registered);

        boolean founded = userService.findByLoginAndPassword("testLogin", "testPass").isPresent();
        Assert.assertTrue(founded);

    }


    private void executeSql(String sql) throws SQLException {
        DataSource dataSource = new DataSourceImpl();
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.executeUpdate();
        }
    }
}
