package by.training.taxi.dao;

import org.apache.log4j.Logger;

import java.lang.reflect.Proxy;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Enumeration;
import java.util.ResourceBundle;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ConnectionPoolImpl implements ConnectionPool {
    private static final Logger log = Logger.getLogger(ConnectionPoolImpl.class);

    private  String url;
    private  String user;
    private  String password;
    private  String driver;
    private  int poolSize;

    private static ConnectionPoolImpl instance;
    private static Lock lock = new ReentrantLock();

    private BlockingQueue<Connection> connectionQueue;
    private BlockingQueue<Connection> usedConnections;

    private ConnectionPoolImpl() {
        init();
    }

    private void init() {
        ResourceBundle resource = ResourceBundle.getBundle("db");

        driver = resource.getString("db.driver");
        url = resource.getString("db.url");
        user = resource.getString("db.user");
        password = resource.getString("db.password");
        poolSize = Integer.parseInt(resource.getString("db.poolSize"));
        connectionQueue = new ArrayBlockingQueue<Connection>(poolSize);
        usedConnections = new ArrayBlockingQueue<Connection>(poolSize);
        try {
            Class.forName(driver);
            for (int i = 0; i < poolSize; i++) {
                Connection connection = DriverManager.getConnection(url, user, password);
                connectionQueue.add(connection);
            }
        } catch (ClassNotFoundException e) {
            log.error(e);
            throw new ConnectionPoolException("Driver cannot be found", e);
        } catch(SQLException e) {
            log.error(e);
            throw new ConnectionPoolException();
        }
        log.info("Connection pool has been created");
    }

    public static ConnectionPoolImpl getInstance() throws ConnectionPoolException {
        if (instance == null) {
            lock.lock();
            if (instance == null) {
                instance = new ConnectionPoolImpl();
            }
            lock.unlock();
        }
        return instance;
    }

    public Connection getConnection() throws ConnectionPoolException {
        lock.lock();
        Connection proxyConnection = null;
        try {
            Connection connection = connectionQueue.take();
            usedConnections.add(connection);
            proxyConnection = createProxyConnection(connection);
            return proxyConnection;
        } catch (InterruptedException e) {
            throw new ConnectionPoolException(e);
        } finally {
            lock.unlock();
        }
    }

    public void releaseConnection(Connection connection) {
        try {
            lock.lock();
            if (!usedConnections.contains(connection)) {
                log.error("connection in not from connection pool");
                throw new ConnectionPoolException("connection is not from connection pool");
            }
            if (!connection.isClosed()) {
                connection.close();
            }
            usedConnections.remove(connection);
            connectionQueue.offer(connection); // TODO
        } catch(SQLException e) {
            throw new ConnectionPoolException(e);
        }
        finally {
            lock.unlock();
        }
    }

    @Override
    public void close() {
        try {
            for (Connection i : connectionQueue) {
                i.close();
            }
            for (Connection i : usedConnections) {
                i.close();
            }
            instance = null;
            Enumeration<Driver> drivers = DriverManager.getDrivers();
            while (drivers.hasMoreElements()){
                DriverManager.deregisterDriver(drivers.nextElement());
            }
        } catch (SQLException e) {
        }
    }

    private Connection createProxyConnection(Connection connection) {
        return (Connection) Proxy.newProxyInstance(connection.getClass().getClassLoader(),
                new Class[]{Connection.class},
                (proxy, method, args) -> {
                    if ("close".equals(method.getName())) {
                        releaseConnection(connection);
                        return null;
                    } else if ("hashCode".equals(method.getName())) {
                        return connection.hashCode();
                    } else {
                        return method.invoke(connection, args);
                    }
                });
    }
}