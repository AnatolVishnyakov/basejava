<%@ page import="com.basejava.webapp.model.SectionType" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%--
  Created by IntelliJ IDEA.
  User: Анатолий
  Date: 20.07.2019
  Time: 20:08
  To change this template use File | Settings | File Templates.
--%>
<h3>Секции:</h3>
<p>
<jsp:useBean id="resume" type="com.basejava.webapp.model.Resume" scope="request"/>
<c:forEach var="sectionType" items="<%=SectionType.values()%>">
    <dl>
        <dt>${sectionType.title}</dt>
        <dd>
            <c:set var="element" scope="session" value="${sectionType}"/>
            <c:choose>
                <c:when test="${element == SectionType.PERSONAL || element == SectionType.OBJECTIVE}">
                    <input name="${sectionType}" type="text" size="98" value="${resume.getSection(sectionType)}">
                </c:when>
                <c:when test="${element == SectionType.ACHIEVEMENT || element == SectionType.QUALIFICATIONS}">
                    <c:forEach var="content" items="${ resume.getSection(element).getContents()}">
                        <textarea name="${sectionType}" rows="5" cols="100">${content}</textarea>
                        <br/>
                    </c:forEach>
                </c:when>
                <c:when test="${element == SectionType.EDUCATION || element == SectionType.EXPERIENCE}">
                    <c:forEach var="institution" items="${resume.getSection(element).getInstitutions()}">
                        <h4>Ссылка на сайт работодателя</h4>
                        <label for="${element}_company_name">Наименование организации</label>
                        <input name="${element}_company_name" type="text" size="50"
                               value="${institution.getHomePage().getName()}">
                        <br/>
                        <label for="${element}_url">URL-адрес</label>
                        <input name="${element}_url" type="text" size="50"
                               value="${institution.getHomePage().getUrl()}">

                        <h4>Позиция</h4>
                        <c:forEach var="position" items="${institution.getPositions()}">
                            <label for="${position.getTitle()}_organization_name">Наименование организации</label>
                            <input name="${position.getTitle()}_organization_name" type="text"
                                   value="${position.getTitle()}"><br/>
                            <label for="${position.getTitle()}_duration_start">Продолжительность c</label>
                            <input name="${position.getTitle()}_duration_start" type="date" value="${position.getStartDate()}">
                            <label for="${position.getTitle()}_duration_end">по</label>
                            <input name="${position.getTitle()}_duration_end" type="date" value="${position.getEndDate()}"><br/>
                            <label for="${position.getTitle()}_description">Описание</label>
                            <textarea name="${position.getTitle()}_description" rows="5"
                                      cols="100">${position.getDescription()}</textarea><br/>
                        </c:forEach>
                    </c:forEach>
                </c:when>

                <c:otherwise>
                    Для элемента ${element} не найден обработчик.
                </c:otherwise>
            </c:choose>
        </dd>
    </dl>
</c:forEach>
</p>