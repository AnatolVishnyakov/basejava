package com.basejava.webapp.storage;

import com.basejava.webapp.exception.NotExistStorageException;
import com.basejava.webapp.exception.StorageException;
import com.basejava.webapp.model.Resume;
import com.basejava.webapp.sql.ConnectionFactory;
import org.postgresql.util.PSQLException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SqlStorage implements Storage {
    public final ConnectionFactory connectionFactory;

    public SqlStorage(String databaseUrl, String databaseUser, String databasePassword) {
        this.connectionFactory = () -> DriverManager.getConnection(databaseUrl, databaseUser, databasePassword);
    }

    @Override
    public void clear() {
        try (Connection connection = connectionFactory.getConnection();
             PreparedStatement statement = connection.prepareStatement("DELETE FROM resume")) {
            statement.execute();
        } catch (SQLException e) {
            new StorageException(e);
        }
    }

    @Override
    public void update(Resume resume) {
        String query = "UPDATE resume SET full_name = ? WHERE uuid = ?";
        try (Connection connection = connectionFactory.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, resume.getFullName());
            statement.setString(2, resume.getUuid());
            statement.executeQuery();
        } catch (SQLException e) {
            if (e instanceof PSQLException && e.getMessage().equals("Запрос не вернул результатов.")) {
                throw new NotExistStorageException(resume.getUuid());
            }
            throw new StorageException(e);
        }
    }

    @Override
    public void save(Resume resume) {
        String query = "INSERT INTO resume(uuid, full_name) VALUES (?, ?)";
        try (Connection connection = connectionFactory.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, resume.getUuid());
            statement.setString(2, resume.getFullName());
            if (statement.execute()) {
                throw new StorageException("Couldn't save resume ", resume.getUuid());
            }
        } catch (SQLException e) {
            throw new StorageException(e);
        }
    }

    @Override
    public Resume get(String uuid) {
        try (Connection connection = connectionFactory.getConnection();
             PreparedStatement statement = connection.prepareStatement("SELECT * FROM resume WHERE uuid = ?")) {
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
             PreparedStatement statement = connection.prepareStatement("DELETE FROM resume WHERE uuid = ?")) {
            statement.setString(1, uuid);
            statement.execute();
        } catch (SQLException e) {
            throw new StorageException(e);
        }
    }

    @Override
    public List<Resume> getAllSorted() {
        String query = "SELECT * FROM resume ORDER BY full_name";
        try (Connection connection = connectionFactory.getConnection()) {
            Statement statement = connection.createStatement();
            ResultSet result = statement.executeQuery(query);

            List<Resume> resumes = new ArrayList<>();
            while (result.next()) {
                String uuid = result.getString("uuid").trim();
                String fullName = result.getString("full_name");
                Resume resume = new Resume(uuid, fullName);
                resumes.add(resume);
            }
            return resumes;
        } catch (SQLException e) {
            throw new StorageException(e);
        }
    }

    @Override
    public int size() {
        String query = "SELECT count(*) AS Total FROM resume";
        try (Connection connection = connectionFactory.getConnection()) {
            Statement statement = connection.createStatement();
            ResultSet result = statement.executeQuery(query);
            final int COUNT_COLUMN = 1;
            if (!result.next()) {
                throw new SQLException("Error query size table");
            }
            return result.getInt(COUNT_COLUMN);
        } catch (SQLException e) {
            throw new StorageException(e);
        }
    }
}
