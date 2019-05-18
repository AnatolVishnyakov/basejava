package com.basejava.webapp.storage.serializer;

import com.basejava.webapp.model.*;
import com.basejava.webapp.utils.XmlParser;

import java.io.*;
import java.nio.charset.StandardCharsets;

public class XmlStreamStrategy implements AbstractStrategy {
    private XmlParser xmlParser;

    public XmlStreamStrategy() {
        this.xmlParser = new XmlParser(
                Resume.class,
                Contact.class,
                Institution.class,
                HyperLink.class,
                InstitutionSection.class,
                Institution.Position.class,
                ListSection.class,
                TextSection.class
        );
    }

    @Override
    public void writeResume(Resume resume, OutputStream outputStream) throws IOException {
        try (Writer writer = new OutputStreamWriter(outputStream, StandardCharsets.UTF_8)) {
            xmlParser.marshall(resume, writer);
        }
    }

    @Override
    public Resume readResume(InputStream inputStream) throws IOException {
        try (Reader reader = new InputStreamReader(inputStream, StandardCharsets.UTF_8)) {
            return xmlParser.unmarshall(reader);
        }
    }
}
