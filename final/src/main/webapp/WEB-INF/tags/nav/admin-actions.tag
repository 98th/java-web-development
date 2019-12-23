<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ tag import="by.training.taxi.ApplicationConstants" %>
<!-- Navigation -->
<nav class="mdl-navigation">
    <a class="mdl-navigation__link" href="?${ApplicationConstants.CMD_REQ_PARAMETER}=${ApplicationConstants.GET_USER_LIST_VIEW}"><fmt:message key="links.user.list"/></a>
    <a class="mdl-navigation__link" href="?${ApplicationConstants.CMD_REQ_PARAMETER}=${ApplicationConstants.GET_DRIVER_LIST_VIEW}"><fmt:message key="links.driver.list"/></a>
</nav>