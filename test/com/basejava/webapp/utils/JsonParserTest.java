package com.basejava.webapp.utils;

import com.basejava.webapp.model.AbstractSection;
import com.basejava.webapp.model.Resume;
import com.basejava.webapp.model.TextSection;
import org.junit.Assert;
import org.junit.Test;

import static com.basejava.webapp.ResumeTestData.RESUME_1;
import static org.junit.Assert.assertEquals;

public class JsonParserTest {

    @Test
    public void read() {
        String json = JsonParser.write(RESUME_1);
        System.out.println(json);
        Resume resume = JsonParser.read(json, Resume.class);
        assertEquals(RESUME_1, resume);
    }

    @Test
    public void write() {
        AbstractSection section1 = new TextSection("Objective");
        String content = JsonParser.write(section1, AbstractSection.class);
        System.out.println(content);

        AbstractSection section2 = JsonParser.read(content, AbstractSection.class);
        Assert.assertEquals(section1, section2);
    }
}