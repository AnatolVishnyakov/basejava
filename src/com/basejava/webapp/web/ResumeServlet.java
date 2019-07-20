package com.basejava.webapp.web;

import com.basejava.webapp.Config;
import com.basejava.webapp.model.ContactType;
import com.basejava.webapp.model.Resume;
import com.basejava.webapp.storage.Storage;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ResumeServlet extends HttpServlet {
    private static final long serialVersionUID = -3538094282869499383L;
    private static Storage storage;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        storage = Config.getInstance().getStorage();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        request.setCharacterEncoding("UTF-8");
        String uuid = request.getParameter("uuid");
        String fullName = request.getParameter("fullName");
        Resume resume = storage.get(uuid);
        resume.setFullName(fullName);

        for (ContactType contactType : ContactType.values()) {
            String value = request.getParameter(contactType.name());
            if (value != null && !value.trim().isEmpty()) {
                resume.setContact(contactType, value);
            } else {
                resume.getContacts().remove(contactType);
            }
        }
        storage.update(resume);
        response.sendRedirect("resume");
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String uuid = request.getParameter("uuid");
        String action = request.getParameter("action");
        if (action == null) {
            request.setAttribute("resumes", storage.getAllSorted());
            request.getRequestDispatcher("/WEB-INF/jsp/resumes.jsp")
                    .forward(request, response);
        } else {
            Resume resume;
            switch (action) {
                case "delete":
                    storage.delete(uuid);
                    response.sendRedirect("resume");
                    return;
                case "view":
                case "edit":
                    resume = storage.get(uuid);
                    break;
                default:
                    throw new IllegalStateException("Action " + action + " is illegal");
            }
            request.setAttribute("resume", resume);
            if ("view".equals(action)) {
                request.getRequestDispatcher("/WEB-INF/jsp/view.jsp").forward(request, response);
            } else {
                request.getRequestDispatcher("/WEB-INF/jsp/edit.jsp").forward(request, response);
            }
        }
    }
}
