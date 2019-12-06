<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="nav" tagdir="/WEB-INF/tags/nav" %>

<!DOCTYPE html>

<html>
<head>
    <meta charset="UTF-8">
    <title>Registration</title>
</head>
<body>
<form name="registrationForm" method="POST" action="app" class="header-right" >
    <h4 class="pb-30">Welcome!</h4>
    <form class="form">
        <div class="from-group">
            <input type="hidden" name="commandName" value="postUserReg" />
            <input class="form-control txt-field" type="text" id="login" name="login" placeholder="<fmt:message key="login"/>"
                   onfocus="this.placeholder = ''" onblur="this.placeholder = <fmt:message key="login"/>">
            <input class="form-control txt-field" type="password" id="password" name="password" placeholder="<fmt:message key="pass"/>"
                   onfocus="this.placeholder = ''" onblur="this.placeholder = <fmt:message key="pass"/>">
            <input class="form-control txt-field" type="password" id="passwordRepeated" name="passwordRepeated" placeholder="<fmt:message key="pass.confirm"/>"
                   onfocus="this.placeholder = ''" onblur="this.placeholder = <fmt:message key="pass.confirm"/>">
            <input class="form-control txt-field" type="text" id="firstName" name="firstName" placeholder="<fmt:message key="f.name"/>"
                   onfocus="this.placeholder = ''" onblur="this.placeholder = <fmt:message key="f.name"/>">
            <input class="form-control txt-field" type="text" id="lastName" name="lastName" placeholder="<fmt:message key="l.name"/>"
                   onfocus="this.placeholder = ''" onblur="this.placeholder = <fmt:message key="l.name"/>">
            <div class="radio">
                <label>
                    <input type="radio" name="role" id="driver" value="driver" onclick="document.getElementById('divUrl').style.display='block'">
                    <fmt:message key="driver"/>
                </label>
                <label>
                    <input type="radio" name="role" id="client" value="client" onclick="document.getElementById('divUrl').style.display='none'">
                    <fmt:message key="client"/>
                </label>
            </div>
            <div id="divUrl" style="display:none">
               <input class="form-control txt-field" type="text" id="carModel" name="carModel" placeholder="<fmt:message key="carModel"/>"
                        onfocus="this.placeholder = ''" onblur="this.placeholder = <fmt:message key="carModel"/>">
                <input class="form-control txt-field" type="text" id="carColor" name="carColor" placeholder="<fmt:message key="carColor"/>"
                       onfocus="this.placeholder = ''" onblur="this.placeholder = <fmt:message key="carColor"/>">
                <input class="form-control txt-field" type="text" id="licencePlateNumber" name="licencePlateNumber" placeholder="<fmt:message key="licencePlateNumber"/>"
                       onfocus="this.placeholder = ''" onblur="this.placeholder = <fmt:message key="drivingLicence"/>">
                <input class="form-control txt-field" type="text" id="drivingLicence" name="drivingLicence" placeholder="<fmt:message key="drivingLicence"/>"
                       onfocus="this.placeholder = ''" onblur="this.placeholder = <fmt:message key="drivingLicence"/>">
            </div>
            </div>

        <div class="form-group">
            <button class="btn btn-default btn-lg btn-block text-center text-uppercase"  value="postUserReg"><fmt:message key="submit"/></button>
        </div>
    </form>
    </div>
    </div>
</form><hr/>
</body></html>
