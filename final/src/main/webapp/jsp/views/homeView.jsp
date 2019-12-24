<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="nav" tagdir="/WEB-INF/tags/nav" %>


<jsp:include page="header.jsp"/>

<div class="banner-content"  id="child">
    <h4 class="text-white "> <fmt:message key="label.layout"/></h4>
    <h1 class="text-uppercase">
        <fmt:message key="label.layout.number"/>
    </h1>
    <p class="text-white">
        <fmt:message key="label.layout.sub"/>
    </p>
</div>
