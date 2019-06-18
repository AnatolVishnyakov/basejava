package com.basejava.webapp.storage;

import com.basejava.webapp.exception.NotExistStorageException;
import com.basejava.webapp.model.ContactType;
import com.basejava.webapp.model.Resume;
import com.basejava.webapp.sql.SqlHelper;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class SqlStorage implements Storage {
    private final SqlHelper helper;

    public SqlStorage(String databaseUrl, String databaseUser, String databasePassword) {
        this.helper = new SqlHelper(databaseUrl, databaseUser, databasePassword);
    }

    @Override
    public void clear() {
        String query = "DELETE FROM resume";
        helper.executeQuery(query, PreparedStatement::execute);
    }

    @Override
    public void update(Resume resume) {
        String query = "UPDATE resume SET full_name = ? WHERE uuid = ?";
        helper.executeQuery(query, statement -> {
            statement.setString(1, resume.getFullName());
            statement.setString(2, resume.getUuid());
            if (statement.executeUpdate() == 0) {
                throw new NotExistStorageException(resume.getUuid());
            }
            return null;
        });
    }

    @Override
    public void save(Resume resume) {
        // language=PostgreSQL
        String query = "INSERT INTO resume(uuid, full_name) VALUES (?, ?)";
        helper.executeQuery(query, statement -> {
            statement.setString(1, resume.getUuid());
            statement.setString(2, resume.getFullName());
            statement.execute();
            return null;
        });
    }

    @Override
    public Resume get(String uuid) {
        // language=PostgreSQL
        String query =
                "SELECT * FROM resume r " +
                "   JOIN contact cnt " +
                "       ON cnt.resume_uuid = r.uuid " +
                "WHERE r.uuid = ?";

        return helper.executeQuery(query, statement -> {
            statement.setString(1, uuid);
            ResultSet result = statement.executeQuery();
            if (!result.next()) {
                throw new NotExistStorageException(uuid);
            }

            Resume resume = new Resume(uuid, result.getString("full_name"));
            do {
                String value = result.getString("value");
                ContactType type = ContactType.valueOf(result.getString("type"));
                resume.setContact(type, value);
            } while (result.next());
            return resume;
        });
    }

    @Override
    public void delete(String uuid) {
        String query = "DELETE FROM resume WHERE uuid = ?";
        helper.executeQuery(query, statement -> {
            statement.setString(1, uuid);
            statement.execute();
            return null;
        });
    }

    @Override
    public List<Resume> getAllSorted() {
        String query = "SELECT * FROM resume ORDER BY full_name";
        return helper.executeQuery(query, statement -> {
            ResultSet result = statement.executeQuery();
            List<Resume> resumes = new ArrayList<>();
            while (result.next()) {
                String uuid = result.getString("uuid").trim();
                String fullName = result.getString("full_name");
                resumes.add(new Resume(uuid, fullName));
            }
            return resumes;
        });
    }

    @Override
    public int size() {
        final String query = "SELECT count(*) AS Total FROM resume";
        final int COUNT_COLUMN = 1;

        return helper.executeQuery(query, statement -> {
            ResultSet result = statement.executeQuery();
            return result.next()
                    ? result.getInt(COUNT_COLUMN)
                    : 0;
        });
    }
}
