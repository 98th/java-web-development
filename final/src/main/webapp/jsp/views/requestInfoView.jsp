<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="nav" tagdir="/WEB-INF/tags/nav" %>

<%@ page import="by.training.taxi.request.RequestStatus" %>

<jsp:include page="headerUserPage.jsp"/>

<c:choose>
    <c:when test="${RequestStatus.ACCEPTED == requestScope.request.requestStatus}">
        <div class="banner-content">
            <div class="text-uppercase">
                <h1>Please wait........</h1>
            </div>
        </div>
    </c:when>
    <c:when test="${ RequestStatus.OFFERED == requestScope.request.requestStatus}">
        <div name="postConfirmRequest" method="POST" action="app" class="header-right" id="child">
            <h5><fmt:message key="message.thanks"/></h5>
                <h6 class="pb-30"><fmt:message key="message.arriving"/> ${sessionScope.waitingTime}   <fmt:message key="request.min"/> </h6>
                <h6 class="pb-30"><fmt:message key="request.price"/> ${sessionScope.price}</h6>
                <form class="form">
                    <select name="currentWallet">
                        <c:forEach items="${wallets}" var="w">
                            <option value="${w.id}"> <fmt:message key="wallet.wallet"/> ${w.id}  (${w.amount})</option>
                        </c:forEach>
                    </select>
                    <input type="hidden" name="commandName" value="postChooseCar"/>
                    <div class="form-group">
                        <button class="btn btn-default btn-lg btn-block text-center text-uppercase"
                                value="postChooseCar"><fmt:message key="label.submit"/></button>
                    </div>
                </form>
        </div>
    </c:when>
    <c:when test="${RequestStatus.CONFIRMED == requestScope.request.requestStatus}">
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
    </c:when>
    <c:when test="${RequestStatus.DECLINED == requestScope.request.requestStatus}">
        <div class="banner-content">
            <div class="text-uppercase">
                <h1><fmt:message key="message.request.declined"/></h1>
            </div>
        </div>
    </c:when>
</c:choose>