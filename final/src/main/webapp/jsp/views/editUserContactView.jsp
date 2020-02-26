<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<jsp:include page="headerUserPage.jsp"/>


<div name="userInfo" method="POST" action="app" class="header-right" id="child">
    <form class="form">
        <c:if test="${not empty error}">
            <c:forEach items="${errors}" var="e">
                <h6 class="pb-30"> <fmt:message key="${requestScope.error}"/> </h6>
            </c:forEach>
        </c:if>
        <div class="from-group">
            <input type="hidden" name="commandName" value="postEditUserInfo"/>
            <input class="form-control txt-field"  type="text" name="firstName"
                   placeholder="<fmt:message key="contact.f.name"/>"
                   onfocus="this.placeholder = ''" onblur="this.placeholder = <fmt:message key="contact.f.name"/>">
            <input class="form-control txt-field"   type="text" name="lastName"
                   placeholder="<fmt:message key="contact.l.name"/>"
                   onfocus="this.placeholder = ''" onblur="this.placeholder = <fmt:message key="contact.l.name"/>">
            <input class="form-control txt-field" type="text" name="email" placeholder="<fmt:message key="contact.email"/>"
                   onfocus="this.placeholder = ''" onblur="this.placeholder = <fmt:message key="contact.email"/>">
            <input class="form-control txt-field" type="text" name="phone" placeholder="<fmt:message key="contact.phone"/>"
                   onfocus="this.placeholder = ''" onblur="this.placeholder = <fmt:message key="contact.phone"/>">
         <c:choose>
            <c:when test="${sessionScope.userRole == Role.DRIVER}">
                <div class="col-lg-4 btn-left">
                    <input class="form-control txt-field" type="text" name="carModel"
                           placeholder="<fmt:message key="driver.carModel"/>"
                           onfocus="this.placeholder = ''" onblur="this.placeholder = <fmt:message key="driver.carModel"/>">
                    <input class="form-control txt-field" type="text" name="carColor"
                           placeholder="<fmt:message key="driver.carColor"/>"
                           onfocus="this.placeholder = ''" onblur="this.placeholder = <fmt:message key="driver.carColor"/>">
                    <input class="form-control txt-field" type="text" name="licencePlateNumber"
                           placeholder="<fmt:message key="driver.licencePlateNumber"/>"
                           onfocus="this.placeholder = ''" onblur="this.placeholder = <fmt:message key="driver.licencePlateNumber"/>">
                    <input class="form-control txt-field" type="text" name="drivingLicence"
                           placeholder="<fmt:message key="driver.drivingLicenceNum"/>"
                           onfocus="this.placeholder = ''" onblur="this.placeholder = <fmt:message key="driver.drivingLicenceNum"/>">
                </div>
            </c:when>
         </c:choose>
        </div>
        <div class="form-group">
            <button class="btn btn-default btn-lg btn-block text-center text-uppercase" value="postEditUserInfo"><fmt:message
                    key="label.submit"/></button>
        </div>
    </form>
    </div>
