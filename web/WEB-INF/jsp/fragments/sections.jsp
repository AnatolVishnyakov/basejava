<%@ page import="com.basejava.webapp.model.InstitutionSection" %>
<%@ page import="com.basejava.webapp.model.ListSection" %>
<%@ page import="com.basejava.webapp.model.SectionType" %>
<%@ page import="com.basejava.webapp.utils.DateUtils" %>
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
            <c:forEach var="insitute" items="<%=((InstitutionSection) section).getInstitutions()%>" varStatus="counter">
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
                <div style="margin-left: 30px">
                    <c:forEach var="position" items="${insitute.positions}">
                        <jsp:useBean id="row" type="com.basejava.webapp.model.Institution.Position"/>
                        <dl>
                            <dt>Начальная дата:</dt>
                            <dd>
                                <input type="text" name="${sectionType}${counter.index}startDate" size="10"
                                       value="<%=DateUtils.format(row.getStartDate())%>" placeholder="MM/yyyy"/>
                            </dd>
                        </dl>
                        <dl>
                            <dt>Конечная дата:</dt>
                            <dd>
                                <input type="text" name="${sectionType}${counter.index}endDate" size="10"
                                       value="<%=DateUtils.format(row.getEndDate())%>" placeholder="MM/yyyy"/>
                            </dd>
                        </dl>
                        <dl>
                            <dt>Должность:</dt>
                            <dd>
                                <input type="text" name="${sectionType}${counter.index}title" size="75"
                                       value="${row.title}>"/>
                            </dd>
                        </dl>
                        <dl>
                            <dt>Описание:</dt>
                            <dd>
                                <textarea name="${sectionType}${counter.index}description" rows="2" cols="75">
                                        ${row.description}
                                </textarea>
                            </dd>
                        </dl>
                    </c:forEach>
                </div>
            </c:forEach>
        </c:when>
    </c:choose>
</c:forEach>
</p>