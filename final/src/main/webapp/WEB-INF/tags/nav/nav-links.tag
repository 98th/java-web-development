<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ tag import="by.training.taxi.ApplicationConstants" %>
<!-- Navigation -->
<nav class="mdl-navigation">
    <a class="mdl-navigation__link" href="?${ApplicationConstants.CMD_REQ_PARAMETER}=${ApplicationConstants.GET_LOGIN_VIEW}"><fmt:message key="links.login"/></a>
    <a class="mdl-navigation__link" href="?${ApplicationConstants.CMD_REQ_PARAMETER}=${ApplicationConstants.GET_USER_REGISTRATION}"><fmt:message key="links.reg"/></a>
    <a class="mdl-navigation__link" href="?${ApplicationConstants.CMD_REQ_PARAMETER}=${ApplicationConstants.ABOUT_VIEW}"><fmt:message key="links.about"/></a>
    <a class="mdl-navigation__link" href="${pageContext.request.contextPath}"><fmt:message key="links.home"/></a>
</nav>
