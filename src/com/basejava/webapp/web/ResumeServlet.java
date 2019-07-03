package com.basejava.webapp.web;

import com.basejava.webapp.Config;
import com.basejava.webapp.model.Resume;
import com.basejava.webapp.storage.SqlStorage;
import com.basejava.webapp.storage.Storage;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;

import static java.lang.String.format;

public class ResumeServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) {
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        PrintWriter writer = response.getWriter();
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=UTF-8");

        Config config = Config.getInstance();
        Storage storage = new SqlStorage(config.getDatabaseUrl(), config.getDatabaseUser(), config.getDatabasePassword());
        // language=html
        StringBuilder record = new StringBuilder();
        String uuid = request.getParameter("uuid");
        if (uuid != null) {
            Resume resume = storage.get(uuid);
            // language=html
            record = new StringBuilder(format("<td>%s</td><td>%s</td>", resume.getUuid(), resume.getFullName()));
        } else {
            for (Resume resume : storage.getAllSorted()) {
                // language=html
                record.append(format("<td>%s</td><td>%s</td></br>", resume.getUuid(), resume.getFullName()));
            }
        }

        String RESUME_PAGE = new String(Files.readAllBytes(Paths.get("resumes.html")));
        writer.write(RESUME_PAGE.replaceFirst("\\{% body_resume %}", record.toString()));
    }
}
