<%@ page import="com.basejava.webapp.model.ContactType" %>
<%@ page import="com.basejava.webapp.model.SectionType" %>
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
    <form method="post" action="resume" enctype="application/x-www-form-urlencoded">
        <input type="hidden" name="uuid" value="${resume.uuid}">
        <dl>
            <dt>Имя:</dt>
            <dd><input type="text" name="fullName" size="50" value="${resume.fullName}"></dd>
        </dl>

        <h3>Контакты:</h3>
        <p>
            <c:forEach var="contactType" items="<%=ContactType.values()%>">
        <dl>
            <dt>${contactType.title}</dt>
            <dd>
                <input type="text" name="${contactType.name()}" size="30" value="${resume.getContact(contactType)}">
            </dd>
        </dl>
        </c:forEach>
        </p>

        <h3>Секции:</h3>
        <p>
            <c:forEach var="sectionType" items="<%=SectionType.values()%>">
        <dl>
            <dt>${sectionType.title}</dt>
            <dd>
                <c:set var="element" scope="session" value="${sectionType}"/>
                <c:choose>
                    <c:when test="${element == SectionType.PERSONAL || element == SectionType.OBJECTIVE}">
                        <input type="text" size="98" name="section" value="${resume.getSection(sectionType)}">
                    </c:when>
                    <c:when test="${element == SectionType.ACHIEVEMENT || element == SectionType.QUALIFICATIONS}">
                        <c:forEach var="content" items="${resume.getSection(element).getContents()}">
                            <textarea name="section" rows="5" cols="100">${content}</textarea>
                            <br/>
                        </c:forEach>
                    </c:when>
                    <c:when test="${element == SectionType.EDUCATION || element == SectionType.EXPERIENCE}">
                        <c:forEach var="institution" items="${resume.getSection(element).getInstitutions()}">
                            <h4>Ссылка на сайт работодателя</h4>
                            <label for="company_url">URL-адрес</label>
                            <input id="company_url" type="text" size="50" name="section"
                                   value="${institution.getHomePage().getUrl()}"><br/>
                            <label for="company_name">Наименование организации</label>
                            <input id="company_name" type="text" size="50" name="section"
                                   value="${institution.getHomePage().getName()}">

                            <h4>Позиция</h4>
                            <c:forEach var="position" items="${institution.getPositions()}">
                                <label for="organization_name">Наименование организации</label>
                                <input id="organization_name" type="text" value="${position.getTitle()}"><br/>
                                <label for="duration_start">Продолжительность c</label>
                                <input id="duration_start" type="date" value="${position.getStartDate()}">
                                <label for="duration_end">по</label>
                                <input id="duration_end" type="date" value="${position.getEndDate()}"><br/>
                                <label for="description">Описание</label>
                                <textarea id="description" rows="5"
                                          cols="100">${position.getDescription()}</textarea><br/>
                            </c:forEach>
                        </c:forEach>
                    </c:when>
                    <c:otherwise>
                        Если ни одно условие не выполнилось.
                    </c:otherwise>
                </c:choose>
            </dd>
        </dl>
        </c:forEach>
        </p>

        <hr>
        <button type="submit">Сохранить</button>
        <button onclick="window.history.back()">Отменить</button>
    </form>
</section>

<jsp:include page="fragments/footer.jsp"/>

</body>
</html>
