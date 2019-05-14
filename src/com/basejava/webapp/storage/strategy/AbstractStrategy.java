package com.basejava.webapp.storage.strategy;

import com.basejava.webapp.model.Resume;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public abstract class AbstractStrategy {
    public abstract void writeResume(Resume resume, OutputStream outputStream) throws IOException;

    public abstract Resume readResume(InputStream inputStream) throws IOException;
}
