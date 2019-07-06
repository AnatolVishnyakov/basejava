package com.basejava.webapp.storage;

import com.basejava.webapp.exception.NotExistStorageException;
import com.basejava.webapp.model.AbstractSection;
import com.basejava.webapp.model.ContactType;
import com.basejava.webapp.model.Resume;
import com.basejava.webapp.model.SectionType;
import com.basejava.webapp.sql.SqlHelper;
import com.basejava.webapp.utils.JsonParser;

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
        helper.executeQuery("DELETE FROM resume", PreparedStatement::execute);
        // language=PostgreSQL
        helper.executeQuery("DELETE FROM contact", PreparedStatement::execute);
        // language=PostgreSQL
        helper.executeQuery("DELETE FROM section", PreparedStatement::execute);
    }

    @Override
    public void update(Resume resume) {
        // language=PostgreSQL
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
            insertSection(connection, resume);
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
        Map<String, Resume> resumes = new LinkedHashMap<>();
        return helper.transactionalExecute(connection -> {
            // Резюме
            try (PreparedStatement statement = connection.prepareStatement("SELECT * FROM resume ORDER BY full_name, uuid")) {
                ResultSet result = statement.executeQuery();
                while (result.next()) {
                    String uuid = result.getString("uuid");
                    String fullName = result.getString("full_name");
                    Resume resume = new Resume(uuid, fullName);
                    resumes.put(uuid, resume);
                }
            }

            // Контакты
            try (PreparedStatement statement = connection.prepareStatement("SELECT * FROM contact")) {
                ResultSet result = statement.executeQuery();
                while (result.next()) {
                    String uuid = result.getString("resume_uuid");
                    Resume resume = resumes.get(uuid);
                    addContact(result, resume);
                }
            }

            // Секции
            try (PreparedStatement statement = connection.prepareStatement("SELECT * FROM section")) {
                ResultSet result = statement.executeQuery();
                while (result.next()) {
                    String uuid = result.getString("resume_uuid");
                    Resume resume = resumes.get(uuid);
                    addSection(result, resume);
                }
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

    private void insertSection(Connection connection, Resume resume) throws SQLException {
        // language=PostgreSQL
        String query = "INSERT INTO section(resume_uuid, type, content) VALUES (?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            for (Map.Entry<SectionType, AbstractSection> section : resume.getSections().entrySet()) {
                statement.setString(1, resume.getUuid());
                statement.setString(2, section.getKey().toString());
                statement.setString(3, section.getValue().toString());
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

    private void addSection(ResultSet result, Resume resume) throws SQLException {
        String content = result.getString("content");
        if (content != null) {
            SectionType sectionType = SectionType.valueOf(result.getString("type"));
            resume.setSection(sectionType, JsonParser.read(content, AbstractSection.class));
        }
    }

    private void deleteContact(Connection connection, Resume resume) throws SQLException {
        // language=PostgreSQL
        try (PreparedStatement ps = connection.prepareStatement("DELETE  FROM contact WHERE resume_uuid = ?")) {
            ps.setString(1, resume.getUuid());
            ps.execute();
        }
    }
}
