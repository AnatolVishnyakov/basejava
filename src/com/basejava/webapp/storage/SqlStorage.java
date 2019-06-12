package com.basejava.webapp.storage;

import com.basejava.webapp.exception.ExistStorageException;
import com.basejava.webapp.exception.NotExistStorageException;
import com.basejava.webapp.exception.StorageException;
import com.basejava.webapp.model.Resume;
import com.basejava.webapp.sql.SqlHelper;
import com.basejava.webapp.sql.SqlHelper.Operation;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static com.basejava.webapp.sql.SqlHelper.Operation.DELETE;

public class SqlStorage implements Storage {
    private static final String DUPLICATE_CODE = "23505";
    private final SqlHelper helper;

    public SqlStorage(String databaseUrl, String databaseUser, String databasePassword) {
        this.helper = SqlHelper.getInstance(databaseUrl, databaseUser, databasePassword);
    }

    @Override
    public void clear() {
        String query = "DELETE FROM resume";
        try {
            helper.executeQuery(query, null, DELETE);
        } catch (SQLException e) {
            throw new StorageException(e);
        }
    }

    @Override
    public void update(Resume resume) {
        String query = "UPDATE resume SET full_name = ? WHERE uuid = ?";
        try {
            helper.executeQuery(query, new String[]{resume.getFullName(), resume.getUuid()}, Operation.UPDATE);
        } catch (SQLException e) {
            throw new StorageException(e);
        } catch (NotExistStorageException e) {
            throw new NotExistStorageException(resume.getUuid());
        }
    }

    @Override
    public void save(Resume resume) {
        String query = "INSERT INTO resume(uuid, full_name) VALUES (?, ?)";
        try {
            helper.executeQuery(query, new String[]{resume.getUuid(), resume.getFullName()}, Operation.INSERT);
        } catch (SQLException e) {
            if (e.getSQLState().equals(DUPLICATE_CODE)) {
                throw new ExistStorageException(resume.getUuid());
            }
            throw new StorageException(e);
        }
    }

    @Override
    public Resume get(String uuid) {
        String query = "SELECT * FROM resume WHERE uuid = ?";
        try {
            ResultSet result = helper.executeQuery(query, new String[]{uuid}, Operation.SELECT);
            if (!result.next()) {
                throw new NotExistStorageException(uuid);
            }

            return new Resume(uuid, result.getString("full_name"));
        } catch (SQLException e) {
            throw new StorageException(e);
        }
    }

    @Override
    public void delete(String uuid) {
        String query = "DELETE FROM resume WHERE uuid = ?";
        try {
            helper.executeQuery(query, new String[]{uuid}, DELETE);
        } catch (SQLException e) {
            throw new StorageException(e);
        }
    }

    @Override
    public List<Resume> getAllSorted() {
        String query = "SELECT * FROM resume ORDER BY full_name";
        try {
            ResultSet result = helper.executeQuery(query, null, Operation.SELECT);
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
        final String query = "SELECT count(*) AS Total FROM resume";
        final int COUNT_COLUMN = 1;

        try {
            ResultSet result = helper.executeQuery(query, null, Operation.SELECT);
            if (!result.next()) {
                throw new SQLException("Error query size table");
            }
            return result.getInt(COUNT_COLUMN);
        } catch (SQLException e) {
            throw new StorageException(e);
        }
    }
}
