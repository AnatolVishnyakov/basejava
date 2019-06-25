package com.basejava.webapp.sql;

import com.basejava.webapp.exception.StorageException;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SqlHelper {
    private final ConnectionFactory connectionFactory;

    public SqlHelper(String databaseUrl, String databaseUser, String databasePassword) {
        this.connectionFactory = () -> DriverManager.getConnection(databaseUrl, databaseUser, databasePassword);
    }

    public <T> T executeQuery(String query, QueryExecutor<T> executor) throws StorageException {
        try (Connection connection = connectionFactory.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            return executor.execute(statement);
        } catch (SQLException e) {
            throw ExceptionHandler.convertException(e);
        }
    }

    public <T> T transactionalExecute(SqlTransaction<T> executor) {
        try (Connection connection = connectionFactory.getConnection()) {
            try {
                connection.setAutoCommit(false);
                T result = executor.execute(connection);
                connection.commit();
                return result;
            } catch (SQLException e) {
                connection.rollback();
                throw ExceptionHandler.convertException(e);
            }
        } catch (SQLException e) {
            throw new StorageException(e);
        }
    }
}
