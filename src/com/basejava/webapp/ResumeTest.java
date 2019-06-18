package com.basejava.webapp;

import com.basejava.webapp.model.*;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public class ResumeTest {
    public static final String UUID_1 = UUID.randomUUID().toString();
    public static final String UUID_2 = UUID.randomUUID().toString();
    public static final String UUID_3 = UUID.randomUUID().toString();
    public static final String UUID_4 = UUID.randomUUID().toString();
    public static final Resume RESUME_1 = new Resume(UUID_1, "Frank Rearden");
    public static final Resume RESUME_2 = new Resume(UUID_2, "Petrov Petr Petrovich");
    public static final Resume RESUME_3 = new Resume(UUID_3, "Ivanov Ivan Ivanovich");
    public static final Resume RESUME_4 = new Resume(UUID_4, "Sidorov Ivan Ivanovich");

    static {
        // Init Resume-1
        RESUME_1.setSection(SectionType.PERSONAL, new TextSection("Test personal 1"));
        RESUME_1.setSection(SectionType.OBJECTIVE, new TextSection("Test objective 1"));
        RESUME_1.setSection(SectionType.ACHIEVEMENT, new ListSection(Arrays.asList("Test achievement 1", "Test achievement 2")));
        RESUME_1.setSection(SectionType.QUALIFICATIONS, new ListSection(Arrays.asList("Test qualification 1", "Test qualification 2")));
        RESUME_1.setSection(SectionType.EXPERIENCE, new InstitutionSection(Arrays.asList(
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
        RESUME_1.setSection(SectionType.EDUCATION, new InstitutionSection(Arrays.asList(
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

        // Init Resume-2
        RESUME_2.setSection(SectionType.PERSONAL, new TextSection("Test personal 2"));
        RESUME_2.setSection(SectionType.OBJECTIVE, new TextSection("Test objective 2"));
        RESUME_2.setSection(SectionType.ACHIEVEMENT, new ListSection(Arrays.asList("Test achievement 1", "Test achievement 2")));
        RESUME_2.setSection(SectionType.QUALIFICATIONS, new ListSection(Arrays.asList("Test qualification 1", "Test qualification 2")));
        RESUME_2.setSection(SectionType.EXPERIENCE, new InstitutionSection(Arrays.asList(
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
        RESUME_2.setSection(SectionType.EDUCATION, new InstitutionSection(Arrays.asList(
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

        // Init Resume-3
        RESUME_3.setSection(SectionType.PERSONAL, new TextSection("Test personal 3"));
        RESUME_3.setSection(SectionType.OBJECTIVE, new TextSection("Test objective 3"));
        RESUME_3.setSection(SectionType.ACHIEVEMENT, new ListSection(Arrays.asList("Test achievement 1", "Test achievement 2")));
        RESUME_3.setSection(SectionType.QUALIFICATIONS, new ListSection(Arrays.asList("Test qualification 1", "Test qualification 2")));
        RESUME_3.setSection(SectionType.EXPERIENCE, new InstitutionSection(Arrays.asList(
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
        RESUME_3.setSection(SectionType.EDUCATION, new InstitutionSection(Arrays.asList(
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

        // Init Resume-4
        RESUME_4.setSection(SectionType.PERSONAL, new TextSection("Test personal 4"));
        RESUME_4.setSection(SectionType.OBJECTIVE, new TextSection("Test objective 4"));
        RESUME_4.setSection(SectionType.ACHIEVEMENT, new ListSection(Arrays.asList("Test achievement 1", "Test achievement 2")));
        RESUME_4.setSection(SectionType.QUALIFICATIONS, new ListSection(Arrays.asList("Test qualification 1", "Test qualification 2")));
        RESUME_4.setSection(SectionType.EXPERIENCE, new InstitutionSection(Arrays.asList(
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
        RESUME_4.setSection(SectionType.EDUCATION, new InstitutionSection(Arrays.asList(
                new Institution(
                        new HyperLink("TestPageEducation", "TestURL"),
                        new Institution.Position("Test education 4", LocalDate.of(2012, 10, 1), LocalDate.of(2013, 10, 12), "Test description"
                        )
                )
        )));
        RESUME_4.setContact(ContactType.WEBSITE, "www.testsite.com");
    }

    private static void printListStringSection(Resume resume, SectionType type) {
        ListSection section = (ListSection) resume.getSection(type);
        List<String> contents = section.getContents();
        contents.forEach(row -> {
            System.out.print("- ");
            Arrays.stream(row.split("[.]")).forEach(System.out::println);
            System.out.println();
        });
    }

    private static void printResumeInformation(Resume resume) {
        System.out.println(resume.getFullName());
        System.out.println(ContactType.PHONE + ": " + resume.getContact(ContactType.PHONE));
        System.out.println(ContactType.SKYPE + ": " + resume.getContact(ContactType.SKYPE));
        System.out.println(ContactType.EMAIL + ": " + resume.getContact(ContactType.EMAIL));
        System.out.println(ContactType.LINKEDIN + ": " + resume.getContact(ContactType.LINKEDIN));
        System.out.println(ContactType.GITHUB + ": " + resume.getContact(ContactType.GITHUB));
        System.out.println(ContactType.STACKOVERFLOW + ": " + resume.getContact(ContactType.STACKOVERFLOW));
        System.out.println(ContactType.WEBSITE + ": " + resume.getContact(ContactType.WEBSITE));
        System.out.println();

        System.out.println(SectionType.OBJECTIVE);
        System.out.println(resume.getSection(SectionType.OBJECTIVE));
        System.out.println();

        System.out.println(SectionType.PERSONAL);
        System.out.println(resume.getSection(SectionType.PERSONAL));
        System.out.println();

        System.out.println(SectionType.ACHIEVEMENT);
        printListStringSection(resume, SectionType.ACHIEVEMENT);

        System.out.println(SectionType.QUALIFICATIONS);
        printListStringSection(resume, SectionType.QUALIFICATIONS);

        System.out.println(SectionType.EXPERIENCE);
        printInstitutionSection(resume, SectionType.EXPERIENCE);
        System.out.println();

        System.out.println(SectionType.EDUCATION);
        printInstitutionSection(resume, SectionType.EDUCATION);
        System.out.println();
    }

    private static void printInstitutionSection(Resume resume, SectionType type) {
        InstitutionSection institutionSection = (InstitutionSection) resume.getSection(type);
//        Map<String, List<Institution>> map = institutionSection.getInstitutions();
//        map.keySet().forEach(key -> {
//            System.out.println(key);
//            List<Institution> institutions = map.get(key);
//            institutions.forEach(value -> {
//                System.out.println(String.format("%s - %s %s", value.getStartDate(), value.getEndDate(), value.getDescription()));
//            });
//        });
    }

    public static void main(String[] args) {
        Resume resume = new Resume("Ivanov Ivan Ivanovich");
        resume.setContact(ContactType.PHONE, "+7(123) 001-1234");
        resume.setContact(ContactType.SKYPE, "user.test");
        resume.setContact(ContactType.EMAIL, "usertest@yandex.ru");
        resume.setContact(ContactType.LINKEDIN, null);
        resume.setContact(ContactType.GITHUB, null);
        resume.setContact(ContactType.STACKOVERFLOW, null);
        resume.setContact(ContactType.WEBSITE, null);

        // Позиция
        resume.setSection(SectionType.PERSONAL, new TextSection("Ведущий стажировок и корпоративного обучения по Java Web и Enterprise технологиям"));

        // Личные качества
        resume.setSection(SectionType.OBJECTIVE, new TextSection("Аналитический склад ума, сильная логика, креативность, инициативность. Пурист кода и архитектуры."));

        // Достижения
        resume.setSection(SectionType.ACHIEVEMENT, new ListSection(Arrays.asList(
                "С 2013 года: разработка проектов \"Разработка Web приложения\",\"Java Enterprise\", \"Многомодульный maven. Многопоточность. XML (JAXB/StAX). Веб сервисы (JAX-RS/SOAP). Удаленное взаимодействие (JMS/AKKA)\". Организация онлайн стажировок и ведение проектов. Более 1000 выпускников.",
                "Реализация двухфакторной аутентификации для онлайн платформы управления проектами Wrike. Интеграция с Twilio, DuoSecurity, Google Authenticator, Jira, Zendesk.",
                "Налаживание процесса разработки и непрерывной интеграции ERP системы River BPM. Интеграция с 1С, Bonita BPM, CMIS, LDAP. Разработка приложения управления окружением на стеке: Scala/Play/Anorm/JQuery. Разработка SSO аутентификации и авторизации различных ERP модулей, интеграция CIFS/SMB java сервера.",
                "Реализация c нуля Rich Internet Application приложения на стеке технологий JPA, Spring, Spring-MVC, GWT, ExtGWT (GXT), Commet, HTML5, Highstock для алгоритмического трейдинга.",
                "Создание JavaEE фреймворка для отказоустойчивого взаимодействия слабо-связанных сервисов (SOA-base архитектура, JAX-WS, JMS, AS Glassfish). Сбор статистики сервисов и информации о состоянии через систему мониторинга Nagios. Реализация онлайн клиента для администрирования и мониторинга системы по JMX (Jython/ Django).",
                "Реализация протоколов по приему платежей всех основных платежных системы России (Cyberplat, Eport, Chronopay, Сбербанк), Белоруcсии(Erip, Osmp) и Никарагуа."
        )));

        // Квалификация
        resume.setSection(SectionType.QUALIFICATIONS, new ListSection(Arrays.asList(
                "JEE AS: GlassFish (v2.1, v3), OC4J, JBoss, Tomcat, Jetty, WebLogic, WSO2",
                "Version control: Subversion, Git, Mercury, ClearCase, Perforce",
                "DB: PostgreSQL(наследование, pgplsql, PL/Python), Redis (Jedis), H2, Oracle",
                "MySQL, SQLite, MS SQL, HSQLDB",
                "Languages: Java, Scala, Python/Jython/PL-Python, JavaScript, Groovy",
                "XML/XSD/XSLT, SQL, C/C++, Unix shell scripts",
                "Java Frameworks: Java 8 (Time API, Streams), Guava, Java Executor, MyBatis, Spring (MVC, Security, Data, Clouds, Boot), JPA (Hibernate, EclipseLink), Guice, GWT(SmartGWT, ExtGWT/GXT), Vaadin, Jasperreports, Apache Commons, Eclipse SWT, JUnit, Selenium (htmlelements).",
                "Python: Django.",
                "JavaScript: jQuery, ExtJS, Bootstrap.js, underscore.js",
                "Scala: SBT, Play2, Specs2, Anorm, Spray, Akka",
                "Технологии: Servlet, JSP/JSTL, JAX-WS, REST, EJB, RMI, JMS, JavaMail, JAXB, StAX, SAX, DOM, XSLT, MDB, JMX, JDBC, JPA, JNDI, JAAS, SOAP, AJAX, Commet, HTML5, ESB, CMIS, BPMN2, LDAP, OAuth1, OAuth2, JWT.",
                "Инструменты: Maven + plugin development, Gradle, настройка Ngnix",
                "администрирование Hudson/Jenkins, Ant + custom task, SoapUI, JPublisher, Flyway, Nagios, iReport, OpenCmis, Bonita, pgBouncer.",
                "Отличное знание и опыт применения концепций ООП, SOA, шаблонов проектирования, архитектурных шаблонов, UML, функционального программирования",
                "Родной русский, английский \"upper intermediate\""
        )));

        // Опыт работы
/*        resume.setSection(SectionType.EXPERIENCE, new InstitutionSection(Arrays.asList(
                new Institution(new HyperLink("Заглушка", ""), "Java Online Projects", LocalDate.of(2013, 10, 1), LocalDate.now(), "Автор проекта. Создание, организация и проведение Java онлайн проектов и стажировок."),
                new Institution(new HyperLink("Заглушка", ""), "Wrike", LocalDate.of(2014, 10, 1), LocalDate.of(2016, 1, 1), "Старший разработчик (backend). Проектирование и разработка онлайн платформы управления проектами Wrike (Java 8 API, Maven, Spring, MyBatis, Guava, Vaadin, PostgreSQL, Redis). Двухфакторная аутентификация, авторизация по OAuth1, OAuth2, JWT SSO."),
                new Institution(new HyperLink("Заглушка", ""), "RIT Center", LocalDate.of(2012, 4, 1), LocalDate.of(2014, 10, 1), "Java архитектор. Организация процесса разработки системы ERP для разных окружений: релизная политика, версионирование, ведение CI (Jenkins), миграция базы (кастомизация Flyway), конфигурирование системы (pgBoucer, Nginx), AAA via SSO. Архитектура БД и серверной части системы. Разработка интергационных сервисов: CMIS, BPMN2, 1C (WebServices), сервисов общего назначения (почта, экспорт в pdf, doc, html). Интеграция Alfresco JLAN для online редактирование из браузера документов MS Office. Maven + plugin development, Ant, Apache Commons, Spring security, Spring MVC, Tomcat,WSO2, xcmis, OpenCmis, Bonita, Python scripting, Unix shell remote scripting via ssh tunnels, PL/Python"),
                new Institution(new HyperLink("Заглушка", ""), "Luxoft (Deutsche Bank)", LocalDate.of(2010, 12, 1), LocalDate.of(2012, 4, 1), "Ведущий программист. Участие в проекте Deutsche Bank CRM (WebLogic, Hibernate, Spring, Spring MVC, SmartGWT, GWT, Jasper, Oracle). Реализация клиентской и серверной части CRM. Реализация RIA-приложения для администрирования, мониторинга и анализа результатов в области алгоритмического трейдинга. JPA, Spring, Spring-MVC, GWT, ExtGWT (GXT), Highstock, Commet, HTML5."),
                new Institution(new HyperLink("Заглушка", ""), "Yota", LocalDate.of(2008, 6, 1), LocalDate.of(2010, 12, 1), "Ведущий специалист\n" +
                        "Дизайн и имплементация Java EE фреймворка для отдела \"Платежные Системы\" (GlassFish v2.1, v3, OC4J, EJB3, JAX-WS RI 2.1, Servlet 2.4, JSP, JMX, JMS, Maven2). Реализация администрирования, статистики и мониторинга фреймворка. Разработка online JMX клиента (Python/ Jython, Django, ExtJS)"),
                new Institution(new HyperLink("Заглушка", ""), "Enkata", LocalDate.of(2007, 3, 1), LocalDate.of(2008, 3, 1), "Разработчик ПО\n" +
                        "Реализация клиентской (Eclipse RCP) и серверной (JBoss 4.2, Hibernate 3.0, Tomcat, JMS) частей кластерного J2EE приложения (OLAP, Data mining)."),
                new Institution(new HyperLink("Заглушка", ""), "Siemens AG", LocalDate.of(2005, 1, 1), LocalDate.of(2007, 2, 1), "Разработчик ПО\n" +
                        "Разработка информационной модели, проектирование интерфейсов, реализация и отладка ПО на мобильной IN платформе Siemens @vantage (Java, Unix)."),
                new Institution(new HyperLink("Заглушка", ""), "Alcatel", LocalDate.of(1997, 9, 1), LocalDate.of(2005, 1, 1), "Инженер по аппаратному и программному тестированию\n" +
                        "Тестирование, отладка, внедрение ПО цифровой телефонной станции Alcatel 1000 S12 (CHILL, ASM).")
        )));

        // Образование
        resume.setSection(SectionType.EDUCATION, new InstitutionSection(Arrays.asList(
                new Institution(new HyperLink("Заглушка", ""), "Coursera", LocalDate.of(2013, 3, 1), LocalDate.of(2013, 3, 1), "\t\"Functional Programming Principles in Scala\" by Martin Odersky"),
                new Institution(new HyperLink("Заглушка", ""), "Luxoft", LocalDate.of(2011, 3, 1), LocalDate.of(2011, 4, 1), "Курс \"Объектно-ориентированный анализ ИС. Концептуальное моделирование на UML.\""),
                new Institution(new HyperLink("Заглушка", ""), "Siemens AG", LocalDate.of(2005, 1, 1), LocalDate.of(2005, 4, 1), "3 месяца обучения мобильным IN сетям (Берлин)"),
                new Institution(new HyperLink("Заглушка", ""), "Alcatel", LocalDate.of(1997, 9, 1), LocalDate.of(1998, 3, 1), "6 месяцев обучения цифровым телефонным сетям (Москва)"),
                new Institution(new HyperLink("Заглушка", ""), "Санкт-Петербургский национальный исследовательский университет информационных технологий, механики и оптики", LocalDate.of(1993, 9, 1), LocalDate.of(1996, 7, 1), "Аспирантура (программист С, С++)"),
                new Institution(new HyperLink("Заглушка", ""), "Санкт-Петербургский национальный исследовательский университет информационных технологий, механики и оптики", LocalDate.of(1987, 9, 1), LocalDate.of(1993, 7, 1), "Инженер (программист Fortran, C)"),
                new Institution(new HyperLink("Заглушка", ""), "Заочная физико-техническая школа при МФТИ", LocalDate.of(1984, 9, 1), LocalDate.of(1987, 6, 1), "Закончил с отличием")
        )));*/

        printResumeInformation(resume);
    }
}