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
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            throw new IllegalStateException(e);
        }
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
            try (PreparedStatement statement = connection.prepareStatement("UPDATE resume SET full_name = ? WHERE uuid_field = ?")) {
                statement.setString(1, resume.getFullName());
                statement.setString(2, resume.getUuid());
                if (statement.executeUpdate() == 0) {
                    throw new NotExistStorageException(resume.getUuid());
                }
            }
            deleteContacts(connection, resume);
            insertContacts(connection, resume);
            deleteSections(connection, resume);
            insertSections(connection, resume);
            return null;
        });
    }

    @Override
    public void save(Resume resume) {
        helper.transactionalExecute(connection -> {
            try (PreparedStatement statement = connection.prepareStatement("INSERT INTO resume(uuid_field, full_name) VALUES (?, ?)")) {
                statement.setString(1, resume.getUuid());
                statement.setString(2, resume.getFullName());
                statement.execute();
            }

            insertContacts(connection, resume);
            insertSections(connection, resume);
            return null;
        });
    }

    @Override
    public Resume get(String uuid) {
        return helper.transactionalExecute(connection -> {
            Resume resume;

            try (PreparedStatement statement = connection.prepareStatement("SELECT * FROM resume WHERE uuid_field = ?")) {
                statement.setString(1, uuid);
                ResultSet result = statement.executeQuery();
                if (!result.next()) {
                    throw new NotExistStorageException(uuid);
                }
                resume = new Resume(uuid, result.getString("full_name"));
            }

            try (PreparedStatement statement = connection.prepareStatement("SELECT * FROM contact WHERE resume_uuid = ?")) {
                statement.setString(1, uuid);
                ResultSet result = statement.executeQuery();
                while (result.next()) {
                    addContact(result, resume);
                }
            }

            try (PreparedStatement statement = connection.prepareStatement("SELECT * FROM section WHERE resume_uuid = ?")) {
                statement.setString(1, uuid);
                ResultSet result = statement.executeQuery();
                while (result.next()) {
                    addSection(result, resume);
                }
            }
            return resume;
        });
    }

    @Override
    public void delete(String uuid) {
        // language=PostgreSQL
        String query = "DELETE FROM resume WHERE uuid_field = ?";
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
            try (PreparedStatement statement = connection.prepareStatement("SELECT * FROM resume ORDER BY full_name, uuid_field")) {
                ResultSet result = statement.executeQuery();
                while (result.next()) {
                    String uuid_field = result.getString("uuid_field");
                    String fullName = result.getString("full_name");
                    Resume resume = new Resume(uuid_field, fullName);
                    resumes.put(uuid_field, resume);
                }
            }

            // Контакты
            try (PreparedStatement statement = connection.prepareStatement("SELECT * FROM contact")) {
                ResultSet result = statement.executeQuery();
                while (result.next()) {
                    String uuid_field = result.getString("resume_uuid");
                    Resume resume = resumes.get(uuid_field);
                    addContact(result, resume);
                }
            }

            // Секции
            try (PreparedStatement statement = connection.prepareStatement("SELECT * FROM section")) {
                ResultSet result = statement.executeQuery();
                while (result.next()) {
                    String uuid_field = result.getString("resume_uuid");
                    Resume resume = resumes.get(uuid_field);
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

    private void insertContacts(Connection connection, Resume resume) throws SQLException {
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

    private void insertSections(Connection connection, Resume resume) throws SQLException {
        // language=PostgreSQL
        String query = "INSERT INTO section(resume_uuid, type, content) VALUES (?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            for (Map.Entry<SectionType, AbstractSection> value : resume.getSections().entrySet()) {
                statement.setString(1, resume.getUuid());
                statement.setString(2, value.getKey().name());
                AbstractSection section = value.getValue();
                statement.setString(3, JsonParser.write(section, AbstractSection.class));
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

    private void deleteContacts(Connection connection, Resume resume) throws SQLException {
        // language=PostgreSQL
        deleteRecords(connection, resume, "DELETE  FROM contact WHERE resume_uuid = ?");
    }

    private void deleteSections(Connection connection, Resume resume) throws SQLException {
        // language=PostgreSQL
        deleteRecords(connection, resume, "DELETE  FROM section WHERE resume_uuid = ?");
    }

    private void deleteRecords(Connection connection, Resume resume, String query) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, resume.getUuid());
            statement.execute();
        }
    }
}
