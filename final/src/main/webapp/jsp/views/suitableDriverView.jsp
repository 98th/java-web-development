<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="nav" tagdir="/WEB-INF/tags/nav" %>

<div name="requirementForm" method="POST" action="app" class="header-right" id="child">
    <h4 class="pb-30"><fmt:message key="label.choice"/> </h4>
    <fmt:message key="contact.l.name"/>  :  ${sessionScope.driver.contact.lastName}
    <br>
    <fmt:message key="contact.f.name"/> : ${sessionScope.driver.contact.firstName}
    <br>
    <fmt:message key="driver.carColor"/>  :  ${sessionScope.driver.car.color}
    <br>
    <fmt:message key="driver.carModel"/> :  ${sessionScope.driver.car.model}
    <br>
    <fmt:message key="driver.licencePlateNumber"/>  : ${requestScope.driver.car.licencePlateNum}
    <br>
    <fmt:message key="request.estimated.waiting.time"/>  :  ${sessionScope.waitingTime} <fmt:message key="request.min"/>
    <br>
    <fmt:message key="request.price"/>  :  ${sessionScope.request.price}

    <form class="form">
        <input type="hidden" name="commandName" value="postConfirmRide"/>
        <div class="form-group">
            <button class="btn btn-default btn-lg btn-block text-center text-uppercase"
                    value="postConfirmRide"><fmt:message key="label.submit"/></button>
        </div>
    </form>
</div>
