package com.basejava.webapp;

import com.basejava.webapp.storage.SqlStorage;
import com.basejava.webapp.storage.Storage;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Config {
    private static final String RESUMES_PROPERTIES_FILE = "/resumes.properties";
    private static final Config INSTANCE = new Config();
    private Properties properties = new Properties();
    private File storageDirectory;
    private final Storage storage;

    public static Config getInstance() {
        return INSTANCE;
    }

    private Config() {
        System.out.println(RESUMES_PROPERTIES_FILE);
        try (InputStream inputStream = Config.class.getResourceAsStream(RESUMES_PROPERTIES_FILE)) {
            properties.load(inputStream);
            storageDirectory = new File(properties.getProperty("storage.directory"));
            storage = new SqlStorage(getDatabaseUrl(), getDatabaseUser(), getDatabasePassword());
        } catch (IOException e) {
            throw new IllegalStateException("Invalid config file " + RESUMES_PROPERTIES_FILE);
        }
    }

    public File getStorageDirectory() {
        return storageDirectory;
    }

    public String getDatabaseUrl() {
        return properties.getProperty("db.url");
    }

    public String getDatabaseUser() {
        return properties.getProperty("db.user");
    }

    public String getDatabasePassword() {
        return properties.getProperty("db.password");
    }

    public Storage getStorage() {
        return storage;
    }
}
