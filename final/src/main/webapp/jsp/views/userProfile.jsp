<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="nav" tagdir="/WEB-INF/tags/nav" %>

<%@ page import="by.training.taxi.user.Role" %>

<jsp:include page="headerUserPage.jsp"/>


<!DOCTYPE html>
<body>

<div class="header-right" id="child">
    <c:set var="avatar" value="${pageContext.request.contextPath}/static/style/img/default-avatar.png"/>
    <c:if test="${sessionScope.user.avatar != null}">
        <c:set var="avatar" value="data:image/jpg;base64,${sessionScope.user.toBase64()}"/>
    </c:if>
    <img src="${avatar}" class="avatar"/>
    <script>
        function getFile() {
            document.getElementById("upfile").click();
        }

        function sub() {
            document.getElementById('myForm').submit();
        }
    </script>
    <style>
        .hidden {
            height: 0;
            width: 0;
            overflow: hidden;
        }
    </style>
    <div class="btn btn-default btn-lg btn-block text-center text-uppercase" onclick="getFile()">
        <fmt:message key="links.change.avatar"/></div>
    <div class="hidden">
        <form name="myForm" id="myForm" action="${pageContext.request.contextPath}/" method="POST"
              enctype='multipart/form-data'>
            <input type="hidden" name="commandName" value="updateAvatar"/>
            <input name="userAvatar" id="upfile" type="file" value="userAvatar" onchange="sub()"
                   accept="image/jpeg,image/png"/>
        </form>
    </div>
    <!-- user info -->
    <h5 class="pb-30">
        <c:if test="${sessionScope.user.contact.firstName != null}">
            ${sessionScope.user.contact.firstName}
        </c:if>
        <c:if test="${sessionScope.user.contact.lastName != null}">
            ${sessionScope.user.contact.lastName}
        </c:if>
    </h5>
    <c:if test="${sessionScope.user.driver.drivingLicenceNum != null}">
        ${sessionScope.user.driver.drivingLicenceNum}
    </c:if>
    <c:if test="${sessionScope.user.driver.car.licencePlateNum != null}">
        ${sessionScope.user.driver.car.licencePlateNum}
    </c:if>
    <c:if test="${sessionScope.user.driver.car.model != null}">
        ${sessionScope.user.driver.car.model}
    </c:if>
    <div>
        <div class="col-lg-4 btn-left">
            <nav:common-actions/>
            <c:choose>
                <c:when test="${Role.ADMIN == sessionScope.userRole}">
                    <nav:admin-actions/>
                </c:when>
                <c:when test="${ Role.CLIENT == sessionScope.userRole}">
                    <nav:user-actions/>
                </c:when>
                <c:when test="${ Role.DRIVER == sessionScope.userRole}">
                    <nav:driver-actions/>
                </c:when>
            </c:choose>
        </div>
    </div>
</div>
</body>
</html>
