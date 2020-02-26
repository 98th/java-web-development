<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="nav" tagdir="/WEB-INF/tags/nav" %>
<%@ taglib prefix="input" tagdir="/WEB-INF/tags" %>

<jsp:include page="header.jsp"/>


<form name="registrationForm" method="POST" action="app" class="header-right" id="child">
    <h4 class="pb-30"><fmt:message key="label.welcome"/></h4>
    <c:if test="${not empty error}">
        <h6 class="pb-30"><fmt:message key="${requestScope.error}"/></h6>
    </c:if>
    </br>
    <form class="form">
        <div class="from-group">
            <input type="hidden" name="commandName" value="postUserReg"/>
            <table width="100%" cellspacing="0" cellpadding="5">
                <tr>
                    <td width="200" valign="top">
                        <input:input fieldName="login" inputPattern="^[A-Za-z]([\\.A-Za-z0-9-]{1,18})([A-Za-z0-9])$"
                                     patternTitle="error.empty.fields" inputPlaceholder="user.login"
                                     errors="${requestScope.login}"/>
                        <input:input fieldName="password" password="true"
                                     inputPattern="(?=^.{8,}$)((?=.*\d)|(?=.*\W+))(?![.\n])(?=.*[A-Z])(?=.*[a-z]).*$"
                                     inputPlaceholder="user.pass" patternTitle="error.empty.fields"
                                     errors="${requestScope.password}"/>
                        <input:input fieldName="passwordRepeated" password="true" inputPlaceholder="user.pass.confirm"
                                     inputPattern="(?=^.{8,}$)((?=.*\d)|(?=.*\W+))(?![.\n])(?=.*[A-Z])(?=.*[a-z]).*$"
                                     patternTitle="error.empty.fields"/>
                    </td>
                    <td valign="top">
                        <input:input fieldName="firstName" inputPlaceholder="contact.f.name"
                                     inputPattern="^[A-Za-zА-Яа-я]{1,20}$" errors="${requestScope.firstName}"
                                     patternTitle="error.empty.fields"/>
                        <input:input fieldName="lastName" inputPlaceholder="contact.l.name"
                                     inputPattern="^[A-Za-zА-Яа-я]{1,20}$" errors="${requestScope.lastName}"
                                     patternTitle="error.empty.fields"/>
                        <input:input fieldName="phone" inputPlaceholder="contact.phone"
                                     patternTitle="error.empty.fields" errors="${requestScope.phone}"
                                     inputPattern="(\+)?(375)?(29|44|33|25)(([1-9]{1}[0-9]{6}))"/>
                        <input:input fieldName="email" inputPlaceholder="contact.email" errors="${requestScope.email}"
                                     inputPattern="^[A-Za-z0-9._%+-]{5,18}@[A-Za-z]{4,8}\.[A-Za-z]{2,4}$"
                                     patternTitle="error.empty.fields"/>
                    </td>
                </tr>
            </table>
            <div class="radio">
                <label>
                    <input type="radio" name="role" value="driver"
                           onclick="document.getElementById('divUrl').style.display='block'"/>
                    <fmt:message key="user.driver"/>
                </label>
                <label>
                    <input type="radio" name="role" value="client"
                           onclick="document.getElementById('divUrl').style.display='none'"/>
                    <fmt:message key="user.client"/>
                </label>
            </div>
            <div id="divUrl" style="display:none">
                <input:input fieldName="carModel" patternTitle="error.wrong.format" errors="${requestScope.carModel}"
                             inputPlaceholder="driver.carModel" inputPattern="^[A-Za-zА-Яа-я]{1,20}$"/>
                <input:input fieldName="carColor" inputPlaceholder="driver.carColor"
                             inputPattern="^[A-Za-zА-Яа-я]{1,20}$" errors="${requestScope.carColor}"
                             patternTitle="error.wrong.format"/>
                <input:input fieldName="licencePlateNumber" patternTitle="error.wrong.format"
                             inputPattern="([0-9]{4})(\s?)([a-zA-Z]{2}-[1-9]{1})" errors="${requestScope.licencePlateNumber}"
                             inputPlaceholder="driver.licencePlateNumber"/>
                <input:input fieldName="drivingLicence" inputPlaceholder="driver.drivingLicenceNum"
                             errors="${requestScope.drivingLicence}"
                             inputPattern="([1-9]{1})([a-zA-Z]{2})(\s?)([0-9]{6})" patternTitle="error.wrong.format"/>
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