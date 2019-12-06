<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="nav" tagdir="/WEB-INF/tags/nav" %>

<!DOCTYPE html>
<fmt:setLocale value="${cookie['lang'].value}"/>
<fmt:setBundle   basename="taxi" scope="application"/>

<html lang="${cookie['lang'].value}">
<head>
    <title>Taxi service</title>
    <link href="https://fonts.googleapis.com/css?family=Poppins:100,200,400,300,500,600,700" rel="stylesheet">
    <link rel="stylesheet" href="static/style/css/linearicons.css">
    <link rel="stylesheet" href="static/style/css/font-awesome.min.css">
    <link rel="stylesheet" href="static/style/css/bootstrap.css">
    <link rel="stylesheet" href="static/style/css/magnific-popup.css">
    <link rel="stylesheet" href="static/style/css/nice-select.css">
    <link rel="stylesheet" href="static/style/css/animate.min.css">
    <link rel="stylesheet" href="static/style/css/main.css">
</head>
<body>
<jsp:include page="/jsp/views/header.jsp"/>

<!-- start banner Area -->
<div class="banner-area" id="parent" >

    <div class="banner-content"  id="child">
        <c:choose>
            <c:when test="${not empty viewName}">
                <jsp:include page="views/${viewName}.jsp" />
            </c:when>
            <c:otherwise>>
                <h4 class="text-white "> <fmt:message key="title.layout"/></h4>
                <h1 class="text-uppercase">
                    911 999 911
                </h1>
                <p class="text-white">
                    <fmt:message key="title.layout.sub"/>
                </p>
                <a href="#" class="primary-btn text-uppercase"> <fmt:message key="book"/></a
            </c:otherwise>
            </c:choose>
    </div>
</div>
</body>
</html>