package by.training.taxi.dao;

import java.sql.Connection;

public interface ConnectionPool {
    Connection getConnection();
    void close();
}
