package com.basejava.webapp.storage;

import com.basejava.webapp.exception.NotExistStorageException;
import com.basejava.webapp.model.ContactType;
import com.basejava.webapp.model.Resume;
import com.basejava.webapp.sql.SqlHelper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class SqlStorage implements Storage {
    private final SqlHelper helper;

    public SqlStorage(String databaseUrl, String databaseUser, String databasePassword) {
        this.helper = new SqlHelper(databaseUrl, databaseUser, databasePassword);
    }

    @Override
    public void clear() {
        // language=PostgreSQL
        String query = "DELETE FROM resume";
        helper.executeQuery(query, PreparedStatement::execute);
    }

    @Override
    public void update(Resume resume) {
        helper.transactionalExecute(connection -> {
            try (PreparedStatement statement = connection.prepareStatement("UPDATE resume SET full_name = ? WHERE uuid = ?")) {
                statement.setString(1, resume.getFullName());
                statement.setString(2, resume.getUuid());
                if (statement.executeUpdate() == 0) {
                    throw new NotExistStorageException(resume.getUuid());
                }
            }
            deleteContact(connection, resume);
            insertContact(connection, resume);
            return null;
        });
    }

    @Override
    public void save(Resume resume) {
        helper.transactionalExecute(connection -> {
            try (PreparedStatement statement = connection.prepareStatement("INSERT INTO resume(uuid, full_name) VALUES (?, ?)")) {
                statement.setString(1, resume.getUuid());
                statement.setString(2, resume.getFullName());
                statement.execute();
            }

            insertContact(connection, resume);
            return null;
        });
    }

    @Override
    public Resume get(String uuid) {
        // language=PostgreSQL
        String query =
                "SELECT * FROM resume r " +
                        "   LEFT JOIN contact cnt " +
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
                addContact(result, resume);
            } while (result.next());
            return resume;
        });
    }

    @Override
    public void delete(String uuid) {
        // language=PostgreSQL
        String query = "DELETE FROM resume WHERE uuid = ?";
        helper.executeQuery(query, statement -> {
            statement.setString(1, uuid);
            statement.execute();
            return null;
        });
    }

    @Override
    public List<Resume> getAllSorted() {
        // language=PostgreSQL
        String query = "SELECT * FROM resume r " +
                "LEFT JOIN contact cnt ON cnt.resume_uuid=r.uuid " +
                "ORDER BY r.full_name, r.uuid";
        return helper.executeQuery(query, statement -> {
            ResultSet result = statement.executeQuery();
            Map<String, Resume> resumes = new LinkedHashMap<>();
            while (result.next()) {
                String uuid = result.getString("uuid").trim();
                String fullName = result.getString("full_name");
                String contact = result.getString("value");
                ContactType contactType = ContactType.valueOf(result.getString("type"));
                resumes.computeIfAbsent(uuid, key -> new Resume(key, fullName))
                        .setContact(contactType, contact);
            }

            return new ArrayList<>(resumes.values());
        });
    }

    @Override
    public int size() {
        // language=PostgreSQL
        final String query = "SELECT count(*) AS Total FROM resume";
        final int COUNT_COLUMN = 1;

        return helper.executeQuery(query, statement -> {
            ResultSet result = statement.executeQuery();
            return result.next()
                    ? result.getInt(COUNT_COLUMN)
                    : 0;
        });
    }

    private void insertContact(Connection connection, Resume resume) throws SQLException {
        // language=PostgreSQL
        String query = "INSERT INTO contact(resume_uuid, type, value) VALUES (?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            for (Map.Entry<ContactType, String> contact : resume.getContacts().entrySet()) {
                statement.setString(1, resume.getUuid());
                statement.setString(2, contact.getKey().name());
                statement.setString(3, contact.getValue());
                statement.addBatch();
            }
            statement.executeBatch();
        }
    }

    private void addContact(ResultSet result, Resume resume) throws SQLException {
        String contact = result.getString("value");
        if (contact != null) {
            ContactType contactType = ContactType.valueOf(result.getString("type"));
            resume.setContact(contactType, contact);
        }
    }

    private void deleteContact(Connection connection, Resume resume) {
        // language=PostgreSQL
        helper.executeQuery("DELETE  FROM contact WHERE resume_uuid = ?", ps -> {
            ps.setString(1, resume.getUuid());
            ps.execute();
            return null;
        });
    }
}
