<%@ page import="com.basejava.webapp.model.ContactType" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%--
  Created by IntelliJ IDEA.
  User: Анатолий
  Date: 19.07.2019
  Time: 14:06
  To change this template use File | Settings | File Templates.
--%>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html">
    <link rel="stylesheet" href="css/style.css">
    <title>Список всех резюме</title>
</head>
<body>

<jsp:include page="fragments/header.jsp"/>

<section>
    <a href="resume?action=add">
        <img src="img/add.png">
    </a>
    <br>
    <table border="1" cellpadding="8" cellspacing="0">
        <tr>
            <th>Имя</th>
            <th>Email</th>
            <th></th>
            <th></th>
        </tr>

        <c:forEach items="${resumes}" var="resume">
            <jsp:useBean id="resume" type="com.basejava.webapp.model.Resume"/>
            <tr>
                <td>
                    <a href="resume?uuid=${resume.uuid}&action=view">${resume.fullName}</a>
                </td>
                <td>
                    <%=ContactType.EMAIL.toHtml(resume.getContact(ContactType.EMAIL))%>
                </td>
                <td>
                    <a href="resume?uuid=${resume.uuid}&action=delete"><img src="img/delete.png" height="20px"
                                                                            width="20px"></a>
                </td>
                <td>
                    <a href="resume?uuid=${resume.uuid}&action=edit"><img src="img/pencil.png" height="20px"
                                                                          width="20px"></a>
                </td>
            </tr>
        </c:forEach>
    </table>
</section>

<jsp:include page="fragments/footer.jsp"/>

</body>
</html>