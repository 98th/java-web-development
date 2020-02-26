<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ tag import="by.training.taxi.ApplicationConstants" %>
<!-- Navigation -->
<nav class="mdl-navigation">
    <a class="mdl-navigation__link" href="?${ApplicationConstants.CMD_REQ_PARAMETER}=${ApplicationConstants.GET_INCOMING_REQUESTS_VIEW}">
        <fmt:message key="links.incoming.requests"/></a>
</nav>
