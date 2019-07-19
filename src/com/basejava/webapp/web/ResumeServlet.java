package com.basejava.webapp.web;

import com.basejava.webapp.Config;
import com.basejava.webapp.model.Resume;
import com.basejava.webapp.storage.Storage;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

import static java.lang.String.format;

public class ResumeServlet extends HttpServlet {
    private static final long serialVersionUID = -3538094282869499383L;
    private static Storage storage;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        storage = Config.getInstance().getStorage();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) {
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        PrintWriter writer = response.getWriter();
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=UTF-8");

/*        // language=html
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
        writer.write(RESUME_PAGE.replaceFirst("\\{% body_resume %}", record.toString()));*/

        writer.write("<!DOCTYPE html>\n" +
                "<html lang=\"en\">\n" +
                "<head>\n" +
                "    <meta charset=\"UTF-8\">\n" +
                "    <title>Резюме</title>\n" +
                "</head>\n" +
                "<body>\n" +
                "    <table>\n" +
                "        <tr id=\"header_resume\">\n" +
                "            <td>Идентификатор резюме</td>\n" +
                "            <td>ФИО</td>\n" +
                "        </tr>\n");

        for (Resume resume : storage.getAllSorted()) {
            writer.write("        <tr id=\"body_resume\">\n" +
                    "            {% body_resume %}\n" +
                    "        </tr>\n".replaceAll("\\{% body_resume %}", format("<td>%s</td><td>%s</td></br>", resume.getUuid(), resume.getFullName())));
        }

        writer.write(
                "    </table>\n" +
                        "</body>\n" +
                        "</html>");
    }
}
