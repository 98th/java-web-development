<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="nav" tagdir="/WEB-INF/tags/nav" %>

<!DOCTYPE html>

<fmt:setLocale value="${lang}"/>
<fmt:setBundle basename="taxi" scope="application"/>

<html>
<head>
    <title>Taxi service</title>
    <link href="https://fonts.googleapis.com/css?family=Poppins:100,200,400,300,500,600,700" rel="stylesheet">
    <link rel="stylesheet" href="static/style/css/font-awesome.min.css">
    <link rel="stylesheet" href="static/style/css/bootstrap.css">
    <link rel="stylesheet" href="static/style/css/main.css">
    <link rel="stylesheet" href="static/style/css/material.min.css">
    <link rel="stylesheet" href="static/style/css/dialog-polyfill.css">
    <script src="js/material.min.js"></script>
    <script src="js/main.js"></script>
    <script src="js/dialog-polyfill.js"></script>
    <script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/dialog-polyfill/0.4.7/dialog-polyfill.js"></script>
</head>
<body>

<div class="banner-area" id="parent">
    <c:choose>
        <c:when test="${not empty viewName}">
            <jsp:include page="views/${viewName}.jsp"/>
        </c:when>
        <c:otherwise>
            <jsp:include page="views/homeView.jsp"/>
        </c:otherwise>
    </c:choose>
</div>
</body>
</html>