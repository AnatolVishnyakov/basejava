package com.basejava.webapp.storage;

import com.basejava.webapp.exception.NotExistStorageException;
import com.basejava.webapp.exception.StorageException;
import com.basejava.webapp.model.Resume;
import com.basejava.webapp.sql.ConnectionFactory;

import java.sql.*;
import java.util.List;

public class SqlStorage implements Storage {
    public final ConnectionFactory connectionFactory;

    public SqlStorage(String databaseUrl, String databaseUser, String databasePassword) {
        this.connectionFactory = () -> DriverManager.getConnection(databaseUrl, databaseUser, databasePassword);
    }

    @Override
    public void clear() {
        try (Connection connection = connectionFactory.getConnection();
             PreparedStatement statement = connection.prepareStatement("DELETE FROM resume;")) {
            statement.execute();
        } catch (SQLException e) {
            new StorageException(e);
        }
    }

    @Override
    public void update(Resume resume) {

    }

    @Override
    public void save(Resume resume) {

    }

    @Override
    public Resume get(String uuid) {
        try (Connection connection = connectionFactory.getConnection();
             PreparedStatement statement = connection.prepareStatement("SELECT * FROM resume WHERE uuid = ?;")) {
            statement.setString(1, uuid);
            ResultSet result = statement.executeQuery();
            if (!result.next()) {
                throw new NotExistStorageException(uuid);
            }
            Resume resume = new Resume(uuid, result.getString("full_name"));
            return resume;
        } catch (SQLException e) {
            throw new StorageException(e);
        }
    }

    @Override
    public void delete(String uuid) {
        try (Connection connection = connectionFactory.getConnection();
            PreparedStatement statement = connection.prepareStatement("DELETE FROM resume WHERE uuid = ?;")) {
            statement.setString(1, uuid);
            statement.execute();
        } catch (SQLException e) {
            throw new StorageException(e);
        }
    }

    @Override
    public List<Resume> getAllSorted() {
        return null;
    }

    @Override
    public int size() {
        return 0;
    }
}
