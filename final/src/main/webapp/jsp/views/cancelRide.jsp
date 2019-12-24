<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="nav" tagdir="/WEB-INF/tags/nav" %>

<jsp:include page="headerUserPage.jsp"/>


<div name="cancelRide" method="POST" action="app" class="header-right" id="child">
    <h5><fmt:message key="message.thanks"/>/h4>
    <h6 class="pb-30"><fmt:message key="message.arriving"/> ${sessionScope.waitingTime}   <fmt:message key="request.min"/> </h6>
    <h6 class="pb-30"> <fmt:message key="message.cancel"/> </h6>

    <form class="form">
        <input type="hidden" name="commandName" value="postCancelRide"/>
        <div class="form-group">
            <button class="btn btn-default btn-lg btn-block text-center text-uppercase"
                    value="cancelRide"><fmt:message key="request.cancel.ride"/></button>
        </div>
    </form>
</div>
