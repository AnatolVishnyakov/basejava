package com.basejava.webapp.model;

import com.basejava.webapp.model.contact.Contact;
import com.basejava.webapp.model.contact.ContactType;

public class ResumeTestData {
    public static void main(String[] args) {
        Resume resume = new Resume("Ivanov Ivan Ivanovich");
        resume.addContact(ContactType.PHONE, "+7(123) 001-1234");
        resume.addContact(ContactType.SKYPE, "user.test");
        resume.addContact(ContactType.EMAIL, "usertest@yandex.ru");
        resume.addContact(ContactType.LINKEDIN, "");
        resume.addContact(ContactType.GITHUB, "");
        resume.addContact(ContactType.STACKOVERFLOW, "");
        resume.addContact(ContactType.WEBSITE, "");

        for(Contact contact: resume.getContacts()) {
            System.out.println(contact);
        }
/*
        // Позиция
        resume.addPosition("Ведущий стажировок и корпоративного обучения по Java Web и Enterprise технологиям");
        // Личные качества
        resume.addPersonal("Аналитический склад ума, сильная логика, креативность, инициативность. Пурист кода и архитектуры.");
        // Достижения
        resume.addAchievement("С 2013 года: разработка проектов \"Разработка Web приложения\",\"Java Enterprise\", \"Многомодульный maven. Многопоточность. XML (JAXB/StAX). Веб сервисы (JAX-RS/SOAP). Удаленное взаимодействие (JMS/AKKA)\". Организация онлайн стажировок и ведение проектов. Более 1000 выпускников.");
        resume.addAchievement("Реализация двухфакторной аутентификации для онлайн платформы управления проектами Wrike. Интеграция с Twilio, DuoSecurity, Google Authenticator, Jira, Zendesk.");
        resume.addAchievement("Налаживание процесса разработки и непрерывной интеграции ERP системы River BPM. Интеграция с 1С, Bonita BPM, CMIS, LDAP. Разработка приложения управления окружением на стеке: Scala/Play/Anorm/JQuery. Разработка SSO аутентификации и авторизации различных ERP модулей, интеграция CIFS/SMB java сервера.");
        resume.addAchievement("Реализация c нуля Rich Internet Application приложения на стеке технологий JPA, Spring, Spring-MVC, GWT, ExtGWT (GXT), Commet, HTML5, Highstock для алгоритмического трейдинга.");
        resume.addAchievement("Создание JavaEE фреймворка для отказоустойчивого взаимодействия слабо-связанных сервисов (SOA-base архитектура, JAX-WS, JMS, AS Glassfish). Сбор статистики сервисов и информации о состоянии через систему мониторинга Nagios. Реализация онлайн клиента для администрирования и мониторинга системы по JMX (Jython/ Django).");
        resume.addAchievement("Реализация протоколов по приему платежей всех основных платежных системы России (Cyberplat, Eport, Chronopay, Сбербанк), Белоруcсии(Erip, Osmp) и Никарагуа.");
        // Квалификация
        resume.addQualification("JEE AS: GlassFish (v2.1, v3), OC4J, JBoss, Tomcat, Jetty, WebLogic, WSO2");
        resume.addQualification("Version control: Subversion, Git, Mercury, ClearCase, Perforce");
        resume.addQualification("DB: PostgreSQL(наследование, pgplsql, PL/Python), Redis (Jedis), H2, Oracle");
        resume.addQualification("MySQL, SQLite, MS SQL, HSQLDB");
        resume.addQualification("Languages: Java, Scala, Python/Jython/PL-Python, JavaScript, Groovy");
        resume.addQualification("XML/XSD/XSLT, SQL, C/C++, Unix shell scripts");
        resume.addQualification("Java Frameworks: Java 8 (Time API, Streams), Guava, Java Executor, MyBatis, Spring (MVC, Security, Data, Clouds, Boot), JPA (Hibernate, EclipseLink), Guice, GWT(SmartGWT, ExtGWT/GXT), Vaadin, Jasperreports, Apache Commons, Eclipse SWT, JUnit, Selenium (htmlelements).");
        resume.addQualification("Python: Django.");
        resume.addQualification("JavaScript: jQuery, ExtJS, Bootstrap.js, underscore.js");
        resume.addQualification("Scala: SBT, Play2, Specs2, Anorm, Spray, Akka");
        resume.addQualification("Технологии: Servlet, JSP/JSTL, JAX-WS, REST, EJB, RMI, JMS, JavaMail, JAXB, StAX, SAX, DOM, XSLT, MDB, JMX, JDBC, JPA, JNDI, JAAS, SOAP, AJAX, Commet, HTML5, ESB, CMIS, BPMN2, LDAP, OAuth1, OAuth2, JWT.");
        resume.addQualification("Инструменты: Maven + plugin development, Gradle, настройка Ngnix");
        resume.addQualification("администрирование Hudson/Jenkins, Ant + custom task, SoapUI, JPublisher, Flyway, Nagios, iReport, OpenCmis, Bonita, pgBouncer.");
        resume.addQualification("Отличное знание и опыт применения концепций ООП, SOA, шаблонов проектирования, архитектурных шаблонов, UML, функционального программирования");
        resume.addQualification("Родной русский, английский \"upper intermediate\"");
        // Опыт работы
        resume.addExperience(title, "10/2013 - Сейчас", "Автор проекта.\n" +
                "Создание, организация и проведение Java онлайн проектов и стажировок.");
        resume.addExperience("");
*/
    }
}