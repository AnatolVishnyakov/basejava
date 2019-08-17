package com.basejava.webapp.web;

import com.basejava.webapp.Config;
import com.basejava.webapp.model.*;
import com.basejava.webapp.storage.Storage;
import com.basejava.webapp.utils.DateUtils;
import com.basejava.webapp.utils.HtmlUtil;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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

        final boolean isCreate = (uuid == null || uuid.length() == 0);
        Resume resume;
        if (isCreate) {
            resume = new Resume(fullName);
        } else {
            resume = storage.get(uuid);
            resume.setFullName(fullName);
        }

        for (ContactType contactType : ContactType.values()) {
            String value = request.getParameter(contactType.name());
            if (HtmlUtil.isEmpty(value)) {
                resume.getContacts().remove(contactType);
            } else {
                resume.setContact(contactType, value);
            }
        }

        for (SectionType sectionType : SectionType.values()) {
            String value = request.getParameter(sectionType.name());
            String[] values = request.getParameterValues(sectionType.name());
            if (HtmlUtil.isEmpty(value) && values.length < 2) {
                resume.getSections().remove(sectionType);
            } else {
                switch (sectionType) {
                    case OBJECTIVE:
                    case PERSONAL:
                        resume.setSection(sectionType, new TextSection(value));
                        break;
                    case ACHIEVEMENT:
                    case QUALIFICATIONS:
                        resume.setSection(sectionType, new ListSection(value.split("\\n")));
                        break;
                    case EDUCATION:
                    case EXPERIENCE:
                        List<Institution> institutions = new ArrayList<>();
                        String[] urls = request.getParameterValues(sectionType.name() + "url");
                        for (int i = 0; i < values.length; i++) {
                            String name = values[i];
                            if (!HtmlUtil.isEmpty(name)) {
                                List<Institution.Position> positions = new ArrayList<>();
                                String pfx = sectionType.name() + i;
                                String[] startDates = request.getParameterValues(pfx + "startDate");
                                String[] endDates = request.getParameterValues(pfx + "endDate");
                                String[] titles = request.getParameterValues(pfx + "title");
                                String[] descriptions = request.getParameterValues(pfx + "description");
                                for (int j = 0; j < titles.length; j++) {
                                    if (!HtmlUtil.isEmpty(titles[j])) {
                                        positions.add(new Institution.Position(titles[j], DateUtils.parse(startDates[j]), DateUtils.parse(endDates[j]), descriptions[j]));
                                    }
                                }
                                institutions.add(new Institution(new HyperLink(name, urls[i]), positions));
                            }
                        }
                        resume.setSection(sectionType, new InstitutionSection(institutions));
                        break;
                }
            }
        }

        if (isCreate) {
            storage.save(resume);
        } else {
            storage.update(resume);
        }
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
                case "add":
                    resume = Resume.EMPTY;
                    break;
                case "delete":
                    storage.delete(uuid);
                    response.sendRedirect("resume");
                    return;
                case "view":
                    resume = storage.get(uuid);
                    break;
                case "edit":
                    resume = storage.get(uuid);
                    for (SectionType sectionType : SectionType.values()) {
                        AbstractSection section = resume.getSection(sectionType);
                        switch (sectionType) {
                            case OBJECTIVE:
                            case PERSONAL:
                                if (section == null) {
                                    section = TextSection.EMPTY;
                                }
                                break;
                            case ACHIEVEMENT:
                            case QUALIFICATIONS:
                                if (section == null) {
                                    section = ListSection.EMPTY;
                                }
                                break;
                            case EXPERIENCE:
                            case EDUCATION:
                                List<Institution> emptyFirstOrganization = new ArrayList<>();
                                emptyFirstOrganization.add(Institution.EMPTY);

                                InstitutionSection institutionSection = (InstitutionSection) resume.getSection(sectionType);
                                if (institutionSection != null) {
                                    for (Institution institution : institutionSection.getInstitutions()) {
                                        List<Institution.Position> emptyFirstPosition = new ArrayList<>();
                                        emptyFirstPosition.add(Institution.Position.EMPTY);
                                        emptyFirstPosition.addAll(institution.getPositions());
                                        emptyFirstOrganization.add(new Institution(institution.getHomePage(), emptyFirstPosition));
                                    }
                                }
                                section = new InstitutionSection(emptyFirstOrganization);
                                break;
                        }
                        resume.setSection(sectionType, section);
                    }
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
