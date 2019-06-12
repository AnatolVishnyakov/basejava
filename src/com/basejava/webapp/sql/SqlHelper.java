package com.basejava.webapp.sql;

import com.basejava.webapp.exception.NotExistStorageException;

import java.sql.*;

public class SqlHelper {
    private static SqlHelper instance;
    private static final int FAILURE_UPDATE = 0;
    private final ConnectionFactory connectionFactory;

    public static SqlHelper getInstance(String databaseUrl, String databaseUser, String databasePassword) {
        if (instance == null) {
            instance = new SqlHelper(databaseUrl, databaseUser, databasePassword);
        }
        return instance;
    }

    public SqlHelper(String databaseUrl, String databaseUser, String databasePassword) {
        this.connectionFactory = () -> DriverManager.getConnection(databaseUrl, databaseUser, databasePassword);
    }

    public ResultSet executeQuery(String query, String[] columns, Operation operation) throws SQLException {
        try (Connection connection = connectionFactory.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(query);
            if (columns != null) {
                for (int i = 0; i < columns.length; i++) {
                    statement.setString(i + 1, columns[i]);
                }
            }
            if (operation == Operation.UPDATE) {
                if (statement.executeUpdate() == FAILURE_UPDATE) {
                    throw new NotExistStorageException(null);
                }
            } else {
                statement.execute();
            }
            return statement.getResultSet();
        }
    }

    public enum Operation {
        SELECT, DELETE, INSERT, UPDATE
    }
}
