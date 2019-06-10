package com.basejava.webapp.storage;

import com.basejava.webapp.Config;

public class SqlStorageTest extends AbstractStorageTest {
    private static Config config = Config.getInstance();

    protected SqlStorageTest() {
        super(new SqlStorage(config.getDatabaseUrl(), config.getDatabaseUser(), config.getDatabasePassword()));
    }
}
