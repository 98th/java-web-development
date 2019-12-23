package by.training.taxi.dao;

import by.training.taxi.bean.Bean;

import java.sql.Connection;

@Bean
public class DataSourceImpl implements DataSource {
    private final ConnectionPool connectionPool;

    public DataSourceImpl() {
        connectionPool = ConnectionPoolImpl.getInstance();
    }

    public Connection getConnection() throws ConnectionPoolException {
        return connectionPool.getConnection();
    }

    @Override
    public void close() {
        connectionPool.close();
    }
}
