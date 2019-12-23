<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="nav" tagdir="/WEB-INF/tags/nav" %>

<form name="registrationForm" method="POST" action="app" class="header-right" id="child">
    <h4 class="pb-30">Welcome!</h4>
    <form class="form">
        <div class="from-group">
            <input type="hidden" name="commandName" value="postUserReg"/>
            <input class="form-control txt-field" type="text" name="login" placeholder="<fmt:message key="user.login"/>"
                   onfocus="this.placeholder = ''" onblur="this.placeholder = <fmt:message key="user.login"/>">
            <input class="form-control txt-field" type="password" name="password"
                   placeholder="<fmt:message key="user.pass"/>"
                   onfocus="this.placeholder = ''" onblur="this.placeholder = <fmt:message key="user.pass"/>">
            <input class="form-control txt-field" type="password" name="passwordRepeated"
                   placeholder="<fmt:message key="user.pass.confirm"/>"
                   onfocus="this.placeholder = ''" onblur="this.placeholder = <fmt:message key="user.pass.confirm"/>">
            <input class="form-control txt-field" type="text" name="firstName" placeholder="<fmt:message key="contact.f.name"/>"
                   onfocus="this.placeholder = ''" onblur="this.placeholder = <fmt:message key="contact.f.name"/>">
            <input class="form-control txt-field" type="text" name="lastName" placeholder="<fmt:message key="contact.l.name"/>"
                   onfocus="this.placeholder = ''" onblur="this.placeholder = <fmt:message key="contact.l.name"/>">
            <div class="radio">
                <label>
                    <input type="radio" name="role" value="driver"
                           onclick="document.getElementById('divUrl').style.display='block'">
                    <fmt:message key="user.driver"/>
                </label>
                <label>
                    <input type="radio" name="role" value="client"
                           onclick="document.getElementById('divUrl').style.display='none'">
                    <fmt:message key="user.client"/>
                </label>
            </div>
            <div id="divUrl" style="display:none">
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
        </div>
        <div class="form-group">
            <button class="btn btn-default btn-lg btn-block text-center text-uppercase" value="postUserReg"><fmt:message
                    key="label.submit"/></button>
        </div>
    </form>
    </div>
    </div>
</form>