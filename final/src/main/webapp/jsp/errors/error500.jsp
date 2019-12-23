<%@ page isErrorPage="true" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>


<html>
<head>
    <title>Error</title>
    <title>Taxi service</title>
    <link rel="stylesheet" href="static/style/css/font-awesome.min.css">
    <link rel="stylesheet" href="static/style/css/main.css">
</head>
<body>

<div class="banner-area" id="parent">
    <div class="banner-content"  id="child">
        <div class="text-uppercase">
            <h1><fmt:message key="error500"/></h1>
        </div>
    </div>
</div>
</body>
</html>