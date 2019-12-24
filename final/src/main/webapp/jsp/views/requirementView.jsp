<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<jsp:include page="headerUserPage.jsp"/>


<div name="requirementForm" method="POST" action="app" class="header-right" id="child">
    <h4 class="pb-30">Book now!</h4>
    <fmt:message key="request.currentLocation"/> :
    <c:if test="${sessionScope.user.location.latitude != null}">
        ${sessionScope.user.location.latitude }
    </c:if>,
    <c:if test="${sessionScope.user.location.longitude != null}">
        ${sessionScope.user.location.longitude}
    </c:if>
    <form class="form">
        <div class="from-group">
            <input type="hidden" name="commandName" value="postCarRequirement"/>
            <div class="radio">
                <label>
                    <input type="radio" name="requirement" value="pet_friendly">
                    <fmt:message key="request.pet-friendly"/>
                </label>
                <label>
                    <input type="radio" name="requirement" value="baby_seat">
                    <fmt:message key="request.baby-seat"/>
                </label>
                <label>
                    <input type="radio" name="requirement" value="booster">
                    <fmt:message key="request.booster"/>
                </label>
            </div>
        </div>
        <div class="form-group">
            <button class="btn btn-default btn-lg btn-block text-center text-uppercase"
                    value="postCarRequirement"><fmt:message key="label.submit"/></button>
        </div>
    </form>
</div>
