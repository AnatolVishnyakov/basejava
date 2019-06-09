package com.basejava.webapp;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Config {
    private static final File RESUMES_PROPERTIES_FILE = new File("config\\resumes.properties");
    private static final Config INSTANCE = new Config();
    private Properties properties = new Properties();
    private File storageDirectory;

    public static Config getInstance() {
        return INSTANCE;
    }

    private Config() {
        System.out.println(RESUMES_PROPERTIES_FILE);
        try (InputStream inputStream = new FileInputStream(RESUMES_PROPERTIES_FILE)) {
            properties.load(inputStream);
            storageDirectory = new File(properties.getProperty("storage.directory"));
        } catch (IOException e) {
            throw new IllegalStateException("Invalid config file " + RESUMES_PROPERTIES_FILE.getAbsolutePath());
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
}
