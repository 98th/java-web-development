<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="nav" tagdir="/WEB-INF/tags/nav" %>


<%@ page import="by.training.taxi.role.Role" %>

<!DOCTYPE html>
<body>
<div class="header-right" id="child">
    <!-- user info here -->
    <h5 class="pb-30"><c:if test="${sessionScope.user.contact.firstName != null}">
        ${sessionScope.user.contact.firstName }
    </c:if>
        <c:if test="${sessionScope.user.contact.lastName != null}">
            ${sessionScope.user.contact.lastName}
        </c:if></h5>
    <c:if test="${sessionScope.user.driver.drivingLicenceNum != null}">
        ${sessionScope.user.driver.drivingLicenceNum}
    </c:if>
    <c:if test="${sessionScope.user.driver.car.licencePlateNum != null}">
        ${sessionScope.user.driver.car.licencePlateNum }
    </c:if>
    <c:if test="${sessionScope.user.driver.car.model != null}">
        ${sessionScope.user.driver.car.model }
    </c:if>
    <div>
        <div class="col-lg-4 btn-left">
            <nav:common-actions/>
        </div>
        <c:choose>
            <c:when test="${sessionScope.userRole == Role.ADMIN}">
                <div class="col-lg-4 btn-left">
                    <nav:admin-actions/>
                </div>
            </c:when>
            <c:when test="${sessionScope.userRole == Role.CLIENT}">
                <div class="col-lg-4 btn-left">
                    <nav:user-actions/>
                </div>
            </c:when>
        <c:otherwise></c:otherwise>
        </c:choose>
    </div>
</body>
</html>
