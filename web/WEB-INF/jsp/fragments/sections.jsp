<%@ page import="com.basejava.webapp.model.InstitutionSection" %>
<%@ page import="com.basejava.webapp.model.ListSection" %>
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
    <c:set var="section" value="${resume.getSection(sectionType)}"/>
    <jsp:useBean id="section" type="com.basejava.webapp.model.AbstractSection"/>

    <h3><a>${sectionType.title}</a></h3>
    <c:choose>
        <c:when test="${sectionType == 'OBJECTIVE'}">
            <input type="text" name="${sectionType}" size="75" value="<%=section%>">
        </c:when>
        <c:when test="${sectionType == 'PERSONAL'}">
            <textarea name="${sectionType}" cols="75" rows="5"><%=section%></textarea>
        </c:when>
        <c:when test="${sectionType == 'QUALIFICATIONS' || sectionType == 'ACHIEVEMENT'}">
            <textarea name="${sectionType}" cols="75" rows="5">
                <%=String.join("\n", ((ListSection) section).getContents())%>
            </textarea>
        </c:when>
        <c:when test="${sectionType == 'EXPERIENCE' || sectionType == 'EDUCATION'}">
            <c:forEach var="insitute" items="<%=((InstitutionSection) section).getInstitutions()%>" varStatus="index">
                <dl>
                    <dt>Название учреждения:</dt>
                    <dd>
                        <input type="text" name="${sectionType}" size="100" value="${insitute.homePage.name}">
                    </dd>
                </dl>
                <dl>
                    <dt>Сайт учреждения:</dt>
                    <dd>
                        <input type="text" name="${sectionType}_url" size="100" value="${insitute.homePage.url}">
                    </dd>
                </dl>
                <br>
                <div style="margin-left: 30px"></div>
            </c:forEach>
        </c:when>
    </c:choose>
</c:forEach>
</p>