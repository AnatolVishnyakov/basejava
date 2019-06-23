package com.basejava.webapp.sql;

import com.basejava.webapp.exception.ExistStorageException;
import com.basejava.webapp.exception.StorageException;
import org.postgresql.util.PSQLException;

import java.sql.SQLException;

public class ExceptionHandler {
    private static final String DUPLICATE_CODE_EXCEPTION = "23505";

    public ExceptionHandler() {
    }

    public static StorageException convertException(SQLException e) {
        if (e instanceof PSQLException) {
            if (e.getSQLState().equals(DUPLICATE_CODE_EXCEPTION)) {
                return new ExistStorageException(null);
            }
        }
        return new StorageException(e);
    }
}
