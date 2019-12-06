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
    <link rel="stylesheet" href="static/style/test/css/main.css">
    <link rel="stylesheet" href="static/style/icons.css">
    <script defer src="static/style/material.min.js"></script>
</head>

<body>

<form>
    <a href="?cookieLocale=be">
        <img src="${pageContext.servletContext.contextPath}/jpg/be.png"  style="height: 25px; width:30px;">
    </a>
    <a href="?cookieLocale=en">
        <img src="${pageContext.servletContext.contextPath}/jpg/us.png"  style="height: 25px; width:30px;">
    </a>

<div id='display'></div>

<div class="demo-layout-waterfall mdl-layout mdl-js-layout">
    <header class="mdl-layout__header mdl-layout__header--waterfall">
        <!-- Top row, always visible -->
        <div class="mdl-layout__header-row">
            <!-- Title -->
            <span class="mdl-layout-title">Title</span>
            <div class="mdl-layout-spacer"></div>
        </div>
        <!-- Bottom row, not visible on scroll -->
        <div class="mdl-layout__header-row">
            <div class="mdl-layout-spacer"></div>
            <nav:nav-links />
        </div>
    </header>
    <div class="mdl-layout__drawer">
        <span class="mdl-layout-title">Title</span>
        <nav:nav-links />
    </div>
    <main class="mdl-layout__content">
        <div class="page-content">
            <div class="mdl-grid">
                <div class="mdl-cell mdl-cell--2-col">
                </div>
                <div class="mdl-cell mdl-cell--8-col">
                    <c:choose>
                        <c:when test="${not empty viewName}">
                            <jsp:include page="views/${viewName}.jsp" />
                        </c:when>
                        <c:otherwise>
                            <div class="mdl-grid">
                                <div class="mdl-cell mdl-cell--12-col">
                                    ......
                                </div>
                            </div>
                        </c:otherwise>
                    </c:choose>
                </div>
                <div class="mdl-cell mdl-cell--2-col">
                </div>
            </div>
        </div>
    </main>
</div>
</body>
</html>