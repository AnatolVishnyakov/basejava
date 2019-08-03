<%@ page import="com.basejava.webapp.model.InstitutionSection" %>
<%@ page import="com.basejava.webapp.model.ListSection" %>
<%@ page import="com.basejava.webapp.model.TextSection" %>
<%@ page import="com.basejava.webapp.utils.HtmlUtil" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%--
  Created by IntelliJ IDEA.
  User: Анатолий
  Date: 19.07.2019
  Time: 21:45
  To change this template use File | Settings | File Templates.
--%>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html" charset="UTF-8">
    <link rel="stylesheet" href="css/style.css">
    <jsp:useBean id="resume" type="com.basejava.webapp.model.Resume" scope="request"/>
    <title>Резюме ${resume.fullName}</title>
</head>
<body>

<jsp:include page="fragments/header.jsp"/>

<section>
    <h2>
        ${resume.fullName}&nbsp;
        <a href="resume?uuid=${resume.uuid}&action=edit">
            <img src="img/pencil.png" height="20px" width="20px">
        </a>
    </h2>
    <p>
        <c:forEach var="contactEntry" items="${resume.contacts}">
            <jsp:useBean id="contactEntry"
                         type="java.util.Map.Entry<com.basejava.webapp.model.ContactType, java.lang.String>"/>
            <%=contactEntry.getKey().toHtml(contactEntry.getValue())%>
            <br/>
        </c:forEach>
    </p>
    <hr>
    <table cellpadding="2">
        <c:forEach var="entry" items="${resume.sections}">
            <jsp:useBean id="entry"
                         type="java.util.Map.Entry<com.basejava.webapp.model.SectionType, com.basejava.webapp.model.AbstractSection>"/>

            <c:set var="sectionType" value="${entry.key}"/>
            <c:set var="section" value="${entry.value}"/>

            <jsp:useBean id="section" type="com.basejava.webapp.model.AbstractSection"/>
            <tr>
                <td>
                    <h3>
                        <a name="${sectionType.name()}">${sectionType.title}</a>
                    </h3>
                </td>
                <c:if test="${sectionType == 'OBJECTIVE'}">
                    <td>
                        <h3>
                            <%=((TextSection) section).getContent()%>
                        </h3>
                    </td>
                </c:if>
            </tr>
            <c:if test="${sectionType != 'OBJECTIVE'}">
                <c:choose>
                    <c:when test="${sectionType == 'PERSONAL'}">
                        <td>
                            <h3>
                                <%=((TextSection) section).getContent()%>
                            </h3>
                        </td>
                    </c:when>
                    <c:when test="${sectionType == 'QUALIFICATIONS' || sectionType == 'ACHIEVEMENT'}">
                        <tr>
                            <td>
                                <ul>
                                    <c:forEach var="content" items="<%=((ListSection) section).getContents()%>">
                                        <li>${content}</li>
                                    </c:forEach>
                                </ul>
                            </td>
                        </tr>
                    </c:when>
                    <c:when test="${sectionType == 'EXPERIENCE' || sectionType == 'EDUCATION'}">
                        <c:forEach var="institute" items="<%=((InstitutionSection) section).getInstitutions()%>">
                            <tr>
                                <td>
                                    <c:choose>
                                        <c:when test="${empty institute.homePage.url}">
                                            <h3>${institute.homePage.url}</h3>
                                        </c:when>
                                        <c:otherwise>
                                            <h3>
                                                <a href="${institute.homePage.url}">${institute.homePage.name}</a>
                                            </h3>
                                        </c:otherwise>
                                    </c:choose>
                                </td>
                            </tr>
                            <c:forEach var="position" items="${institute.positions}">
                                <jsp:useBean id="position" type="com.basejava.webapp.model.Institution.Position"/>
                                <tr>
                                    <td><%=HtmlUtil.formatDates(position)%>
                                    </td>
                                    <td>
                                        <b>${position.title}</b>
                                        <br>${position.description}
                                    </td>
                                </tr>
                            </c:forEach>
                        </c:forEach>
                    </c:when>
                </c:choose>
            </c:if>
        </c:forEach>
        <button onclick="window.history.back()">Ok</button>
    </table>
</section>

<jsp:include page="fragments/footer.jsp"/>

</body>
</html>
