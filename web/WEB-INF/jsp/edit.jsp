<%@ page import="com.basejava.webapp.model.ContactType" %>
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

        <%-------------------------------------------------- Секции --------------------------------------------------%>
        <jsp:include page="fragments/sections.jsp"/>
        <%------------------------------------------------------------------------------------------------------------%>
        <hr>
        <button type="submit">Сохранить</button>
        <button onclick="window.history.back()">Отменить</button>
    </form>
</section>

<jsp:include page="fragments/footer.jsp"/>

</body>
</html>
