package com.basejava.webapp.sql;

import com.basejava.webapp.exception.ExistStorageException;
import com.basejava.webapp.exception.StorageException;

import java.sql.*;

public class SqlHelper {
    private static final String DUPLICATE_CODE = "23505";
    private final ConnectionFactory connectionFactory;

    public SqlHelper(String databaseUrl, String databaseUser, String databasePassword) {
        this.connectionFactory = () -> DriverManager.getConnection(databaseUrl, databaseUser, databasePassword);
    }

    public <T> T executeQuery(String query, QueryExecutor<T> executor) throws StorageException {
        try (Connection connection = connectionFactory.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            return executor.execute(statement);
        } catch (SQLException e) {
            if (e.getSQLState().equals(DUPLICATE_CODE)) {
                throw new ExistStorageException(null);
            }
            throw new StorageException(e);
        }
    }

    public <T> T transactionExecute(SqlTransaction<T> executor) {
        try (Connection connection = connectionFactory.getConnection()) {
            // TODO
        } catch (SQLException e) {
            // TODO
        }
    }
}
