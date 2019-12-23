<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ tag import="by.training.taxi.ApplicationConstants" %>
<!-- Navigation -->
<nav class="mdl-navigation">
    <a class="mdl-navigation__link" href="?${ApplicationConstants.CMD_REQ_PARAMETER}=${ApplicationConstants.GET_LOGIN_VIEW}"><fmt:message key="links.login"/></a>
    <a class="mdl-navigation__link" href="?${ApplicationConstants.CMD_REQ_PARAMETER}=${ApplicationConstants.GET_USER_REGISTRATION}"><fmt:message key="links.reg"/></a>
    <a class="mdl-navigation__link" href="?${ApplicationConstants.CMD_REQ_PARAMETER}=${ApplicationConstants.ABOUT_VIEW}"><fmt:message key="links.about"/></a>
    <a class="mdl-navigation__link" href=""><fmt:message key="label.home"/></a>

    <a  href="#" onclick="changeLang('be_BY')">
        <img src="${pageContext.request.contextPath}/static/style/img/be.png" style="height: 25px; width:30px;">
    </a>

    <a  href="#" onclick="changeLang('en_US')">
        <img src="${pageContext.request.contextPath}/static/style/img/us.png" style="height: 25px; width:30px;">
    </a>

</nav>
