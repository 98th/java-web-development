package by.training.taxi.dao;

import org.apache.log4j.Logger;

import java.lang.reflect.Proxy;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ConnectionPoolImpl implements ConnectionPool {
    private static final Logger log = Logger.getLogger(ConnectionPoolImpl.class);

    private static ConnectionPoolImpl INSTANCE;
    private static Lock lock = new ReentrantLock();

    private final int POOL_SIZE = 15;
    private BlockingQueue<Connection> connectionQueue = new ArrayBlockingQueue<>(POOL_SIZE);
    private BlockingQueue<Connection> usedConnections = new ArrayBlockingQueue<>(POOL_SIZE);

    private Driver driver;

    private ConnectionPoolImpl() {
        init();
    }

    private void init() {
        ResourceBundle resource = ResourceBundle.getBundle("db");
        String driverName = resource.getString("db.driver");
        String url = resource.getString("db.url");
        String user = resource.getString("db.user");
        String password = resource.getString("db.password");
        try {
            Class driverClass = Class.forName(driverName);
            driver = (Driver)driverClass.newInstance();
            DriverManager.registerDriver(driver);
            for (int i = 0; i < POOL_SIZE; i++) {
                Connection connection = DriverManager.getConnection(url, user, password);
                connectionQueue.add(connection);
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException e) {
            log.error("Driver cannot be found");
            throw new ConnectionPoolException("Driver cannot be found", e);
        } catch(SQLException e) {
            log.error(e);
            throw new ConnectionPoolException();
        }
        log.info("Connection pool has been created");
    }

    public static ConnectionPoolImpl getInstance() throws ConnectionPoolException {
        if (INSTANCE == null) {
            lock.lock();
            try {
                if (INSTANCE == null) {
                    INSTANCE = new ConnectionPoolImpl();
                }
            } finally {
                lock.unlock();
            }
        }
        return INSTANCE;
    }

    public Connection getConnection() throws ConnectionPoolException {
        try {
            Connection connection = connectionQueue.take();
            usedConnections.put(connection);
            log.info("Connection was taken from connection pool");
            return createProxyConnection(connection);
        } catch (InterruptedException e) {
            log.error("Cannot get connection from connection pool");
            throw new ConnectionPoolException(e);
        }
    }

    public void releaseConnection(Connection connection) {
        if (!usedConnections.contains(connection)) {
            log.error("connection is not from connection pool");
            throw new ConnectionPoolException("connection is not from connection pool");
        }
        if (usedConnections.remove(connection)) {
            connectionQueue.offer(connection);
        }
        log.info("connection was offered to connection pool");
        log.info("connection pool size " + connectionQueue.size());
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
            INSTANCE = null;
            DriverManager.deregisterDriver(driver);
        } catch (SQLException e) {
            log.error("Exception while closing all connections");
        }
    }

    private Connection createProxyConnection(Connection connection) {
        return (Connection) Proxy.newProxyInstance(connection.getClass().getClassLoader(),
                new Class[]{Connection.class},
                (proxy, method, args) -> {
                    if ("close".equals(method.getName())) {
                        releaseConnection(connection);
                        return null;
                    } else {
                        return method.invoke(connection, args);
                    }
                });
    }
}