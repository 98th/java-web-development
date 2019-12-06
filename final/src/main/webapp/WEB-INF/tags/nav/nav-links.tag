<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ tag import="by.training.taxi.ApplicationConstants" %>
<!-- Navigation -->
<nav class="mdl-navigation">
    <a class="mdl-navigation__link" href="?${ApplicationConstants.CMD_REQ_PARAMETER}=${ApplicationConstants.GET_LOGIN_VIEW}"><fmt:message key="login.ref"/></a>
    <a class="mdl-navigation__link" href="?${ApplicationConstants.CMD_REQ_PARAMETER}=${ApplicationConstants.GET_USER_REGISTRATION}"><fmt:message key="reg.ref"/></a>
    <a class="mdl-navigation__link" href="?${ApplicationConstants.CMD_REQ_PARAMETER}=${ApplicationConstants.GET_ALL_USERS}"><fmt:message key="viewUsers.ref"/></a>
    <a class="mdl-navigation__link" href="?${ApplicationConstants.CMD_REQ_PARAMETER}=${ApplicationConstants.ABOUT_VIEW}"><fmt:message key="about.ref"/></a>
    <a class="mdl-navigation__link" href="">Home</a>
    <a href="?cookieLocale=be">
        <img src="${pageContext.servletContext.contextPath}/jpg/be.png"  style="height: 25px; width:30px;">
    </a>
    <a href="?cookieLocale=en">
        <img src="${pageContext.servletContext.contextPath}/jpg/us.png"  style="height: 25px; width:30px;">
    </a>
</nav>