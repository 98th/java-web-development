<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<html>
<head>
    <title>Error Page</title>
    <link rel="stylesheet" href="static/style/css/main.css">
    <link rel="stylesheet" href="static/style/css/font-awesome.min.css">
</head>
<body>
<div class="banner-content"  id="child">

    <c:choose>
        <c:when test="${not empty error}">
            <h1 class="pb-30"> <fmt:message key="${requestScope.error}"/> </h1>
        </c:when>
        <c:otherwise>
            <h1 class="pb-30"> <fmt:message key="error.general"/> </h1>
        </c:otherwise>
    </c:choose>
</div>

</body>
</html>