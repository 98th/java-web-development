package by.training.taxi.dao;

import java.sql.Connection;

public interface DataSource {
    Connection getConnection() throws ConnectionPoolException;
    void close();
}
