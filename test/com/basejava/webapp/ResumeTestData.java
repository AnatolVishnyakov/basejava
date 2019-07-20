package com.basejava.webapp;

import com.basejava.webapp.model.*;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.UUID;

public class ResumeTestData {
    public static final String UUID_1 = UUID.randomUUID().toString();
    public static final String UUID_2 = UUID.randomUUID().toString();
    public static final String UUID_3 = UUID.randomUUID().toString();
    public static final String UUID_4 = UUID.randomUUID().toString();
    public static final String UUID_5 = UUID.randomUUID().toString();
    public static final Resume RESUME_1 = new Resume(UUID_1, "Frank Rearden");
    public static final Resume RESUME_2 = new Resume(UUID_2, "Petrov Petr Petrovich");
    public static final Resume RESUME_3 = new Resume(UUID_3, "Ivanov Ivan Ivanovich");
    public static final Resume RESUME_4 = new Resume(UUID_4, "Sidorov Ivan Ivanovich");
    public static final Resume RESUME_5 = new Resume(UUID_5, "Kelly Johns");

    static {
        // Init Resume-1
        RESUME_1.setSection(SectionType.PERSONAL, new TextSection("Test personal 1"));
        RESUME_1.setSection(SectionType.OBJECTIVE, new TextSection("Test objective 1"));
        RESUME_1.setSection(SectionType.ACHIEVEMENT, new ListSection(Arrays.asList("Test achievement 1", "Test achievement 2")));
        RESUME_1.setSection(SectionType.QUALIFICATIONS, new ListSection(Arrays.asList("Test qualification 1", "Test qualification 2")));
        RESUME_1.setSection(SectionType.EXPERIENCE, new InstitutionSection(Collections.singletonList(
                new Institution(
                        new HyperLink("TestPageExperience 1", "TestURL"),
                        new Institution.Position(
                                "Test experience 1",
                                LocalDate.of(2008, 10, 1),
                                LocalDate.of(2013, 10, 1),
                                "University"
                        )
                )
        )));
        RESUME_1.setSection(SectionType.EDUCATION, new InstitutionSection(Collections.singletonList(
                new Institution(
                        new HyperLink("TestPageEducation", "TestURL"),
                        new Institution.Position(
                                "Test education 1",
                                LocalDate.of(2012, 10, 1),
                                LocalDate.of(2013, 10, 12),
                                "Test description"
                        )
                )
        )));
        RESUME_1.setContact(ContactType.WEBSITE, "www.testsite.com");
        RESUME_1.setContact(ContactType.EMAIL, "test_1@ololo.ru");

        // Init Resume-2
        RESUME_2.setSection(SectionType.PERSONAL, new TextSection("Test personal 2"));
        RESUME_2.setSection(SectionType.OBJECTIVE, new TextSection("Test objective 2"));
        RESUME_2.setSection(SectionType.ACHIEVEMENT, new ListSection(Arrays.asList("Test achievement 1", "Test achievement 2")));
        RESUME_2.setSection(SectionType.QUALIFICATIONS, new ListSection(Arrays.asList("Test qualification 1", "Test qualification 2")));
        RESUME_2.setSection(SectionType.EXPERIENCE, new InstitutionSection(Collections.singletonList(
                new Institution(
                        new HyperLink("TestPageExperience 2", "TestURL"),
                        new Institution.Position(
                                "Test experience 2",
                                LocalDate.of(2008, 10, 1),
                                LocalDate.of(2013, 10, 1),
                                "University"
                        )
                )
        )));
        RESUME_2.setSection(SectionType.EDUCATION, new InstitutionSection(Collections.singletonList(
                new Institution(
                        new HyperLink("TestPageEducation", "TestURL"),
                        new Institution.Position(
                                "Test education 2",
                                LocalDate.of(2012, 10, 1),
                                LocalDate.of(2013, 10, 12),
                                "Test description"
                        )
                )
        )));
        RESUME_2.setContact(ContactType.WEBSITE, "www.testsite.com");
        RESUME_2.setContact(ContactType.EMAIL, "test_2@ololo.ru");

        // Init Resume-3
        RESUME_3.setSection(SectionType.PERSONAL, new TextSection("Test personal 3"));
        RESUME_3.setSection(SectionType.OBJECTIVE, new TextSection("Test objective 3"));
        RESUME_3.setSection(SectionType.ACHIEVEMENT, new ListSection(Arrays.asList("Test achievement 1", "Test achievement 2")));
        RESUME_3.setSection(SectionType.QUALIFICATIONS, new ListSection(Arrays.asList("Test qualification 1", "Test qualification 2")));
        RESUME_3.setSection(SectionType.EXPERIENCE, new InstitutionSection(Collections.singletonList(
                new Institution(
                        new HyperLink("TestPageExperience 3", "TestURL"),
                        new Institution.Position(
                                "Test experience 3",
                                LocalDate.of(2008, 10, 1),
                                LocalDate.of(2013, 10, 1),
                                "University"
                        )
                )
        )));
        RESUME_3.setSection(SectionType.EDUCATION, new InstitutionSection(Collections.singletonList(
                new Institution(
                        new HyperLink("TestPageEducation", "TestURL"),
                        new Institution.Position(
                                "Test education 3",
                                LocalDate.of(2012, 10, 1),
                                LocalDate.of(2013, 10, 12),
                                "Test description"
                        )
                )
        )));
        RESUME_3.setContact(ContactType.WEBSITE, "www.testsite.com");
        RESUME_3.setContact(ContactType.EMAIL, "test_3@ololo.ru");

        // Init Resume-4
        RESUME_4.setSection(SectionType.PERSONAL, new TextSection("Test personal 4"));
        RESUME_4.setSection(SectionType.OBJECTIVE, new TextSection("Test objective 4"));
        RESUME_4.setSection(SectionType.ACHIEVEMENT, new ListSection(Arrays.asList("Test achievement 1", "Test achievement 2")));
        RESUME_4.setSection(SectionType.QUALIFICATIONS, new ListSection(Arrays.asList("Test qualification 1", "Test qualification 2")));
        RESUME_4.setSection(SectionType.EXPERIENCE, new InstitutionSection(Collections.singletonList(
                new Institution(
                        new HyperLink("TestPageExperience 4", "TestURL"),
                        new Institution.Position(
                                "Test experience 4",
                                LocalDate.of(2008, 10, 1),
                                LocalDate.of(2013, 10, 1),
                                "University"
                        )
                )
        )));
        RESUME_4.setSection(SectionType.EDUCATION, new InstitutionSection(Collections.singletonList(
                new Institution(
                        new HyperLink("TestPageEducation", "TestURL"),
                        new Institution.Position("Test education 4", LocalDate.of(2012, 10, 1), LocalDate.of(2013, 10, 12), "Test description"
                        )
                )
        )));
        RESUME_4.setContact(ContactType.WEBSITE, "www.testsite.com");
        RESUME_4.setContact(ContactType.EMAIL, "test_4@ololo.ru");
    }
}
