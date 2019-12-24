package by.training.taxi;

import by.training.taxi.dao.*;
import by.training.taxi.user.UserAccountEntity;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import javax.sql.DataSource;
import java.sql.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;


@RunWith(JUnit4.class)
public class ConnectionPoolTest {

    private ConnectionPoolImpl connectionPool;

    @Before
    public void init () throws ConnectionPoolException {
        connectionPool = ConnectionPoolImpl.getInstance();
    }

    @Test
    public void shouldGetConnection()  throws ConnectionPoolException {
        Connection connection = connectionPool.getConnection();
        Assert.assertNotNull(connection);
    }

    @Test
    public void shouldTestReleasingWrongConnection() throws SQLException, ClassNotFoundException {
        Class.forName ("org.h2.Driver");
        Connection connection = DriverManager.getConnection("jdbc:h2:mem:");
        assertThrows(RuntimeException.class, () -> connectionPool.releaseConnection(connection));
    }


    @After
    public void destroy(){
        connectionPool.close();
    }

}
